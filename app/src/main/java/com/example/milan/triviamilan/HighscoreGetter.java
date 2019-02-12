package com.example.milan.triviamilan;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HighscoreGetter implements Response.ErrorListener, Response.Listener<JSONArray> {

    Context context;
    Callback callback;

    public HighscoreGetter(Context context) {
        this.context = context;
    }

    // get high score from server
    public void getScores(Callback callback) {
        this.callback = callback;

        // url to server with the scores from the users
        String url = "https://ide50-milaneetje.legacy.cs50.io:8080/list";

        // request
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,
                this, this);
        queue.add(request);

    }

    @Override
    public void onResponse(JSONArray response) {

        // Arraylist for Highscore items
        ArrayList<Player> quest = new ArrayList<>();

        try {

            // each time pick a new object, with a username and a score from that username
            for (int i = 0; i < response.length(); i++) {

                // get JSON object from server at position i
                JSONObject json = response.getJSONObject(i);

                // get username and score from JSON file in url
                String Username = json.getString("username");
                Integer Score = json.getInt("scores");
                String Difficulty = json.getString("difficulty");

                // put username, difficulty and score in class
                Player p = new Player(Username, Score, Difficulty);

                // add class object to list
                quest.add(p);
            }

            //Sort objects in arraylist by score
            Collections.sort(quest, Collections.reverseOrder());

            for(int i = 0; i < quest.size(); i++) {
                System.out.println(quest.get(i).getScore());
            }

            callback.gotscores(quest);

            // if failed
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public interface Callback {
        void gotscores(ArrayList<Player> scores);
        void gotscoreserror(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callback.gotscoreserror(error.getMessage());
    }


}
