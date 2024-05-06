package org.challenge.candidate.discounts.impl;

import org.challenge.candidate.discounts.Discount;

public class BulkDiscount implements Discount {
    private static final double DISCOUNT_PERCENTAGE = 0.3;

    @Override
    public double applyDiscount(double price, int quantity) {
        if (quantity >= 3) {
            double discountAmount = price * DISCOUNT_PERCENTAGE;
            return price * quantity - discountAmount;
        }
        return price * quantity;
    }
}
