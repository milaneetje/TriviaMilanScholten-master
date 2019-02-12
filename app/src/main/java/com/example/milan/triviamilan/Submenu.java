package com.example.milan.triviamilan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Submenu extends AppCompatActivity {

    Player currentPlayer;
    EditText Name;
    Spinner Difficulty;
    Integer Score =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);
    }


    public void CreatePlayer(View view) {

        //finds the textview with the name and the spinner selection of difficulty
        Name = findViewById(R.id.Name);
        Difficulty = findViewById(R.id.spinnerDifficulty);

        //Gets the filled in the name and the selected difficulty
        String nameString = Name.getText().toString();
        String difficultyString = Difficulty.getSelectedItem().toString();

        //creates a new player
        currentPlayer = new Player(nameString, Score, difficultyString);

        //pushes the player to the next activity
        Intent intent = new Intent(Submenu.this, QuestionActivity.class);
        intent.putExtra("current_Player", currentPlayer);
        startActivity(intent);
    }
}
