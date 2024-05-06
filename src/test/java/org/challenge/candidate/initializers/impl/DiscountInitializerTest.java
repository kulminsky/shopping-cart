package org.challenge.candidate.initializers.impl;

import org.apache.log4j.Logger;
import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.discounts.impl.BulkDiscount;
import org.challenge.candidate.discounts.impl.TwoForOneDiscount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DiscountInitializerTest {
    private static final String TEMP_FILE_PATH = "temp.properties";

    private static final Logger LOGGER = Logger.getLogger(DiscountInitializerTest.class);

    @Before
    public void setUp() {
        try (var writer = new FileWriter(TEMP_FILE_PATH)) {
            writer.write("MUG=org.challenge.candidate.discounts.impl.TwoForOneDiscount\n");
            writer.write("TSHIRT=org.challenge.candidate.discounts.impl.BulkDiscount\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @After
    public void tearDown() {
        var file = new File(TEMP_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testInitializeDiscounts() {
        // Given
        var discountInitializer = new DiscountInitializer(TEMP_FILE_PATH);

        // When
        var discounts = discountInitializer.initialize();

        // Then
        assertEquals(2, discounts.size());
        assertTrue(discounts.containsKey("MUG"));
        assertTrue(discounts.containsKey("TSHIRT"));
        assertTrue(discounts.get("MUG") instanceof TwoForOneDiscount);
        assertTrue(discounts.get("TSHIRT") instanceof BulkDiscount);
    }
}