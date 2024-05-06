package org.challenge.messaging;

import lombok.AllArgsConstructor;

import java.util.Queue;

@AllArgsConstructor
public class MessagePublisherFactory implements MessagePublisher {

    private final Queue<String> queue;

    @Override
    public Object send(String message) {
        queue.offer(message);
        return "Message received.";
    }
}