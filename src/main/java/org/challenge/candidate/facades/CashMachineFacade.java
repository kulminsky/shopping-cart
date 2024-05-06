package org.challenge.candidate.facades;

import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.models.Cart;
import org.challenge.candidate.models.Product;
import org.challenge.candidate.processors.CheckoutProcessor;
import org.challenge.candidate.processors.ScanProcessor;
import org.challenge.candidate.services.CheckoutService;
import org.challenge.candidate.services.ScanService;
import org.challenge.messaging.MessageConsumerFactory;
import org.challenge.messaging.MessagePublisherFactory;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CashMachineFacade {

    public static boolean inProcess = true;
    private final ExecutorService executor;
    private final ScanService scanService;
    private final CheckoutService checkoutService;

    public CashMachineFacade(int nThreads, Map<String, Product> products, Map<String, Discount> discounts) {

        var cart = new Cart();
        executor = Executors.newFixedThreadPool(nThreads);

        var scanQueue = new LinkedList<String>();
        var checkoutQueue = new LinkedList<String>();

        var scanPublisherFactory = new MessagePublisherFactory(scanQueue);
        var scanConsumerFactory = new MessageConsumerFactory();

        var checkoutPublisherFactory = new MessagePublisherFactory(checkoutQueue);
        var checkoutConsumerFactory = new MessageConsumerFactory();

        var scanProcessor = new ScanProcessor(products, cart);
        var checkoutProcessor = new CheckoutProcessor(cart, discounts);

        scanService = new ScanService(cart, executor, checkoutQueue, scanPublisherFactory, scanConsumerFactory, scanProcessor);
        checkoutService = new CheckoutService(cart, executor, scanQueue, checkoutPublisherFactory, checkoutConsumerFactory, checkoutProcessor);
    }

    public void execute() {

        scanService.startScan();
        checkoutService.startCheckout();

        executor.shutdown();
    }


}
