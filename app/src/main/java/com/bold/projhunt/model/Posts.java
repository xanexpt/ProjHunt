package com.bold.projhunt.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class Posts {

    @SerializedName("id")
    private String mId;

    @SerializedName("votes_count")
    private String mVotesCount;

    @SerializedName("comments_count")
    private String mComments_count;

    @SerializedName("name")
    private String mName;

    @SerializedName("tagline")
    private String mTagLine;

    @SerializedName("day")
    private String mDay;

    @SerializedName("created_at")
    private String mCreated_at;

    @SerializedName("redirect_url")
    private String mRedirect_url;

    @SerializedName("screenshot_url")
    private ImageUrl mScreenshotUrl;

    @SerializedName("user")
    private User mUser;

    @SerializedName("makers")
    private ArrayList<User> mMakers;

    public Posts() {
    }

    public Posts(String id, String votesCount, String comments_count, String name, String tagLine, String day,
                 String created_at, String redirect_url, ImageUrl screenshot,
                 User user, ArrayList<User> makers) {

        mId = id;
        mVotesCount = votesCount;
        mComments_count = comments_count;
        mName = name;
        mTagLine = tagLine;
        mDay = day;
        mCreated_at = created_at;
        mRedirect_url = redirect_url;
        mScreenshotUrl = screenshot;
        mUser = user;
        mMakers = makers;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getVotesCount() {
        return mVotesCount;
    }

    public void setVotesCount(String votesCount) {
        mVotesCount = votesCount;
    }

    public String getComments_count() {
        return mComments_count;
    }

    public void setComments_count(String comments_count) {
        mComments_count = comments_count;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTagLine() {
        return mTagLine;
    }

    public void setTagLine(String tagLine) {
        mTagLine = tagLine;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getCreated_at() {
        return mCreated_at;
    }

    public void setCreated_at(String created_at) {
        mCreated_at = created_at;
    }

    public String getRedirect_url() {
        return mRedirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        mRedirect_url = redirect_url;
    }

    public ImageUrl getScreenshotUrl() {
        return mScreenshotUrl;
    }

    public void setScreenshotUrl(ImageUrl screenshot_url_hdpi) {
        mScreenshotUrl = screenshot_url_hdpi;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public ArrayList<User> getMakers() {
        return mMakers;
    }

    public void setMakers(ArrayList<User> makers) {
        mMakers = makers;
    }
}
