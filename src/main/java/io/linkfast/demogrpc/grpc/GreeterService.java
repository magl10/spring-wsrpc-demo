package io.linkfast.demogrpc.grpc;

import io.grpc.stub.StreamObserver;
import io.linkfast.demogrpc.arangodb.entity.User;
import io.linkfast.demogrpc.arangodb.repository.UserRepository;

import io.linkfast.demogrpc.grpc.greeter.GreeterGrpc;
import io.linkfast.demogrpc.grpc.greeter.GreeterReply;
import io.linkfast.demogrpc.grpc.greeter.GreeterRequest;

public class GreeterService extends GreeterGrpc.GreeterImplBase {

    private final UserRepository userRepository;

    public GreeterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void greeter(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        userRepository.save(
                User.builder()
                        .name("ANDRES")
                        .nickName("VSOTELO")
                        .build()
        );
        responseObserver.onNext(
                GreeterReply.newBuilder()
                        .setMessage("Hola Mundo!")
                        .build()
        );
        responseObserver.onCompleted();
    }
}
