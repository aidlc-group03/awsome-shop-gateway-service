package com.awsome.shop.gateway.common.exception;

import com.awsome.shop.gateway.common.enums.ErrorCode;

/**
 * Authorization exception (HTTP 403)
 *
 * <p>Thrown when an authenticated user lacks the required role/permission
 * to access a resource (e.g. non-admin accessing admin routes).</p>
 */
public class AuthorizationException extends GatewayException {

    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthorizationException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}
