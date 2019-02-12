package com.example.milan.triviamilan;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class HighscoreSetter {

    Context context;

    public HighscoreSetter(Context context) {
        this.context = context;
    }

    public void setScore(final String username, final int score , final String difficulty){

        RequestQueue queue = Volley.newRequestQueue(this.context);

        // url where we want to post the username and score
        String urlHighscore = "https://ide50-milaneetje.legacy.cs50.io:8080/list";

        // new string request
        StringRequest pastescore = new StringRequest(Request.Method.POST, urlHighscore,
                null, new Response.ErrorListener() {

            // if post failed
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            // create new hashmap
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("scores", String.valueOf(score));
                params.put("difficulty", difficulty);

                return  params;
            }
        };

        queue.add(pastescore);

    }

}
