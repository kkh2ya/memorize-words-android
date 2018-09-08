package com.example.phypi.memorizeenglish;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by phypi on 2018-02-16.
 */

public class Sub2Activity extends AppCompatActivity {
    ArrayList<String> wordsList;
    ArrayList<String> answerList;
    ArrayList<String> sentenceList;
    ArrayList<String> sentenceAnswerList;
    ArrayList<String> randomWordsList;
    ArrayList<String> randomAnswerList;
    ArrayList<String> randomSentenceList;
    ArrayList<String> randomSentenceAnswerList;
    ArrayList<String> randomWordsList2;
    ArrayList<String> randomAnswerList2;
    ArrayList<String> randomSentenceList2;
    ArrayList<String> randomSentenceAnswerList2;

    RadioGroup quizTypeRadioGroup;

    TextView sub2TextView;

    TextView guideTextView;
    RadioGroup answerRadioGroup;
    EditText answerEditText;

    ImageButton sub2ForwardButton;
    ImageButton sub2BackwardButton;

    int pageNumber;
    int pageCount;

    ArrayList<String> answerSheetList;
    int score;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        Intent intent = getIntent();

        wordsList = intent.getStringArrayListExtra("wordsList");
        answerList = intent.getStringArrayListExtra("answerList");
        randomWordsList = intent.getStringArrayListExtra("randomWordsList");
        randomAnswerList = intent.getStringArrayListExtra("randomAnswerList");

        sentenceList = intent.getStringArrayListExtra("sentenceList");
        sentenceAnswerList = intent.getStringArrayListExtra("sentenceAnswerList");

        randomSentenceList = new ArrayList<>();
        randomSentenceAnswerList = new ArrayList<>();
        for(String rw : randomWordsList){
            randomSentenceList.add(sentenceList.get(wordsList.indexOf(rw)));
            randomSentenceAnswerList.add(sentenceAnswerList.get(wordsList.indexOf(rw)));
        }

        makeRandomList();

        pageNumber = randomWordsList2.size();
        pageCount = pageNumber;

        sub2TextView = (TextView) findViewById(R.id.sub2TextView);
        guideTextView = (TextView) findViewById(R.id.guideTextView);
        answerRadioGroup = (RadioGroup) findViewById(R.id.answerRadioGroup);
        answerEditText = (EditText) findViewById(R.id.answerEditText);
        answerSheetList = new ArrayList<>();

