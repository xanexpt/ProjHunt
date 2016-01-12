package com.bold.projhunt.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class Provider extends ContentProvider {

    private Database mDatabase;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int POST = 100;
    private static final int USER = 200;
    private static final int IMAGE_URL = 300;

    @Override
    public boolean onCreate() {
        mDatabase = new Database(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase database = mDatabase.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case POST:
                queryBuilder.setTables(Database.Tables.POSTS);
                break;

            case USER:
                queryBuilder.setTables(Database.Tables.USERS);
                break;

            case IMAGE_URL:
                queryBuilder.setTables(Database.Tables.IMAGE_URL);
                break;

            default:
                throw new UnsupportedOperationException("Unsupported uri: " + uri);
        }
        //db, projectionIn, selection, selectionArgs, groupBy, having, sortOrder,
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case POST:
                return Contract.PostType.CONTENT_TYPE;

            case USER:
                return Contract.UserType.CONTENT_TYPE;

            case IMAGE_URL:
                return Contract.ImageUrlType.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unsupported uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase database = mDatabase.getWritableDatabase();
        long insertedId;

        switch (match) {
            case POST:
                insertedId = database.insertWithOnConflict(Database.Tables.POSTS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                break;

            case USER:
                insertedId = database.insertWithOnConflict(Database.Tables.USERS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                break;

            case IMAGE_URL:
                insertedId = database.insertWithOnConflict(Database.Tables.IMAGE_URL, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                break;

            default:
                throw new IllegalArgumentException("Unsupported Uri");
        }

        getContext().getContentResolver().notifyChange(uri, null, true);
        return ContentUris.withAppendedId(uri, insertedId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase database = mDatabase.getWritableDatabase();
        int rowsDeleted;

        switch (match) {
            case POST:
                rowsDeleted = database.delete(Database.Tables.POSTS, "1", null);
                break;

            case USER:
                rowsDeleted = database.delete(Database.Tables.USERS, "1", null);
                break;

            case IMAGE_URL:
                rowsDeleted = database.delete(Database.Tables.IMAGE_URL, "1", null);
                break;


            default:
                throw new IllegalArgumentException("Unsupported Uri");
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(Contract.CONTENT_AUTHORITY, Contract.Paths.POST, POST);

        matcher.addURI(Contract.CONTENT_AUTHORITY, Contract.Paths.USER, USER);

        matcher.addURI(Contract.CONTENT_AUTHORITY, Contract.Paths.IMAGE_URL, IMAGE_URL);

        return matcher;
    }
}
