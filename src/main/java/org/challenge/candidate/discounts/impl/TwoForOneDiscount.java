package org.challenge.candidate.discounts.impl;

import org.challenge.candidate.discounts.Discount;

public class TwoForOneDiscount implements Discount {
    @Override
    public double applyDiscount(double price, int quantity) {
        int discountedQuantity = quantity / 2;
        int remainingQuantity = quantity % 2;
        return (discountedQuantity * price) + (remainingQuantity * price);
    }
}
