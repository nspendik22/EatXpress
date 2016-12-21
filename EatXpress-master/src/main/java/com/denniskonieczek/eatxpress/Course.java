package com.denniskonieczek.eatxpress;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class Course extends AppCompatActivity {


    private Button button;
    String RestaurantName;
    String username;
    String course;
    String tablenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);


        Bundle extras = getIntent().getExtras();
        RestaurantName = extras.getString("RestaurantName");
        username = extras.getString("username");
        tablenumber = extras.getString("tablenumber",tablenumber);


try {

    /* Get current time */
    SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");//dd/MM/yyyy
    Date now = new Date();
    String currentTime = sdfDate.format(now);
    Date current = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
    Calendar c = Calendar.getInstance();
    c.setTime(current);

    String breakfastTime = "06:00:00";
    Date time1 = new SimpleDateFormat("HH:mm:ss").parse(breakfastTime);
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(time1);

    String lunchTime = "11:59:59";
    Date time2 = new SimpleDateFormat("HH:mm:ss").parse(lunchTime);
    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(time2);
    calendar2.add(Calendar.DATE, 1);

    String dinnerTime = "18:00:00";
    Date time3 = new SimpleDateFormat("HH:mm:ss").parse(dinnerTime);
    Calendar calendar3 = Calendar.getInstance();
    calendar3.setTime(time3);
    calendar3.add(Calendar.DATE, 1);

    button = (Button) findViewById(R.id.button2);



    if (current.before(time2)) {
        course = getString(R.string.breakfast);
        button.setText(course);
    }
    else if (current.after(time2) && current.before(time3)) {
        course = getString(R.string.lunch);
        button.setText(course);
    }
    else if (current.after(time3) ) {
        course = getString(R.string.dinner);
        button.setText(course);
    }
    else {
        button.setEnabled(false);
    }
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(Course.this , Choice.class);
            intent.putExtra("RestaurantName", RestaurantName);
            intent.putExtra("username", username);
            intent.putExtra("course", course);
            intent.putExtra("tablenumber",tablenumber);
            startActivity(intent);
        }
    });


} catch (ParseException e) {
    Toast.makeText(getApplicationContext(), getString(R.string.exception),
            Toast.LENGTH_LONG).show();
}



    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course, menu);
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
