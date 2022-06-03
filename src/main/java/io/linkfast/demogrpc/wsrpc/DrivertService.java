package io.linkfast.demogrpc.wsrpc;

import io.linkfast.demogrpc.Drivert.DrivertRq;
import io.linkfast.demogrpc.Drivert.DrivertRs;
import io.linkfast.demogrpc.arangodb.entity.Drivert;
import io.linkfast.demogrpc.arangodb.repository.DrivertRepository;
import io.linkfast.demogrpc.wsrpc.base.proto.DrivertServiceCreateDrivertWsRpcBaseUnary;
import org.springframework.stereotype.Component;

@Component
public class DrivertService {
    DrivertRepository drivertRepository;

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
                        .status(request.getStatus())
                        .build();

                drivertRepository.save(drivert);


                return DrivertRs.newBuilder()
                        .setId(drivert.getId())
                        .setName(drivert.getName())
                        .setDni(drivert.getDni())

                        .build();
            }
        };
    }
}
