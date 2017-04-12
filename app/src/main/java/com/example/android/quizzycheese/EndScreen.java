package com.example.android.quizzycheese;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static com.example.android.quizzycheese.R.id.score1;
import static com.example.android.quizzycheese.R.id.score2;
import static com.example.android.quizzycheese.R.id.score3;
import static com.example.android.quizzycheese.R.id.score4;
import static com.example.android.quizzycheese.R.id.score5;

public class EndScreen extends AppCompatActivity {

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        //get the score from the Button questions with a SharedPreference
        SharedPreferences sharedPref1 = PreferenceManager.getDefaultSharedPreferences(this);
        int score1 = sharedPref1.getInt("score", 0); // 0 is default if no number is fetched
        int score2 = sharedPref1.getInt("score2", 0);
        int score3 = sharedPref1.getInt("score3", 0);
        int score4 = sharedPref1.getInt("score4", 0);
        userName = sharedPref1.getString("userName", "User");
        int totalScore = score1 + score2 + score3 + score4;

        //output score and tell user his performance in a pre defined TextView
        scoreOutput(score1, score2, score3, score4, totalScore);
        String userPerformance = performance((totalScore));
        TextView textView = (TextView) findViewById(R.id.conclusion);
        textView.setText(userPerformance);
    }

    /**
     * Calculating the performance of the User and make a comment on his number of points
     * @param points user score
     * @return user performance description
     */
    public String performance(int points){
        if (points == 18){
            return userName + " is a real Traveler between the Stars!";
        } else if (points >= 14){
            return userName + " is well known beyond Earth!";
        } else if (points >=11) {
            return userName + "s hands reaching towards the Stars!";
        } else if (points >=8) {
            return userName + ", that was more a basic but down-to-earth performance.";
        }else if (points >=5) {
            return "You need more Training " + userName + ". Try again!";
        } else {
            return "That was to hard for you? Give it another Try, " + userName + "!";
        }
    }

    /**
     * Display Points on Screen
     * @param points1 Button score
     * @param points2 Radio Button score
     * @param points3 Checkbox Button score
     * @param points4 EditText score
     * @param totalScore Total user score
     */
    public void scoreOutput(int points1,int points2, int points3, int points4, int totalScore){
        TextView textView1 = (TextView) findViewById(score1);
        TextView textView2 = (TextView) findViewById(score2);
        TextView textView3 = (TextView) findViewById(score3);
        TextView textView4 = (TextView) findViewById(score4);
        TextView textView5 = (TextView) findViewById(score5);
        String solution1 = points1 + " of 12 Points";
        String solution2 = points2 + " of 2 Points";
        String solution3 = points3 + " of 2 Points";
        String solution4 = points4 + " of 2 Points";
        String solution5 = totalScore + " Points";
        textView1.setText(solution1);
        textView2.setText(solution2);
        textView3.setText(solution3);
        textView4.setText(solution4);
        textView5.setText(solution5);
    }

    /**
     * Return Button for Restarting the Quiz
     */
    public void returnButton(View view){
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }
}
