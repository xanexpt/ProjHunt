package com.bold.projhunt.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bold.projhunt.Constants;
import com.bold.projhunt.R;
import com.bold.projhunt.application.VolleyApplication;
import com.bold.projhunt.provider.Contract;
import com.bold.projhunt.utils.LruBitmapCache;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class ItemGenericPostsListView extends CursorAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ItemGenericPostsListView(Context context) {
        super(context, null, false);
        mLayoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View view = mLayoutInflater.inflate(R.layout.adapter_generic_post_item, parent, false);

        viewHolder.likeCounterTextView = (TextView) view.findViewById(R.id.like_counter_number);
        viewHolder.postImageView = (ImageView) view.findViewById(R.id.imageview_post);
        viewHolder.postTitleTextView = (TextView) view.findViewById(R.id.post_title);
        viewHolder.postDescriptionTextView = (TextView) view.findViewById(R.id.post_description);
        viewHolder.userImageView = (NetworkImageView) view.findViewById(R.id.imageview_user_icon);
        viewHolder.commentsCounterTextView = (TextView) view.findViewById(R.id.comments_counter);

        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (cursor.getPosition() % 2 == 0) {
            view.setBackgroundColor(context.getResources().getColor(R.color.list_view_grey));
        } else {
            view.setBackgroundColor(context.getResources().getColor(R.color.list_view_white));
        }


        viewHolder.likeCounterTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.PostType.POST_VOTES_COUNT)));
        //viewHolder.postImageView.setText(cursor.getString(cursor.getColumnIndex(Contract.GanhadoresType.GANHADORES_DATA)));
        viewHolder.postTitleTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.PostType.POST_NAME)));
        viewHolder.postTitleTextView.setTag(cursor.getString(cursor.getColumnIndex(Contract.PostType._ID)));

        viewHolder.postDescriptionTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.PostType.POST_TAG_LINE)));
        //viewHolder.userImageView.setText(cursor.getString(cursor.getColumnIndex(Contract.GanhadoresType.GANHADORES_NOME)));
        viewHolder.commentsCounterTextView.setText(cursor.getString(cursor.getColumnIndex(Contract.PostType.POST_COMMENT_COUNT)));

        String whereClause = BaseColumns._ID + " = " + cursor.getString(cursor.getColumnIndex(Contract.PostType.POST_USER));
        Cursor postImagesCursor = mContext.getContentResolver().query(
                Contract.ImageUrlType.CONTENT_URI, null, whereClause, null, null);

        if(postImagesCursor.moveToFirst()) {
            String imageUrl = postImagesCursor.getString(postImagesCursor.getColumnIndex(Contract.ImageUrlType.IMAGE_URL_SMALL));
            ImageLoader mImageLoader = new ImageLoader(VolleyApplication.getInstance().getRequestQueue(),
                    new LruBitmapCache(LruBitmapCache.getCacheSize(mContext)));

            viewHolder.userImageView.setImageUrl(imageUrl, mImageLoader);
        } else {

        }

    }

    private static class ViewHolder {
        private TextView likeCounterTextView;
        private ImageView postImageView;
        private TextView postTitleTextView;
        private TextView postDescriptionTextView;
        private NetworkImageView userImageView;
        private TextView commentsCounterTextView;
    }
}
