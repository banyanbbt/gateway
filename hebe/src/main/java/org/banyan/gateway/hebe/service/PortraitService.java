package org.banyan.gateway.hebe.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.banyan.gateway.hebe.dto.ConsumerIndex;
import org.banyan.gateway.hebe.dto.EcommerceIndex;
import org.banyan.gateway.hebe.dto.InternetBehaviorIndex;
import org.banyan.gateway.helios.common.*;
import org.banyan.gateway.helios.proto.dto.product.InterfaceConfig;
import org.banyan.gateway.helios.proto.dto.product.ProductResponse;
import org.banyan.gateway.helios.proto.iface.product.hebe.IPortraitService;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.IMantoService;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.MantoRequest;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.MantoResponse;
import org.banyan.gateway.helios.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.banyan.gateway.helios.common.SubmitCode.CHANNEL_ERROR;
import static org.banyan.gateway.helios.common.SubmitCode.QUERY_SUCCESS;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * 用户画像接口
 *
 * @author Kevin Huang
 * @since 0.1.0
 * 2018年07月31日 17:35:00
 */
public class PortraitService implements IPortraitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortraitService.class);
    @Autowired
    private IMantoService mantoService;
    private static final Map<String, SubmitCode> SUBMIT_CODE_MAP = new HashMap<>();
    private static final Map<String, SubmitCode.ArgsFailedDetail> ARGS_FAILED_DETAIL_MAP = new HashMap<>();


    @Override
    public ProductResponse pH0101(String card, String name, String cid, String mobile, List<String> portraits, InterfaceConfig interfaceConfig) {
        LOGGER.info("请求H0101消费类自定画像服务（手机号查询）开始, mobile:{}, name:{}", mobile, name);
        ProductResponse response = this.assembleAndSend("H0101", card, name, cid, mobile, portraits, Interface.YONGHUHUAXIANG_H0101, interfaceConfig);

        // 转换标签为 ConsumerIndex
        List<ConsumerIndex> indexs = null;
        if (QUERY_SUCCESS == response.getSubmitCode()) {
            String data = response.getResult().toString();
            try {
                indexs = JacksonUtil.toObject(data, new TypeReference<List<ConsumerIndex>>() {});
            } catch (Exception e) {
                LOGGER.info("转化指标出错", e);
                response.setResult(null);
                response.setSubmitCode(CHANNEL_ERROR);
            }
        }

        if (!CollectionUtils.isEmpty(indexs)) {
            response.setResult(indexs);
            int cut = indexs.size(); // 计费次数

            ThirdpartyFee thirdpartyFee = response.getThirdpartyFees().get(0);
            response.setProductFee(new ProductFee(thirdpartyFee.getFee(), thirdpartyFee.getFee() ? cut : 0));
        }

        return response;
    }

    @Override
    public ProductResponse pH0201(String name, String cid, String mobile, List<String> portraits, InterfaceConfig interfaceConfig) {
        LOGGER.info("请求H0201电商消费行为评级服务（手机号查询）开始, mobile:{}, name:{}", mobile, name);
        ProductResponse response = this.assembleAndSend("H0201", null, name, cid, mobile, portraits, Interface.YONGHUHUAXIANG_H0201, interfaceConfig);

        // 转换标签为 EcommerceIndex
        EcommerceIndex ecommerceIndex = null;
        if (QUERY_SUCCESS == response.getSubmitCode()) {
            try {
                ecommerceIndex = JacksonUtil.toObject(response.getResult().toString(), new TypeReference<EcommerceIndex>() {});
            } catch (Exception e) {
                LOGGER.info("转化指标出错", e);
                response.setResult(null);
                response.setSubmitCode(CHANNEL_ERROR);
            }
        }

        if (null != ecommerceIndex) {
            response.setResult(ecommerceIndex);
            ThirdpartyFee thirdpartyFee = response.getThirdpartyFees().get(0);

            response.setProductFee(new ProductFee(thirdpartyFee.getFee(), thirdpartyFee.getFee() ? 1 : 0));
        }
        return response;
    }

    @Override
    public ProductResponse pH0301(String mobile, List<String> portraits, InterfaceConfig interfaceConfig) {
        LOGGER.info("请求H0301互联网行为自定画像（手机号查询）开始, mobile:{}", mobile);
        ProductResponse response = this.assembleAndSend("H0301", null, null, null, mobile, portraits, Interface.YONGHUHUAXIANG_H0301, interfaceConfig);

        // 转换标签为 InternetBehaviorIndex
        InternetBehaviorIndex internetBehaviorIndex = null;
        if (QUERY_SUCCESS == response.getSubmitCode()) {
            try {
                internetBehaviorIndex = JacksonUtil.toObject(response.getResult().toString(), new TypeReference<InternetBehaviorIndex>() {});
            } catch (Exception e) {
                LOGGER.info("转化指标出错", e);
                response.setResult(null).setSubmitCode(CHANNEL_ERROR);
            }
        }

        if (null != internetBehaviorIndex) {
            response.setResult(internetBehaviorIndex);
            ThirdpartyFee thirdpartyFee = response.getThirdpartyFees().get(0);

            response.setProductFee(new ProductFee(thirdpartyFee.getFee(), thirdpartyFee.getFee() ? 1 : 0));
        }
        return response;
    }

    /**
     * 组装发送
     *
     * @param productId 产品编号
     * @param card      卡号
     * @param name      姓名
     * @param cid       身份证
     * @param mobile    手机号
     * @param portraits 标签
     * @return 组装结果
     */
    private ProductResponse assembleAndSend(String productId, String card, String name, String cid, String mobile, List<String> portraits, Interface iface, InterfaceConfig interfaceConfig) {
        LOGGER.info("请求产品{}，调用第三方服务{} 开始", productId, iface.getName());
        MantoRequest mantoRequest = new MantoRequest(productId, iface);
        mantoRequest.setInterfaceConfig(interfaceConfig)
                .addParameter(RequestField.CARD, card)
                .addParameter(RequestField.NAME, name)
                .addParameter(RequestField.MOBILE, mobile)
                .addParameter(RequestField.CID, cid)
                .addParameter(RequestField.PORTRAIT, portraits);
        MantoResponse mantoResponse = this.mantoService.callMantoService(mantoRequest);

        ThirdpartyFee thirdpartyFee = mantoResponse.getThirdpartyFee();
        ProductResponse response = new ProductResponse().setProductFee(new ProductFee());
        SubmitCode submitCode = mantoResponse.getCode() == null ? CHANNEL_ERROR : getSubmitCode(mantoResponse.getStatus());
        //如果结果不为空，映射结果
        if (null != mantoResponse.getResult() && submitCode == QUERY_SUCCESS) {
            response.setResult(mantoResponse.getResult());
        }
        response.setSubmitCode(submitCode).addThirdpartyFees(thirdpartyFee);
        if (submitCode == SubmitCode.ARGS_FAILED) {
            response.setArgsFailedDetail(getArgsFailedDetail(mantoResponse.getMessage()));
        }
        LOGGER.info("请求产品{}，调用第三方服务{} 结束", productId, iface.getName());
        return response;
    }

    /**
     * 参数异常提示
     * @param failedMsg 参数异常信息
     * @return ArgsFailedDetail
     */
    private static SubmitCode.ArgsFailedDetail getArgsFailedDetail(String failedMsg) {
        if (ARGS_FAILED_DETAIL_MAP.isEmpty()) {
            synchronized (ARGS_FAILED_DETAIL_MAP) {
                if (ARGS_FAILED_DETAIL_MAP.isEmpty()) {
                    for (SubmitCode.ArgsFailedDetail key : SubmitCode.ArgsFailedDetail.values()) {
                        ARGS_FAILED_DETAIL_MAP.put(key.getMsg(), key);
                    }
                }
            }
        }
        return ARGS_FAILED_DETAIL_MAP.get(null != failedMsg ? failedMsg.toUpperCase() : null);
    }

    /**
     * 状态码转换
     * @param status 状态
     * @return SubmitCode
     */
    private static SubmitCode getSubmitCode(String status) {
        if (SUBMIT_CODE_MAP.isEmpty()) {
            synchronized (SUBMIT_CODE_MAP) {
                if (SUBMIT_CODE_MAP.isEmpty()) {
                    for (SubmitCode key : SubmitCode.values()) {
                        if (!key.getCode().equals(QUERY_SUCCESS.getCode()) && key != SubmitCode.ARGS_FAILED) {
                            SUBMIT_CODE_MAP.put(key.getStatus(), CHANNEL_ERROR);
                        } else {
                            SUBMIT_CODE_MAP.put(key.getStatus(), key);
                        }
                    }
                }
            }
        }

        return SUBMIT_CODE_MAP.get(null != status ? status.toUpperCase() : null);
    }
}
