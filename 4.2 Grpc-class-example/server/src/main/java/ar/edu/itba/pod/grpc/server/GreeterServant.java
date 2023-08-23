package ar.edu.itba.pod.grpc.server;

import ar.edu.itba.pod.grpc.GreeterGrpc;
import ar.edu.itba.pod.grpc.HelloReply;
import ar.edu.itba.pod.grpc.HelloRequest;
import io.grpc.stub.StreamObserver;

public class GreeterServant extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        // Hago lo que tengo que hacer con el request
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();

        // Esto va fijo para devolver la respuesta al cliente con el objeto observer
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
