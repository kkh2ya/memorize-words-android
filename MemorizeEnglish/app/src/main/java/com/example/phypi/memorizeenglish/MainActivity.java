package com.example.phypi.memorizeenglish;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button MainButton1;
    Button MainButton2;

    ArrayList<Userlist> userList;
    ArrayList<String> wordsList;
    ArrayList<String> answerList;
    ArrayList<String> sentenceList;
    ArrayList<String> sentenceAnswerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainButton1 = (Button) findViewById(R.id.MainButton1);
        MainButton2 = (Button) findViewById(R.id.MainButton2);

        MainButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackGroundTask().execute();
            }
        });

        MainButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Sub3Activity.class);
                intent.putExtra("updateList","not yet");
                startActivity(intent);
            }
        });

    }


    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String ip ="10.0.2.2";
        String target;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            target = "http://"+ip+"/memorize_english/getlist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));

                String temp;

                StringBuilder stringBuilder = new StringBuilder();

                while((temp=bufferedReader.readLine())!=null){
                    stringBuilder.append(temp+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            }catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            getList(result);
        }
    }

    private void getList(String result) {
        userList = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String words, answer, sentence, sentence_answer;

            userList = new ArrayList<>();

            while (count<jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                words = object.getString("words");
                answer = object.getString("answer");
                sentence = object.getString("sentence");
                sentence_answer = object.getString("sentence_answer");

                Userlist list = new Userlist(words, answer, sentence, sentence_answer);

                userList.add(list);

                count++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        wordsList = new ArrayList<>();
        answerList = new ArrayList<>();
        sentenceList = new ArrayList<>();
        sentenceAnswerList = new ArrayList<>();

        for(Userlist ul : userList) wordsList.add(ul.getWords());
        for(Userlist ul : userList) answerList.add(ul.getAnswer());
        for(Userlist ul : userList) sentenceList.add(ul.getSentence());
        for(Userlist ul : userList) sentenceAnswerList.add(ul.getSentence_answer());

        Intent intent = new Intent(MainActivity.this, Sub1Activity.class);
        intent.putStringArrayListExtra("wordsList",wordsList);
        intent.putStringArrayListExtra("answerList",answerList);
        intent.putStringArrayListExtra("sentenceList",sentenceList);
        intent.putStringArrayListExtra("sentenceAnswerList",sentenceAnswerList);
        startActivity(intent);
    }

}
