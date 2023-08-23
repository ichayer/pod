package ar.edu.itba.pod.concurrency.exercises.e2;

public class HelloThread extends Thread {

    @Override
    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String[] args) {
        Thread thread = new HelloThread();
        thread.start();
    }
}