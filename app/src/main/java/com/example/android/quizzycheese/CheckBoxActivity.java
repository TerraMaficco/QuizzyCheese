package com.example.android.quizzycheese;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckBoxActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_check_box);

        /**
         * Allocate starting Values for all global Variables
         */
        counter = 15;
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

    public void onNextQuestionButton(View view) {
        /**
         * Get all Checkbox Button Views and afterwards fill them with the UserInput
         */
        CheckBox answerBtnA = (CheckBox) findViewById(R.id.checkbox_button_1);
        CheckBox answerBtnB = (CheckBox) findViewById(R.id.checkbox_button_2);
        CheckBox answerBtnC = (CheckBox) findViewById(R.id.checkbox_button_3);
        CheckBox answerBtnD = (CheckBox) findViewById(R.id.checkbox_button_4);

        checkedA = answerBtnA.isChecked();
        checkedB = answerBtnB.isChecked();
        checkedC = answerBtnC.isChecked();
        checkedD = answerBtnD.isChecked();

        /**
         * Check whether the User inserted the right number of answers (always 2) and
         * request them, if they had not been given
         */
        if (checkedA & checkedB | checkedA & checkedC | checkedA & checkedD | checkedB & checkedC |
                checkedB & checkedD | checkedC & checkedD){
            calculateMethod();
        } else if(checkedA | checkedB | checkedC | checkedD){
            Toast.makeText(this,R.string.noAnswer2,Toast.LENGTH_SHORT).show();
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

        if (counter != 16) {
            ifElseCalculation();
        } else {
            ifElseCalculation();

            //saves final Points for 2nd Activity
            shareData();
            //opens 2nd Activity for Result
            Intent intent = new Intent(this, EditTextActivity.class);
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
        CheckBox answerBtnA = (CheckBox) findViewById(R.id.checkbox_button_1);
        CheckBox answerBtnB = (CheckBox) findViewById(R.id.checkbox_button_2);
        CheckBox answerBtnC = (CheckBox) findViewById(R.id.checkbox_button_3);
        CheckBox answerBtnD = (CheckBox) findViewById(R.id.checkbox_button_4);
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
        editor.putInt("score3", points);

        //commits your edits
        editor.commit();
    }

    public void ifElseCalculation(){

        //get AnswerArray and Split into single right answers
        String[] separated = solutions[counter].split(":");

        //Ask whether String 1 is right and then look whether String 2 is also right
        //This function is limited to two out of four answers
        //all answer in StringArray are saved in order A|B|C|D
        if (separated[0].equals("A") & checkedA){
            if(separated[1].equals("B") & checkedB){
                rightAnswer(); //A+B
            } else if(separated[1].equals("C") & checkedC){
                rightAnswer(); //A+C
            } else if(separated[1].equals("D") & checkedD){
                rightAnswer(); //A+D
            }else{
                wrongAnswer();
            }
        }else if(separated[0].equals("B") & checkedB){
            if(separated[1].equals("C") & checkedC){
                rightAnswer();
            } else if(separated[1].equals("D") & checkedD){
                rightAnswer();
            } else {
                wrongAnswer();
            }
        }else if(separated[0].equals("C") & checkedC){
            if(separated[1].equals("D") & checkedD){
                rightAnswer();
            }
        }else{
            wrongAnswer();
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
