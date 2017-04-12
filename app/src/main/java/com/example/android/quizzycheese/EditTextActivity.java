package com.example.android.quizzycheese;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditTextActivity extends AppCompatActivity {

    int points;
    String answerA;
    String answerB;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        points = 0;
    }

    public void onNextQuestionButton(View view) {
        EditText editText1 = (EditText) findViewById(R.id.edit_text1);
        EditText editText2 = (EditText) findViewById(R.id.edit_text2);
        EditText editText3 = (EditText) findViewById(R.id.edit_text3);

        answerA = editText1.getText().toString();
        answerB = editText2.getText().toString();
        name = editText3.getText().toString();

        if(answerA.length() != 0 || answerB.length() != 0){
            calculation();
        }else{
            Toast.makeText(this,R.string.noAnswer3,Toast.LENGTH_SHORT).show();
        }
    }

    public void calculation(){
        if(answerA.equals("INTERNATIONAL SPACE STATION")){
            points += 1;
            if (answerB.equals("red")){
                points += 1;
                Toast.makeText(this,R.string.right2,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,R.string.right3,Toast.LENGTH_SHORT).show();
            }
        }else if(answerB.equals("red")) {
            points += 1;
            Toast.makeText(this,R.string.right3, Toast.LENGTH_SHORT).show();
        }

        shareData();

        Intent intent = new Intent(this, EndScreen.class);
        startActivity(intent);
    }

    public void shareData(){
        // Create object of SharedPreferences.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //now get Editor
        SharedPreferences.Editor editor = sharedPref.edit();
        //put your value
        editor.putInt("score4", points);
        editor.putString("userName",name);

        //commits your edits
        editor.commit();
    }

}
