package com.example.phypi.memorizeenglish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by phypi on 2018-02-20.
 */

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private Activity parentActivity;
    private ArrayList<Userlist> userList;
    private ArrayList<Userlist> saveList;

    public UserListAdapter(Context context, Activity parentActivity, ArrayList<Userlist> userList, ArrayList<Userlist> saveList) {
        this.context = context;
        this.parentActivity = parentActivity;
        this.userList = userList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View inflateView = View.inflate(context,R.layout.userlist,null);

        final TextView wordsInflateTextView = inflateView.findViewById(R.id.wordsInflateTextView);
        final TextView answerInflateTextView = inflateView.findViewById(R.id.answerInflateTextView);
        final TextView sentenceInflateTextView = inflateView.findViewById(R.id.sentenceInflateTextView);
        final TextView sentenceAnswerInflateTextView = inflateView.findViewById(R.id.sentenceAnswerInflateTextView);

        wordsInflateTextView.setText(userList.get(i).getWords());
        answerInflateTextView.setText(userList.get(i).getAnswer());
        sentenceInflateTextView.setText(userList.get(i).getSentence());
        sentenceAnswerInflateTextView.setText(userList.get(i).getSentence_answer());

        inflateView.setTag(userList.get(i).getWords());

        Button deleteButton = (Button) inflateView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success){
                                userList.remove(i);
                                for (int i=0; i<saveList.size(); i++){
                                    if(saveList.get(i).getWords().equals(wordsInflateTextView.getText().toString())){
                                        saveList.remove(i);
                                        break;
                                    }
                                }
                                notifyDataSetChanged();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest = new DeleteRequest(wordsInflateTextView.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(deleteRequest);
            }
        });

        Button updateButton = (Button) inflateView.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Sub3Activity.class);
                intent.putExtra("updateList","do");
                intent.putExtra("words", wordsInflateTextView.getText().toString());
                intent.putExtra("answer", answerInflateTextView.getText().toString());
                intent.putExtra("sentence", sentenceInflateTextView.getText().toString());
                intent.putExtra("sentence_answer", sentenceAnswerInflateTextView.getText().toString());
                context.startActivity(intent);
            }
        });

        return inflateView;
    }
}
