package org.challenge.candidate;

import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.discounts.impl.BulkDiscount;
import org.challenge.candidate.models.Cart;
import org.challenge.candidate.models.Product;
import org.challenge.candidate.processors.CheckoutProcessor;
import org.challenge.candidate.processors.ScanProcessor;
import org.challenge.messaging.DomainError;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CashMachineTest {

    @Test
    void testScanProcessorAddItemToCart() {
        // Given
        var products = new HashMap<String, Product>();
        products.put("MUG", new Product("MUG", 10.0));
        var cart = new Cart();
        var scanProcessor = new ScanProcessor(products, cart);

        // When
        scanProcessor.process("MUG");

        // Then
        assert cart.getItems().containsKey(products.get("MUG"));
    }

    @Test
    void testScanProcessorUnknownItem() {
        // Given
        var products = new HashMap<String, Product>();
        var cart = new Cart();
        var scanProcessor = new ScanProcessor(products, cart);

        // When
        var result = scanProcessor.process("UNKNOWN");

        // Then
        assert result instanceof DomainError;
    }

    @Test
    void testScanProcessorCommandDone() {
        // Given
        var cart = new Cart();
        var scanProcessor = new ScanProcessor(new HashMap<>(), cart);

        // When
        var result = scanProcessor.process("done");

        // Then
        assert result instanceof String;
        assert ((String) result).contains("command");
        assert ((String) result).contains("done");
    }

    @Test
    void testCheckoutProcessorCalculateTotal() {
        // Given
        var cart = new Cart();
        cart.addItem(new Product("MUG", 10.0), 2);
        var checkoutProcessor = new CheckoutProcessor(cart, new HashMap<>());

        // When
        var result = checkoutProcessor.process("");

        // Then
        assert result instanceof String;
        assert Double.parseDouble((String) result) == 20.0;
    }

    @Test
    void testCheckoutProcessorWithDiscount() {
        // Given
        var cart = new Cart();
        var mug = new Product("MUG", 10.0);
        cart.addItem(mug, 3);
        var discounts = new HashMap<String, Discount>();
        discounts.put("MUG", new BulkDiscount());
        var checkoutProcessor = new CheckoutProcessor(cart, discounts);

        // When
        var result = checkoutProcessor.process("");

        // Then
        assert result instanceof String;
        assert Double.parseDouble((String) result) == 27.0;
    }
}