package org.challenge.candidate.processors;

import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.models.Cart;
import org.challenge.messaging.Processor;

import java.util.Map;

public class CheckoutProcessor implements Processor {

    private final Cart cart;
    private final Map<String, Discount> discounts;

    public CheckoutProcessor(Cart cart, Map<String, Discount> discounts) {
        this.cart = cart;
        this.discounts = discounts;
    }

    @Override
    public Object process(String input) {
        var total = 0.0;
        for (var entry : cart.getItems().entrySet()) {
            var product = entry.getKey();
            var quantity = entry.getValue();
            var price = product.getPrice();
            if (discounts.containsKey(product.getCode())) {
                var discount = discounts.get(product.getCode());
                price = discount.applyDiscount(price, quantity);
                total += price;
            } else {
                total += price * quantity;
            }
        }
        cart.setTotalPrice(total);
        return String.valueOf(total);
    }
}
