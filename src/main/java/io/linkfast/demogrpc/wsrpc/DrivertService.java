package io.linkfast.demogrpc.wsrpc;
import io.linkfast.demogrpc.Drivert.DrivertRq;
import io.linkfast.demogrpc.Drivert.DrivertRs;
import io.linkfast.demogrpc.Drivert.DrivertUpdateRq;
import io.linkfast.demogrpc.Drivert.ListDrivertsResponse;
import io.linkfast.demogrpc.arangodb.entity.Drivert;
import io.linkfast.demogrpc.arangodb.entity.User;
import io.linkfast.demogrpc.arangodb.repository.DrivertRepository;
import io.linkfast.demogrpc.user.UserRs;
import io.linkfast.demogrpc.user.UserUpdateRq;
import io.linkfast.demogrpc.wsrpc.base.proto.DrivertServiceCreateDrivertWsRpcBaseUnary;
import io.linkfast.demogrpc.wsrpc.base.proto.DrivertServiceGetAllDrivertWsRpcBaseUnary;
import io.linkfast.demogrpc.wsrpc.base.proto.DrivertServiceUpdateDrivertWsRpcBaseUnary;
import io.linkfast.demogrpc.wsrpc.base.proto.UserServiceUpdateUserWsRpcBaseUnary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DrivertService {
    private final DrivertRepository drivertRepository;

    public DrivertService(DrivertRepository drivertRepository){
        this.drivertRepository = drivertRepository;
    }

    public DrivertServiceCreateDrivertWsRpcBaseUnary createDrivert(){
        return new DrivertServiceCreateDrivertWsRpcBaseUnary() {
            @Override
            protected DrivertRs createDrivert(DrivertRq request) {

                Drivert drivert = Drivert.builder()
                        .name(request.getName())
                        .dni(request.getDni())
                        .placa(request.getPlaca())
                        .marca(request.getMarca())
                        .status(request.getStatus())
                        .build();

                drivertRepository.save(drivert);


                return DrivertRs.newBuilder()
                        .setId(drivert.getId())
                        .setName(drivert.getName())
                        .setDni(drivert.getDni())
                        .setPlaca(drivert.getPlaca())
                        .setMarca(drivert.getMarca())
                        .setStatus(drivert.getStatus())


                        .build();
            }
        };
    }

    public DrivertServiceGetAllDrivertWsRpcBaseUnary getAllDrivert(){

        return new DrivertServiceGetAllDrivertWsRpcBaseUnary() {

            @Override
            protected ListDrivertsResponse getAllDriverts() {
                List<Drivert> listDrivert = drivertRepository.findAll();
                ListDrivertsResponse.Builder builder = ListDrivertsResponse.newBuilder();
                builder.addAllDriverts(listDrivert.stream().map(
                                drivert -> DrivertRs.newBuilder()
                                        .setId(drivert.getId())
                                        .setName(drivert.getName())
                                        .setDni(drivert.getDni())
                                        .setPlaca(drivert.getPlaca())
                                        .setMarca(drivert.getMarca())
                                        .setStatus(drivert.getStatus())

                                        .build()
                        ).collect(Collectors.toList())
                );
                return builder.build();
            }
        };

    }

    public DrivertServiceUpdateDrivertWsRpcBaseUnary updateDrivert() {
        return new DrivertServiceUpdateDrivertWsRpcBaseUnary() {
            @Override
            protected DrivertRs updateDrivert(DrivertUpdateRq request) {
                Optional<Drivert> drivertOptional = drivertRepository.findById(request.getId());
                if (drivertOptional.isPresent()) {
                    Drivert drivert = drivertOptional.get();

                    drivert.setName(request.getName());
                    drivert.setLastname(request.getLastname());
                    drivert.setDni(request.getLastname());
                    drivert.setPlaca(request.getPlaca());
                    drivert.setMarca(request.getMarca());
                    drivert.setStatus(request.getStatus());

                    drivertRepository.save(drivert);

                    return DrivertRs.newBuilder()
                            .setId(drivert.getId())
                            .setName(drivert.getName())
                            .setLastname(drivert.getLastname())
                            .setDni(drivert.getDni())
                            .setPlaca(drivert.getPlaca())
                            .setMarca(drivert.getMarca())
                            .setStatus(drivert.getStatus())
                            .build();
                } else {
                    throw new RuntimeException("User not found.");
                }
            }
        };
    }

}