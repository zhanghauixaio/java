package com.seven.redisson;

import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class RedssionTest {
    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create();
        RLock lock = redissonClient.getLock("lock");
        RedissonMultiLock redissonMultiLock = new RedissonMultiLock();

        RedissonRedLock redissonRedLock = new RedissonRedLock();
        RateLimiter rateLimiter = RateLimiter.create(1.3);

    }
}
