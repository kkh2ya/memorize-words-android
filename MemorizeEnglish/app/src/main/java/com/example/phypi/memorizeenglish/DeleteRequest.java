package com.example.phypi.memorizeenglish;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phypi on 2018-02-20.
 */

public class DeleteRequest extends StringRequest {
    final static String ip = "10.0.2.2";
    final static private String URL = "http://"+ip+"/memorize_english/delete.php";

    private Map<String, String> parameters;

    public DeleteRequest(String words, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("words",words);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
