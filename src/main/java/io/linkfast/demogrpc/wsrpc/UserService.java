package io.linkfast.demogrpc.wsrpc;

import io.linkfast.demogrpc.arangodb.entity.User;
import io.linkfast.demogrpc.arangodb.repository.UserRepository;
import io.linkfast.demogrpc.grpc.user.*;
import io.linkfast.demogrpc.wsrpc.base.proto.UserServiceCreateUserWsRpcBase;
import io.linkfast.demogrpc.wsrpc.base.proto.UserServiceDeleteUserWsRpcBase;
import io.linkfast.demogrpc.wsrpc.base.proto.UserServiceGetAllUserWsRpcBase;
import io.linkfast.demogrpc.wsrpc.base.proto.UserServiceUpdateUserWsRpcBase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public UserServiceCreateUserWsRpcBase createUser() {
        return new UserServiceCreateUserWsRpcBase() {
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

    public UserServiceDeleteUserWsRpcBase deleteUser() {
        return new UserServiceDeleteUserWsRpcBase() {
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

    public UserServiceGetAllUserWsRpcBase getAllUsers() {
        return new UserServiceGetAllUserWsRpcBase() {
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

    public UserServiceUpdateUserWsRpcBase updateUser() {
        return new UserServiceUpdateUserWsRpcBase() {
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

}
