package org.banyan.gateway.poseidon.listener;

import com.rabbitmq.client.Channel;
import org.banyan.gateway.helios.data.mongo.model.MongoRecord;
import org.banyan.gateway.helios.data.mongo.service.MongoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RecordMessageListener
 * Rabbit Message Listener
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月22日 11:08:00
 */
public class RecordMessageListener implements ChannelAwareMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordMessageListener.class);
    private MessageConverter messageConverter;
    private MongoService mongoService;
    private RecordHandler recordHandler;
    private String handlerName;

    public RecordMessageListener(MessageConverter messageConverter, MongoService mongoService, RecordHandler recordHandler) {
        this.messageConverter = messageConverter;
        this.mongoService = mongoService;
        this.recordHandler = recordHandler;
        this.handlerName = recordHandler.getClass().getSimpleName();
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties properties = message.getMessageProperties();

        Object object = null;
        try {
            object = this.messageConverter.fromMessage(message);
        } catch (Exception e) {
            LOGGER.info("处理器" + handlerName + "转换消息异常，丢弃", e);
        }

        if (null == object) {
            channel.basicAck(properties.getDeliveryTag(), false); // 消息确认消费
            return;
        }

        try {
            // 特殊处理 前端使用的mongo库
            MongoRecord mongoRecord = recordHandler.handle(object);
            this.mongoService.saveRecord(mongoRecord);
            channel.basicAck(properties.getDeliveryTag(), false); // 消息确认消费
        } catch (Exception e) {
            LOGGER.info("处理器" + handlerName + "处理产品记录异常", e);
            channel.basicReject(properties.getDeliveryTag(), true);// 消息消费失败
        }
    }
}
