package ar.edu.itba.pod.grpc.server;

public record TrainModel(String id, String destination, String time, int availableSeats) {
}
