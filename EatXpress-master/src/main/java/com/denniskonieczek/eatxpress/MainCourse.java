package com.denniskonieczek.eatxpress;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class MainCourse extends Fragment {

    ArrayList<menuItem> data;
    ListView lv;
    CustomAdapter adapter;
    TextView dialogTitle;
    TextView dialogDescription;
    Button dialogButtonDone;
    Button dialogButtonCancel;
    Button plus;
    Button minus;
    EditText quantity;
    ImageView img;
    Order order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Choice activity = (Choice) getActivity();
        data = activity.getMainItems();
        order = activity.getOrder();
        View rootView = inflater.inflate(R.layout.activity_drinks, container, false);
        lv = (ListView) rootView.findViewById(R.id.list);
        adapter = new CustomAdapter(getActivity(), data);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.maindialog);

                img = (ImageView) dialog.findViewById(R.id.dialogImage);
                Picasso.with(getContext())
                        .load("http://munro.humber.ca/~spnn0141/eatxpress/pics/bpizza/"+data.get(position).getPicname())
                        .into(img);

                dialogTitle = (TextView) dialog.findViewById(R.id.dialogtitle);
                dialogTitle.setText(data.get(position).getItemname() + " $" + data.get(position).getPrice());

                dialogDescription = (TextView) dialog.findViewById(R.id.dialogDescription);
                dialogDescription.setText(data.get(position).getDescription());
                dialogDescription.setMovementMethod(new ScrollingMovementMethod());

                quantity = (EditText) dialog.findViewById(R.id.quantity);

                dialogButtonDone = (Button) dialog.findViewById(R.id.dialogDone);
                dialogButtonDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        order.addToOrder(data.get(position), Integer.parseInt(quantity.getText().toString()));
                        dialog.dismiss();
                    }
                });
                dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogCancel);
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                plus = (Button) dialog.findViewById(R.id.plus);
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        quantity.setText(Integer.toString((Integer.parseInt(quantity.getText().toString()) + 1)));
                    }
                });
                minus = (Button) dialog.findViewById(R.id.minus);
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!quantity.getText().toString().equals("0")) {
                            quantity.setText(Integer.toString((Integer.parseInt(quantity.getText().toString()) - 1)));
                        }
                    }
                });


                dialog.show();
            }
        });

        return rootView;
    }

}

