package io.linkfast.demogrpc.wsrpc.base.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class SpringWsRpcHandler extends BinaryWebSocketHandler {
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        final var byteBuffer = message.getPayload();
        try {
            final var responseByteBuffer = process(byteBuffer);
            session.sendMessage(new BinaryMessage(responseByteBuffer));
            session.close();
        } catch (InvalidProtocolBufferException ex) {
            session.sendMessage(new TextMessage("Data is not protobuf."));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            session.sendMessage(new TextMessage("Text messages are not supported by wsRPC."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract ByteBuffer process(ByteBuffer requestByteBuffer) throws IOException;
}
