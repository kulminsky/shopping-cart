package org.challenge.candidate.utils;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CountDownLatch;

@Log4j2
public class ThreadUtils {

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public static void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public static void countDown() {
        latch.countDown();
    }
}
