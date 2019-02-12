package com.example.milan.triviamilan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoreAdapter extends ArrayAdapter<Player> {

    Context context;
    private ArrayList<Player> scores;

    public HighscoreAdapter(@NonNull Context context, int resource, ArrayList<Player> objects) {
        super(context, resource, objects);
        this.context = context;
        scores = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.highscore_layout, parent, false);

        }

        // get item highscore at view position
        Player Highscore = getItem(position);

        // find textvieuws username and score
        TextView username  = convertView.findViewById(R.id.HighscoreName);
        TextView score = convertView.findViewById(R.id.HighscoreScore);

        // set username and text
        score.setText(Highscore.getScore().toString());
        username.setText(Highscore.getName());

        return convertView;
    }
}
