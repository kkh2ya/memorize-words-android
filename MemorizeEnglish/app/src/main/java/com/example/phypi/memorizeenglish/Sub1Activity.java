package com.example.phypi.memorizeenglish;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

/**
 * Created by phypi on 2018-02-15.
 */

public class Sub1Activity extends AppCompatActivity {
    TextView sub1TextView;
    ArrayList<String> wordsList;
    ArrayList<String> answerList;
    ArrayList<String> sentenceList;
    ArrayList<String> sentenceAnswerList;
    RadioGroup numberRadioGroup;
    ImageButton forwardButton;
    ImageButton backwardButton;
    int number;
    int numberMove;
    Random random;
    ArrayList<String> randomWordsList;
    ArrayList<String> randomAnswerList;

    Button quizButton;

    CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);
        Intent intent = getIntent();

        wordsList = intent.getStringArrayListExtra("wordsList");
        answerList = intent.getStringArrayListExtra("answerList");
        sentenceList = intent.getStringArrayListExtra("sentenceList");
        sentenceAnswerList = intent.getStringArrayListExtra("sentenceAnswerList");

        forwardButton = (ImageButton) findViewById(R.id.forwardButton);

        timer = new CountDownTimer(8*1000,1000) {

            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                forwardButton.performClick();
            }
        };

        sub1TextView = (TextView) findViewById(R.id.sub1TextView);

        numberRadioGroup = (RadioGroup) findViewById(R.id.numberRadioGroup);
        numberRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.numberRadioButton1:
                        number = 10;
                        numberMove = 10;
                        makeRandomWordsList();
                        break;
                    case R.id.numberRadioButton2:
                        number = 20;
                        numberMove = 20;
                        makeRandomWordsList();
                        break;
                    case R.id.numberRadioButton3:
                        number = 30;
                        numberMove = 30;
                        makeRandomWordsList();
                        break;
                }
            }
        });
        numberRadioGroup.check(R.id.numberRadioButton1);

        backwardButton = (ImageButton) findViewById(R.id.backwardButton);

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberMove>1) {
                    numberMove -= 1;
                    sub1TextView.setText(randomWordsList.get(numberMove-1)+"\n\n"+randomAnswerList.get(numberMove-1));
                    timer.cancel();
                    timer.start();
                }
            }
        });

        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number>numberMove) {
                    numberMove += 1;
                    sub1TextView.setText(randomWordsList.get(numberMove-1)+"\n\n"+randomAnswerList.get(numberMove-1));
                    timer.cancel();
                    timer.start();
                }
            }
        });

        quizButton = (Button) findViewById(R.id.quizButton);
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                Intent intent = new Intent(Sub1Activity.this,Sub2Activity.class);
                intent.putStringArrayListExtra("wordsList",wordsList);
                intent.putStringArrayListExtra("answerList",answerList);
                intent.putStringArrayListExtra("randomWordsList",randomWordsList);
                intent.putStringArrayListExtra("randomAnswerList",randomAnswerList);
                intent.putStringArrayListExtra("sentenceList", sentenceList);
                intent.putStringArrayListExtra("sentenceAnswerList", sentenceAnswerList);
                startActivity(intent);
            }
        });

    }


    private void makeRandomWordsList() {
        Toast toast1 = Toast.makeText(Sub1Activity.this, "8秒ずつカウント", Toast.LENGTH_SHORT);
        toast1.show();
        if(wordsList.size() >= 30) {
            random = new Random();
            randomWordsList = new ArrayList<>();
            int temp;

                while (randomWordsList.size() < number) {
                    temp = random.nextInt(wordsList.size());
                    if (!(randomWordsList.contains(wordsList.get(temp))))
                        randomWordsList.add(wordsList.get(temp));
                }

            randomAnswerList = new ArrayList<>();
            for(String rw : randomWordsList){
                randomAnswerList.add(answerList.get(wordsList.indexOf(rw)));
            }
            sub1TextView.setText(randomWordsList.get(numberMove-1)+"\n\n"+randomAnswerList.get(numberMove-1));
            timer.cancel();
            timer.start();
        }else{
            Toast toast2 = Toast.makeText(this,"まず、30個以上登録なさってください。",Toast.LENGTH_LONG);
            toast2.show();
            finish();
        }
    }

}