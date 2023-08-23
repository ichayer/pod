package ar.edu.itba.pod.concurrency.exercises.e2;

import ar.edu.itba.pod.concurrency.exercises.e1.GenericService;
import ar.edu.itba.pod.concurrency.exercises.e1.GenericServiceImpl;

import java.util.stream.IntStream;

public class FiveRunnable implements Runnable {

    private static GenericService service;

    public FiveRunnable() {
        service = new GenericServiceImpl();
    }

    @Override
    public void run() {
        IntStream.range(0, 5).forEach(i -> {
            service.addVisit();
            System.out.println("Visit number " + service.getVisitCount());
        });
    }
}
