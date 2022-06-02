package io.linkfast.demogrpc.wsrpc.base.proto;


import io.linkfast.demogrpc.user.UserRq;
import io.linkfast.demogrpc.user.UserRs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class UserServiceCreateUserWsRpcBase extends SpringWsRpcHandler {

    ByteBuffer process(ByteBuffer requestByteBuffer) throws IOException {
        UserRq request;
        request = UserRq.parseFrom(requestByteBuffer);
        final var response = createUser(request);
        final var responseOutputStream = new ByteArrayOutputStream();
        response.writeTo(responseOutputStream);
        responseOutputStream.close();
        // Response
       return ByteBuffer.wrap(responseOutputStream.toByteArray());
    }

    protected abstract UserRs createUser(UserRq request);

}
