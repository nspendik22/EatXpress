package com.denniskonieczek.eatxpress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class Choice extends AppCompatActivity implements Serializable{

    private static final String TAG_MENU = "menu";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_COURSE = "course";
    private static final String TAG_TYPE = "type";
    private static final String TAG_PICNAME = "picname";
    private static final String TAG_ITEMNAME = "itemname";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PRICE = "price";

    JSONArray menuArray = null;
    //private ProgressDialog pDialog;
    String RestaurantName;
    String RUsername;
    String course;
    String drinkURL;
    String mainURL;
    String desertURL;
    String tablenumber;
    ArrayList<menuItem> drinkItems;
    ArrayList<menuItem> mainItems;
    ArrayList<menuItem> desertItems;
    Order FoodOrder;
    Button orderButton;

    public ArrayList<menuItem> getDrinks() {
        return drinkItems;
    }
    public ArrayList<menuItem> getMainItems() {
        return mainItems;
    }
    public ArrayList<menuItem> getDesertItems() {
        return desertItems;
    }
    public Order getOrder() {
        return FoodOrder;
    }
    public void addto(menuItem item, int q){
        FoodOrder.addToOrder(item, q);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Bundle extras = getIntent().getExtras();
        RestaurantName = extras.getString("RestaurantName");
        RUsername = extras.getString("username");
        course = extras.getString("course");
        tablenumber = extras.getString("tablenumber");


        setTitle(RestaurantName + getString(R.string.menu));

        FoodOrder = new Order();

        /* Get all drink items */
        drinkURL = "http://munro.humber.ca/~spnn0141/eatxpress/menu.php?username="+RUsername+"&course="+course+"&type=drink";
        mainURL = "http://munro.humber.ca/~spnn0141/eatxpress/menu.php?username="+RUsername+"&course="+course+"&type=main";
        desertURL = "http://munro.humber.ca/~spnn0141/eatxpress/menu.php?username="+RUsername+"&course="+course+"&type=desert";
        try {

            drinkItems = new GetMenu().execute(drinkURL).get();
            mainItems = new GetMenu().execute(mainURL).get();
            desertItems = new GetMenu().execute(desertURL).get();

        }catch (Exception e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.mainCourse)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.drinks)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.dessert)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        orderButton = (Button) findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderedItems = "";
                double totalPrice = 0;

                for (int x = 0; x <FoodOrder.getSize() ;x++) {
                    orderedItems += "(x"+ Integer.toString(FoodOrder.getQuantity(x)) + ") " + FoodOrder.getOrder(x).getItemname() + "  $" + FoodOrder.getOrder(x).getPrice() + "\n";
                    totalPrice += Double.parseDouble(FoodOrder.getOrder(x).getPrice()) * Double.parseDouble(Integer.toString(FoodOrder.getQuantity(x)));
                }

                Intent intent = new Intent(Choice.this , ConfirmOrder.class);
                intent.putExtra("order", orderedItems);
                intent.putExtra("total", Double.toString(totalPrice));
                intent.putExtra("name",RestaurantName);
                intent.putExtra("username",RUsername);
                intent.putExtra("tablenumber",tablenumber);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choice, menu);
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
    private class GetMenu extends AsyncTask<String, Void, ArrayList> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //ProgressDialog not working
        }

        @Override
        protected ArrayList doInBackground(String... arg0) {
            ArrayList<menuItem> thismenu;
            DownloadJSON jsonString = new DownloadJSON(arg0[0]);
            String json = jsonString.getJSON();
            thismenu = new ArrayList<menuItem>();
            try {
                JSONObject jsonObj = new JSONObject(json);
                menuArray = jsonObj.getJSONArray(TAG_MENU);
                for (int i = 0;i<menuArray.length();i++) {
                    JSONObject c = menuArray.getJSONObject(i);

                    thismenu.add(new menuItem(c.getString(TAG_USERNAME), c.getString(TAG_COURSE),
                            c.getString(TAG_TYPE), c.getString(TAG_PICNAME), c.getString(TAG_ITEMNAME),
                            c.getString(TAG_DESCRIPTION), c.getString(TAG_PRICE),null));


                    }
                Log.v("testt","got here");
            }catch (Exception e) {
                e.printStackTrace();
            }
            return thismenu;
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            super.onPostExecute(result);
        }

    } //end of GetMenu




}
