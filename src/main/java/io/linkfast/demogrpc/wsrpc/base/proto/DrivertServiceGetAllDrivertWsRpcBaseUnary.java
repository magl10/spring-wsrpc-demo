package io.linkfast.demogrpc.wsrpc.base.proto;

import io.linkfast.demogrpc.Drivert.ListDrivertsResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class DrivertServiceGetAllDrivertWsRpcBaseUnary extends UnarySpringWsRpcHandler{
    ByteBuffer process(ByteBuffer requestByteBuffer) throws IOException {
        final var response = getAllDriverts();
        final var responseOutputStream = new ByteArrayOutputStream();
        response.writeTo(responseOutputStream);
        responseOutputStream.close();
        // Response
        return ByteBuffer.wrap(responseOutputStream.toByteArray());
    }

    protected abstract ListDrivertsResponse getAllDriverts();
}
