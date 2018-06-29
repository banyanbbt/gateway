package org.banyan.gateway.helios.data.jpa.domain.channel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 第三方数据提供渠道方的接口评分表，一段时间内的评分记录
 * @date 2018-01-03 14:00:42
 */
@Entity
@Table(name = "interface_score")
public class InterfaceScore implements Serializable {
    private static final long serialVersionUID = -590861668058653132L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    // 接口
    @Column(name = "interface", nullable = false)
    private String iface;
    // 评分来源
    @Column(name = "source")
    private String source;
    // 开始时间, 计算周期开始
    @Column(name = "begin_at")
    private Date beginTime;
    // 结束时间，计算周期结束
    @Column(name = "end_at")
    private Date endTime;
    // 平均耗时评分，值越高耗时越小
    @Column(name = "consume_score")
    private Integer consumeScore;
    // 成功率分数，值越高成功率越高
    @Column(name = "success_score")
    private Integer successScore;
    // 价格评分，值越高越便宜
    @Column(name = "price_score")
    private Integer priceScore;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public InterfaceScore setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getIface() {
        return iface;
    }

    public InterfaceScore setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public String getSource() {
        return source;
    }

    public InterfaceScore setSource(String source) {
        this.source = source;
        return this;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public InterfaceScore setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public InterfaceScore setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getConsumeScore() {
        return consumeScore;
    }

    public InterfaceScore setConsumeScore(Integer consumeScore) {
        this.consumeScore = consumeScore;
        return this;
    }

    public Integer getSuccessScore() {
        return successScore;
    }

    public InterfaceScore setSuccessScore(Integer successScore) {
        this.successScore = successScore;
        return this;
    }

    public Integer getPriceScore() {
        return priceScore;
    }

    public InterfaceScore setPriceScore(Integer priceScore) {
        this.priceScore = priceScore;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public InterfaceScore setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public InterfaceScore setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
