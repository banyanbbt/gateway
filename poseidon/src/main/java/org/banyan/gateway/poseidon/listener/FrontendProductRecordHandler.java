package org.banyan.gateway.poseidon.listener;

import org.apache.commons.lang.BooleanUtils;
import org.banyan.gateway.helios.common.ProductFee;
import org.banyan.gateway.helios.common.ThirdpartyFee;
import org.banyan.gateway.helios.data.mongo.model.FrontendProductRecord;
import org.banyan.gateway.helios.data.mongo.model.MongoRecord;
import org.banyan.gateway.helios.data.mongo.model.ProductRecord;
import org.banyan.gateway.helios.data.rabbitmq.dto.ProductRecordMessage;
import org.banyan.gateway.helios.util.JacksonUtil;
import org.banyan.gateway.poseidon.service.FeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * FrontendProductRecordHandler
 * 产品记录处理器
 *
 * @author Kevin Huang
 * @since version
 * 2018年06月15日 11:24:00
 */
@Service
public class FrontendProductRecordHandler implements RecordHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontendProductRecordHandler.class);
    @Autowired
    private FeeService feeService;

    @Override
    public MongoRecord handle(Object message) {
        FrontendProductRecord frontendProductRecord = null;
        if (message instanceof ProductRecordMessage) {
            frontendProductRecord = convertFrontendProductRecord((ProductRecordMessage) message);

            // 产品费用
            ProductFee productFee = frontendProductRecord.getProductFee();
            if (null != productFee && BooleanUtils.isTrue(productFee.getFee())) {
                BigDecimal price = this.feeService.getProductFee(frontendProductRecord.getAccount(), frontendProductRecord.getProduct(), productFee.getPrice(), productFee.getCnt());
                if (null != price) {
                    productFee.setPrice(price);
                }
            }

            // 成本
            List<ThirdpartyFee> thirdPartyFees = frontendProductRecord.getThirdpartyFees();
            Set<String> interfaces = this.feeService.handleMultiThirdpartyFee(thirdPartyFees);

            if (!CollectionUtils.isEmpty(interfaces)) {
                frontendProductRecord.setInterfaces(interfaces);
            }

        } else {
            LOGGER.info("处理前端产品记录失败，当前消息：{}", JacksonUtil.toJson(message));
        }
        return frontendProductRecord;
    }

    private FrontendProductRecord convertFrontendProductRecord(ProductRecordMessage message) {
        ProductRecord productRecord = ProductRecordHandler.convertProductRecord(message);
        return new FrontendProductRecord(productRecord);
    }

}
