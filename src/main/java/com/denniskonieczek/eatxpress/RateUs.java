package com.denniskonieczek.eatxpress;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class RateUs extends AppCompatActivity {

    RatingBar rating;
    EditText email;
    EditText comment;
    Button buttonSend;
    String username;

    String url;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        buttonSend = (Button) findViewById(R.id.button3);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        email = (EditText) findViewById(R.id.email);
        comment = (EditText) findViewById(R.id.comment);

        buttonSend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                try {
                    //URLEncoder.encode(q, "UTF-8")

                    url = "http://munro.humber.ca/~spnn0141/eatxpress/rating.php?username=" +
                    username + "&rating=" +
                    URLEncoder.encode(String.valueOf(rating.getRating()), "UTF-8") +
                                    "&comment=" +
                    URLEncoder.encode(comment.getText().toString(),"UTF-8") +
                                            "&email=" +
                    URLEncoder.encode(email.getText().toString(),"UTF-8");

                    Log.v("testt",">"+ url);


                    HttpAsyncTask hat = new HttpAsyncTask();
                    hat.execute(url);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(RateUs.this, Course.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);

            }
        });


    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            try {
                Log.v("testt",urls[0]);
                URL requestUrl = new URL(urls[0]);
                URLConnection con = requestUrl.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                int cp;

                while ((cp = in.read()) != -1) {
                    sb.append((char) cp);
                }
                response = sb.toString();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return response;


        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rate_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
