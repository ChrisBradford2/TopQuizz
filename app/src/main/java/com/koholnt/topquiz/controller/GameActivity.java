package com.koholnt.topquiz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.koholnt.topquiz.R;
import com.koholnt.topquiz.model.model.Question;
import com.koholnt.topquiz.model.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTextView;
    Button mGameButton1;
    Button mGameButton2;
    Button mGameButton3;
    Button mGameButton4;
    Question mCurentQuestion;
    private int mRemainingQuestionCount;
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mTextView = findViewById(R.id.game_activity_textview_question);
        mGameButton1 = findViewById(R.id.game_activity_button_1);
        mGameButton2 = findViewById(R.id.game_activity_button_2);
        mGameButton3 = findViewById(R.id.game_activity_button_3);
        mGameButton4 = findViewById(R.id.game_activity_button_4);

        mTextView.setOnClickListener(this);
        mGameButton1.setOnClickListener(this);
        mGameButton2.setOnClickListener(this);
        mGameButton3.setOnClickListener(this);
        mGameButton4.setOnClickListener(this);

        mCurentQuestion = mQuestionBank.getCurrentQuestion();
        displayQuestion(mCurentQuestion);

        mRemainingQuestionCount = 4;

        mScore = 0;
    }

    private void displayQuestion(final Question question) {
        mTextView.setText(question.getQuestion());
        mGameButton1.setText(question.getChoiceList().get(0));
        mGameButton2.setText(question.getChoiceList().get(1));
        mGameButton3.setText(question.getChoiceList().get(2));
        mGameButton4.setText(question.getChoiceList().get(3));
    }

    private final QuestionBank mQuestionBank = generateQuestions();

    private QuestionBank generateQuestions(){
        Question question1 = new Question(
                "Who is the creator of Android?",
                Arrays.asList(
                        "Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"
                ),
                0
        );

        Question question2 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );

        Question question3 = new Question(
                "What is the house number of The Simpsons?",
                Arrays.asList(
                        "42",
                        "101",
                        "666",
                        "742"
                ),
                3
        );

        Question question4 = new Question(
                "Lorsqu'un pancake tombe dans la glace avant le 31 décembre, on dit qu'il est ?",
                Arrays.asList(
                        "Tombé dans la glace avant le 31 décembre",
                        "Un frizby comestible",
                        "Une kipa surgelée",
                        "La réponse 4"
                ),
                1
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3, question4));
    }

    @Override
    public void onClick(View v) {
        int index;

        if (v == mGameButton1) {
            index = 0;
        } else if (v == mGameButton2) {
            index = 1;
        } else if (v == mGameButton3) {
            index = 2;
        } else if (v == mGameButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()) {
            Toast.makeText(this, "Correct !", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Wrong !", Toast.LENGTH_SHORT).show();
        }

        mRemainingQuestionCount--;

        if (mRemainingQuestionCount > 0){
            mCurentQuestion = mQuestionBank.getNextQuestion();
            displayQuestion(mCurentQuestion);
        } else {
            //No question left, end of the game
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Well done!")
                    .setMessage("Your score is " + mScore)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
    }
}