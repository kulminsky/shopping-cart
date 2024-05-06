package org.challenge.candidate;

import lombok.extern.log4j.Log4j2;
import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.facades.CashMachineFacade;
import org.challenge.candidate.initializers.impl.DiscountInitializer;
import org.challenge.candidate.initializers.impl.ProductInitializer;
import org.challenge.candidate.models.Product;

import java.util.Map;

@Log4j2
public class CashMachine {

    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.properties";
    private static final String DISCOUNT_FILE_PATH = "src/main/resources/discount.properties";


    public ProductInitializer productInitializer;
    public DiscountInitializer discountInitializer;
    public Map<String, Product> products;
    public Map<String, Discount> discounts;
    public CashMachineFacade cashMachineFacade;

    public static void main(String[] args) {
        var cashMachine = new CashMachine();
        cashMachine.start();
    }

    private void start() {

        initialize();
        productInfo();

        cashMachineFacade.execute();
    }

    private void initialize() {
        productInitializer = new ProductInitializer(PRODUCTS_FILE_PATH);
        discountInitializer = new DiscountInitializer(DISCOUNT_FILE_PATH);
        products = productInitializer.initialize();
        discounts = discountInitializer.initialize();
        cashMachineFacade = new CashMachineFacade(2, products, discounts);
    }

    private void productInfo() {
        log.info("Product list: ");
        products.keySet().forEach(log::info);
        log.info("---------------\n");
    }
}
