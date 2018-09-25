package org.banyan.gateway.proteus;

import org.banyan.gateway.helios.common.SubmitCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 返回码映射绑定
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/18 09:59
 */
public class CodeMapping {
    final static Map<String, SubmitCode> ERROR_MAP = new HashMap<>();

    static {
        ERROR_MAP.put("S0001", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1000", SubmitCode.ARGS_FAILED);
        ERROR_MAP.put("S1001", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1002", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1004", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1005", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1006", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1007", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1010", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1011", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1012", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1013", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1014", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S1015", SubmitCode.VALIDATE_UNSUPPORT);
        ERROR_MAP.put("S1016", SubmitCode.VALIDATE_UNSUPPORT);
        ERROR_MAP.put("S2000", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S2001", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S2002", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S2003", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S2004", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S2005", SubmitCode.CHANNEL_ERROR);
        ERROR_MAP.put("S2006", SubmitCode.CHANNEL_ERROR);
    }

    final static Map<String, SubmitCode> VALIDATE_CODE_MAP = new HashMap<>();
    final static Map<String, SubmitCode> DEBT_CODE_MAP = new HashMap<>();
    final static Map<String, SubmitCode> BLACK_CODE_MAP = new HashMap<>();
    final static Map<String, SubmitCode> WHITE_CODE_MAP = new HashMap<>();
    final static Map<String, SubmitCode> OVERDUE_CODE_MAP = new HashMap<>();
    final static Map<String, SubmitCode> RADAR_CODE_MAP = new HashMap<>();
    final static Map<String, SubmitCode> MOBILE_CODE_MAP = new HashMap<>();

    static {
        VALIDATE_CODE_MAP.put("0", SubmitCode.VALIDATE_CONSISTENT);
        VALIDATE_CODE_MAP.put("1", SubmitCode.VALIDATE_INCONSISTENT);
        VALIDATE_CODE_MAP.put("3", SubmitCode.VALIDATE_FAILED);
        VALIDATE_CODE_MAP.put("9", SubmitCode.VALIDATE_UNSUPPORT);

        DEBT_CODE_MAP.put("0", SubmitCode.QUERY_SUCCESS);
        DEBT_CODE_MAP.put("1", SubmitCode.QUERY_NO_DATA);
        DEBT_CODE_MAP.put("9", SubmitCode.QUERY_FAILED);

        BLACK_CODE_MAP.put("0", SubmitCode.QUERY_SUCCESS);
        BLACK_CODE_MAP.put("1", SubmitCode.QUERY_NO_DATA);
        BLACK_CODE_MAP.put("2", SubmitCode.QUERY_NO_DATA);
        BLACK_CODE_MAP.put("9", SubmitCode.QUERY_FAILED);

        WHITE_CODE_MAP.put("0", SubmitCode.QUERY_SUCCESS);
        WHITE_CODE_MAP.put("1", SubmitCode.QUERY_SUCCESS);
        WHITE_CODE_MAP.put("2", SubmitCode.QUERY_NO_DATA);
        WHITE_CODE_MAP.put("3", SubmitCode.QUERY_NO_DATA);
        WHITE_CODE_MAP.put("9", SubmitCode.QUERY_FAILED);

        OVERDUE_CODE_MAP.put("0", SubmitCode.QUERY_SUCCESS);
        OVERDUE_CODE_MAP.put("1", SubmitCode.QUERY_NO_DATA);
        OVERDUE_CODE_MAP.put("9", SubmitCode.QUERY_FAILED);

        RADAR_CODE_MAP.put("0", SubmitCode.QUERY_SUCCESS);
        RADAR_CODE_MAP.put("1", SubmitCode.QUERY_NO_DATA);
        RADAR_CODE_MAP.put("9", SubmitCode.QUERY_FAILED);


        MOBILE_CODE_MAP.put("0", SubmitCode.VALIDATE_CONSISTENT);
        MOBILE_CODE_MAP.put("1", SubmitCode.VALIDATE_INCONSISTENT);
        MOBILE_CODE_MAP.put("2", SubmitCode.VALIDATE_UNSURE);
        MOBILE_CODE_MAP.put("9", SubmitCode.CHANNEL_ERROR);
    }

    final static Map<String, SubmitCode.ArgsFailedDetail> ARGS_FAILED = new HashMap<>();

    static {
        ARGS_FAILED.put("银行卡号不能为空", SubmitCode.ArgsFailedDetail.CARD_FAILED);
        ARGS_FAILED.put("银行卡号格式有误", SubmitCode.ArgsFailedDetail.CARD_FAILED);
        ARGS_FAILED.put("身份证姓名不能为空", SubmitCode.ArgsFailedDetail.NAME_FAILED);
        ARGS_FAILED.put("身份证姓名格式有误", SubmitCode.ArgsFailedDetail.NAME_FAILED);
        ARGS_FAILED.put("手机号不能为空", SubmitCode.ArgsFailedDetail.MOBILE_FAILED);
        ARGS_FAILED.put("手机号型格式有误", SubmitCode.ArgsFailedDetail.MOBILE_FAILED);
        ARGS_FAILED.put("身份证号不能为空", SubmitCode.ArgsFailedDetail.CID_FAILED);
    }
}
