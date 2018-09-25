package org.banyan.gateway.proteus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.data.rabbitmq.dto.ThirdpartyRecordMessage;
import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.constant.RequestStatus;
import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.proteus.ArchivesRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.proteus.RadarRequest;
import org.banyan.gateway.helios.proto.iface.thirdparty.proteus.IProteusService;
import org.banyan.gateway.helios.util.DateUtil;
import org.banyan.gateway.helios.util.UniqueIdUtil;
import org.banyan.gateway.helios.util.codec.RSAUtil;
import org.banyan.gateway.helios.util.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 服务实体提供类
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/18 15:28
 */
public class ProteusService implements IProteusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProteusService.class);

    /**
     * 全景雷达报告
     */
    private static final String RADAR_REPORT = "/product/radar/v3/report";
    private static final Interface RADAR_REPORT_IFACE = Interface.XINDAI_RADAR_REPORT;

    /**
     * 申请雷达报告
     */
    private static final String RADAR_APPLY = "/product/radar/v3/apply";
    private static final Interface RADAR_APPLY_IFACE = Interface.XINDAI_RADAR_APPLY;

    /**
     * 信用现状报告
     */
    private static final String RADAR_CURRENT = "/product/radar/v3/current";
    private static final Interface RADAR_CURRENT_IFACE = Interface.XINDAI_RADAR_CURRENT;

    /**
     * 行为雷达报告
     */
    private static final String RADAR_BEHAVIOR = "/product/radar/v3/behavior";
    private static final Interface RADAR_BEHAVIOR_IFACE = Interface.XINDAI_RADAR_BEHAVIOR;

    /**
     * 共债档案
     */
    private static final String DEBT = "/product/archive/v1/totaldebt";
    private static final Interface DEBT_IFACE = Interface.XINDAI_DEBT;

    /**
     * 负面拉黑
     */
    private static final String BLACK = "/product/negative/v3/black";
    private static final Interface BLACK_IFACE = Interface.XINDAI_BLACK;

    /**
     * 负面洗白
     */
    private static final String WHITE = "/product/negative/v3/white";
    private static final Interface WHITE_IFACE = Interface.XINDAI_WHITE;

    /**
     * 逾期档案
     */
    private static final String OVERDUE = "/product/archive/v3/overdue";
    private static final Interface OVERDUE_IFACE = Interface.XINDAI_OVERDUE;


    private ThirdpartyRecordService thirdPartyRecordService;

    private ProteusWDProperties proteusWDProperties;

    private RSAUtil rsaUtil;

    public ProteusService(ThirdpartyRecordService thirdPartyRecordService, ProteusWDProperties proteusWDProperties, RSAUtil rsaUtil) {
        this.thirdPartyRecordService = thirdPartyRecordService;
        this.proteusWDProperties = proteusWDProperties;
        this.rsaUtil = rsaUtil;
    }

    @Override
    public BaseResponse totaldebt(ArchivesRequest request) {
        LOGGER.info("proteus共债档案请求参数：{}", JSON.toJSONString(request));
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(DEBT_IFACE).setRequest(request).setFee(Boolean.FALSE).setGid(MDC.get(Constants.TRACKID));

        Map<String, String> content = new HashMap<>(16);

        content.put("member_id", this.proteusWDProperties.getMemberId());
        content.put("terminal_id", this.proteusWDProperties.getTerminalId());
        content.put("trans_id", DateUtil.getDateTime() + UniqueIdUtil.getUniqueId());
        content.put("trade_date", DateUtil.getDateTime());
        content.put("industry_type", "A1");

        content.put("id_no", request.getIdNo());
        content.put("id_name", request.getIdName());
        content.put("phone_no", request.getPhoneNo());
        content.put("bankcard_no", request.getBankcardNo());
        content.put("versions", "1.1.0");

        String response = assembleAndSend(this.proteusWDProperties.getBaseUrl() + DEBT, JSONObject.toJSONString(content), DEBT_IFACE, this.proteusWDProperties.getMemberId(),
                this.proteusWDProperties.getTerminalId(), this.proteusWDProperties.getPrivateKey());

        return transResponse(record, response, CodeMapping.DEBT_CODE_MAP, CodeMapping.ERROR_MAP);
    }

    @Override
    public BaseResponse black(ArchivesRequest request) {
        LOGGER.info("proteus负面拉黑请求参数：{}", JSON.toJSONString(request));
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(BLACK_IFACE).setRequest(request).setFee(Boolean.FALSE).setGid(MDC.get(Constants.TRACKID));

        Map<String, String> content = new HashMap<>(16);

        content.put("member_id", this.proteusWDProperties.getMemberId());
        content.put("terminal_id", this.proteusWDProperties.getTerminalId());
        content.put("trans_id", DateUtil.getDateTime() + UniqueIdUtil.getUniqueId());
        content.put("trade_date", DateUtil.getDateTime());
        content.put("industry_type", "A1");

        content.put("id_no", request.getIdNo());
        content.put("id_name", request.getIdName());
        content.put("phone_no", request.getPhoneNo());
        content.put("bankcard_no", request.getBankcardNo());
        content.put("versions", "1.3.0");

        String response = assembleAndSend(this.proteusWDProperties.getBaseUrl() + BLACK, JSONObject.toJSONString(content), BLACK_IFACE, this.proteusWDProperties.getMemberId(),
                this.proteusWDProperties.getTerminalId(), this.proteusWDProperties.getPrivateKey());

        return transResponse(record, response, CodeMapping.BLACK_CODE_MAP, CodeMapping.ERROR_MAP);
    }

    @Override
    public BaseResponse white(ArchivesRequest request) {
        LOGGER.info("proteus负面洗白请求参数：{}", JSON.toJSONString(request));
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(WHITE_IFACE).setRequest(request).setFee(Boolean.FALSE).setGid(MDC.get(Constants.TRACKID));

        Map<String, String> content = new HashMap<>(16);

        content.put("member_id", this.proteusWDProperties.getMemberId());
        content.put("terminal_id", this.proteusWDProperties.getTerminalId());
        content.put("trans_id", DateUtil.getDateTime() + UniqueIdUtil.getUniqueId());
        content.put("trade_date", DateUtil.getDateTime());
        content.put("industry_type", "A1");

        content.put("id_no", request.getIdNo());
        content.put("id_name", request.getIdName());
        content.put("phone_no", request.getPhoneNo());
        content.put("bankcard_no", request.getBankcardNo());
        content.put("versions", "1.3.0");

        String response = assembleAndSend(this.proteusWDProperties.getBaseUrl() + WHITE, JSONObject.toJSONString(content), WHITE_IFACE, this.proteusWDProperties.getMemberId(),
                this.proteusWDProperties.getTerminalId(), this.proteusWDProperties.getPrivateKey());

        return transResponse(record, response, CodeMapping.WHITE_CODE_MAP, CodeMapping.ERROR_MAP);
    }

    @Override
    public BaseResponse overdue(ArchivesRequest request) {
        LOGGER.info("proteus逾期档案请求参数：{}", JSON.toJSONString(request));
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(OVERDUE_IFACE).setRequest(request).setFee(Boolean.FALSE).setGid(MDC.get(Constants.TRACKID));

        Map<String, String> content = new HashMap<>(16);

        content.put("member_id", this.proteusWDProperties.getMemberId());
        content.put("terminal_id", this.proteusWDProperties.getTerminalId());
        content.put("trans_id", DateUtil.getDateTime() + UniqueIdUtil.getUniqueId());
        content.put("trade_date", DateUtil.getDateTime());
        content.put("industry_type", "A1");

        content.put("id_no", request.getIdNo());
        content.put("id_name", request.getIdName());
        content.put("phone_no", request.getPhoneNo());
        content.put("bankcard_no", request.getBankcardNo());
        content.put("versions", "1.3.0");

        String response = assembleAndSend(this.proteusWDProperties.getBaseUrl() + OVERDUE, JSONObject.toJSONString(content), OVERDUE_IFACE, this.proteusWDProperties.getMemberId(),
                this.proteusWDProperties.getTerminalId(), this.proteusWDProperties.getPrivateKey());

        return transResponse(record, response, CodeMapping.OVERDUE_CODE_MAP, CodeMapping.ERROR_MAP);
    }

    @Override
    public BaseResponse radarReport(RadarRequest radarRequest) {
        LOGGER.info("proteus全景雷达报告请求参数：{}", JSON.toJSONString(radarRequest));
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(RADAR_REPORT_IFACE).setRequest(radarRequest).setFee(Boolean.FALSE).setGid(MDC.get(Constants.TRACKID));

        HashMap<String, String> content = new HashMap<>();
        content.put("member_id", this.proteusWDProperties.getMemberId());
        content.put("terminal_id", this.proteusWDProperties.getTerminalId());
        content.put("trans_id", DateUtil.getDateTime() + UniqueIdUtil.getUniqueId());
        content.put("trade_date", DateUtil.getDateTime());
        content.put("industry_type", "A1");
        content.put("versions", "1.3.0");

        content.put("id_no", radarRequest.getIdNo());
        content.put("id_name", radarRequest.getIdName());
        content.put("phone_no", radarRequest.getPhoneNo());
        content.put("bankcard_no", radarRequest.getBankcardNo());
        String response = assembleAndSend(this.proteusWDProperties.getBaseUrl() + RADAR_REPORT, JSONObject.toJSONString(content), RADAR_REPORT_IFACE, this.proteusWDProperties.getMemberId(),
                this.proteusWDProperties.getTerminalId(), this.proteusWDProperties.getPrivateKey());

        return transResponse(record, response, CodeMapping.RADAR_CODE_MAP, CodeMapping.ERROR_MAP);
    }

    @Override
    public BaseResponse radarApply(RadarRequest radarRequest) {
        LOGGER.info("proteus申请雷达报告请求参数：{}", JSON.toJSONString(radarRequest));
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(RADAR_APPLY_IFACE).setRequest(radarRequest).setFee(Boolean.FALSE).setGid(MDC.get(Constants.TRACKID));

        HashMap<String, String> content = new HashMap<>();
        content.put("member_id", this.proteusWDProperties.getMemberId());
        content.put("terminal_id", this.proteusWDProperties.getTerminalId());
        content.put("trans_id", DateUtil.getDateTime() + UniqueIdUtil.getUniqueId());
        content.put("trade_date", DateUtil.getDateTime());
        content.put("industry_type", "A1");
        content.put("versions", "1.3.0");

        content.put("id_no", radarRequest.getIdNo());
        content.put("id_name", radarRequest.getIdName());
        content.put("phone_no", radarRequest.getPhoneNo());
        content.put("bankcard_no", radarRequest.getBankcardNo());
        String response = assembleAndSend(this.proteusWDProperties.getBaseUrl() + RADAR_APPLY, JSONObject.toJSONString(content), RADAR_APPLY_IFACE, this.proteusWDProperties.getMemberId(),
                this.proteusWDProperties.getTerminalId(), this.proteusWDProperties.getPrivateKey());
        return transResponse(record, response, CodeMapping.RADAR_CODE_MAP, CodeMapping.ERROR_MAP);
    }

    @Override
    public BaseResponse radarCurrent(RadarRequest radarRequest) {
        LOGGER.info("proteus信用现状报告请求参数：{}", JSON.toJSONString(radarRequest));
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(RADAR_CURRENT_IFACE).setRequest(radarRequest).setFee(Boolean.FALSE).setGid(MDC.get(Constants.TRACKID));

        HashMap<String, String> content = new HashMap<>();
        content.put("member_id", this.proteusWDProperties.getMemberId());
        content.put("terminal_id", this.proteusWDProperties.getTerminalId());
        content.put("trans_id", DateUtil.getDateTime() + UniqueIdUtil.getUniqueId());
        content.put("trade_date", DateUtil.getDateTime());
        content.put("industry_type", "A1");
        content.put("versions", "1.3.0");

        content.put("id_no", radarRequest.getIdNo());
        content.put("id_name", radarRequest.getIdName());
        content.put("phone_no", radarRequest.getPhoneNo());
        content.put("bankcard_no", radarRequest.getBankcardNo());
        String response = assembleAndSend(this.proteusWDProperties.getBaseUrl() + RADAR_CURRENT, JSONObject.toJSONString(content), RADAR_CURRENT_IFACE, this.proteusWDProperties.getMemberId(),
                this.proteusWDProperties.getTerminalId(), this.proteusWDProperties.getPrivateKey());
        return transResponse(record, response, CodeMapping.RADAR_CODE_MAP, CodeMapping.ERROR_MAP);
    }

    @Override
    public BaseResponse radarBehavior(RadarRequest radarRequest) {
        LOGGER.info("proteus行为雷达报告请求参数：{}", JSON.toJSONString(radarRequest));
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(RADAR_BEHAVIOR_IFACE).setRequest(radarRequest).setFee(Boolean.FALSE).setGid(MDC.get(Constants.TRACKID));

        HashMap<String, String> content = new HashMap<>();
        content.put("member_id", this.proteusWDProperties.getMemberId());
        content.put("terminal_id", this.proteusWDProperties.getTerminalId());
        content.put("trans_id", DateUtil.getDateTime() + UniqueIdUtil.getUniqueId());
        content.put("trade_date", DateUtil.getDateTime());
        content.put("industry_type", "A1");
        content.put("versions", "1.3.0");

        content.put("id_no", radarRequest.getIdNo());
        content.put("id_name", radarRequest.getIdName());
        content.put("phone_no", radarRequest.getPhoneNo());
        content.put("bankcard_no", radarRequest.getBankcardNo());
        String response = assembleAndSend(this.proteusWDProperties.getBaseUrl() + RADAR_BEHAVIOR, JSONObject.toJSONString(content), RADAR_BEHAVIOR_IFACE, this.proteusWDProperties.getMemberId(),
                this.proteusWDProperties.getTerminalId(), this.proteusWDProperties.getPrivateKey());
        return transResponse(record, response, CodeMapping.RADAR_CODE_MAP, CodeMapping.ERROR_MAP);
    }

    /**
     * 请求包装
     *
     * @param url     url
     * @param content 业务参数string
     * @param iface   接口
     * @return 请求返回内容
     */
    private String assembleAndSend(String url, String content, Interface iface, String memberId, String terminalId, String privateKey) {
        Map<String, String> params = new HashMap<>(6);
        params.put("member_id", memberId);
        params.put("terminal_id", terminalId);
        params.put("data_type", "json");

        try {
            params.put("data_content", this.rsaUtil.encryptByPrivateKeyBase64(privateKey, content));
        } catch (Exception e) {
            LOGGER.info("proteus加密出错", e);
            return null;
        }

        //发送请求
        LOGGER.info("请求{}参数: {}", iface.getName(), content);
        String response = HttpRequest.post(url, params, iface);
        LOGGER.info("{}返回结果: {}", iface.getName(), response);
        return response;
    }

    /**
     * 返回内容太处理
     *
     * @param record   消息记录
     * @param response 渠道返回结果
     * @param codeMap  渠道data中code映射关系
     * @param errorMap 渠道错误结果映射关系
     * @return
     */
    private BaseResponse transResponse(ThirdpartyRecordMessage record, String response, Map<String, SubmitCode> codeMap, Map<String, SubmitCode> errorMap) {
        //1.初始化返回
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(response).setStatus(RequestStatus.FAILED).setFee(Boolean.FALSE);

        //2. 解析response
        try {
            if (null != response) {
                JSONObject result = JSON.parseObject(response);
                baseResponse.setResponse(result).setStatus(RequestStatus.SUCCESS);
                record.setResponse(result).setResp(response);

                if (null != result) {
                    //2.1 判断交易是否成功
                    String isSuccess = result.getString("success");
                    if (null != result.get("data") && "true".equals(isSuccess)) {
                        //2.2 获取成功结果中的code与计费fee
                        JSONObject data = result.getJSONObject("data");
                        String code = data.getString("code");
                        String fee = data.getString("fee");
                        Boolean isFee = "Y".equals(fee) ? Boolean.TRUE : Boolean.FALSE;
                        baseResponse.setSubmitCode(codeMap.get(code)).setFee(isFee);
                        record.setFee(isFee);
                    } else {
                        //2.3 处理失败映射
                        String errorCode = result.getString("errorCode");
                        String errorMsg = result.getString("errorMsg");
                        if ("S1000".equals(errorCode)) {
                            baseResponse.setErrorMsg(CodeMapping.ARGS_FAILED.get(errorMsg));
                        }
                        baseResponse.setSubmitCode(errorMap.get(errorCode));
                    }
                }
            } else {
                LOGGER.info("请求错误，未获取到返回结果");
                baseResponse.setSubmitCode(SubmitCode.CHANNEL_ERROR);
            }
        } catch (Exception e) {
            LOGGER.info("渠道请求发生异常: ", e);
        } finally {
            if (null == baseResponse.getSubmitCode()) {
                baseResponse.setSubmitCode(SubmitCode.CHANNEL_ERROR);
            }

            if (null == baseResponse.getFee()) {
                baseResponse.setFee(Boolean.FALSE);
            }

            if (null == record.getFee()) {
                record.setFee(Boolean.FALSE);
            }
            //3. 发送到队列
            this.thirdPartyRecordService.send(record);
        }
        return baseResponse;
    }
}
