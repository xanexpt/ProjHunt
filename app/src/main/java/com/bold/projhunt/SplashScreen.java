package com.bold.projhunt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bold.projhunt.request.ServerApi;
import com.bold.projhunt.request.ServerRequest;
import com.bold.projhunt.utils.NetworkUtils;
import com.bold.projhunt.utils.PreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeoutException;


public class SplashScreen extends Activity {

    private final int splashTime = 1000;

    private TextView mTextViewHelper;
    private Button mRetryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mTextViewHelper = (TextView) findViewById(R.id.splashHelperTextView);
        mRetryButton = (Button) findViewById(R.id.retry_button);

        begginTransactions();
    }

    public void begginTransactions(){
        //if(isTokenValid()) {
        mTextViewHelper.setText(getResources().getText(R.string.working_status));
        mRetryButton.setVisibility(View.GONE);
        getToken();
        // }
    }

    public void getToken(){

        ServerRequest sr = new ServerRequest(this, new ServerRequest.OnServerRequestListener() {
            @Override
            public void serverRequestEndedWithError(String error) {
                //TODO
                Log.e("TAG DEV", "serverRequestEndedWithError String");
                mTextViewHelper.setText(getResources().getText(R.string.error_no_internet));
                mRetryButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void serverRequestEndedWithError(VolleyError  error) {
                //TODO
                Log.e("TAG DEV", "serverRequestEndedWithError VolleyError");
                splashTimer();
            }

            @Override
            public void serverRequestEndedWithSuccess(String response) {
                //TODO
            }

            @Override
            public void serverRequestEndedWithSuccess(JSONObject response) {
                try {
                    PreferencesUtils.setClientToken(getApplication(), response.getString(ServerApi.PARAMETER_ACCESS_TOKEN));
                    PreferencesUtils.setTokenExpiration(getApplication(), response.getString(ServerApi.PARAMETER_EXPIRES_IN));
                    splashTimer();

                } catch (JSONException e) {
                    e.printStackTrace();
                    mTextViewHelper.setText(e.toString());
                }
            }
        });

        sr.executeServerTokenRequest();
    }

    public void onRetryClick(View v) {
        v.setVisibility(View.INVISIBLE);
        begginTransactions();
    }

    private void splashTimer(){
        Log.e("TAG DEV" , "splashTimer");
        Thread loading = new Thread() {
            public void run() {
                try {
                    sleep(splashTime);
                    Intent main = new Intent(SplashScreen.this, BaseActivity.class);
                    startActivity(main);
                }

                catch (Exception e) {
                    e.printStackTrace();
                }

                finally {
                    finish();
                }
            }
        };

        loading.start();
    }
}
