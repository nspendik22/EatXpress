package com.denniskonieczek.eatxpress;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class WaitingFood extends AppCompatActivity {

    String order;
    String total;
    String name;
    String username;
    String tablenumber;
    private String waitressID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_food);


        Bundle extras = getIntent().getExtras();
        order = extras.getString("order");
        total = extras.getString("total");
        name = extras.getString("name");
        username = extras.getString("username");
        tablenumber = extras.getString("tablenumber");


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        final Button waitress = (Button) findViewById(R.id.waitress);
        //waitress.setVisibility(View.VISIBLE);
        //waitress.setBackgroundColor(Color.TRANSPARENT);
        waitress.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                // showDialog(ALERT_DIALOG1);
                AlertDialog.Builder builder = new AlertDialog.Builder(WaitingFood.this);
                builder.setCancelable(false);
                builder.setTitle(getString(R.string.confirm));
                builder.setView(input);
                builder.setMessage(getString(R.string.msg));
                builder.setPositiveButton(getString(R.string.next), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        waitressID = input.getText().toString();
                        if (waitressID.equals(tablenumber)) {

                            Intent intent = new Intent(WaitingFood.this, Eating.class);
                            intent.putExtra("order",order);
                            intent.putExtra("total",total);
                            intent.putExtra("name",name);
                            intent.putExtra("username",username);
                            intent.putExtra("tablenumber",tablenumber);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Wrong code", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                //builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });





    }

    @Override
    public void onBackPressed() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_waiting_food, menu);
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
