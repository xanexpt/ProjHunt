package com.bold.projhunt.fragment;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bold.projhunt.BaseActivity;
import com.bold.projhunt.Constants;
import com.bold.projhunt.R;
import com.bold.projhunt.application.VolleyApplication;
import com.bold.projhunt.provider.Contract;
import com.bold.projhunt.utils.LruBitmapCache;

/**
 * Created by bruno.marques on 20/07/2015.
 */
public class PostDetailsFragment extends Fragment {

    private static final String POST_ID = Constants.PACKAGE_NAME + ".extra.POST_ID";

    public static PostDetailsFragment newInstance(String postId) {
        Bundle arguments = new Bundle();

        arguments.putString(POST_ID, postId);

        PostDetailsFragment fragment = new PostDetailsFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_generic_post, container, false);
        String postId = "vazio";

        Bundle arguments = getArguments();
        if (arguments != null) {
            postId = arguments.getString(POST_ID);
        }

        String whereClause = Contract.PostType._ID + " LIKE '%" + postId + "%'";;
        Cursor postCursor = getActivity().getContentResolver().query(
                Contract.PostType.CONTENT_URI, null, whereClause, null, null);

        String whereClauseImage = BaseColumns._ID + " = " + Constants.DATABASE_HELPER_POSTS_TAG + "" + postId;

        Cursor postImagesCursor = getActivity().getContentResolver().query(
                Contract.ImageUrlType.CONTENT_URI, null, whereClauseImage, null, null);

        if(postCursor.moveToFirst()){
            if (postImagesCursor.moveToFirst()) {
                setActionBarMods(postCursor.getString(postCursor.getColumnIndex(Contract.PostType.POST_NAME)));

                String imageUrl = postImagesCursor.getString(postImagesCursor.getColumnIndex(Contract.ImageUrlType.IMAGE_URL_SCREEN_SHOT_BIG));
                ImageLoader mImageLoader = new ImageLoader(VolleyApplication.getInstance().getRequestQueue(),
                        new LruBitmapCache(LruBitmapCache.getCacheSize(getActivity())));

                NetworkImageView mNetworkImageView = (NetworkImageView) rootView.findViewById(R.id.networkImageView);

                mNetworkImageView.setImageUrl(imageUrl, mImageLoader);

            } else {

            }
        } else {
            //TODO erro
            setActionBarMods(postId);
        }

        return rootView;
    }


    public void setActionBarMods(String title){
        Toolbar toolbar = ((BaseActivity)getActivity()).getActionBarToolbar();
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();

                Toolbar toolbar = ((BaseActivity) getActivity()).getActionBarToolbar();
                toolbar.setNavigationIcon(R.drawable.ic_bold);
                toolbar.setTitle(getActivity().getResources().getString(R.string.app_name));
                toolbar.setOnClickListener(null);
            }
        });
    }
}
