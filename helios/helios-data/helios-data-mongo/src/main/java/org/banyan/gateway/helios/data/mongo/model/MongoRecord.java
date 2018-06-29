package org.banyan.gateway.helios.data.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * MongoRecord
 * mongo基础基类
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月21日 10:03:00
 */
public class MongoRecord implements Serializable {
    private static final long serialVersionUID = -1124695540951833174L;
    @Id
    private String id;
    // 记录时间
    @Field("record_time")
    private Date recordTime;
    // 创建时间
    @Field("create_time")
    private Date createTime;
    // 创建时间戳, 毫秒
    @Field("ts")
    @Indexed(name = "ts", direction = IndexDirection.DESCENDING)
    private long timestamp;

    public MongoRecord() {
        this.createTime = new Date();
        this.timestamp = createTime.getTime();
    }

    public String getId() {
        return id;
    }

    public MongoRecord setId(String id) {
        this.id = id;
        return this;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public MongoRecord setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public MongoRecord setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public MongoRecord setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
