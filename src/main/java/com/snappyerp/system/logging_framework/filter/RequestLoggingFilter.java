package com.snappyerp.system.logging_framework.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        try {
            log.info("Incoming Request - Method: {}, URI: {}, TraceId: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    traceId);

            filterChain.doFilter(request, response);

            log.info("Outgoing Response - Status: {}, TraceId: {}",
                    response.getStatus(),
                    traceId);
        } finally {
            MDC.clear();
        }
    }
}