package org.banyan.gateway.poseidon.listener;

import org.apache.commons.lang.BooleanUtils;
import org.banyan.gateway.helios.data.mongo.model.MongoRecord;
import org.banyan.gateway.helios.data.mongo.model.ThirdpartyRecord;
import org.banyan.gateway.helios.data.rabbitmq.dto.ThirdpartyRecordMessage;
import org.banyan.gateway.helios.util.JacksonUtil;
import org.banyan.gateway.poseidon.service.FeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ThirdpartyRecordHandler
 * 第三方记录处理器
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月23日 09:43:00
 */
@Service
public class ThirdpartyRecordHandler implements RecordHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdpartyRecordHandler.class);
    @Autowired
    private FeeService feeService;

    @Override
    public MongoRecord handle(Object message) {
        ThirdpartyRecord thirdPartyRecord = null;
        if (message instanceof ThirdpartyRecordMessage) {
            thirdPartyRecord = convertThirdpartyRecord((ThirdpartyRecordMessage) message);
            // 成本
            if (null != thirdPartyRecord && BooleanUtils.isTrue(thirdPartyRecord.getFee())) {
                thirdPartyRecord.setPrice(feeService.getInterfaceFee(thirdPartyRecord.getIface(), thirdPartyRecord.getPrice()));
            }
        } else {
            LOGGER.info("处理第三方记录失败，当前消息：{}", JacksonUtil.toJson(message));
        }

        return thirdPartyRecord;
    }

    private ThirdpartyRecord convertThirdpartyRecord(ThirdpartyRecordMessage message) {
        ThirdpartyRecord thirdPartyRecord = new ThirdpartyRecord();
        thirdPartyRecord.setGid(message.getGid());
        thirdPartyRecord.setRequest(message.getRequest());
        thirdPartyRecord.setResp(message.getResp());
        thirdPartyRecord.setResponse(message.getResponse());
        thirdPartyRecord.setFee(message.getFee());
        thirdPartyRecord.setIface(message.getIface());
        thirdPartyRecord.setPrice(message.getPrice());
        thirdPartyRecord.setRecordTime(message.getCreateTime());
        return thirdPartyRecord;
    }
}
