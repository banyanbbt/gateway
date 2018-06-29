package org.banyan.gateway.helios.data.rabbitmq.service;

import org.banyan.gateway.helios.data.rabbitmq.constant.Constant;
import org.banyan.gateway.helios.data.rabbitmq.dto.ThirdpartyRecordMessage;
import org.banyan.gateway.helios.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.util.Assert;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ThirdpartyRecordService
 * 第三方数据落地服务
 *
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月19日 20:32:00
 */
public class ThirdpartyRecordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdpartyRecordService.class);

    private AmqpTemplate amqpTemplate;

    public ThirdpartyRecordService(AmqpTemplate amqpTemplate) {
        Assert.notNull(amqpTemplate, "amqpTemplate 不能为空");
        this.amqpTemplate = amqpTemplate;
    }

    public void send(ThirdpartyRecordMessage thirdPartyRecordMessage) {
        try {
            this.amqpTemplate.convertAndSend(Constant.EXCHANGE_THIRD_PARTY, Constant.QUEUE_THIRD_PARTY, thirdPartyRecordMessage);
        } catch (Exception e) {
            LOGGER.info("Rabbitmq 发送到：{} 失败，请求记录：{}", Constant.QUEUE_THIRD_PARTY, JacksonUtil.toJson(thirdPartyRecordMessage));
            LOGGER.info("请求记录发送到Rabbitmq异常", e);
        }
    }
}
