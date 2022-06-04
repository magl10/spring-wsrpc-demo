package io.linkfast.demogrpc.wsrpc.base.proto;

import io.linkfast.demogrpc.Driver.DriverRq;
import io.linkfast.demogrpc.Drivert.DrivertRq;
import io.linkfast.demogrpc.Drivert.DrivertRs;
import io.linkfast.demogrpc.arangodb.entity.Drivert;
import io.linkfast.demogrpc.user.UserRs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class DrivertServiceCreateDrivertWsRpcBaseUnary extends UnarySpringWsRpcHandler {
    ByteBuffer process(ByteBuffer requestByteBuffer) throws IOException {
        DrivertRq request;
        request = DrivertRq.parseFrom(requestByteBuffer);
        final var response = createDrivert(request);
        final var responseOutputStream = new ByteArrayOutputStream();
        response.writeTo(responseOutputStream);
        responseOutputStream.close();
        // Response
        return ByteBuffer.wrap(responseOutputStream.toByteArray());
    }

    protected abstract DrivertRs createDrivert(DrivertRq request);
}