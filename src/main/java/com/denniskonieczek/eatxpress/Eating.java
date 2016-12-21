package com.denniskonieczek.eatxpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class Eating extends AppCompatActivity {

    String order;
    String total;
    String name;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eating);


        Bundle extras = getIntent().getExtras();
        order = extras.getString("order");
        total = extras.getString("total");
        name = extras.getString("name");
        username = extras.getString("username");



        Button pay = (Button) findViewById(R.id.btn);
        pay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Eating.this, Payment.class);
                intent.putExtra("order",order);
                intent.putExtra("total", total);
                intent.putExtra("name",name);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eating, menu);
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
