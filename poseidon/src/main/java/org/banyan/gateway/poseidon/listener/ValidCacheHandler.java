package org.banyan.gateway.poseidon.listener;

import org.banyan.gateway.helios.data.mongo.model.MongoRecord;
import org.banyan.gateway.helios.data.mongo.model.ValidCacheRecord;
import org.banyan.gateway.helios.data.rabbitmq.dto.ValidCacheMessage;
import org.banyan.gateway.helios.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ValidCacheHandler
 * 要素认证缓存处理器
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月23日 10:07:00
 */
@Service
public class ValidCacheHandler implements RecordHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidCacheHandler.class);

    @Override
    public MongoRecord handle(Object message) {
        ValidCacheRecord validCacheRecord = null;
        if (message instanceof ValidCacheMessage) {
            validCacheRecord = convertValidCacheRecord((ValidCacheMessage) message);
        } else {
            LOGGER.info("处理要素认证缓存记录失败，当前消息：{}", JacksonUtil.toJson(message));
        }
        return validCacheRecord;
    }

    private ValidCacheRecord convertValidCacheRecord(ValidCacheMessage message) {
        ValidCacheRecord validCacheRecord = new ValidCacheRecord();
        validCacheRecord.setCard(message.getCard());
        validCacheRecord.setName(message.getName());
        validCacheRecord.setCid(message.getCid());
        validCacheRecord.setMobile(message.getMoblie());
        validCacheRecord.setIface(message.getIface());
        validCacheRecord.setRecordTime(message.getCreateTime());
        return validCacheRecord;
    }
}
