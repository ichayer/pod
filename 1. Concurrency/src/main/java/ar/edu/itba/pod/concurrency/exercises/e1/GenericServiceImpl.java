package ar.edu.itba.pod.concurrency.exercises.e1;

import java.util.*;

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
        /*
           Review: Optional
           Optional is a wrapper that encapsulates an object. According to
           object purists, performing a null check with an if statement would be "wrong". Optional avoids this.
           There are 3 ways to instance an optional: empty, ofNullable, and of.
         */
        return Optional.ofNullable(message).map(String::toUpperCase).orElse(null);
    }

    @Override
    public void addVisit() {
        visitCounter++;
    }

    @Override
    public int getVisitCount() {
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
