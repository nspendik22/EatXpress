package com.denniskonieczek.eatxpress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class Start extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText tablenumber;
    String NameResult;
    String UsernameResult;
    String PasswordResult;
    String tablenumberr;
    private ProgressDialog pDialog;
    private static String url;
    String json;
    JSONArray loginInfo = null;
    private static final String TAG_LOGIN = "login";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void login(View v)
    {
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        tablenumber = (EditText)findViewById(R.id.tablenumber);
        tablenumberr = tablenumber.getText().toString();
        url = "http://munro.humber.ca/~spnn0141/eatxpress/login.php?username="+username.getText();
        try {
            json = new GetLogin().execute().get();

            /* Parse */
            JSONObject jsonObj = new JSONObject(json);
            loginInfo = jsonObj.getJSONArray(TAG_LOGIN);

            JSONObject c = loginInfo.getJSONObject(0);

            UsernameResult = c.getString(TAG_USERNAME);
            PasswordResult = c.getString(TAG_PASSWORD);
            NameResult = c.getString(TAG_NAME);

        }catch (Exception e) {
            e.printStackTrace();
        }


        if (username.getText().toString().equals(UsernameResult) && password.getText().toString().equals(PasswordResult))
        {
            Intent intent = new Intent(Start.this , Course.class);
            intent.putExtra("RestaurantName", NameResult);
            intent.putExtra("username", UsernameResult);
            intent.putExtra("tablenumber",tablenumberr);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), getString(R.string.errorMsg),
                    Toast.LENGTH_LONG).show();
        }





    }

    public void register(View v){
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://munro.humber.ca/~spnn0141/eatxpress/register.php");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

/*******************************************************/
/*******************************************************/

    private class GetLogin extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Start.this);
            pDialog.setMessage(getString(R.string.wait));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {

            DownloadJSON jsonString = new DownloadJSON(url);
            return jsonString.getJSON();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            json = result;
            if (pDialog.isShowing()) { pDialog.dismiss(); }

        }

    } //end of GetLogin
}//end of class Start
