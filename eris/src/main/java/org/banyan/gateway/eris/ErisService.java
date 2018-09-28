package org.banyan.gateway.eris;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import org.banyan.gateway.eris.util.RequestUtil;
import org.banyan.gateway.eris.util.StaxonUtil;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.data.rabbitmq.dto.ThirdpartyRecordMessage;
import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.constant.RequestStatus;
import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.eris.RiskListVipRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.eris.RiskPersonlistVipRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.eris.SaicSimpleRequest;
import org.banyan.gateway.helios.proto.iface.thirdparty.eris.IErisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 工商服务接口对接类
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/28 16:29
 */
public class ErisService implements IErisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErisService.class);
    private static JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).virtualRoot("response").build();

    private static final String RISK_LIST_VIP = "risk_list_vip";
    private static final String RISK_PERSONLIST_VIP = "risk_personlist_vip";
    private static final String SAIC_SIMPLE = "saic_simple";

    private static final Interface SAIC_SIMPLE_IFACE = Interface.GONGSHANG_BUSINESS_SIMPLE;
    private static final Interface RISK_LIST_VIP_IFACE = Interface.GONGSHANG_RISK_BUSINESS_VIP;
    private static final Interface RISK_PERSON_VIP_IFACE = Interface.GONGSHANG_RISK_PERSON_VIP;

    private final ErisProperties properties;
    private final RequestUtil requestUtil;
    private ThirdpartyRecordService thirdPartyRecordService;

    ErisService(ErisProperties properties, RequestUtil requestUtil, ThirdpartyRecordService thirdpartyRecordService) {
        this.properties = properties;
        this.requestUtil = requestUtil;
        this.thirdPartyRecordService = thirdpartyRecordService;
    }

    @Override
    public BaseResponse getSaicSimple(SaicSimpleRequest saicSimpleRequest) {
        ThirdpartyRecordMessage recordMessage = new ThirdpartyRecordMessage();
        recordMessage.setIface(SAIC_SIMPLE_IFACE).setRequest(saicSimpleRequest);

        // 组装requestMap
        Map<String, String> requestMap = createRequestMap();
        requestMap.put("service", SAIC_SIMPLE);
        requestMap.put("key", saicSimpleRequest.getKey());
        requestMap.put("keytype", saicSimpleRequest.getKeyType());

        // 发送请求
        LOGGER.info("工商简项查询请求参数: {}", JSON.toJSONString(requestMap));
        String response = requestUtil.invokeService(this.properties.getBaseUrl(), requestMap, SAIC_SIMPLE_IFACE);
        LOGGER.info("工商简项查询返回内容: {}", response);

        // 组装返回结果
        return assembleResponse(recordMessage, response);
    }


    @Override
    public BaseResponse getRiskListVip(RiskListVipRequest riskListVipRequest) {
        ThirdpartyRecordMessage recordMessage = new ThirdpartyRecordMessage();
        recordMessage.setIface(RISK_LIST_VIP_IFACE).setRequest(riskListVipRequest);

        // 组装requestMap
        Map<String, String> requestMap = createRequestMap();
        requestMap.put("service", RISK_LIST_VIP);
        requestMap.put("ent_name", riskListVipRequest.getEntName());
        requestMap.put("datatype", riskListVipRequest.getDataType());
        requestMap.put("pageno", riskListVipRequest.getPageNo());
        requestMap.put("sortTime", riskListVipRequest.getSortTime());
        requestMap.put("crawlTime", riskListVipRequest.getCrawlTime());

        // 发送请求
        LOGGER.info("企业风控接口VIP请求参数: {}", JSON.toJSONString(requestMap));
        String response = requestUtil.invokeService(this.properties.getBaseUrl(), requestMap, RISK_LIST_VIP_IFACE);
        LOGGER.info("企业风控接口VIP返回内容: {}", response);

        // 组装返回结果
        return assembleResponse(recordMessage, response);
    }


    @Override
    public BaseResponse getRiskPersonlistVip(RiskPersonlistVipRequest riskPersonlistVipRequest) {
        ThirdpartyRecordMessage recordMessage = new ThirdpartyRecordMessage();
        recordMessage.setIface(RISK_PERSON_VIP_IFACE).setRequest(riskPersonlistVipRequest);

        // 组装requestMap
        Map<String, String> requestMap = createRequestMap();
        requestMap.put("service", RISK_PERSONLIST_VIP);
        requestMap.put("name", riskPersonlistVipRequest.getName());
        requestMap.put("id_no", riskPersonlistVipRequest.getIdNo());
        requestMap.put("pageNo", riskPersonlistVipRequest.getPageNo());

        // 发送请求
        LOGGER.info("个人风险信息VIP请求参数: {}", JSON.toJSONString(requestMap));
        String response = requestUtil.invokeService(this.properties.getBaseUrl(), requestMap, RISK_PERSON_VIP_IFACE);
        LOGGER.info("个人风险信息VIP返回内容: {}", response);

        // 组装返回结果
        return assembleResponse(recordMessage, response);
    }

    private Map<String, String> createRequestMap() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("username", this.properties.getUserName());
        requestMap.put("password", this.properties.getPassword());
        return requestMap;
    }

    private BaseResponse assembleResponse(ThirdpartyRecordMessage recordMessage, String response) {
        recordMessage.setGid(MDC.get(Constants.TRACKID)).setResp(response).setFee(Boolean.FALSE);
        BaseResponse baseResponse = new BaseResponse().setContent(response);

        if (response == null || "".equals(response)) {
            baseResponse.setStatus(RequestStatus.FAILED).setSubmitCode(SubmitCode.CHANNEL_ERROR).setFee(Boolean.FALSE);
        } else {
            JSONObject jsonObject = StaxonUtil.xml2jsonObject(response, config);
            recordMessage.setResponse(jsonObject);

            JSONObject meta = jsonObject.getJSONObject("meta");
            if (null != meta) {
                String code = meta.getString("code");
                SubmitCode submitCode = CodeMapping.MAPPING.get(code);

                baseResponse.setStatus(RequestStatus.SUCCESS).setResponse(jsonObject).setSubmitCode(submitCode == null ? SubmitCode.CHANNEL_ERROR : submitCode)
                        .setFee(CodeMapping.FEE_MAPPING.get(code));
                recordMessage.setFee(CodeMapping.FEE_MAPPING.get(code));
            } else {
                baseResponse.setStatus(RequestStatus.FAILED).setSubmitCode(SubmitCode.CHANNEL_ERROR).setFee(Boolean.FALSE);
            }
        }

        // 向队列发送第三方请求记录
        this.thirdPartyRecordService.send(recordMessage);

        return baseResponse;
    }
}
