package com.example.phypi.memorizeenglish;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phypi on 2018-02-19.
 */

public class RegisterRequest extends StringRequest{
    final static String ip = "10.0.2.2";
    final static private String URL = "http://"+ip+"/memorize_english/register.php";

    private Map<String, String> parameters;

    public RegisterRequest(String words, String answer, String sentence, String sentence_answer, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("words",words);
        parameters.put("answer",answer);
        parameters.put("sentence",sentence);
        parameters.put("sentence_answer",sentence_answer);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
