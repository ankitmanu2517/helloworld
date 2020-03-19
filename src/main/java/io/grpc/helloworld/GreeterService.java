package io.grpc.helloworld;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class GreeterService extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> response) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello 123" + request.getName()).build();
        response.onNext(reply);
        response.onCompleted();
    }
}