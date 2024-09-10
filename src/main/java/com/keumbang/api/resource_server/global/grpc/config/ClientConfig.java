package com.keumbang.api.resource_server.global.grpc.config;

import com.keumbang.api.resource_server.global.grpc.interceptor.LoggingInterceptor;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @GrpcGlobalClientInterceptor
    LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }
}
