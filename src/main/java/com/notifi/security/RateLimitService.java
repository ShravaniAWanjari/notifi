package com.notifi.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitService {

    private final LettuceBasedProxyManager<byte[]> proxyManager;

    public RateLimitService(LettuceBasedProxyManager<byte[]> proxyManager) {
        this.proxyManager = proxyManager;
    }

    public Bucket resolveBucket(String key, RateLimitTier tier) {
        BucketConfiguration configuration = BucketConfiguration.builder()
                .addLimit(getLimit(tier))
                .build();

        return proxyManager.builder().build(key.getBytes(), configuration);
    }

    private Bandwidth getLimit(RateLimitTier tier) {
        switch (tier) {
            case AUTH:
                return Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));
            case MANAGEMENT:
                return Bandwidth.classic(60, Refill.greedy(60, Duration.ofMinutes(1)));
            case NOTIFICATION_TEAMS:
                return Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1)));
            case NOTIFICATION_PERSONAL:
            default:
                return Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        }
    }

    public enum RateLimitTier {
        AUTH,
        MANAGEMENT,
        NOTIFICATION_PERSONAL,
        NOTIFICATION_TEAMS
    }
}
