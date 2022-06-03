package io.linkfast.demogrpc.wsrpc;

import io.linkfast.demogrpc.arangodb.entity.Driver;
import io.linkfast.demogrpc.arangodb.entity.Position;
import io.linkfast.demogrpc.arangodb.entity.User;
import io.linkfast.demogrpc.arangodb.repository.DriverRepository;
import io.linkfast.demogrpc.arangodb.repository.PositionRepository;
import io.linkfast.demogrpc.arangodb.repository.UserRepository;
import io.linkfast.demogrpc.user.*;
import io.linkfast.demogrpc.wsrpc.base.proto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PositionRepository positionRepository;

    public UserService(
            UserRepository userRepository,
            DriverRepository driverRepository,
            PositionRepository positionRepository
    ) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.positionRepository = positionRepository;
    }

    public UserServiceCreateUserWsRpcBaseUnary createUser() {

        return new UserServiceCreateUserWsRpcBaseUnary() {

            @Override
            protected UserRs createUser(UserRq request) {

                User user = User.builder()
                        .name(request.getName())
                        .nickName(request.getNickName())
                        .address(request.getAddress())
                        .active(request.getActive())
                        .age(request.getAge())
                        .sex(request.getSex())
                        .build();

                userRepository.save(user);

                return UserRs.newBuilder()
                        .setId(user.getId())
                        .setName(user.getName())
                        .setNickName(user.getNickName())
                        .setAddress(user.getAddress())
                        .setActive(user.getActive())
                        .setAge(user.getAge())
                        .setSex(user.getSex())
                        .build();
            }

        };
    }

    public UserServiceDeleteUserWsRpcBaseUnary deleteUser() {
        return new UserServiceDeleteUserWsRpcBaseUnary() {
            @Override
            protected DeleteUserRs deleteUser(DeleteUserRq request) {
                Optional<User> userOptional = userRepository.findById(request.getId());
                userOptional.ifPresent(userRepository::delete);
                if (userRepository.findById(request.getId()).isEmpty()) {
                    return
                            DeleteUserRs.newBuilder()
                                    .setComplete(true)
                                    .build();
                } else {
                    return DeleteUserRs.newBuilder()
                            .setComplete(false)
                            .build();
                }
            }
        };
    }

    public UserServiceGetAllUserWsRpcBaseUnary getAllUsers() {
        return new UserServiceGetAllUserWsRpcBaseUnary() {
            @Override
            protected ListUsersResponse getAllUser() {
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
                return builder.build();
            }
        };
    }

    public UserServiceUpdateUserWsRpcBaseUnary updateUser() {
        return new UserServiceUpdateUserWsRpcBaseUnary() {
            @Override
            protected UserRs updateUser(UserUpdateRq request) {
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

                    return UserRs.newBuilder()
                            .setId(user.getId())
                            .setName(user.getName())
                            .setNickName(user.getNickName())
                            .setAddress(user.getAddress())
                            .setActive(user.getActive())
                            .setAge(user.getAge())
                            .setSex(user.getSex())
                            .build();
                } else {
                    throw new RuntimeException("User not found.");
                }
            }
        };
    }

    public UserServiceOnReceivedLocationDriverWsRpc onReceivedLocationDriver() {
        return new UserServiceOnReceivedLocationDriverWsRpc() {
            @Override
            protected void onReceivedLocationDriver(
                    GetPositionDriverRq request,
                    WsRpcStreamObserver<PositionRs> responseObserver
            ) {
                Optional<Driver> optionalDriver = driverRepository.findById(request.getIdDriver());

                double lat = 0.0;
                double lng = 0.0;

                if (optionalDriver.isPresent()) {

                    String positionId = "";

                    Optional<Position> optionalPositionFirst = Optional.ofNullable(optionalDriver.get().getPosition());
                    if (optionalPositionFirst.isPresent()) {
                        positionId = optionalPositionFirst.get().getId();
                    } else {
                        responseObserver.onNext(
                                PositionRs.newBuilder()
                                        .setLatitude(lat)
                                        .setLongitude(lng)
                                        .build()
                        );
                        responseObserver.onCompleted();
                    }


                    Boolean listen = true;

                    while (listen) {
                        Optional<Position> optionalPosition = positionRepository.findById(positionId);
                        if (optionalPosition.isPresent()) {

                            Position position = optionalPosition.get();
                            listen = position.getListen();
                            if (position.getListen()) {
                                if (position.getLatitude() != lat || position.getLongitude() != lng) {
                                    lat = position.getLatitude();
                                    lng = position.getLongitude();
                                    responseObserver.onNext(
                                            PositionRs.newBuilder()
                                                    .setLatitude(lat)
                                                    .setLongitude(lng)
                                                    .build()
                                    );
                                }
                            } else {
                                responseObserver.onNext(
                                        PositionRs.newBuilder()
                                                .setLatitude(lat)
                                                .setLongitude(lng)
                                                .build()
                                );
                                responseObserver.onCompleted();
                            }
                        } else {
                            listen = false;
                        }
                    }
                } else {
                    responseObserver.onNext(
                            PositionRs.newBuilder()
                                    .setLatitude(lat)
                                    .setLongitude(lng)
                                    .build()
                    );
                    responseObserver.onCompleted();
                }

            }
        };
    }

}
