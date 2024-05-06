package org.challenge.candidate.services;

import lombok.AllArgsConstructor;
import org.challenge.candidate.facades.CashMachineFacade;
import org.challenge.candidate.models.Cart;
import org.challenge.candidate.processors.CheckoutProcessor;
import org.challenge.candidate.utils.ThreadUtils;
import org.challenge.messaging.MessageConsumerFactory;
import org.challenge.messaging.MessagePublisherFactory;

import java.util.Queue;
import java.util.concurrent.ExecutorService;

@AllArgsConstructor
public class CheckoutService {

    private final Cart cart;

    private final ExecutorService executor;
    private final Queue<String> scanQueue;
    private final MessagePublisherFactory checkoutPublisherFactory;
    private final MessageConsumerFactory checkoutConsumerFactory;
    private final CheckoutProcessor checkoutProcessor;

    public void startCheckout() {
        executor.submit(() -> {

            ThreadUtils.await();

            while (CashMachineFacade.inProcess) {

                ThreadUtils.sleep(500);

                if (cart.isCheckout()) {
                    Object result = checkoutConsumerFactory.consume(scanQueue);
                    if (result instanceof String message) {
                        String total = (String) checkoutProcessor.process(message);
                        cart.setCheckout(false);
                        checkoutPublisherFactory.send(total);
                    }
                }
            }
        });
    }
}
