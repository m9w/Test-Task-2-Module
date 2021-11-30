package com.example.test;

import com.example.ShoppingCart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.ShoppingCart.ItemType.*;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void appendFormatted() {
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