package io.linkfast.demogrpc.grpc;

import io.grpc.stub.StreamObserver;
import io.linkfast.demogrpc.arangodb.entity.User;
import io.linkfast.demogrpc.arangodb.repository.UserRepository;
import io.linkfast.demogrpc.grpc.user.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    public UserGrpcService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserRq request, StreamObserver<UserRs> responseObserver) {

        User user = User.builder()
                .name(request.getName())
                .nickName(request.getNickName())
                .address(request.getAddress())
                .active(request.getActive())
                .age(request.getAge())
                .sex(request.getSex())
                .build();

        userRepository.save(user);

        responseObserver.onNext(
                UserRs.newBuilder()
                        .setId(user.getId())
                        .setName(user.getName())
                        .setNickName(user.getNickName())
                        .setAddress(user.getAddress())
                        .setActive(user.getActive())
                        .setAge(user.getAge())
                        .setSex(user.getSex())
                        .build()
        );

        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UserUpdateRq request, StreamObserver<UserRs> responseObserver) {

        Optional<User> userOptional = userRepository.findById(request.getId());
        if (userOptional.isPresent()) {

            User user = userOptional.get();

            user.setName(request.getName());
            user.setNickName(request.getNickName());
            user.setAddress(request.getAddress());
            user.setActive(request.getActive());
            user.setAge(request.getAge());
            user.setSex(request.getSex());

            userRepository.save(user);

            responseObserver.onNext(
                    UserRs.newBuilder()
                            .setId(user.getId())
                            .setName(user.getName())
                            .setNickName(user.getNickName())
                            .setAddress(user.getAddress())
                            .setActive(user.getActive())
                            .setAge(user.getAge())
                            .setSex(user.getSex())
                            .build()
            );
        }

        responseObserver.onCompleted();

    }

    @Override
    public void deleteUser(DeleteUserRq request, StreamObserver<DeleteUserRs> responseObserver) {

        Optional<User> userOptional = userRepository.findById(request.getId());
        userOptional.ifPresent(userRepository::delete);

        if (userRepository.findById(request.getId()).isEmpty()) {
            responseObserver.onNext(
                    DeleteUserRs.newBuilder()
                            .setComplete(true)
                            .build()
            );
        } else {
            responseObserver.onNext(
                    DeleteUserRs.newBuilder()
                            .setComplete(false)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void getAllUser(EmptyRq request, StreamObserver<ListUsersResponse> responseObserver) {

        List<User> listUsers = userRepository.findAll();
        ListUsersResponse.Builder builder = ListUsersResponse.newBuilder();

        builder.addAllUsers(listUsers.stream().map(
                user -> UserRs.newBuilder()
                        .setId(user.getId())
                        .setName(user.getName())
                        .setAddress(user.getAddress())
                        .setActive(user.getActive())
                        .setAge(user.getAge())
                        .setSex(user.getSex())
                        .build()
                ).collect(Collectors.toList())
        );

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
