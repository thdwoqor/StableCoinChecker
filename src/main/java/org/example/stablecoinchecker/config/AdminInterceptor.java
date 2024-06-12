package org.example.stablecoinchecker.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {

    private static final String HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_TYPE = "Basic ";
    public static final String DELIMITER = ":";
    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        String method = request.getMethod();
        if (method.equals(HttpMethod.GET.name())) {
            return true;
        }

        String authorization = request.getHeader(HEADER_NAME);
        if (authorization == null || !authorization.startsWith(AUTHORIZATION_TYPE)) {
            throw new IllegalArgumentException("인증 헤더가 없거나 올바른 인증 유형으로 시작하지 않습니다.");
        }

        String base64Credentials = authorization.substring(AUTHORIZATION_TYPE.length());

        byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Credentials);
        String[] loginInfo = new String(decodedBytes).split(DELIMITER);
        if (loginInfo.length != 2) {
            throw new IllegalArgumentException("인증 헤더가 없거나 올바른 인증 유형으로 시작하지 않습니다.");
        }

        String username = loginInfo[0];
        String password = loginInfo[1];

        if (!this.username.equals(username) || !this.password.equals(password)) {
            throw new IllegalArgumentException("이메일 또는 패스워드가 일치하지 않습니다.");
        }
        return true;
    }
}
