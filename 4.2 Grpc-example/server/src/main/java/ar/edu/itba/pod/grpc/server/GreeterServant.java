package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.GreeterGrpc;
import ar.edu.itba.pod.grpc.HelloReply;
import ar.edu.itba.pod.grpc.HelloRequest;
import io.grpc.stub.StreamObserver;

public class GreeterServant extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        // Do what you need to do with the request.
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();

        // This is needed to return the response to the client using the observer object.
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
