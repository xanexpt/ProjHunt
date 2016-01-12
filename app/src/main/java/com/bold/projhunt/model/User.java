package com.bold.projhunt.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class User {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("headline")
    private String mHeadline;

    @SerializedName("username")
    private String mUsername;

    @SerializedName("website_url")
    private String mWebsiteUrl;

    @SerializedName("image_url")
    private ImageUrl mImageUrl;

    @SerializedName("profile_url")
    private String mProfileUrl;

    public User(){
    }

    public User(String id, String name, String headline, String username, String websiteUrl, ImageUrl imageUrl, String profileUrl){
        mId = id;
        mName = name;
        mHeadline = headline;
        mUsername = username;
        mWebsiteUrl = websiteUrl;
        mImageUrl = imageUrl;
        mProfileUrl = profileUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public void setHeadline(String headline) {
        mHeadline = headline;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getWebsiteUrl() {
        return mWebsiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        mWebsiteUrl = websiteUrl;
    }

    public ImageUrl getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(ImageUrl imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getProfileUrl() {
        return mProfileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        mProfileUrl = profileUrl;
    }

}
