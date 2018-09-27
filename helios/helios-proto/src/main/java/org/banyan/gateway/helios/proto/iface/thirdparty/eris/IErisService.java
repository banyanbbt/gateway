package org.banyan.gateway.helios.proto.iface.thirdparty.eris;

import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.eris.RiskListVipRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.eris.RiskPersonlistVipRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.eris.SaicSimpleRequest;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 工商信息服务接口
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/26 10:33
 */
public interface IErisService {

    /**
     * 工商简项查询
     *
     * @param saicSimpleRequest
     * @return
     */
    BaseResponse getSaicSimple(SaicSimpleRequest saicSimpleRequest);

    /**
     * 企业风控接口VIP
     *
     * @param riskListVipRequest
     * @return
     */
    BaseResponse getRiskListVip(RiskListVipRequest riskListVipRequest);

    /**
     * 个人风险信息VIP
     *
     * @param riskPersonlistVipRequest
     * @return
     */
    BaseResponse getRiskPersonlistVip(RiskPersonlistVipRequest riskPersonlistVipRequest);
}
