package io.linkfast.demogrpc.wsrpc.base.proto;

import io.linkfast.demogrpc.Drivert.DrivertRs;
import io.linkfast.demogrpc.Drivert.DrivertUpdateRq;
import io.linkfast.demogrpc.user.UserRs;
import io.linkfast.demogrpc.user.UserUpdateRq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class DrivertServiceUpdateDrivertWsRpcBaseUnary extends  UnarySpringWsRpcHandler{

    @Override
    ByteBuffer process(ByteBuffer requestByteBuffer) throws IOException {
        DrivertUpdateRq request;
        request = DrivertUpdateRq.parseFrom(requestByteBuffer);
        final var response = updateDrivert(request);

        final var responseOutputStream = new ByteArrayOutputStream();
        response.writeTo(responseOutputStream);
        responseOutputStream.close();
        // Response
        return ByteBuffer.wrap(responseOutputStream.toByteArray());
    }

    protected abstract DrivertRs updateDrivert(DrivertUpdateRq request);

}
