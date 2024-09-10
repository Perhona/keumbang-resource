package com.keumbang.api.resource_server.global.grpc.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        logMessage("Received call to", method.getFullMethodName());
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {

            @Override
            public void sendMessage(ReqT message) {
                // 요청 메시지 로깅
                logMessage("Request message", message.toString());
                super.sendMessage(message);
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                super.start(
                        new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {

                            @Override
                            public void onMessage(RespT message) {
                                // 응답 메시지 로깅
                                logMessage("Response message", message.toString());
                                super.onMessage(message);
                            }

                            @Override
                            public void onHeaders(Metadata headers) {
                                logMessage("Response headers", headers.toString());
                                super.onHeaders(headers);
                            }

                            @Override
                            public void onClose(Status status, Metadata trailers) {
                                // 응답 상태 로깅
                                logMessage("Response status", status.toString());
                                logMessage("Response trailers", trailers.toString());
                                super.onClose(status, trailers);
                            }
                        }, headers);
            }
        };

    }

    private void logMessage(String title, String message) {
        log.info("{} [{}]", title, message);
    }
}
