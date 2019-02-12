package com.example.milan.triviamilan;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HighscoreActivity extends AppCompatActivity implements HighscoreGetter.Callback  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        Intent intent = getIntent();
        HighscoreGetter getter = new HighscoreGetter(this);

        // get scores from user
        getter.getScores(this);
    }

    @Override
    public void gotscores(ArrayList<Player> scores) {

        System.out.println(scores.toString());
        // set listview with the scores

        HighscoreAdapter adapter = new HighscoreAdapter(this, R.layout.highscore_layout, scores);
        ListView scoreList = findViewById(R.id.highscoreList);
        scoreList.setAdapter(adapter);

    }


    // if getscore failed
    @Override
    public void gotscoreserror(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // if user click on back
    public void Start(View view) {

        // go to startactivity class
        Intent stopintent = new Intent(HighscoreActivity.this, MainActivity.class);
        startActivity(stopintent);

    }
}
