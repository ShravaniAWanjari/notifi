package com.notifi.security;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    public RateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // Skip rate limiting for actuator and swagger
        if (path.startsWith("/actuator") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = request.getRemoteAddr();
        RateLimitService.RateLimitTier tier;

        if (path.startsWith("/api/auth")) {
            tier = RateLimitService.RateLimitTier.AUTH;
        } else if (path.startsWith("/api/projects") || path.startsWith("/api/templates")) {
            tier = RateLimitService.RateLimitTier.MANAGEMENT;
        } else if (path.startsWith("/api/notifications")) {
            // Determine if teams or personal. 
            // Mocking logic: checking a custom header for now until JWT is fully implemented
            String planHeader = request.getHeader("X-Plan");
            if ("TEAMS".equalsIgnoreCase(planHeader)) {
                tier = RateLimitService.RateLimitTier.NOTIFICATION_TEAMS;
            } else {
                tier = RateLimitService.RateLimitTier.NOTIFICATION_PERSONAL;
            }
        } else {
            tier = RateLimitService.RateLimitTier.MANAGEMENT; // default
        }

        // The key could be IP or User ID. For auth we use IP.
        String bucketKey = tier.name() + "_" + clientIp;
        Bucket bucket = rateLimitService.resolveBucket(bucketKey, tier);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests");
        }
    }
}
