package com.awsome.shop.gateway.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Authentication result value object
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResult {

    private boolean authenticated;

    private String operatorId;

    /**
     * 角色: EMPLOYEE / ADMIN（认证通过时）
     */
    private String role;

    private String message;

    public static AuthenticationResult success(String operatorId) {
        return AuthenticationResult.builder()
                .authenticated(true)
                .operatorId(operatorId)
                .build();
    }

    public static AuthenticationResult success(String operatorId, String role) {
        return AuthenticationResult.builder()
                .authenticated(true)
                .operatorId(operatorId)
                .role(role)
                .build();
    }

    public static AuthenticationResult failure(String message) {
        return AuthenticationResult.builder()
                .authenticated(false)
                .message(message)
                .build();
    }
}
