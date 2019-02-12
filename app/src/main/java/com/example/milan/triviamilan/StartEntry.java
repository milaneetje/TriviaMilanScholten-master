package com.example.milan.triviamilan;

import java.io.Serializable;

public class StartEntry implements Serializable {

    private String Name;
    private int Score;

    public StartEntry(String Name, int Score) {
        Name = Name;
        Score = Score;
    }

    public String getName() {
        return Name;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
