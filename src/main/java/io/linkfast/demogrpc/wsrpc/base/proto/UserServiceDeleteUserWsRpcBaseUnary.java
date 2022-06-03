package io.linkfast.demogrpc.wsrpc.base.proto;

import io.linkfast.demogrpc.user.DeleteUserRq;
import io.linkfast.demogrpc.user.DeleteUserRs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class UserServiceDeleteUserWsRpcBaseUnary extends UnarySpringWsRpcHandler {

    ByteBuffer process(ByteBuffer requestByteBuffer) throws IOException {
        DeleteUserRq request;
        request = DeleteUserRq.parseFrom(requestByteBuffer);
        final var response = deleteUser(request);
        final var responseOutputStream = new ByteArrayOutputStream();
        response.writeTo(responseOutputStream);
        responseOutputStream.close();
        // Response
        return ByteBuffer.wrap(responseOutputStream.toByteArray());
    }

    protected abstract DeleteUserRs deleteUser(DeleteUserRq request);

}
