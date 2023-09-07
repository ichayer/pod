package ar.edu.itba.pod.grpc.client;

import ar.edu.itba.pod.grpc.trainTickets.*;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TrainClient {
    private static final Logger logger = LoggerFactory.getLogger(TrainClient.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("grpc-streams TrainClient Starting ...");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        try {
            TrainTicketServiceGrpc.TrainTicketServiceBlockingStub blockingStub = TrainTicketServiceGrpc.newBlockingStub(channel);

            Destinations response = blockingStub.getDestinations(Empty.newBuilder().build());
            System.out.println("Destinations are: " + Arrays.toString(response.getDestinationsList().toArray()));

            List<Ticket> ticketsToRequest = new ArrayList<>();

            for (String dest : response.getDestinationsList()) {
                StringValue getDestinationName = StringValue.newBuilder().setValue(dest).build();
                Iterator<Train> iter = blockingStub.getTrainsForDestination(getDestinationName);
                while (iter.hasNext()) {
                    Train miau = iter.next();
                    if (ticketsToRequest.size() < 4 && (ticketsToRequest.isEmpty() || Math.random() < 0.5))
                        ticketsToRequest.add(Ticket.newBuilder().setTrainId(miau.getId()).setPassengerName("Pedro").setId("1").build());
                    System.out.format("Train for destination %s: id=%s, time=%s, availableCount=%d%n", dest, miau.getId(), miau.getTime(), miau.getAvailableCount());
                }
            }

            System.out.println("--------------------------------------------------");
            System.out.println("          PURCHASING TICKET");
            System.out.println("--------------------------------------------------");
            System.out.println("Will request the following tickets:");
            System.out.println(Arrays.toString(ticketsToRequest.toArray()));
            TrainTicketServiceGrpc.TrainTicketServiceStub stub = TrainTicketServiceGrpc.newStub(channel);

            StreamObserver<Ticket> ticketsStream = stub.purchaseTicket(new StreamObserver<Reservation>() {
                private Reservation reservation;

                @Override
                public void onNext(Reservation reservation) {
                    System.out.println("RETURNED RESERVATION: " + reservation);
                    this.reservation = reservation;
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("ERROR! F." + throwable);
                }

                @Override
                public void onCompleted() {
                    System.out.println("DONE :)");
                    getAndPrintReservation(stub, reservation);
                }
            });

            ticketsToRequest.forEach(ticketsStream::onNext);
            ticketsStream.onCompleted();
            channel.awaitTermination(10, TimeUnit.SECONDS);

        } finally {
            channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        }
    }

    private static void getAndPrintReservation(TrainTicketServiceGrpc.TrainTicketServiceStub stub, Reservation r) {
        StreamObserver<Reservation> reservationStreamObserver = stub.getTicketsForReservations(new StreamObserver<Ticket>() {
            @Override
            public void onNext(Ticket ticket) {
                System.out.println("Ticket: " + ticket);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("Done :)");
            }
        });

        System.out.println("---------------------------------");
        System.out.println("LISTING TICKETS FOR RESERVATION " + r.getId());
        System.out.println("---------------------------------");
        reservationStreamObserver.onNext(r);
        reservationStreamObserver.onCompleted();
    }
}
