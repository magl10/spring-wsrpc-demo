package io.linkfast.demogrpc.wsrpc.base.proto;

import io.linkfast.demogrpc.user.GetPositionDriverRq;
import io.linkfast.demogrpc.user.PositionRs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class UserServiceOnReceivedLocationDriverWsRpc extends ServerStreamingSpringWsRpcHandler {

    @Override
    void process(
            ByteBuffer requestByteBuffer,
            WsRpcStreamObserver<ByteBuffer> responseObserverByteBuffer) throws IOException {
        GetPositionDriverRq request;
        request = GetPositionDriverRq.parseFrom(requestByteBuffer);
        final var responseObserver = new WsRpcStreamObserver<PositionRs>() {
            @Override
            public void onNext(PositionRs response) {
                final var responseOutputStream = new ByteArrayOutputStream();
                try {
                    response.writeTo(responseOutputStream);
                    responseOutputStream.close();
                    responseObserverByteBuffer.onNext(ByteBuffer.wrap(responseOutputStream.toByteArray()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCompleted() {
                responseObserverByteBuffer.onCompleted();
            }
            @Override
            public void onError(Exception ex) {
            }
        };
        onReceivedLocationDriver(request, responseObserver);
    }

    protected abstract void onReceivedLocationDriver(GetPositionDriverRq request,
                                                     WsRpcStreamObserver<PositionRs> responseObserver);
}
