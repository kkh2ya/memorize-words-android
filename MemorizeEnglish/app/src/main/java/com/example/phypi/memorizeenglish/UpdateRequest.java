package com.example.phypi.memorizeenglish;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phypi on 2018-02-20.
 */

public class UpdateRequest extends StringRequest {
    final static String ip = "10.0.2.2";
    final static private String URL = "http://"+ip+"/memorize_english/update.php";

    private Map<String, String> parameters;

    public UpdateRequest(String words, String answer, String sentence, String sentence_answer, Response.Listener<String> listener) {
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
