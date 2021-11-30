package com.example;

import java.util.*;
import java.text.*;
/**
 * Containing items and calculating price.
 */
public class ShoppingCart {

    public enum ItemType {NEW, REGULAR, SECOND_FREE, SALE}

    private List<Item> items = new LinkedList<>();
    private static final NumberFormat MONEY = new DecimalFormat("$#.00", new DecimalFormatSymbols(Locale.ENGLISH));

    /**
     * Adds new item.
     *
     * @param title item title 1 to 32 symbols
     * @param price item price in USD, > 0
     * @param quantity item quantity, from 1
     * @param type item type
     *
     * @throws IllegalArgumentException if some value is wrong
     */
    public void addItem(String title, double price, int quantity, ItemType type){
        if (title == null || title.length() == 0 || title.length() > 32)
            throw new IllegalArgumentException("Illegal title");
        if (price < 0.01)
            throw new IllegalArgumentException("Illegal price");
        if (quantity <= 0)
            throw new IllegalArgumentException("Illegal quantity");
        Item item = new Item();
        item.title = title;
        item.price = price;
        item.quantity = quantity;
        item.type = type;
        items.add(item);
    }

    /**
     * Formats shopping price.
     *
     * @return string as lines, separated with \n,
     * first line: # Item Price Quan. Discount Total
     * second line: ---------------------------------------------------------
     * next lines: NN Title $PP.PP Q DD% $TT.TT
     * 1 Some title $.30 2 - $.60
     * 2 Some very long $100.00 1 50% $50.00
     * ...
     * 31 Item 42 $999.00 1000 - $999000.00
     * end line: ---------------------------------------------------------
     * last line: 31 $999050.60
     *
     * if no items in cart returns "No items." string.
     */
    public String formatTicket(){
        if (items.size() == 0) return "No items.";
        FormatTable format = new FormatTable("#","Item","Price","Quan.","Discount","Total").setAlign(1, -1, 1, 1, 1, 1);
        double total = 0.00;
        int index = 0;
        for (Item item : items) {
            int discount = calculateDiscount(item.type, item.quantity);
            double itemTotal = item.price * item.quantity * (100.00 - discount) / 100.00;
            format.addLine(
                    String.valueOf(++index),
                    item.title,
                    MONEY.format(item.price),
                    String.valueOf(item.quantity),
                    (discount == 0) ? "-" : (discount + "%"),
                    MONEY.format(itemTotal)
            );
            total += itemTotal;
        }
        return format.addLine(String.valueOf(index),"","","","", MONEY.format(total)).toString();
    }
    /**
     * Appends to sb formatted value.
     * Trims string if its length > width.
     * @param align -1 for align left, 0 for center and +1 for align right.
     */

    /**
     * Calculates item's discount.
     * For NEW item discount is 0%;
     * For SECOND_FREE item discount is 50% if quantity > 1
     * For SALE item discount is 70%
     * For each full 10 not NEW items item gets additional 1% discount,
     * but not more than 80% total
     */
    public static int calculateDiscount(ItemType type, int quantity){
        int discount = 0;
        switch (type) {
            case NEW: return 0;
            case REGULAR: discount = 0;
                break;
            case SECOND_FREE: if (quantity > 1) discount = 50;
                break;
            case SALE: discount = 70;
                break;
        }
        discount += quantity / 10;
        return Math.min(discount, 80);
    }

    private static class Item{
        String title;
        double price;
        int quantity;
        ItemType type;
    }
}