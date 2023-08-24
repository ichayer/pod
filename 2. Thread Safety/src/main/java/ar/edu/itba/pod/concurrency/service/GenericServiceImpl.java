package ar.edu.itba.pod.concurrency.service;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * Basic implementation of {@link GenericService}.
 */
public class GenericServiceImpl implements GenericService {

    private static Queue<String> queue;
    private int visitCounter;

    public GenericServiceImpl() {
        visitCounter = 0;
        queue = new LinkedList<>();
    }

    @Override
    public String echo(String message) {
        return message;
    }

    @Override
    public String toUpper(String message) {
        return Optional.ofNullable(message).map(String::toUpperCase).orElse(null);
    }

    @Override
    public synchronized void addVisit() {
        visitCounter++;
    }

    @Override
    public synchronized int getVisitCount() {
        return visitCounter;
    }

    @Override
    public boolean isServiceQueueEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void addToServiceQueue(String name) {
        queue.add(name);
    }

    @Override
    public String getFirstInServiceQueue() {
        return Optional.ofNullable(queue.poll()).orElseThrow(() -> new IllegalStateException("No one in queue"));
    }
}
