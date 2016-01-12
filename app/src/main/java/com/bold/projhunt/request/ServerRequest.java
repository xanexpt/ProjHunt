package com.bold.projhunt.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bold.projhunt.application.VolleyApplication;
import com.bold.projhunt.Constants;
import com.bold.projhunt.utils.PreferencesUtils;
import com.bold.projhunt.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class ServerRequest {

    /**
     * Interface for server responses
     */
    public interface OnServerRequestListener {

        /**
         * Request ended with error, returning response in String error
         *
         * @param error
         */
        void serverRequestEndedWithError(String error);

        /**
         * Request ended with error, returning response in VolleyError error
         *
         * @param error
         */
        void serverRequestEndedWithError(VolleyError error);

        /**
         * Sucess response in JSONObject format
         *
         * @param response
         */
        void serverRequestEndedWithSuccess(String response);

        /**
         * Sucess response in JSONObject format
         *
         * @param jObject
         */
        void serverRequestEndedWithSuccess(JSONObject jObject);

    }

    private Context mcontext;
    private OnServerRequestListener mServerRequestListener;

    public ServerRequest(Context context, OnServerRequestListener serverRequestListener) {
        mcontext = context;
        mServerRequestListener = serverRequestListener;
    }

    /**
     * Server Request for Posts data
     *
     * @param formattedDate formatted date in YYYY-MM-DD format
     */
    public void executeServerGetPostsRequest(final String formattedDate){

        //TODO adicionar parametros pelo JsonObjectRequest
        //String url = ServerApi.EndPoint.GET_POSTS.get() + "?"+ServerApi.PARAMETER_DAY+"="+formattedDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ServerApi.EndPoint.GET_POSTS.getWithDate(formattedDate),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if(response != null) {
                            mServerRequestListener.serverRequestEndedWithSuccess(response);
                        } else {
                            mServerRequestListener.serverRequestEndedWithError("-1");
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mServerRequestListener.serverRequestEndedWithError(error);
                    }
                }

        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  params = new HashMap<String, String>();
                params.put(ServerApi.PARAMETER_ACCEPT, ServerApi.PARAMETER_VALUE_ACCEPT);
                params.put(ServerApi.PARAMETER_CONTENT_TYPE, ServerApi.PARAMETER_VALUE_CONTENT_TYPE);
                params.put(ServerApi.PARAMETER_AUTHORIZATION, ServerApi.PARAMETER_VALUE_AUTHORIZATION + PreferencesUtils.getClientToken(mcontext));
                params.put(ServerApi.PARAMETER_HOST, ServerApi.PARAMETER_VALUE_HOST);

                return params;
            }
            /*
            //problemas a adicionar parametros
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ServerApi.PARAMETER_DAYS_AGO, Utils.getDayDiference(formattedDate));
                return params;
            }
            */
            };

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }


    /**
     * Server Request for token data
     */
    public void executeServerTokenRequest(){

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(ServerApi.PARAMETER_CLIENT_ID, Constants.API_KEY);
            jsonBody.put(ServerApi.PARAMETER_CLIENT_SECRET, Constants.API_SECRET);
            jsonBody.put(ServerApi.PARAMETER_GRANT_TYPE, ServerApi.PARAMETER_CLIENT_CREDENTIALS);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(ServerApi.EndPoint.GET_TOKEN.get(), jsonBody,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if(response != null && response.has(ServerApi.PARAMETER_ACCESS_TOKEN)) {
                            mServerRequestListener.serverRequestEndedWithSuccess(response);
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mServerRequestListener.serverRequestEndedWithError(error);
                    }
                }
        );

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }


}
