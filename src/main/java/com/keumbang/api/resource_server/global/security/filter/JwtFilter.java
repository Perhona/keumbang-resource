package com.keumbang.api.resource_server.global.security.filter;

import com.keumbang.api.auth_server.grpc.ValidateTokenResponse;
import com.keumbang.api.resource_server.global.common.exception.ErrorCode;
import com.keumbang.api.resource_server.global.common.exception.JwtAuthenticationException;
import com.keumbang.api.resource_server.global.grpc.client.AuthGrpcClient;
import com.keumbang.api.resource_server.global.security.user.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

import static com.keumbang.api.resource_server.global.security.config.SecurityConfig.PERMIT_URL_ARRAY;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final AuthGrpcClient authGrpcClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 허용된 URL인 경우, 필터 체인을 그대로 진행
        if (isPermittedUrl(request.getServletPath())) {
            log.info("허용된 URL");
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        try {
            // Authorization 헤더 검증
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new JwtAuthenticationException(ErrorCode.AUTHORIZATION_HEADER_MISSING);
            }

            //Bearer 부분 제거 후 순수 토큰만 획득
            String accessToken = authorization.split(" ")[1];

            //토큰 유효성 검증
            ValidateTokenResponse validateTokenResponse = authGrpcClient.validateToken(accessToken);
            if (!validateTokenResponse.getIsValid()) {
                throw new JwtAuthenticationException(ErrorCode.valueOf(validateTokenResponse.getErrorCode()));
            }

            //세션에 사용자 등록
            CustomUserDetails userDetails = new CustomUserDetails(validateTokenResponse.getUserId(), validateTokenResponse.getAccount());

            //스프링 시큐리티 인증 토큰 생성
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException e) {
            handleException(request, response, e, filterChain);
        } catch (Exception e) {
            handleException(request, response, new JwtAuthenticationException(ErrorCode.INTERNAL_SERVER_ERROR, e), filterChain);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, JwtAuthenticationException e, FilterChain filterChain) throws IOException, ServletException {
        request.setAttribute("exception", e);
        filterChain.doFilter(request, response);
    }

    private boolean isPermittedUrl(String requestPath) {
        // PERMIT_URL_ARRAY에서 허용된 URL 경로를 확인
        for (String url : PERMIT_URL_ARRAY) {
            if (requestPath.startsWith(url.replace("/**", ""))) {
                return true;
            }
        }
        return false;
    }

}
