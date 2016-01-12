package com.bold.projhunt;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bold.projhunt.fragment.GenericPostsFragment;
import com.bold.projhunt.fragment.PostDetailsFragment;
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
import java.util.List;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar actionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        actionBarToolbar = getActionBarToolbar();
        //actionBarToolbar.setNavigationIcon(R.drawable.menu);
        actionBarToolbar.setNavigationIcon(R.drawable.ic_bold);
        actionBarToolbar.setTitleTextColor(getResources().getColor(R.color.tool_box_text_colot));


        if (savedInstanceState == null) {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_TYPE);
            final String formattedDate = df.format(c.getTime());

            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(500);

            //chooseFromDay(formattedDate);
            StartPostsView();
        } else {

        }
    }

    public Toolbar getActionBarToolbar() {
        if (actionBarToolbar == null) {
            actionBarToolbar = (Toolbar) findViewById(R.id.tool_bar);
            if (actionBarToolbar != null) {
                setSupportActionBar(actionBarToolbar);
            }
        }
        return actionBarToolbar;
    }


    private void StartPostsView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        GenericPostsFragment frg = GenericPostsFragment.newInstance();
        ft.replace(R.id.fragment_container, frg, null);
        ft.addToBackStack(Constants.POSTS_FRAGMENT_TAG);

        ft.commit();
    }

    public void goPostDetails(String postID){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        PostDetailsFragment frg = PostDetailsFragment.newInstance(postID);
        ft.replace(R.id.fragment_container, frg, Constants.POST_FRAGMENT_TAG);
        ft.addToBackStack(Constants.POST_FRAGMENT_TAG);

        ft.commit();
    }

    /*
    private void chooseFromDay(String formattedDate){

        ServerRequest firstRequest = new ServerRequest(this, new ServerRequest.OnServerRequestListener() {
            @Override
            public void serverRequestEndedWithError(String error) {
            }

            @Override
            public void serverRequestEndedWithError(VolleyError error) {
            }

            @Override
            public void serverRequestEndedWithSuccess(String response) {
            }

            @Override
            public void serverRequestEndedWithSuccess(JSONObject response) {

                if(response != null && response.has("posts")) {

                    try {
                        JSONArray jA = response.getJSONArray("posts");
                        List<Posts> todayPost = JsonParser.parsePosts(jA.toString());

                        if(todayPost.size() > 0) {
                            for (Posts post : todayPost) {

                                ContentValues imageUrlTypeValues = new ContentValues();
                                imageUrlTypeValues.put(BaseColumns._ID, Constants.DATABASE_HELPER_POSTS_TAG + "" + post.getId());
                                imageUrlTypeValues.put(Contract.ImageUrlType.IMAGE_URL_SCREEN_SHOT_SMALL, post.getScreenshotUrl().getScreenShotSmall());
                                imageUrlTypeValues.put(Contract.ImageUrlType.IMAGE_URL_SCREEN_SHOT_BIG, post.getScreenshotUrl().getScreenShotBig());

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
                                    Uri userImagesUri = getContentResolver().insert(Contract.ImageUrlType.CONTENT_URI, imageUserUrlTypeValues);

                                    usersTypeValues.put(Contract.UserType.USER_IMAGE_URL, userImagesUri.toString());

                                    getContentResolver().insert(Contract.UserType.CONTENT_URI, usersTypeValues);
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

                                getContentResolver().insert(Contract.PostType.CONTENT_URI, postTypeValues);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    StartPostsView();
                }

            }
        });

        firstRequest.executeServerGetPostsRequest(formattedDate);
    }*/
}