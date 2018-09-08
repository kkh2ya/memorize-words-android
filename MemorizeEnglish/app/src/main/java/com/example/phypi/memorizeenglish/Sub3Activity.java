package com.example.phypi.memorizeenglish;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

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


/**
 * Created by phypi on 2018-02-17.
 */

public class Sub3Activity extends AppCompatActivity {
    int flag;

    RadioGroup CRUDRadioGroup;
    LinearLayout registerLayout;
    LinearLayout userListLayout;

    EditText registerWordsEditText;
    EditText registerAnswerEditText;
    EditText registerSentenceEditText;
    EditText registerSentenceAnswerEditText;

    LinearLayout uploadButtonlayout;
    LinearLayout updateButtonLayout;
    Button uploadButton;
    Button updateButton;

    ArrayList<Userlist> userList;
    ArrayList<Userlist> saveList;

    UserListAdapter userListAdapter;
    ListView userListListView;
    EditText searchUserListEditText;

    ImageButton goHomeImageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub3);

        Intent intent = getIntent();
        if(intent.getStringExtra("updateList").equals("do")) updateWords();

        registerLayout = findViewById(R.id.registerLayout);
        userListLayout = findViewById(R.id.userListLayout);

        CRUDRadioGroup = findViewById(R.id.CRUDRadioGroup);
        CRUDRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                goHomeImageButton = (ImageButton) findViewById(R.id.goHomeImageButton);
                goHomeImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Intent intent = new Intent(Sub3Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                switch (i){
                    case R.id.CRUDRadioButton1:
                        userListLayout.setVisibility(View.GONE);
                        registerLayout.setVisibility(View.VISIBLE);
                        flag = 1;
                        new BackGroundTask().execute();
                        break;
                    case R.id.CRUDRadioButton2:
                        registerLayout.setVisibility(View.GONE);
                        userListLayout.setVisibility(View.VISIBLE);
                        flag = 2;
                        new BackGroundTask().execute();
                        break;
                }
            }
        });
        CRUDRadioGroup.check(R.id.CRUDRadioButton1);

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
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

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
        saveList = new ArrayList<>();

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
                saveList.add(list);

                count++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(flag == 1){
            startRegistration();
        }else if(flag == 2){
            startUserListing();
        }
    }

    private void startRegistration() {
        registerWordsEditText = findViewById(R.id.registerWordsEditText);
        registerAnswerEditText = findViewById(R.id.registerAnswerEditText);
        registerSentenceEditText = findViewById(R.id.registerSentenceEditText);
        registerSentenceAnswerEditText = findViewById(R.id.registerSentenceAnswerEditText);

        registerWordsEditText.requestFocus();

        uploadButton = (Button) findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRegistration();
                registerWordsEditText.setText("");
                registerAnswerEditText.setText("");
                registerSentenceEditText.setText("");
                registerSentenceAnswerEditText.setText("");
            }
        });
    }

    private void makeRegistration() {
        String words = registerWordsEditText.getText().toString();
        String answer = registerAnswerEditText.getText().toString();
        String sentence = registerSentenceEditText.getText().toString();
        String sentence_answer = registerSentenceAnswerEditText.getText().toString();

        for(int i=0; i<userList.size(); i++) {
            if(words.equals(userList.get(i).getWords())){
                AlertDialog.Builder builder = new AlertDialog.Builder(Sub3Activity.this);
                builder.setMessage("既に登録されています。")
                        .setPositiveButton("確認", null)
                        .create()
                        .show();
                return;
            }
        }

        if(words.equals("") | answer.equals("") | sentence.equals("") | sentence_answer.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Sub3Activity.this);
            builder.setMessage("空欄を埋めてください。")
                    .setPositiveButton("確認", null)
                    .create()
                    .show();
            return;
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Sub3Activity.this);
                        builder.setMessage("登録成功")
                                .setPositiveButton("確認", null)
                                .create()
                                .show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Sub3Activity.this);
                        builder.setMessage("登録失敗")
                                .setPositiveButton("確認", null)
                                .create()
                                .show();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest registerRequest = new RegisterRequest(words, answer, sentence, sentence_answer, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(Sub3Activity.this);
        requestQueue.add(registerRequest);
    }

    private void startUserListing() {
        userListListView = (ListView) findViewById(R.id.userListListView);
        userListAdapter = new UserListAdapter(getApplicationContext(), this, userList, saveList);
        userListListView.setAdapter(userListAdapter);

        searchUserListEditText = (EditText) findViewById(R.id.searchUserListEditText);
        searchUserListEditText.setText("");
        searchUserListEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchWords(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchWords(String search) {
        userList.clear();
        for(int i=0; i<saveList.size(); i++){
            if(saveList.get(i).getWords().contains(search)){
                userList.add(saveList.get(i));
            }
        }
        userListAdapter.notifyDataSetChanged();
    }

    private void updateWords(){
        registerLayout = findViewById(R.id.registerLayout);
        userListLayout = findViewById(R.id.userListLayout);
        userListLayout.setVisibility(View.GONE);
        registerLayout.setVisibility(View.VISIBLE);

        registerWordsEditText = findViewById(R.id.registerWordsEditText);
        registerAnswerEditText = findViewById(R.id.registerAnswerEditText);
        registerSentenceEditText = findViewById(R.id.registerSentenceEditText);
        registerSentenceAnswerEditText = findViewById(R.id.registerSentenceAnswerEditText);

        registerWordsEditText.setFocusableInTouchMode(false);

        Intent intent = getIntent();
        String words = intent.getStringExtra("words");
        String answer = intent.getStringExtra("answer");
        String sentence = intent.getStringExtra("sentence");
        String sentence_answer = intent.getStringExtra("sentence_answer");

        registerWordsEditText.setText(words);
        registerAnswerEditText.setText(answer);
        registerSentenceEditText.setText(sentence);
        registerSentenceAnswerEditText.setText(sentence_answer);

        uploadButtonlayout = findViewById(R.id.uploadButtonLayout);
        updateButtonLayout = findViewById(R.id.updateButtonLayout);
        uploadButtonlayout.setVisibility(View.GONE);
        updateButtonLayout.setVisibility(View.VISIBLE);

        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String words = registerWordsEditText.getText().toString();
                String answer = registerAnswerEditText.getText().toString();
                String sentence = registerSentenceEditText.getText().toString();
                String sentence_answer = registerSentenceAnswerEditText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                registerWordsEditText.setFocusableInTouchMode(true);
                                uploadButtonlayout.setVisibility(View.VISIBLE);
                                updateButtonLayout.setVisibility(View.GONE);
                                registerWordsEditText.setText("");
                                registerAnswerEditText.setText("");
                                registerSentenceEditText.setText("");
                                registerSentenceAnswerEditText.setText("");
                                CRUDRadioGroup.check(R.id.CRUDRadioButton2);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                UpdateRequest updateRequest = new UpdateRequest(words, answer, sentence, sentence_answer, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Sub3Activity.this);
                queue.add(updateRequest);
            }
        });
    }

}
