package com.example.android.quizzycheese;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * Initializing all global Variables
     */
    String[] solutions;
    int counter;
    int points;
    boolean buttonStateA;
    boolean buttonStateB;
    boolean buttonStateC;
    boolean buttonStateD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Allocate starting Values for all global Variables
         */
        counter = 0;
        points = 0;
        buttonStateA = false;
        buttonStateB = false;
        buttonStateC = false;
        buttonStateD = false;

        /**
         * Get the Array from strings.xml with all Right Answers
         */
        solutions = getResources().getStringArray(R.array.solutions);
    }

    /**
     * Every Button starts the Calculation
     * Every Button pressed sets its global Value to true
     */
    public void buttonA(View view){
        buttonStateA = true;
        calculateMethod();
    }
    public void buttonB(View view){
        buttonStateB = true;
        calculateMethod();
    }
    public void buttonC(View view){
        buttonStateC = true;
        calculateMethod();
    }
    public void buttonD(View view){
        buttonStateD = true;
        calculateMethod();
    }

    public void calculateMethod() {
        //Log control in case of problems
        //String stateoftheart = "Counter: " + counter + " ButtonState: " + buttonStateA + buttonStateB + buttonStateC + buttonStateD + " Antwort: " + solutions[counter];
        //Log.i("MainActivity.java", stateoftheart);

        /**
         * Calculation for right/wrong answer including pointCounter that is only revealed on the EndScreen
         * Including Toast that tells the User whether he was right or wrong
         */
        if (counter > 0) {
            if (counter != 12) {
                ifElseCalculation();
            } else {

                ifElseCalculation();
                //saves final Points for 2nd Activity
                shareData();
                //opens 2nd Activity for Result
                Intent intent = new Intent(this, RadioButtonActivity.class);
                startActivity(intent);
            }

            /**
             * Set back all Buttons
             */
            buttonStateA = false;
            buttonStateB = false;
            buttonStateC = false;
            buttonStateD = false;

            /**
             * Set up all included Views for the next Question
             */
            nextQuestion();

        } else { //This else Statement is only for the first entry, when the User Starts the game and don't need any toast or calculation
            counter += 1;

            buttonStateA = false;
            buttonStateB = false;
            buttonStateC = false;
            buttonStateD = false;

            nextQuestion();
        }
    }

    public void nextQuestion() {
        /**
         * Initializing all Views
         */
        Button answerBtnA = (Button) findViewById(R.id.answerA);
        Button answerBtnB = (Button) findViewById(R.id.answerB);
        Button answerBtnC = (Button) findViewById(R.id.answerC);
        Button answerBtnD = (Button) findViewById(R.id.answerD);
        TextView question = (TextView) findViewById(R.id.question_tv);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        /**
         * Load all String-Arrays from string.xml
         */
        String[] questionString = getResources().getStringArray(R.array.question);
        String[] answerStringA = getResources().getStringArray(R.array.answerA);
        String[] answerStringB = getResources().getStringArray(R.array.answerB);
        String[] answerStringC = getResources().getStringArray(R.array.answerC);
        String[] answerStringD = getResources().getStringArray(R.array.answerD);

        /**
         * Set all Questions and Answers for the Question
         * depending on the actual Question counter
         */
        question.setText(questionString[counter]);
        answerBtnA.setText(answerStringA[counter]);
        answerBtnB.setText(answerStringB[counter]);
        answerBtnC.setText(answerStringC[counter]);
        answerBtnD.setText(answerStringD[counter]);

        /**
         * Same as for Questions and Answers but this time for the Images
         */
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_array);
        imageView.setImageResource(imgs.getResourceId(counter, -1));
        imgs.recycle();
    }

    public void rightAnswer(){
        points += 1;
        Toast.makeText(this,R.string.right,Toast.LENGTH_SHORT).show();
    }

    public void wrongAnswer(){
        Toast.makeText(this,R.string.wrong,Toast.LENGTH_SHORT).show();
    }

    /**
     * Method for sharing the score with the EndScreen.java
     */
    public void shareData(){
        // Create object of SharedPreferences.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //now get Editor
        SharedPreferences.Editor editor = sharedPref.edit();
        //put your value
        editor.putInt("score", points);

        //commits your edits
        editor.commit();
    }

    /**
     * Method for the If-Else Calculation, whether the User got the answer Right/Wrong
     * It was externalized because it is needed twice
     */
    public void ifElseCalculation(){
        if (buttonStateA){
            if (solutions[counter].equals("A")){
                rightAnswer();
            } else {
                wrongAnswer();
            }
        } else if (buttonStateB){
            if (solutions[counter].equals("B")){
                rightAnswer();
            } else {
                wrongAnswer();
            }
        } else if (buttonStateC){
            if (solutions[counter].equals("C")){
                rightAnswer();
            } else {
                wrongAnswer();
            }
        } else if (buttonStateD){
            if (solutions[counter].equals("D")){
                rightAnswer();
            } else {
                wrongAnswer();
            }
        } else {
            /**
             * Error: No Button has been pushed?
             */
        }

        counter += 1;
    }

}
