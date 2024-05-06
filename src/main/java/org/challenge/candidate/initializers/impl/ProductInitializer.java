package org.challenge.candidate.initializers.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.challenge.candidate.initializers.Initializer;
import org.challenge.candidate.models.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Log4j2
@AllArgsConstructor
public class ProductInitializer implements Initializer<Product> {
    private final String filePath;

    @Override
    public Map<String, Product> initialize() {
        var properties = new Properties();
        var products = new HashMap<String, Product>();
        try (var fis = new FileInputStream(filePath)) {
            properties.load(fis);
            properties.stringPropertyNames().forEach(code -> {
                var property = properties.getProperty(code);
                if (!property.isEmpty()) {
                    var price = Double.parseDouble(property);
                    products.put(code, new Product(code, price));
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return products;
    }
}
