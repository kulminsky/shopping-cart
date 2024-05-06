package org.challenge.candidate.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.challenge.candidate.utils.JsonUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product {

    private static final String KEY_ITEM = "itemCode";

    private String code;
    private Double price;

    public String getProductJSON() {
        return JsonUtils.convertToJson(KEY_ITEM, code);
    }
}
