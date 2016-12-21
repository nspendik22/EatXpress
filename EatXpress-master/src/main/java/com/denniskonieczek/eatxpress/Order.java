package com.denniskonieczek.eatxpress;


import java.util.ArrayList;
import java.util.List;

/**
 * DenNikRak - EatXpress v1.0
 * Dennis Konieczek
 * Nikolas Spendik
 * Rakul Mahenthiran
 *
 * Submission Date: Dec 8 2015
 * */
public class Order  {

    List<menuItem> order;
    List<Integer> quantity;


    public Order () {
        this.order = new ArrayList<menuItem>();
        this.quantity = new ArrayList<Integer>();
    }


    public void addToOrder(menuItem item, int q) {
        order.add(item);
        quantity.add(q);
    }
    public menuItem getOrder(int i) {
        return order.get(i);
    }
    public int getQuantity(int j) {
        return quantity.get(j);
    }
    public int getSize() {
        return  order.size();
    }

}