        sub2ForwardButton = (ImageButton) findViewById(R.id.sub2ForwardButton);
        sub2ForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quizTypeRadioGroup.getCheckedRadioButtonId()==R.id.quizTypeRadioButton2) answerSheetList.set(pageCount-1,answerEditText.getText().toString());
                if(pageCount>1) {
                    pageCount -= 1;
                    answerRadioGroup.clearCheck();
                    answerEditText.setText("");
                    currentRadioButton();
                }else if(pageCount==1){
                    doGrade();
                }
            }
        });

        sub2BackwardButton = (ImageButton) findViewById(R.id.sub2BackwardButton);
        sub2BackwardButton.setVisibility(View.GONE);

        final ArrayList<String> falseAnswerSheetList= new ArrayList<>();
        for (int i=0; i<pageNumber; i++) falseAnswerSheetList.add("ffff");

        quizTypeRadioGroup = (RadioGroup) findViewById(R.id.quizTypeRadioGroup);
        quizTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                score = 0;
                answerSheetList.clear();
                answerSheetList.addAll(falseAnswerSheetList);
                pageCount = pageNumber;
                sub2ForwardButton.setVisibility(View.VISIBLE);
                switch (i){
                    case R.id.quizTypeRadioButton1:
                            sub2TextView.setText(randomWordsList2.get(pageCount-1));
                            makeAnswer1();
                        break;
                    case R.id.quizTypeRadioButton2:
                            sub2TextView.setText(randomAnswerList2.get(pageCount-1));
                            makeAnswer2();
                        break;
                    case R.id.quizTypeRadioButton3:
                            makeSentenceSub2TextView();
                            makeAnswer3();
                        break;
                }
            }
        });
        quizTypeRadioGroup.check(R.id.quizTypeRadioButton1);

        answerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.answerRadioButton1:
                        answerSheetList.set(pageCount-1,(String)((RadioButton)findViewById(R.id.answerRadioButton1)).getText());
                        break;
                    case R.id.answerRadioButton2:
                        answerSheetList.set(pageCount-1,(String)((RadioButton)findViewById(R.id.answerRadioButton2)).getText());
                        break;
                    case R.id.answerRadioButton3:
                        answerSheetList.set(pageCount-1,(String)((RadioButton)findViewById(R.id.answerRadioButton3)).getText());
                        break;
                    case R.id.answerRadioButton4:
                        answerSheetList.set(pageCount-1,(String)((RadioButton)findViewById(R.id.answerRadioButton4)).getText());
                        break;
                }
            }
        });

    }

    private void makeSentenceSub2TextView() {
        String quizSentence = "";
        String fullSentence = randomSentenceList2.get(pageCount-1);
        String answerPart = randomSentenceAnswerList2.get(pageCount-1);
        int st = 0;
        for(int i=0; i<fullSentence.length(); i++){
            ArrayList<Integer> eq = new ArrayList<>();
            if(i+answerPart.length()-1<=fullSentence.length()-1) {
                for (int j = i; j < i+answerPart.length(); j++) {
                    if (fullSentence.charAt(j) != answerPart.charAt(j - i)) {
                        eq.add(1);
                    }else{
                        eq.add(0);
                    }
                }
                if (eq.contains(1)==false) {
                    st = i;
                    break;
                }
            }else{
                break;
            }
        }
        if(st+answerPart.length()<=fullSentence.length()-1) {
            quizSentence = fullSentence.substring(0, st) + " _____ " + fullSentence.substring(st + answerPart.length());
        }else{
            quizSentence = fullSentence.substring(0, st) + " _____ ";
        }
        sub2TextView.setText(quizSentence);
        quizSentence = "";
    }

    private void doGrade() {
        RadioButton radioButton = (RadioButton) findViewById(quizTypeRadioGroup.getCheckedRadioButtonId());
        guideTextView.setVisibility(View.GONE);
        answerEditText.setVisibility(View.GONE);
        answerRadioGroup.setVisibility(View.GONE);
        sub2ForwardButton.setVisibility(View.GONE);
        sub2BackwardButton.setVisibility(View.GONE);
        if(radioButton.getId()==R.id.quizTypeRadioButton1){
            int i=0;
            for(String yourAnswer : answerSheetList){
                if(yourAnswer.equals(randomAnswerList2.get(i))) score++;
                i++;
            }
        } else if(radioButton.getId()==R.id.quizTypeRadioButton2){
            int i=0;
            for(String yourAnswer : answerSheetList){
                if(yourAnswer.equals(randomWordsList2.get(i))) score++;
                i++;
            }
        } else if(radioButton.getId()==R.id.quizTypeRadioButton3){
            int i=0;
            for(String yourAnswer : answerSheetList){
                if(yourAnswer.equals(randomWordsList2.get(i))) score++;
                i++;
            }
        }
        sub2TextView.setText(score+"/"+pageNumber);
        sub2TextView.setTextColor(Color.BLACK);
    }

    private void currentRadioButton() {
        if(quizTypeRadioGroup.getCheckedRadioButtonId()==R.id.quizTypeRadioButton1){
            sub2TextView.setText(randomWordsList2.get(pageCount-1));
            makeAnswer1();
        } else if(quizTypeRadioGroup.getCheckedRadioButtonId()==R.id.quizTypeRadioButton2){
            sub2TextView.setText(randomAnswerList2.get(pageCount-1));
            makeAnswer2();
        } else if(quizTypeRadioGroup.getCheckedRadioButtonId()==R.id.quizTypeRadioButton3){
            makeSentenceSub2TextView();
            makeAnswer3();
        }
    }

    private void makeAnswer3() {
        guideTextView.setText(" ● 答えを選んでください。(原型)");
        int temp1 = -1; int temp2 = -1; int temp3 = -1; int temp4 = -1;
        int answer = wordsList.indexOf(randomWordsList2.get(pageCount-1));
        while(temp1 != answer && temp2 != answer && temp3 != answer && temp4 != answer) {
            do{temp1 = (int) (Math.random() * wordsList.size());}while(temp2 == temp1 | temp3 == temp1 | temp4 == temp1);
            do{temp2 = (int) (Math.random() * wordsList.size());}while(temp1 == temp2 | temp3 == temp2 | temp4 == temp2);
            do{temp3 = (int) (Math.random() * wordsList.size());}while(temp1 == temp3 | temp2 == temp3 | temp4 == temp3);
            do{temp4 = (int) (Math.random() * wordsList.size());}while(temp1 == temp4 | temp2 == temp4 | temp3 == temp4);
        }
        RadioButton r1 = (RadioButton) findViewById(R.id.answerRadioButton1);
        r1.setText(wordsList.get(temp1));
        RadioButton r2 = (RadioButton) findViewById(R.id.answerRadioButton2);
        r2.setText(wordsList.get(temp2));
        RadioButton r3 = (RadioButton) findViewById(R.id.answerRadioButton3);
        r3.setText(wordsList.get(temp3));
        RadioButton r4 = (RadioButton) findViewById(R.id.answerRadioButton4);
        r4.setText(wordsList.get(temp4));

        guideTextView.setVisibility(View.VISIBLE);
        answerEditText.setVisibility(View.GONE);
        answerRadioGroup.setVisibility(View.VISIBLE);
    }

    private void makeAnswer2() {
        guideTextView.setText(" ● 答えを書いてください。");
        guideTextView.setVisibility(View.VISIBLE);
        answerRadioGroup.setVisibility(View.GONE);
        answerEditText.setVisibility(View.VISIBLE);
    }

    private void makeAnswer1() {
        guideTextView.setText(" ● 答えを選んでください。");
        int temp1 = -1; int temp2 = -1; int temp3 = -1; int temp4 = -1;
        int answer = wordsList.indexOf(randomWordsList2.get(pageCount-1));
        while(temp1 != answer && temp2 != answer && temp3 != answer && temp4 != answer) {
            do{temp1 = (int) (Math.random() * wordsList.size());}while(temp2 == temp1 | temp3 == temp1 | temp4 == temp1);
            do{temp2 = (int) (Math.random() * wordsList.size());}while(temp1 == temp2 | temp3 == temp2 | temp4 == temp2);
            do{temp3 = (int) (Math.random() * wordsList.size());}while(temp1 == temp3 | temp2 == temp3 | temp4 == temp3);
            do{temp4 = (int) (Math.random() * wordsList.size());}while(temp1 == temp4 | temp2 == temp4 | temp3 == temp4);
        }
        RadioButton r1 = (RadioButton) findViewById(R.id.answerRadioButton1);
        r1.setText(answerList.get(temp1));
        RadioButton r2 = (RadioButton) findViewById(R.id.answerRadioButton2);
        r2.setText(answerList.get(temp2));
        RadioButton r3 = (RadioButton) findViewById(R.id.answerRadioButton3);
        r3.setText(answerList.get(temp3));
        RadioButton r4 = (RadioButton) findViewById(R.id.answerRadioButton4);
        r4.setText(answerList.get(temp4));

        guideTextView.setVisibility(View.VISIBLE);
        answerEditText.setVisibility(View.GONE);
        answerRadioGroup.setVisibility(View.VISIBLE);
    }

    private void makeRandomList() {
        Random random = new Random();
        randomWordsList2 = new ArrayList<>();
        randomAnswerList2 = new ArrayList<>();
        randomSentenceList2 = new ArrayList<>();
        randomSentenceAnswerList2 = new ArrayList<>();
        int temp;
        while(randomWordsList2.size()<randomWordsList.size()
                | randomAnswerList2.size()<randomAnswerList.size()
                | randomSentenceList2.size()<randomSentenceList.size()
                | randomSentenceAnswerList2.size()<randomSentenceAnswerList.size()){
            temp = random.nextInt(randomWordsList.size());
            if(!(randomWordsList2.contains(randomWordsList.get(temp)))) randomWordsList2.add(randomWordsList.get(temp));
            if(!(randomAnswerList2.contains(randomAnswerList.get(temp)))) randomAnswerList2.add(randomAnswerList.get(temp));
            if(!(randomSentenceList2.contains(randomSentenceList.get(temp)))) randomSentenceList2.add(randomSentenceList.get(temp));
            if(!(randomSentenceAnswerList2.contains(randomSentenceAnswerList.get(temp)))) randomSentenceAnswerList2.add(randomSentenceAnswerList.get(temp));
        }
    }
}
