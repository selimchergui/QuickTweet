package fr.ecp.sio.superchat;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ecp.sio.superchat.model.Tweet;
import fr.ecp.sio.superchat.model.User;

/**
 * Created by Michaël on 12/12/2014.
 */
public class ApiClient {

    private static final String API_BASE = "http://hackndo.com:5667/mongo/";

    public String login(String handle, String password) throws IOException {
        String url = Uri.parse(API_BASE + "session").buildUpon()
                .appendQueryParameter("handle", handle)
                .appendQueryParameter("password", password)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Login: " + url);
        InputStream stream = new URL(url).openStream();
        return IOUtils.toString(stream);
    }

    public List<User> getUsers() throws IOException {
        InputStream stream = new URL(API_BASE + "users").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
    }


    public List<Tweet> getUserTweets(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + handle + "/tweets").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, Tweet[].class));
    }

    public void postTweet(String handle, String token, String content) throws IOException {
        String url = Uri.parse(API_BASE + handle + "/tweets/post/").buildUpon()
                .appendQueryParameter("content", content)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Ad tweet: " + url + " token :" + token + " handle:" + handle);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer-" + token);
        connection.getInputStream();

    }





    // Requête pour récuperer les followers de l'utilisateur principal
    public List<User> getUserFollowers(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + handle + "/followers").openStream();
        String response = IOUtils.toString(stream);
        Log.i("SELIM  : ",response);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
    }



    // Requête pour récuperer les utilsateurs suivis par l'utilisateur principal
    public List<User> getUserFollowings(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + handle + "/followings").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
    }


   //Requête pour récuperer une liste des handlers des followings
    // elle faite au début lors du chargement de la liste des users dans la première interface
    public List<String> getFollowingsHandles(String handle) throws IOException, JSONException {
        InputStream stream = new URL(API_BASE + handle + "/followings").openStream();
        String response = IOUtils.toString(stream);
        JSONArray jsResponse = null;
        try {
            jsResponse  = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<String> followList = new ArrayList<String>();
        for(int i = 0; i < jsResponse.length(); i++){
            followList.add(jsResponse.getJSONObject(i).getString("handle"));
        }
        return followList;
    }

// Requête pour suivre un autre utilisateur
    public void follow(String handle, String token,String userToFollow) throws IOException {
        String url = Uri.parse(API_BASE + handle + "/followings/post/").buildUpon()
                .appendQueryParameter("handle", userToFollow)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Ad tweet: " + url + " token :" + token + " handle:" + handle);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer-" + token);
        connection.getInputStream();

    }

// Requête pour désuivre un autre utilisateur
    public void unfollow(String handle, String token,String userToUnfollow) throws IOException {
        String url = Uri.parse(API_BASE + handle + "/followings/delete/").buildUpon()
                .appendQueryParameter("handle", userToUnfollow)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Ad tweet: " + url + " token :" + token + " handle:" + handle);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer-" + token);
        connection.getInputStream();

    }



}