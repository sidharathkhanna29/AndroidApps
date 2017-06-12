package com.example.android.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static com.example.android.quizapp.R.id.ans5;


public class MainActivity extends AppCompatActivity {


    int correct = 0;
    int incorrect = 0;
    boolean flag = false;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Submit (View view) {

        try {
            EditText text1 = (EditText) findViewById(R.id.ans1);
            int ans1 = Integer.parseInt(text1.getText().toString());
            if (ans1 == 1)
                correct = correct + 1;
            else
                incorrect = incorrect + 1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            incorrect = incorrect + 1;
            j++;
            flag = true;
        }

        RadioButton button = (RadioButton) findViewById(R.id.option4);
        boolean ans2 = button.isChecked();
        if (ans2)
            correct = correct + 1;
        else
        {
            incorrect = incorrect + 1;
            j++;
        }

        try {
            EditText text3 = (EditText) findViewById(R.id.ans3);
            int ans3 = Integer.parseInt(text3.getText().toString());
            if (ans3 == 88)
                correct = correct + 1;
            else
                incorrect = incorrect + 1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            incorrect = incorrect + 1;
            j++;
            flag = true;
        }

        RadioButton button4 = (RadioButton) findViewById(R.id.ans4_option2);
        boolean ans4 = button4.isChecked();
        if (ans4)
            correct = correct + 1;
        else
        {
            incorrect = incorrect + 1;
            j++;
        }


        try {
            EditText text5 = (EditText) findViewById(ans5);
            int ans5 = Integer.parseInt(text5.getText().toString());
            if(ans5 == 41)
                correct = correct + 1;
            else
                incorrect = incorrect + 1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            incorrect = incorrect + 1;
            j++;
            flag = true;
        }


        CheckBox option1 = (CheckBox) findViewById(R.id.ans6option1);
        boolean ans6o1 = option1.isChecked();

        CheckBox option2 = (CheckBox) findViewById(R.id.ans6option2);
        boolean ans6o2 = option2.isChecked();

        CheckBox option3 = (CheckBox) findViewById(R.id.ans6option3);
        boolean ans6o3 = option3.isChecked();

        CheckBox option4 = (CheckBox) findViewById(R.id.ans6option4);
        boolean ans6o4 = option4.isChecked();

        if ((ans6o1 == true) && (ans6o2 == false) && (ans6o3 == true)&& (ans6o4 == false)) {
            correct = correct + 1;
        }
        else
        {
            incorrect = incorrect + 1;
            j++;
        }



        if (correct == 6)
            Toast.makeText(this, "Congratulations!!!", Toast.LENGTH_SHORT).show();

        if (flag == true)
            Toast.makeText(this, "" + j + "Questions left unattempted", Toast.LENGTH_SHORT).show();


        Toast.makeText(this,"Correct: " + correct + " Incorrect: " + incorrect, Toast.LENGTH_SHORT).show();



        j = 0; correct = 0; incorrect = 0;
    }
}
