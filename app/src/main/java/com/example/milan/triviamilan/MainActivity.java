package com.example.milan.triviamilan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Starts a new activity when the start button is pressed
    public void Start(View view) {
        Intent intent = new Intent(this, Submenu.class);
        startActivity(intent);
    }
}
