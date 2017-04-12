package com.example.android.quizzycheese;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RadioButtonActivity extends AppCompatActivity {

    /**
     * Initializing all global Variables
     */
    String[] solutions;
    int counter;
    int points;
    boolean checkedA;
    boolean checkedB;
    boolean checkedC;
    boolean checkedD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button);

        /**
         * Allocate starting Values for all global Variables
         */
        counter = 13;
        points = 0;
        checkedA = false;
        checkedB = false;
        checkedC = false;
        checkedD = false;

        /**
         * Get the Array from strings.xml with all Right Answers
         */
        solutions = getResources().getStringArray(R.array.solutions);
        nextQuestion();
    }

    public void onNextQuestionButton(View view){
        RadioButton answerBtnA = (RadioButton) findViewById(R.id.radio_button_1);
        RadioButton answerBtnB = (RadioButton) findViewById(R.id.radio_button_2);
        RadioButton answerBtnC = (RadioButton) findViewById(R.id.radio_button_3);
        RadioButton answerBtnD = (RadioButton) findViewById(R.id.radio_button_4);

        checkedA = answerBtnA.isChecked();
        checkedB = answerBtnB.isChecked();
        checkedC = answerBtnC.isChecked();
        checkedD = answerBtnD.isChecked();

        if (checkedA | checkedB | checkedC | checkedD){
            calculateMethod();
        } else {
            Toast.makeText(this,R.string.noAnswer,Toast.LENGTH_SHORT).show();
        }
    }

    public void calculateMethod() {
        //Log control in case of problems
        //String stateoftheart = "Counter: " + counter + " ButtonState: " + buttonStateA + buttonStateB + buttonStateC + buttonStateD + " Antwort: " + solutions[counter];
        //Log.i("MainActivity.java", stateoftheart);

        /**
         * Calculation for right/wrong answer including pointCounter that is only revealed on the EndScreen
         * Including Toast that tells the User whether he was right or wrong
         */

        if (counter != 14) {
            ifElseCalculation();
        } else {
            ifElseCalculation();

            //saves final Points for 2nd Activity
            shareData();
            //opens 2nd Activity for Result
            Intent intent = new Intent(this, CheckBoxActivity.class);
            startActivity(intent);
        }

        /**
         * Set back all Buttons
         */
        checkedA = false;
        checkedB = false;
        checkedC = false;
        checkedD = false;

        /**
         * Set up all included Views for the next Question
         */
        nextQuestion();

    }

    public void nextQuestion() {
        /**
         * Initializing all Views
         */
        RadioButton answerBtnA = (RadioButton) findViewById(R.id.radio_button_1);
        RadioButton answerBtnB = (RadioButton) findViewById(R.id.radio_button_2);
        RadioButton answerBtnC = (RadioButton) findViewById(R.id.radio_button_3);
        RadioButton answerBtnD = (RadioButton) findViewById(R.id.radio_button_4);
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
         * Set back all Radio Buttons to false
         */
        answerBtnA.setChecked(false);
        answerBtnB.setChecked(false);
        answerBtnC.setChecked(false);
        answerBtnD.setChecked(false);

        /**
         * Same as for Questions and Answers but this time for the Images
         */
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_array);
        imageView.setImageResource(imgs.getResourceId(counter, -1));
        imgs.recycle();
    }

    public void shareData(){
        // Create object of SharedPreferences.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //now get Editor
        SharedPreferences.Editor editor = sharedPref.edit();
        //put your value
        editor.putInt("score2", points);

        //commits your edits
        editor.commit();
    }

    public void ifElseCalculation(){
        if (checkedA){ //What Answer has been chosen
            if (solutions[counter].equals("A")){ //is the answer the right one?
                rightAnswer();
            } else {
                wrongAnswer();
            }
        } else if (checkedB){
            if (solutions[counter].equals("B")){
                rightAnswer();
            } else {
                wrongAnswer();
            }
        } else if (checkedC){
            if (solutions[counter].equals("C")){
                rightAnswer();
            } else {
                wrongAnswer();
            }
        } else if (checkedD){
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

    public void rightAnswer(){
        points += 1;
        Toast.makeText(this,R.string.right,Toast.LENGTH_SHORT).show();
    }

    public void wrongAnswer(){
        Toast.makeText(this,R.string.wrong,Toast.LENGTH_SHORT).show();
    }

}


