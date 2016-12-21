package com.denniskonieczek.eatxpress;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class ConfirmOrder extends AppCompatActivity {

    String order;
    String total;
    String name;
    String username;
    String tablenumber;
    String url;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


        Bundle extras = getIntent().getExtras();
        order = extras.getString("order");
        total = extras.getString("total");
        name = extras.getString("name");
        username = extras.getString("username");
        tablenumber = extras.getString("tablenumber");



        TextView orderText = (TextView) findViewById(R.id.order);
        orderText.setText(order);
        TextView orderTotal = (TextView) findViewById(R.id.total);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        orderTotal.setText("$"+decimalFormat.format(Double.parseDouble(total)));
        Button backButton = (Button) findViewById(R.id.orderBackButton);
        Button orderConfirmed = (Button) findViewById(R.id.orderConfirmButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    url = "http://munro.humber.ca/~spnn0141/eatxpress/orders.php?username="+
                            username+"&ordered="+URLEncoder.encode(order,"UTF-8")+"&tablenumber="+tablenumber;
                    HttpAsyncTask hat = new HttpAsyncTask();
                    hat.execute(url);

                }catch (Exception e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(ConfirmOrder.this, WaitingFood.class);
                i.putExtra("order", order);
                i.putExtra("name", name);
                i.putExtra("total", total);
                i.putExtra("username", username);
                i.putExtra("tablenumber", tablenumber);
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
        @Override
        protected void onPostExecute(String result) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_order, menu);
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
