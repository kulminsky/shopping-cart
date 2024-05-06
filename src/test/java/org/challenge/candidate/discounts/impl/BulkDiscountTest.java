package org.challenge.candidate.discounts.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulkDiscountTest {

    private final double PRICE = 10.0;

    @Test
    void testApplyDiscount_NoDiscount() {
        // Given
        var bulkDiscount = new BulkDiscount();
        var quantityWithoutDiscount = 2;
        var expectedResultWithoutDiscount = 20.0;

        // When
        var discountedPrice = bulkDiscount.applyDiscount(PRICE, quantityWithoutDiscount);

        // Then
        assertEquals(expectedResultWithoutDiscount, discountedPrice);
    }

    @Test
    void testApplyDiscount_WithDiscount() {
        // Given
        var bulkDiscount = new BulkDiscount();
        var quantityWithDiscount = 3;
        var expectedResultWithoutDiscount = 27.0;

        // When
        var discountedPrice = bulkDiscount.applyDiscount(PRICE, quantityWithDiscount);

        // Then
        assertEquals(expectedResultWithoutDiscount, discountedPrice);
    }

    @Test
    void testApplyDiscount_WithHigherQuantity() {
        // Given
        var bulkDiscount = new BulkDiscount();
        var expectedResultHigherQuantity = 47.0;
        var higherQuantity = 5;

        // When
        var discountedPrice = bulkDiscount.applyDiscount(PRICE, higherQuantity);

        // Then
        assertEquals(expectedResultHigherQuantity, discountedPrice);
    }
}