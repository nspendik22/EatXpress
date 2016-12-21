package com.denniskonieczek.eatxpress;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class menuItem {

    String username;
    String course;
    String type;
    String picname;
    String itemname;
    String description;
    String price;
    ImageView img;

    public menuItem(String username, String course, String type, String picname, String itemname, String description, String price, ImageView img) {

        this.username = username;
        this.course = course;
        this.type = type;
        this.picname = picname;
        this.itemname = itemname;
        this.description = description;
        this.price = price;
        this.img = img;

    }

    public String getUsername() {
        return username;
    }
    public String getCourse() {
        return course;
    }
    public String getType() {
        return type;
    }
    public String getPicname() {
        return picname;
    }
    public String getItemname() {
        return itemname;
    }
    public String getDescription() {
        return description;
    }
    public String getPrice() {
        return price;
    }
    public ImageView getImg() {return img;}
    public void setUsername(String x) {
         username = x;
    }
    public void setCourse(String x) {
        course = x;
    }
    public void setType(String x) {
        type = x;
    }
    public void setPicname(String x) {
        picname = x;
    }
    public void setItemname(String x) {
        itemname = x;
    }
    public void setDescription(String x) {
        description = x;
    }
    public void setPrice(String x) {
        price = x;
    }
    public void setImg (ImageView x) { img = x; }





}
