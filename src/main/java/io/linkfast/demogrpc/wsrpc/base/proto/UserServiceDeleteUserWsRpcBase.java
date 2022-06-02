package io.linkfast.demogrpc.wsrpc.base.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import io.linkfast.demogrpc.grpc.user.DeleteUserRq;
import io.linkfast.demogrpc.grpc.user.DeleteUserRs;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class UserServiceDeleteUserWsRpcBase extends SpringWsRpcHandler {

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
