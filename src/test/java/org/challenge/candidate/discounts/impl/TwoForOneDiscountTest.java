package org.challenge.candidate.discounts.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TwoForOneDiscountTest {

    private final double PRICE = 10.0;

    @Test
    void testApplyDiscountEvenQuantity() {
        // Given
        var twoForOneDiscount = new TwoForOneDiscount();
        var quantity = 4;
        var expectedResult = 20.0;

        // When
        var discountedPrice = twoForOneDiscount.applyDiscount(PRICE, quantity);

        // Then
        assertEquals(expectedResult, discountedPrice, 0.01);
    }

    @Test
    void testApplyDiscountOddQuantity() {
        // Given
        var twoForOneDiscount = new TwoForOneDiscount();
        var quantity = 5;
        var expectedResult = 30.0;

        // When
        var discountedPrice = twoForOneDiscount.applyDiscount(PRICE, quantity);

        // Then
        assertEquals(expectedResult, discountedPrice, 0.01);
    }

    @Test
    void testApplyDiscountSingleItem() {
        // Given
        var twoForOneDiscount = new TwoForOneDiscount();
        var quantity = 1;
        var expectedResult = 10.0;

        // When
        var discountedPrice = twoForOneDiscount.applyDiscount(PRICE, quantity);

        // Then
        assertEquals(expectedResult, discountedPrice, 0.01);
    }

    @Test
    void testApplyDiscount_NoItems() {
        // Given
        var twoForOneDiscount = new TwoForOneDiscount();
        var quantity = 0;
        var expectedResult = 0.0;

        // When
        var discountedPrice = twoForOneDiscount.applyDiscount(PRICE, quantity);

        // Then
        assertEquals(expectedResult, discountedPrice, 0.01);
    }
}