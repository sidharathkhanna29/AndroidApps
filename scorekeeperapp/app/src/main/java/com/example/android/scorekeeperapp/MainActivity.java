package com.example.android.scorekeeperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int TeamA_score = 0 ;
    int TeamB_score = 0;

    public void addScore_A(View view) {
        TeamA_score = TeamA_score + 1;
        displayScore_teamA(TeamA_score);
    }

    public void subScore_A(View view) {
        TeamA_score = TeamA_score - 1;
        displayScore_teamA(TeamA_score);
    }

    public void addScore_B(View view) {
        TeamB_score = TeamB_score + 1;
        displayScore_teamB(TeamB_score);
    }

    public void subScore_B(View view) {
        TeamB_score = TeamB_score - 1;
        displayScore_teamB(TeamB_score);
    }

    public void resetvalues (View view) {
        TeamA_score = 0;
        TeamB_score = 0;
        displayScore_teamA(TeamA_score);
        displayScore_teamB(TeamB_score);
    }

    private void displayScore_teamA(int score) {
        TextView displayScore_teamA = (TextView) findViewById(R.id.score_A);
        displayScore_teamA.setText("" + score);
    }

    private void displayScore_teamB(int score) {
        TextView displayScore_teamB = (TextView) findViewById(R.id.score_B);
        displayScore_teamB.setText("" + score);
    }

}
