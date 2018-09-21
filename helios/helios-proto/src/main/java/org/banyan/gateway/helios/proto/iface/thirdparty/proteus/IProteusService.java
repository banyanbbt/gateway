package org.banyan.gateway.helios.proto.iface.thirdparty.proteus;

import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.proteus.ArchivesRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.proteus.RadarRequest;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 借贷服务接口
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/18 09:04
 */
public interface IProteusService {

    /**
     * 共债信息查询接口
     *
     * @param request 请求参数
     * @return 查询结果
     */
    BaseResponse totaldebt(ArchivesRequest request);

    /**
     * 负面拉黑接口
     *
     * @param request 请求参数
     * @return 查询结果
     */
    BaseResponse black(ArchivesRequest request);

    /**
     * 负面洗白接口
     *
     * @param request 请求参数
     * @return 查询结果
     */
    BaseResponse white(ArchivesRequest request);

    /**
     * 逾期档案接口
     *
     * @param request 请求参数
     * @return 查询结果
     */
    BaseResponse overdue(ArchivesRequest request);

    /**
     * 全景雷达报告
     *
     * @param radarRequest 请求参数
     * @return 查询结果
     */
    BaseResponse radarReport(RadarRequest radarRequest);

    /**
     * 申请雷达报告
     *
     * @param radarRequest 请求参数
     * @return 查询结果
     */
    BaseResponse radarApply(RadarRequest radarRequest);

    /**
     * 信用现状报告
     *
     * @param radarRequest 请求参数
     * @return 查询结果
     */
    BaseResponse radarCurrent(RadarRequest radarRequest);

    /**
     * 行为雷达报告
     *
     * @param radarRequest 请求参数
     * @return 查询结果
     */
    BaseResponse radarBehavior(RadarRequest radarRequest);

}
