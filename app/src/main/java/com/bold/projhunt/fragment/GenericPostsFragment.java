package com.bold.projhunt.fragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bold.projhunt.BaseActivity;
import com.bold.projhunt.Constants;
import com.bold.projhunt.R;
import com.bold.projhunt.adapters.ItemGenericPostsListView;
import com.bold.projhunt.model.Posts;
import com.bold.projhunt.model.User;
import com.bold.projhunt.provider.Contract;
import com.bold.projhunt.request.JsonParser;
import com.bold.projhunt.request.ServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class GenericPostsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private View rootView = null;
    private ListView mListView;
    private ItemGenericPostsListView mAdapter;

    private String formattedDate;
    private TextView noDataAvailable;

    private Button buttonChooser;
    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;

    private boolean downloadFirstTry = true;

    public static GenericPostsFragment newInstance() {
        return new GenericPostsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_generic_posts, container, false);

            if (savedInstanceState != null) {
                formattedDate = savedInstanceState.getString(Constants.POSTS_FRAGMENT_STATE_DAY);
            } else {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_TYPE);
                formattedDate = df.format(c.getTime());
            }

            mAdapter = new ItemGenericPostsListView(getActivity());
            mListView = (ListView) rootView.findViewById(R.id.listview_generic_post);
            mListView.setAdapter(mAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView titleWithPostId = (TextView) view.findViewById(R.id.post_title);
                    String postId = (String) titleWithPostId.getTag();
                    ((BaseActivity) getActivity()).goPostDetails(postId);
                }
            });

            myCalendar = Calendar.getInstance();
            date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            };

            buttonChooser = (Button) rootView.findViewById(R.id.button_date_choose);
            buttonChooser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new DatePickerDialog(getActivity(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            buttonChooser.setText(formattedDate);

            noDataAvailable = (TextView) rootView.findViewById(R.id.no_data_text_view);
        }

        return rootView;
    }


    private void updateLabel() {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_TYPE);
        String dateString = formatter.format(new Date(myCalendar.getTimeInMillis()));
        buttonChooser.setText(dateString);

        formattedDate = dateString;
        downloadFirstTry=true;
        getLoaderManager().destroyLoader(Constants.POSTS_CURSOR_ADAPTER_TOKEN);
        getLoaderManager().initLoader(Constants.POSTS_CURSOR_ADAPTER_TOKEN, null, this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(Constants.POSTS_CURSOR_ADAPTER_TOKEN, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case Constants.POSTS_CURSOR_ADAPTER_TOKEN:

                Loader<Cursor> cursor = new CursorLoader(
                        getActivity(),
                        Contract.PostType.CONTENT_URI,
                        null,
                        Contract.PostType.POST_DAY + "=?",
                        new String[] { formattedDate },
                        null);

                return cursor;

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case Constants.POSTS_CURSOR_ADAPTER_TOKEN:

                if (data.getCount() < 1) {
                    if(downloadFirstTry) {
                        chooseFromDay(formattedDate);
                    } else {

                    }
                    return;
                }
                noDataAvailable.setVisibility(View.GONE);

                mAdapter.swapCursor(data);
                break;

            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        switch (loader.getId()) {
            case Constants.POSTS_CURSOR_ADAPTER_TOKEN:
                mAdapter.swapCursor(null);
                break;

            default:
                break;
        }
    }


    private void chooseFromDay(final String formattedDate){

        ServerRequest firstRequest = new ServerRequest(getActivity(), new ServerRequest.OnServerRequestListener() {
            @Override
            public void serverRequestEndedWithError(String error) {
                noDataAvailable.setVisibility(View.VISIBLE);
            }

            @Override
            public void serverRequestEndedWithError(VolleyError error) {
                noDataAvailable.setVisibility(View.VISIBLE);
            }

            @Override
            public void serverRequestEndedWithSuccess(String response) {
                noDataAvailable.setVisibility(View.GONE);

            }

            @Override
            public void serverRequestEndedWithSuccess(JSONObject response) {

                if(response != null && response.has("posts")) {
                    noDataAvailable.setVisibility(View.GONE);

                    try {
                        JSONArray jA = response.getJSONArray("posts");
                        List<Posts> postList = JsonParser.parsePosts(jA.toString());

                        if(postList.size() > 0) {
                            for (Posts post : postList) {


                                ContentValues imageUrlTypeValues = new ContentValues();
                                imageUrlTypeValues.put(BaseColumns._ID, Constants.DATABASE_HELPER_POSTS_TAG + "" + post.getId());
                                imageUrlTypeValues.put(Contract.ImageUrlType.IMAGE_URL_SCREEN_SHOT_SMALL, post.getScreenshotUrl().getScreenShotSmall());
                                imageUrlTypeValues.put(Contract.ImageUrlType.IMAGE_URL_SCREEN_SHOT_BIG, post.getScreenshotUrl().getScreenShotBig());
                                getActivity().getContentResolver().insert(Contract.ImageUrlType.CONTENT_URI, imageUrlTypeValues);

                                for (int i = 0; i <= post.getMakers().size(); i++) {
                                    User user;

                                    //if its the last item of the for cicle, we use the user instead
                                    if (i != post.getMakers().size()) {
                                        user = post.getMakers().get(i);
                                    } else {
                                        user = post.getUser();
                                    }

                                    ContentValues usersTypeValues = new ContentValues();
                                    usersTypeValues.put(BaseColumns._ID, user.getId());
                                    usersTypeValues.put(Contract.UserType.USER_NAME, user.getName());
                                    usersTypeValues.put(Contract.UserType.USER_HEADLINE, user.getHeadline());
                                    usersTypeValues.put(Contract.UserType.USER_USERNAME, user.getUsername());
                                    usersTypeValues.put(Contract.UserType.USER_WEBSITE_URL, user.getWebsiteUrl());
                                    usersTypeValues.put(Contract.UserType.USER_PROFILE_URL, user.getProfileUrl());
                                    usersTypeValues.put(Contract.UserType.USER_IMAGE_URL, user.getProfileUrl());

                                    ContentValues imageUserUrlTypeValues = new ContentValues();
                                    imageUserUrlTypeValues.put(BaseColumns._ID, Constants.DATABASE_HELPER_USERS_TAG + "" + user.getId());
                                    imageUserUrlTypeValues.put(Contract.ImageUrlType.IMAGE_URL_SMALL, user.getImageUrl().getSmall());
                                    imageUserUrlTypeValues.put(Contract.ImageUrlType.IMAGE_URL_MEDIUM, user.getImageUrl().getMedium());
                                    imageUserUrlTypeValues.put(Contract.ImageUrlType.IMAGE_URL_LARGE, user.getImageUrl().getLarge());
                                    Uri userImagesUri = getActivity().getContentResolver().insert(Contract.ImageUrlType.CONTENT_URI, imageUserUrlTypeValues);

                                    usersTypeValues.put(Contract.UserType.USER_IMAGE_URL, userImagesUri.toString());

                                    getActivity().getContentResolver().insert(Contract.UserType.CONTENT_URI, usersTypeValues);
                                }

                                ContentValues postTypeValues = new ContentValues();
                                postTypeValues.put(BaseColumns._ID, post.getId());
                                postTypeValues.put(Contract.PostType.POST_VOTES_COUNT, post.getVotesCount());
                                postTypeValues.put(Contract.PostType.POST_COMMENT_COUNT, post.getComments_count());
                                postTypeValues.put(Contract.PostType.POST_NAME, post.getName());
                                postTypeValues.put(Contract.PostType.POST_TAG_LINE, post.getTagLine());
                                postTypeValues.put(Contract.PostType.POST_DAY, post.getDay());
                                postTypeValues.put(Contract.PostType.POST_CREATED_AT, post.getCreated_at());
                                postTypeValues.put(Contract.PostType.POST_REDIRECT_URL, post.getRedirect_url());
                                postTypeValues.put(Contract.PostType.POST_SCREEN_SHOT_URL, post.getId());
                                postTypeValues.put(Contract.PostType.POST_USER, Constants.DATABASE_HELPER_USERS_TAG + "" + post.getUser().getId());

                                String formateIds = "";
                                for (int j = 0; j < post.getMakers().size(); j++) {
                                    formateIds = formateIds + post.getMakers().get(j).getId() + ",";
                                }

                                if (!formateIds.isEmpty()) {
                                    formateIds = formateIds.substring(0, formateIds.length() - 1);
                                    postTypeValues.put(Contract.PostType.POST_MAKERS, formateIds);
                                }

                                getActivity().getContentResolver().insert(Contract.PostType.CONTENT_URI, postTypeValues);

                            }
                        } else {
                            //TODO
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String whereClause = Contract.PostType.POST_DAY + " LIKE '%" + formattedDate + "%'";;
                    Cursor cur = getActivity().getContentResolver().query(
                            Contract.PostType.CONTENT_URI, null, whereClause, null, null);

                    if(mAdapter != null) {
                        mAdapter.changeCursor(cur);
                    }
                }
            }
        });
        downloadFirstTry = false;
        firstRequest.executeServerGetPostsRequest(formattedDate);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.POSTS_FRAGMENT_STATE_DAY, buttonChooser.getText().toString());
    }

}
