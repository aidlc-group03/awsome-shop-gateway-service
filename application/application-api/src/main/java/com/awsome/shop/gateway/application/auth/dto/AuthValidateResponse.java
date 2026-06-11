package com.awsome.shop.gateway.application.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Auth validation response DTO from external auth service
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthValidateResponse {

    private boolean success;

    private String operatorId;

    /**
     * 角色: EMPLOYEE / ADMIN（验证通过时由 Auth 服务回传，供管理员路由角色鉴权）
     */
    private String role;

    private String message;
}
