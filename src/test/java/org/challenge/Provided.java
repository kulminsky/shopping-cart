package org.challenge;

import lombok.Getter;
import lombok.Setter;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


@Getter
@Setter
public class Provided {
    private Queue<String> scanQueue;
    private Queue<String> checkoutQueue;

    public Provided() {
        this.scanQueue = new LinkedBlockingQueue<>();
        this.checkoutQueue = new LinkedBlockingQueue<>();
    }

}