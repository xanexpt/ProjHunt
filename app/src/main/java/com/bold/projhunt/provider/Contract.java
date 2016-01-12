package com.bold.projhunt.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import com.bold.projhunt.Constants;
import com.bold.projhunt.model.ImageUrl;
import com.bold.projhunt.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class Contract {

    public static final String CONTENT_AUTHORITY = Constants.PACKAGE_NAME;

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String URI_MIME_TYPE_BASE = "/vnd.proj_hunt_bold";

    public interface Paths {
        public static final String POST = "post";
        public static final String USER  = "user";
        public static final String IMAGE_URL  = "image_url";
    }

    public interface PostTypeColumns {
        //public static final String POST_SERVER_ID = "post_server_id";
        public static final String POST_VOTES_COUNT = "post_votes_count";
        public static final String POST_COMMENT_COUNT = "post_comment_count";
        public static final String POST_NAME = "post_name";
        public static final String POST_TAG_LINE = "post_tag_line";
        public static final String POST_DAY = "post_day";
        public static final String POST_CREATED_AT = "post_created_at";
        public static final String POST_REDIRECT_URL = "post_redirect_url";
        public static final String POST_SCREEN_SHOT_URL = "post_screen_shot_url";
        public static final String POST_USER = "post_user";
        public static final String POST_MAKERS = "post_makers";
    }

    public interface UserTypeColumns {
        //public static final String USER_SERVER_ID = "server_id";
        public static final String USER_NAME = "name";
        public static final String USER_HEADLINE = "headline";
        public static final String USER_USERNAME = "username";
        public static final String USER_WEBSITE_URL = "website_url";
        public static final String USER_IMAGE_URL = "image_url";
        public static final String USER_PROFILE_URL = "profile_url";
    }

    public interface ImageUrlTypeColumns {
        //public static final String IMAGE_ID = "image_id";
        public static final String IMAGE_URL_SMALL = "image_url_small";
        public static final String IMAGE_URL_MEDIUM = "image_url_medium";
        public static final String IMAGE_URL_LARGE = "image_url_large";
        public static final String IMAGE_URL_SCREEN_SHOT_SMALL = "image_url_screen_shot_small";
        public static final String IMAGE_URL_SCREEN_SHOT_BIG = "image_url_screen_shot_big";
    }

    public static abstract class PostType implements BaseColumns, PostTypeColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(Paths.POST).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + URI_MIME_TYPE_BASE + ".PostType";
    }

    public static abstract class UserType implements BaseColumns, UserTypeColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(Paths.USER).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + URI_MIME_TYPE_BASE + ".UserType";
    }
    public static abstract class ImageUrlType implements BaseColumns, ImageUrlTypeColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(Paths.IMAGE_URL).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + URI_MIME_TYPE_BASE + ".ImageUrlType";
    }

}
