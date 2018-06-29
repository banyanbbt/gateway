package org.banyan.gateway.poseidon.listener;

import org.banyan.gateway.helios.common.ProductFee;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.common.ThirdpartyFee;
import org.banyan.gateway.helios.data.jpa.service.account.AccountProductService;
import org.banyan.gateway.helios.data.jpa.service.account.AccountService;
import org.banyan.gateway.helios.data.mongo.model.MongoRecord;
import org.banyan.gateway.helios.data.mongo.model.ProductRecord;
import org.banyan.gateway.helios.data.rabbitmq.dto.ProductRecordMessage;
import org.banyan.gateway.helios.util.JacksonUtil;
import org.banyan.gateway.poseidon.service.FeeService;
import org.apache.commons.lang.BooleanUtils;
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
 * ProductRecordHandler
 * 产品记录处理器
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月22日 19:57:00
 */
@Service
public class ProductRecordHandler implements RecordHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRecordHandler.class);
    @Autowired
    private AccountProductService accountProductService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FeeService feeService;

    @Override
    public MongoRecord handle(Object message) {
        ProductRecord productRecord = null;
        if (message instanceof ProductRecordMessage) {
            productRecord = convertProductRecord((ProductRecordMessage) message);

            // 产品费用
            ProductFee productFee = productRecord.getProductFee();
            if (null != productFee && BooleanUtils.isTrue(productFee.getFee())) {
                BigDecimal price = this.feeService.getProductFee(productRecord.getAccount(), productRecord.getProduct(), productFee.getPrice(), productFee.getCnt());
                if (null != price) {
                    productFee.setPrice(price);
                    accountService.deductMoney(productRecord.getAccount(), productFee.getPrice());
                }
            }

            // 成本
            List<ThirdpartyFee> thirdPartyFees = productRecord.getThirdpartyFees();
            Set<String> interfaces = this.feeService.handleMultiThirdpartyFee(thirdPartyFees);

            if (!CollectionUtils.isEmpty(interfaces)) {
                productRecord.setInterfaces(interfaces);
            }

            if (!SubmitCode.CONCURRENCY_FAILED.getStatus().equals(productRecord.getStatus())) {
                this.accountProductService.increaseUsedCount(productRecord.getAccount(), productRecord.getProduct());
            }
        } else {
            LOGGER.info("处理产品记录失败，当前消息：{}", JacksonUtil.toJson(message));
        }
        return productRecord;
    }

    public static ProductRecord convertProductRecord(ProductRecordMessage message) {
        ProductRecord productRecord = new ProductRecord();
        productRecord.setRequest(message.getRequest());
        productRecord.setResponse(message.getResponse());
        productRecord.setIp(message.getIp());
        productRecord.setAccount(message.getAccount());
        productRecord.setProduct(message.getProduct());
        productRecord.setRequestBody(message.getRequestBody());
        productRecord.setCode(message.getCode());
        productRecord.setStatus(message.getStatus());
        productRecord.setMessage(message.getMessage());
        productRecord.setResult(message.getResult());
        productRecord.setCustomerId(message.getCustomerId());
        productRecord.setGid(message.getGid());
        productRecord.setProductFee(message.getProductFee());
        productRecord.setThirdpartyFees(message.getThirdpartyFees());
        productRecord.setRecordTime(message.getCreateTime());
        productRecord.setConfigs(message.getConfigs());
        return productRecord;
    }

}
