package io.linkfast.demogrpc.wsrpc.base.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class ServerStreamingSpringWsRpcHandler extends BinaryWebSocketHandler {

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        final var byteBuffer = message.getPayload();
        final var responseObserver = new WsRpcStreamObserver<ByteBuffer>() {
            @Override
            public void onNext(ByteBuffer response) {
                try {
                    session.sendMessage(new BinaryMessage(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCompleted() {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception ex) {
                // TODO
            }
        };
        process(byteBuffer, responseObserver);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            session.sendMessage(new TextMessage("Text messages are not supported by wsRPC."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract void process(
            ByteBuffer requestByteBuffer,
            WsRpcStreamObserver<ByteBuffer> responseObserverByteBuffer
    ) throws IOException;
}
