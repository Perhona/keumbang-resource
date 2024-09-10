package com.keumbang.api.resource_server.global.grpc.client;

import com.keumbang.api.auth_server.grpc.AuthServiceGrpc;
import com.keumbang.api.auth_server.grpc.ValidateTokenRequest;
import com.keumbang.api.auth_server.grpc.ValidateTokenResponse;
import com.keumbang.api.resource_server.global.common.exception.JwtAuthenticationException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class AuthGrpcClient {
    @GrpcClient("keumbang-auth")
    private AuthServiceGrpc.AuthServiceBlockingStub stub;

    public ValidateTokenResponse validateToken(String accessToken) {
        ValidateTokenRequest validateRequest = ValidateTokenRequest.newBuilder()
                .setAccessToken(accessToken)
                .build();

        try {
            return stub.validateToken(validateRequest);
        } catch (StatusRuntimeException e) {
            throw new JwtAuthenticationException(e.getLocalizedMessage());
        }
    }
}
