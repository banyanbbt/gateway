package org.banyan.gateway.helios.data.rabbitmq.service;

import org.banyan.gateway.helios.data.rabbitmq.constant.Constant;
import org.banyan.gateway.helios.data.rabbitmq.dto.ProductRecordMessage;
import org.banyan.gateway.helios.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.util.Assert;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ProductRecordService
 * 系统请求记录陆地
 *
 * @author Kevin Huang
 * @since 0.0.1
 * 2018年03月20日 10:19:00
 */
public class ProductRecordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRecordService.class);

    private AmqpTemplate amqpTemplate;

    public ProductRecordService(AmqpTemplate amqpTemplate) {
        Assert.notNull(amqpTemplate, "amqpTemplate 不能为空");
        this.amqpTemplate = amqpTemplate;
    }

    public void send(ProductRecordMessage productRecordMessage) {
        try {
            this.amqpTemplate.convertAndSend(Constant.EXCHANGE_PRODUCT_RECORD, null, productRecordMessage);
        } catch (Exception e) {
            LOGGER.info("Rabbitmq 发送到：{} 失败，请求记录：{}", Constant.QUEUE_PRODUCT_RECORD, JacksonUtil.toJson(productRecordMessage));
            LOGGER.info("请求记录发送到Rabbitmq异常", e);
        }
    }

}
