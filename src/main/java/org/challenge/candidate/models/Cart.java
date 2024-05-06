package org.challenge.candidate.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.challenge.candidate.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Setter
@Getter
public class Cart {

    private static final String KEY_VALUE = "value";

    private Map<Product, Integer> items;
    private double totalPrice = 0.0;
    private boolean checkout = false;

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }


    public void clearCart() {
        this.items = new HashMap<>();
        this.totalPrice = 0.0;
        checkout = false;
    }


    public String getTotalPriceJSON() {
        return JsonUtils.convertToJson(KEY_VALUE, String.valueOf(totalPrice));
    }
}
