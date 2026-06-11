package com.awsome.shop.gateway.infrastructure.filter;

import com.awsome.shop.gateway.common.constants.RouteConstants;
import com.awsome.shop.gateway.common.enums.GatewayErrorCode;
import com.awsome.shop.gateway.common.exception.AuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Global filter for role-based authorization on admin routes.
 *
 * <p>Order: +150 - executes after {@link AuthenticationGatewayFilter} (+100, which
 * resolves the operator role) and before {@link OperatorIdInjectionFilter} (+200).</p>
 *
 * <p>Requests matching {@code /api/v1/admin/**} require role {@code ADMIN}; otherwise
 * a 403 is returned. Non-admin paths pass through untouched.</p>
 */
@Slf4j
@Component
public class RoleAuthorizationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // Only guard admin routes; everything else is unaffected
        if (!path.startsWith(RouteConstants.PATH_PREFIX_ADMIN)) {
            return chain.filter(exchange);
        }

        String role = exchange.getAttribute(RouteConstants.ATTR_OPERATOR_ROLE);
        if (!RouteConstants.ROLE_ADMIN.equalsIgnoreCase(role)) {
            String requestId = exchange.getAttribute(RouteConstants.ATTR_REQUEST_ID);
            log.warn("[{}] Authorization denied for admin path {} - role: {}", requestId, path, role);
            return Mono.error(new AuthorizationException(GatewayErrorCode.AUTHZ_ADMIN_REQUIRED));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 150;
    }
}
