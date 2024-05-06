package org.challenge.candidate.initializers.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.initializers.Initializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Log4j2
@AllArgsConstructor
public class DiscountInitializer implements Initializer<Discount> {
    private final String filePath;

    @Override
    public Map<String, Discount> initialize() {
        var properties = new Properties();
        var discounts = new HashMap<String, Discount>();
        try (var fis = new FileInputStream(filePath)) {
            properties.load(fis);
            properties.stringPropertyNames().forEach(code -> {
                var discountClassName = properties.getProperty(code);
                try {
                    var discountClass = Class.forName(discountClassName);
                    var discountObject = discountClass.getDeclaredConstructor().newInstance();
                    if (discountObject instanceof Discount discount) {
                        discounts.put(code, discount);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return discounts;
    }
}
