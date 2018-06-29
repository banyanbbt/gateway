package org.banyan.gateway.poseidon.listener;

import org.banyan.gateway.helios.data.mongo.model.MongoRecord;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RecordHandler
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月22日 19:51:00
 */
public interface RecordHandler {

    /**
     * 消息处理方便
     * @param message
     */
    MongoRecord handle(Object message);
}
