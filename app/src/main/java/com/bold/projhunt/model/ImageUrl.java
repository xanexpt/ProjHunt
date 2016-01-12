package com.bold.projhunt.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by bruno.marques on 12/07/2015.
 */
public class ImageUrl {

    @SerializedName("48px")
    private String mSmall;

    @SerializedName("146px")
    private String mMedium;

    @SerializedName("264px")
    private String mLarge;

    @SerializedName("original")
    private String mOriginal;

    @SerializedName("300px")
    private String mScreenShotSmall;

    @SerializedName("850px")
    private String mScreenShotBig;

    public ImageUrl() {

    }

    public ImageUrl(String small, String medium, String large, String original) {
        mSmall = small;
        mMedium = medium;
        mLarge = large;
        mOriginal = original;
    }


    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        mSmall = small;
    }

    public String getMedium() {
        return mMedium;
    }

    public void setMedium(String medium) {
        mMedium = medium;
    }

    public String getLarge() {
        return mLarge;
    }

    public void setLarge(String large) {
        mLarge = large;
    }

    public String getOriginal() {
        return mOriginal;
    }

    public void setOriginal(String original) {
        mOriginal = original;
    }

    public String getScreenShotSmall() {
        return mScreenShotSmall;
    }

    public void setScreenShotSmall(String screenShotSmall) {
        mScreenShotSmall = screenShotSmall;
    }

    public String getScreenShotBig() {
        return mScreenShotBig;
    }

    public void setScreenShotBig(String screenShotBig) {
        mScreenShotBig = screenShotBig;
    }
}
