package org.banyan.gateway.helios.data.mongo.service;

import org.apache.commons.lang.time.StopWatch;
import org.banyan.gateway.helios.data.mongo.model.MongoRecord;
import org.banyan.gateway.helios.util.codec.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * MongoService
 * 操作mongo
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月21日 10:01:00
 */
@Service
public class MongoService {
    private static Logger LOGGER = LoggerFactory.getLogger(MongoService.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存记录
     * @param baseRecord
     */
    public void saveRecord(MongoRecord baseRecord) {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            mongoTemplate.save(baseRecord);
            LOGGER.info("插入数据到mongodb成功");
        } catch (Exception e) {
            LOGGER.info("保存数据到mongodb异常", e);
        } finally {
            stopWatch.stop();
            long time = stopWatch.getTime();
            LOGGER.info("插入数据到mongodb耗时：{}", time);
        }
    }

    public <T> T getRecord(Object id, Class<T> entityClass) {
        return this.mongoTemplate.findById(id, entityClass);
    }

    /**
     * @param keys 生产query字符串
     * @return MD5加密后的query
     */
    private String generateQuery(String... keys) {
        StringBuilder builder = new StringBuilder();
        for (String param : keys) {
            if (null != param) {
                builder.append(param);
            }
        }

        return MD5Util.md5Lower(builder.toString());
    }

}
