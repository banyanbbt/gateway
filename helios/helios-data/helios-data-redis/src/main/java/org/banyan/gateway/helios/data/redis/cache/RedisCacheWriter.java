package org.banyan.gateway.helios.data.redis.cache;

import org.banyan.gateway.helios.common.Constants;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RedisCache
 *
 * @author Kevin Huang
 * @since version
 * 2018年06月07日 10:23:00
 */
public class RedisCacheWriter {
    private final RedisConnectionFactory connectionFactory;
    private final Long sleepTime;
    private final byte[] key;
    private final static byte[] LOCK_KEY = "~lock".getBytes(Constants.CHARSET_UTF8);

    /**
     * @param connectionFactory must not be null.
     * @param name must not be null.
     * @param sleepTime sleep time between lock request attempts. Must not be null. Use {@link Duration#ZERO} to disable locking.
     */
    public RedisCacheWriter(RedisConnectionFactory connectionFactory, String name, Long sleepTime) {
        Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(sleepTime, "SleepTime must not be null!");

        this.connectionFactory = connectionFactory;
        this.sleepTime = sleepTime;
        this.key = name.getBytes(Constants.CHARSET_UTF8);
    }

    public void put(byte[] field, byte[] value) {
        Assert.notNull(field, "Field must not be null!");
        Assert.notNull(value, "Value must not be null!");

        execute(connection -> connection.hSet(key, field, value));
    }

    public byte[] get(byte[] field) {
        Assert.notNull(field, "Field must not be null!");
        return execute(connection -> connection.hGet(key, field));
    }

    public byte[] putIfAbsent(byte[] field, byte[] value) {
        Assert.notNull(field, "Field must not be null!");
        Assert.notNull(value, "Value must not be null!");

        return execute(connection -> {
            if (isLockingCacheWriter()) {
                doLock(connection);
            }

            try {
                if (connection.hSetNX(key, field, value)) {
                    return null;
                }
                return connection.hGet(key, field);
            } finally {
                if (isLockingCacheWriter()) {
                    doUnlock(connection);
                }
            }
        });
    }

    public void remove(byte[] field) {
        Assert.notNull(field, "Field must not be null!");
        execute(connection -> connection.hDel(key, field));
    }

    public void clean() {
        execute(connection -> {
            boolean wasLocked = false;
            try {
                if (isLockingCacheWriter()) {
                    doLock(connection);
                    wasLocked = true;
                }
                connection.del(key);
            } finally {
                if (wasLocked && isLockingCacheWriter()) {
                    doUnlock(connection);
                }
            }
            return "OK";
        });
    }

    public void lock() {
        execute(this::doLock);
    }

    public void unlock() {
        executeLockFree(this::doUnlock);
    }

    private Boolean doLock(RedisConnection connection) {
        return connection.hSetNX(key, LOCK_KEY, new byte[0]);
    }

    private Long doUnlock(RedisConnection connection) {
        return connection.hDel(key, LOCK_KEY);
    }

    boolean doCheckLock(RedisConnection connection) {
        return connection.hExists(key, LOCK_KEY);
    }

    private boolean isLockingCacheWriter() {
        return sleepTime > 0;
    }

    private <T> T execute(Function<RedisConnection, T> callback) {
        RedisConnection connection = connectionFactory.getConnection();
        try {
            checkAndPotentiallyWaitUntilUnlocked(connection);
            return callback.apply(connection);
        } finally {
            connection.close();
        }
    }

    private void executeLockFree(Consumer<RedisConnection> callback) {
        RedisConnection connection = connectionFactory.getConnection();
        try {
            callback.accept(connection);
        } finally {
            connection.close();
        }
    }

    private void checkAndPotentiallyWaitUntilUnlocked(RedisConnection connection) {
        if (!isLockingCacheWriter()) {
            return;
        }
        try {
            while (doCheckLock(connection)) {
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new PessimisticLockingFailureException(String.format("Interrupted while waiting to unlock cache %s", new String(key, Constants.CHARSET_UTF8)), ex);
        }
    }
}