package org.challenge.candidate.processors;

import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.discounts.impl.BulkDiscount;
import org.challenge.candidate.discounts.impl.TwoForOneDiscount;
import org.challenge.candidate.models.Cart;
import org.challenge.candidate.models.Product;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
 class CheckoutProcessorTest {

    @Test
    void testProcess_NoDiscount() {
        // Given
        var cart = new Cart();
        var discounts = new HashMap<String, Discount>();
        discounts.put("MUG", new TwoForOneDiscount());
        discounts.put("TSHIRT", new BulkDiscount());
        var checkoutProcessor = new CheckoutProcessor(cart, discounts);

        var mug = new Product("MUG", 10.0);
        var tshirt = new Product("TSHIRT", 20.0);
        cart.addItem(mug, 2); // No discount applied
        cart.addItem(tshirt, 3); // No discount applied

        // When
        checkoutProcessor.process(null);

        // Then
        assertEquals(64.0, cart.getTotalPrice(), 0.01);
    }

    @Test
    public void testProcess_WithDiscount() {
        // Given
        var cart = new Cart();
        var discounts = new HashMap<String, Discount>();
        discounts.put("MUG", new TwoForOneDiscount());
        discounts.put("TSHIRT", new BulkDiscount());
        var checkoutProcessor = new CheckoutProcessor(cart, discounts);

        var mug = new Product("MUG", 10.0);
        var tshirt = new Product("TSHIRT", 20.0);
        cart.addItem(mug, 3); // 2 for 1 discount applied
        cart.addItem(tshirt, 5); // Bulk discount applied

        // When
        checkoutProcessor.process(null);

        // Then
        assertEquals(114.0, cart.getTotalPrice(), 0.01);
    }
}