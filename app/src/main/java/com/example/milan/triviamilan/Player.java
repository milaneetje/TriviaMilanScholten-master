package com.example.milan.triviamilan;

import java.io.Serializable;

public class Player implements Serializable, Comparable < Player > {

    String Name;
    Integer Score;
    String Difficulty;

    public Player(String name, Integer score, String difficulty) {
        this.Name = name;
        this.Score = score;
        this.Difficulty = difficulty;
    }

    public String getName() {
        return Name;
    }

    public Integer getScore() {
        return Score;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public void setScore(Integer score) {
        this.Score = score;
    }

    @Override
    public int compareTo(Player player) {
        return this.getScore().compareTo(player.getScore());
    }
}
