package org.banyan.gateway.helios.data.redis.cache;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RedisCacheUtil
 *
 * <pre>
 * |<-                 message header                  ->|<- message body ->|
 * +-------------------------+---------------------------+------------------+
 * |   expired (LONG -> 8)   |   delimiter (byte -> 1)   |   message body   |
 * +-------------------------+---------------------------+------------------+
 * |<-                        message size                                ->|
 * </pre>
 *
 * @author Kevin Huang
 * @since version
 * 2018年06月08日 09:53:00
 */
public class RedisCacheUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheUtil.class);
    private static final Integer EXPIRED_SIZE = 8;
    private static final Integer HEADER_SIZE = 9;
    private static final byte DELIMITER = ':';

    public static byte[] convertToMsg(Long ttl, byte[] body) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput(HEADER_SIZE + body.length);
        long expired = 0;
        if (null != ttl && ttl > 0) {
            expired = System.currentTimeMillis() + ttl;
        }
        output.writeLong(expired);
        output.writeByte(DELIMITER);
        output.write(body);
        return output.toByteArray();
    }

    public static byte[] convertToBytes(byte[] msg) {
        byte[] body = null;
        if (null != msg && msg.length >= HEADER_SIZE) {
            byte delimiter = msg[EXPIRED_SIZE];
            if (DELIMITER == delimiter) {
                Long expired = ByteStreams.newDataInput(msg).readLong();
                if (expired > System.currentTimeMillis() || 0 == expired) {
                    body = Arrays.copyOfRange(msg, HEADER_SIZE, msg.length);
                } else {
                    LOGGER.debug("该消息已过期");
                }
            }
        }
        return body;
    }

}
