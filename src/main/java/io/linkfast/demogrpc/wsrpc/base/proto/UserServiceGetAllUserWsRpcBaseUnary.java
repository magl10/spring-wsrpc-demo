package io.linkfast.demogrpc.wsrpc.base.proto;

import io.linkfast.demogrpc.user.ListUsersResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class UserServiceGetAllUserWsRpcBaseUnary extends UnarySpringWsRpcHandler {

    ByteBuffer process(ByteBuffer requestByteBuffer) throws IOException {
        final var response = getAllUser();
        final var responseOutputStream = new ByteArrayOutputStream();
        response.writeTo(responseOutputStream);
        responseOutputStream.close();
        // Response
        return ByteBuffer.wrap(responseOutputStream.toByteArray());
    }

    protected abstract ListUsersResponse getAllUser();
}
