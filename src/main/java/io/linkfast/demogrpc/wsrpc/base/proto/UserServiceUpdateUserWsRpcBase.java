package io.linkfast.demogrpc.wsrpc.base.proto;

import io.linkfast.demogrpc.user.UserRs;
import io.linkfast.demogrpc.user.UserUpdateRq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class UserServiceUpdateUserWsRpcBase extends SpringWsRpcHandler {

    ByteBuffer process(ByteBuffer requestByteBuffer) throws IOException {
        UserUpdateRq request;
        request = UserUpdateRq.parseFrom(requestByteBuffer);
        final var response = updateUser(request);
        final var responseOutputStream = new ByteArrayOutputStream();
        response.writeTo(responseOutputStream);
        responseOutputStream.close();
        // Response
        return ByteBuffer.wrap(responseOutputStream.toByteArray());
    }

    protected abstract UserRs updateUser(UserUpdateRq request);

}
