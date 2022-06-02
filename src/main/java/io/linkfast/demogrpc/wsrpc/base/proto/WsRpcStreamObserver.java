package io.linkfast.demogrpc.wsrpc.base.proto;

public interface WsRpcStreamObserver<T> {
    void onNext(T response);
    void onCompleted();
    void onError(Exception ex);
}
