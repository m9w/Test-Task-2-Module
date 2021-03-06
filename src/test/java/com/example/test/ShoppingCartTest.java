package com.example.test;

import com.example.FormatTable;
import com.example.ShoppingCart;
import org.junit.jupiter.api.Test;

import static com.example.ShoppingCart.ItemType.*;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    @Test
    void appendFormatted() {
        StringBuilder builder;
        String[] exp = new String[]{"12"+" ".repeat(29), " ".repeat(14)+"12" + " ".repeat(15), " ".repeat(28)+"12 "};
        for (int i = -1; i <= 1; i++) {
            assertEquals(exp[i+1], FormatTable.appendFormatted("12", i,30));
            assertEquals("12".repeat(15) + " ", FormatTable.appendFormatted( "12".repeat(20), i, 30));
        }
    }

    @Test
    void calculateDiscount() {
        for (int i = 0; i < 10000; i++) {
            assertEquals(0, ShoppingCart.calculateDiscount(NEW, i));
            assertEquals(Math.min(i / 10, 80), ShoppingCart.calculateDiscount(REGULAR, i));
            assertEquals(i < 2 ? 0 : Math.min((i+500) / 10, 80), ShoppingCart.calculateDiscount(SECOND_FREE, i));
            assertEquals(Math.min((i+700) / 10, 80), ShoppingCart.calculateDiscount(SALE, i));
        }
    }
}