package com.bold.projhunt.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.bold.projhunt.model.ImageUrl;
import com.bold.projhunt.model.User;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class Database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "proj_hunt_bold.db";

    public interface Tables {
        String USERS = "users";
        String POSTS  = "posts";
        String IMAGE_URL  = "image_url";
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL("CREATE TABLE " + Tables.POSTS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + Contract.PostTypeColumns.POST_VOTES_COUNT + " TEXT,"
                + Contract.PostTypeColumns.POST_COMMENT_COUNT + " TEXT,"
                + Contract.PostTypeColumns.POST_NAME + " TEXT,"
                + Contract.PostTypeColumns.POST_TAG_LINE + " TEXT,"
                + Contract.PostTypeColumns.POST_DAY + " TEXT,"
                + Contract.PostTypeColumns.POST_CREATED_AT + " TEXT,"
                + Contract.PostTypeColumns.POST_REDIRECT_URL + " TEXT,"
                + Contract.PostTypeColumns.POST_SCREEN_SHOT_URL + " TEXT,"
                + Contract.PostTypeColumns.POST_USER + " TEXT,"
                + Contract.PostTypeColumns.POST_MAKERS + " DATE)");

        database.execSQL("CREATE TABLE " + Tables.USERS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + Contract.UserTypeColumns.USER_NAME + " TEXT,"
                + Contract.UserTypeColumns.USER_HEADLINE + " TEXT,"
                + Contract.UserTypeColumns.USER_USERNAME + " TEXT,"
                + Contract.UserTypeColumns.USER_WEBSITE_URL + " TEXT,"
                + Contract.UserTypeColumns.USER_IMAGE_URL + " TEXT," //TODO
                + Contract.UserTypeColumns.USER_PROFILE_URL + " TEXT)");

        database.execSQL("CREATE TABLE " + Tables.IMAGE_URL + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + Contract.ImageUrlTypeColumns.IMAGE_URL_SMALL + " TEXT,"
                + Contract.ImageUrlTypeColumns.IMAGE_URL_MEDIUM + " TEXT,"
                + Contract.ImageUrlTypeColumns.IMAGE_URL_LARGE + " TEXT,"
                + Contract.ImageUrlTypeColumns.IMAGE_URL_SCREEN_SHOT_SMALL + " TEXT,"
                + Contract.ImageUrlTypeColumns.IMAGE_URL_SCREEN_SHOT_BIG + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
