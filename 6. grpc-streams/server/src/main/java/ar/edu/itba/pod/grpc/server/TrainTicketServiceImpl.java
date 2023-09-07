package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.trainTickets.*;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainTicketServiceImpl extends TrainTicketServiceGrpc.TrainTicketServiceImplBase {

    TicketRepository ticketRepository = new TicketRepository();

    @Override
    public void getDestinations(Empty request, StreamObserver<Destinations> responseObserver) {
        Destinations destinations = Destinations.newBuilder().addAllDestinations(ticketRepository.getDestinations()).build();
        responseObserver.onNext(destinations);
        responseObserver.onCompleted();
    }

    @Override
    public void getTrainsForDestination(StringValue request, StreamObserver<Train> responseObserver) {
        List<TrainModel> trains = ticketRepository.getAvailability(request.getValue());
        for (TrainModel t : trains) {
            Train pedro = Train.newBuilder()
                    .setId(t.id())
                    .setDestination(t.destination())
                    .setTime(t.time())
                    .setAvailableCount(t.availableSeats())
                    .build();

            responseObserver.onNext(pedro);
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Ticket> purchaseTicket(StreamObserver<Reservation> responseObserver) {
        return new StreamObserver<Ticket>() {
            final List<Ticket> tickets = new ArrayList<>();
            @Override
            public void onNext(Ticket ticket) {
                tickets.add(ticket);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                String reservationId = ticketRepository.addReservation(tickets);
                Reservation reservation = Reservation.newBuilder()
                        .setId(reservationId)
                        .setTicketCount(tickets.size())
                        .build();
                responseObserver.onNext(reservation);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Reservation> getTicketsForReservations(StreamObserver<Ticket> responseObserver) {
        return new StreamObserver<Reservation>() {
            @Override
            public void onNext(Reservation reservation) {
                Optional<List<Ticket>> pepe = ticketRepository.getReservation(reservation.getId());
                pepe.ifPresent(p -> p.forEach(responseObserver::onNext));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
