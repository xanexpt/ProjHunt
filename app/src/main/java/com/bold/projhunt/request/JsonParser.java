package com.bold.projhunt.request;

import com.bold.projhunt.model.Posts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno.marques on 12/07/2015.
 */
public class JsonParser {

    public static List<Posts> parsePosts(String json) {
        Type listType = new TypeToken<ArrayList<Posts>>(){}.getType();
        List<Posts> todayPosts = new Gson().fromJson(json, listType);
        return todayPosts;
    }
}
