package org.challenge.candidate.services;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.challenge.candidate.facades.CashMachineFacade;
import org.challenge.candidate.models.Cart;
import org.challenge.candidate.models.Commands;
import org.challenge.candidate.processors.ScanProcessor;
import org.challenge.candidate.utils.JsonUtils;
import org.challenge.candidate.utils.ThreadUtils;
import org.challenge.messaging.DomainError;
import org.challenge.messaging.MessageConsumerFactory;
import org.challenge.messaging.MessagePublisherFactory;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

@AllArgsConstructor
public class ScanService {
    private static final String KEY_ERROR = "error";
    private static final Logger LOGGER = Logger.getLogger(ScanService.class);

    private final Cart cart;
    private final ExecutorService executor;
    private final Queue<String> checkoutQueue;
    private final MessagePublisherFactory scanPublisherFactory;
    private final MessageConsumerFactory scanConsumerFactory;
    private final ScanProcessor scanProcessor;

    public void startScan() {
        executor.submit(() -> {
            var scanner = new Scanner(System.in);
            LOGGER.info("Scanned item: ");

            while (CashMachineFacade.inProcess) {
                try {
                    var scannedItem = scanner.nextLine().trim();
                    var process = scanProcessor.process(scannedItem);

                    handleProcessResult(process, scannedItem);

                    ThreadUtils.countDown();
                    ThreadUtils.sleep(1000);

                    var total = scanConsumerFactory.consume(checkoutQueue);

                    if (total instanceof String) {
                        handleCheckoutTotal();
                    }
                } catch (NoSuchElementException e) {
                    LOGGER.error("Error during scanning: " + e.getMessage());
                }
            }
        });
    }

    private void handleProcessResult(Object process, String scannedItem) {
        if (process instanceof DomainError error) {
            LOGGER.error(JsonUtils.convertToJson(KEY_ERROR, error.getMessage()));
        }
        if (process instanceof String result) {
            LOGGER.info(result);
            handleCommands(scannedItem);
            scanPublisherFactory.send(scannedItem);
        }
    }

    private void handleCommands(String scannedItem) {
        if (scannedItem.equalsIgnoreCase(Commands.DONE.toString())) {
            CashMachineFacade.inProcess = false;
        }
        if (scannedItem.equalsIgnoreCase(Commands.CHECKOUT.toString())) {
            cart.setCheckout(true);
        }
    }

    private void handleCheckoutTotal() {
        LOGGER.info(cart.getTotalPriceJSON());
        cart.clearCart();
        LOGGER.info("Scanned item: ");
    }
}
