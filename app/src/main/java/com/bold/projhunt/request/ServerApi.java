package com.bold.projhunt.request;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class ServerApi {

    public enum EndPoint {

        BASE_PATH("https://api.producthunt.com/v1"),

        GET_TOKEN(BASE_PATH, "/oauth/token"),
        GET_POSTS(BASE_PATH, "/posts");

        private final String mPath;

        EndPoint(EndPoint basePath, final String path) {
            mPath = basePath.get() + path;
        }

        EndPoint(final String path) {
            mPath = path;
        }

        public final String get() {
            return mPath;
        }

        public final String getWithDate(String date) {
            return mPath+ "?"+ServerApi.PARAMETER_DAY+"="+date;
        }

    }

    public static final String PARAMETER_CLIENT_ID = "client_id";
    public static final String PARAMETER_CLIENT_SECRET = "client_secret";
    public static final String PARAMETER_GRANT_TYPE = "grant_type";
    public static final String PARAMETER_CLIENT_CREDENTIALS = "client_credentials";

    public static final String PARAMETER_ACCESS_TOKEN = "access_token";
    public static final String PARAMETER_EXPIRES_IN = "expires_in";

    public static final String PARAMETER_DAYS_AGO = "days_ago";
    public static final String PARAMETER_DAY = "day";

    public static final String PARAMETER_ACCEPT = "Accept";
    public static final String PARAMETER_CONTENT_TYPE = "Content-Type";
    public static final String PARAMETER_AUTHORIZATION = "Authorization";
    public static final String PARAMETER_HOST = "Host";

    public static final String PARAMETER_VALUE_ACCEPT = "application/json";
    public static final String PARAMETER_VALUE_CONTENT_TYPE = "application/json";
    public static final String PARAMETER_VALUE_AUTHORIZATION = "Bearer ";
    public static final String PARAMETER_VALUE_HOST = "api.producthunt.com";


}
