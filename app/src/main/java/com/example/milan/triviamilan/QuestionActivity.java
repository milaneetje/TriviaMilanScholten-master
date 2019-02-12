package com.example.milan.triviamilan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {

    RequestQueue queue;

    //creates a list of questions
    private List<Question> parsedQuestions = new ArrayList<>();

    //creates the components of the activity
    public ListView answerListView;
    public TextView QuestionTxt;
    public TextView QuestionNum;
    public  TextView ScoreNum;

    //creates integer variables for the answersequence, question number and score
    int[] answerseq = new int[4];
    int questNum;
    int Score;

    //creates string array to hold all answers
    String[] answers = new String[4];

    //creates String variables for the question, correct answer, name, difficulty and the url
    String questionString;
    String corAnswer;
    String Name;
    String Difficulty;
    String urlquestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //gets the intent from the previous activity
        Intent intent = getIntent();

        //retrieves player
        Player retrievedPlayer = (Player) intent.getSerializableExtra("current_Player");

        queue = Volley.newRequestQueue(this);

        //finds the components of the activity
        answerListView = findViewById(R.id.Answerlist);

        //creates variables from retrieved player
        Score = retrievedPlayer.Score;
        Name = retrievedPlayer.Name;
        Difficulty = retrievedPlayer.Difficulty;

        //sets question number to 0
        questNum = 0;

        init();
    }

    public void init() {

        queue = Volley.newRequestQueue(this);

        //determines the url by difficulty
        if (Difficulty.equals("Easy")) {
            urlquestion = "https://opentdb.com/api.php?amount=25&difficulty=easy&type=multiple";
            System.out.println(Difficulty);
        }
        else if (Difficulty.equals("Medium")) {
            urlquestion = "https://opentdb.com/api.php?amount=25&difficulty=medium&type=multiple";
            System.out.println(Difficulty);
        }
        else if (Difficulty.equals("Hard")) {
            urlquestion = "https://opentdb.com/api.php?amount=25&difficulty=hard&type=multiple";
            System.out.println(Difficulty);
        }

        //posts a request to URL
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, urlquestion, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");

                            //creates a question object for the amount of results
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject questionsJSONObject = results.getJSONObject(i);

                                answers = new String[answers.length];
                                questionString = questionsJSONObject.getString("question");
                                JSONArray incAnswers = questionsJSONObject.getJSONArray("incorrect_answers");

                                //replaces HTML coding strings with special symbols for the question
                                questionString = questionString.replace("&quot;", "'")
                                        .replace("&#039;","'")
                                        .replace("&rsquo;","/")
                                        .replace("&eacute;","é")
                                        .replace("&amp;","&")
                                        .replace("&Uuml;", "Ü")
                                        .replace("&ntilde;", "ñ")
                                        .replace("&aacute;", "á");

                                //Generates a random number sequence for the answer sequence
                                generateAnsSeq();

                                //gets string for correct answer
                                corAnswer = questionsJSONObject.getString("correct_answer");

                                //puts answer in the first place of the answersequence
                                answers[answerseq[0]] = corAnswer;

                                //puts the incorrect answers in the other places of the answer seq
                                for (int j = 0; j < incAnswers.length(); j++) {

                                    answers[answerseq[j+1]] = incAnswers.get(j).toString();
                                }

                                //replaces HTML coding strings with special symbols for the answers
                                for ( int k = 0; k < answers.length; k++) {
                                    answers[k] = answers[k].replace("&quot;", "'")
                                        .replace("&#039;","'")
                                        .replace("&rsquo;","/")
                                        .replace("&eacute;","é")
                                        .replace("&amp;","&")
                                        .replace("&Uuml;", "Ü")
                                        .replace("&ntilde;", "ñ")
                                        .replace("&aacute;", "á");
                                }

                                //creates a new object of class question
                                Question question = new Question(questionString, corAnswer,
                                        answers);

                                //adds question to the list of questions
                                parsedQuestions.add(question);
                            }
                            updateQuestionView(questNum);


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.response", error.toString());
                    }
                }
        );
        queue.add(getRequest);
    }

    public void generateAnsSeq() {

        //creates a random sequence of numbers to order the answers by
        Random rand = new Random();

        answerseq[0] = rand.nextInt(4);

        answerseq[1] = rand.nextInt(4);
        while (answerseq[1] == answerseq[0]) {
            answerseq[1] = rand.nextInt(4);
        }

        answerseq[2] = rand.nextInt(4);
        while ((answerseq[2] == answerseq[0]) ||
                (answerseq[2] == answerseq[1])) {
            answerseq[2] = rand.nextInt(4);
        }

        answerseq[3] = rand.nextInt(4);
        while ((answerseq[3] == answerseq[0]) ||
                (answerseq[3] == answerseq[1]) ||
                (answerseq[3] == answerseq[2])) {
            answerseq[3] = rand.nextInt(4);
        }
    }

    private void updateQuestionView(int number) {

        // gets a question by number from the parsed questions
        final Question questionObject = parsedQuestions.get(number);

        //gets the correct answer from the question object
        final String correct = questionObject.getCorAnswerString();

        //fills the listview with answers
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.answer_layout,
                R.id.AnswerText, questionObject.answers);
        answerListView.setAdapter(adapter);

        //find score textview and fills it with the current score
        ScoreNum = findViewById(R.id.ScoreTxt);
        String ScoreNumTxt = "Score: " + Score;
        ScoreNum.setText(ScoreNumTxt);

        //find question number textview and fills it with the current question number
        QuestionNum = findViewById(R.id.Questionnumtxt);
        String QuestionNumTxt = "Question " + (questNum+1);
        QuestionNum.setText(QuestionNumTxt);

        //find question textview and fills it with the current question
        QuestionTxt = findViewById(R.id.QuestionTxt);
        QuestionTxt.setText(questionObject.getQuestionString());

        //checks if the pressed answer is correct or incorrect and gets new question
        answerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (questNum < (parsedQuestions.size()-1)) {
                    if (questionObject.answers[position].equals(correct)) {
                        Score++;
                        questNum++;
                        updateQuestionView(questNum);
                    }
                    else {
                        questNum++;
                        updateQuestionView(questNum);
                    }
                }
                else {
                    gameOver();
                }
            }
        });
    }

    public void gameOver() {

        //creates a final player to pass onto the next activity
        Player gameOver = new Player(Name, Score, Difficulty);

        HighscoreSetter post = new HighscoreSetter(QuestionActivity.this);
        post.setScore(Name, Score, Difficulty);

        //fires intent into next activity
        Intent intent = new Intent(QuestionActivity.this, HighscoreActivity.class);
        intent.putExtra("player", gameOver);

        //starts next activity
        startActivity(intent);
        finish();
    }
}