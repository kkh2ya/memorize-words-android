package com.example.phypi.memorizeenglish;

/**
 * Created by phypi on 2018-02-19.
 */

public class Userlist {
    String words;
    String answer;
    String sentence;
    String sentence_answer;

    public Userlist(String words, String answer, String sentence, String sentence_answer) {
        this.words = words;
        this.answer = answer;
        this.sentence = sentence;
        this.sentence_answer = sentence_answer;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentence_answer() {
        return sentence_answer;
    }

    public void setSentence_answer(String sentence_answer) {
        this.sentence_answer = sentence_answer;
    }
}
