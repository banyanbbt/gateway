package org.banyan.gateway.eris;

import org.banyan.gateway.helios.common.SubmitCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 返回码映射类
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/28 09:27
 */
public class CodeMapping {
    public static Map<String, SubmitCode> MAPPING = new HashMap<>();
    public static Map<String, SubmitCode> VERIFY_MAPPING = new HashMap<>();
    public static Map<String, Boolean> FEE_MAPPING = new HashMap<>();

    static {
        MAPPING.put("100", SubmitCode.QUERY_SUCCESS);
        MAPPING.put("101", SubmitCode.QUERY_NO_DATA);

        MAPPING.put("200", SubmitCode.CHANNEL_ERROR);
        MAPPING.put("201", SubmitCode.CHANNEL_ERROR);
        MAPPING.put("202", SubmitCode.CHANNEL_ERROR);
        MAPPING.put("203", SubmitCode.CHANNEL_ERROR);
        MAPPING.put("210", SubmitCode.CHANNEL_ERROR);
        MAPPING.put("300", SubmitCode.CHANNEL_ERROR);
        MAPPING.put("301", SubmitCode.CHANNEL_ERROR);
        MAPPING.put("302", SubmitCode.ARGS_FAILED);
        MAPPING.put("501", SubmitCode.CHANNEL_ERROR);
        MAPPING.put("502", SubmitCode.CHANNEL_ERROR);

        FEE_MAPPING.put("100", Boolean.TRUE);
        FEE_MAPPING.put("101", Boolean.FALSE);

        FEE_MAPPING.put("200", Boolean.FALSE);
        FEE_MAPPING.put("201", Boolean.FALSE);
        FEE_MAPPING.put("202", Boolean.FALSE);
        FEE_MAPPING.put("203", Boolean.FALSE);
        FEE_MAPPING.put("210", Boolean.FALSE);
        FEE_MAPPING.put("300", Boolean.FALSE);
        FEE_MAPPING.put("301", Boolean.FALSE);
        FEE_MAPPING.put("302", Boolean.FALSE);
        FEE_MAPPING.put("501", Boolean.FALSE);
        FEE_MAPPING.put("502", Boolean.FALSE);

        VERIFY_MAPPING.put("0000", SubmitCode.VALIDATE_CONSISTENT);
        VERIFY_MAPPING.put("1103", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("1201", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("1302", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("1305", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("1399", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2208", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2301", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2302", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2306", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2308", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2309", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2310", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2311", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2314", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2315", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("2316", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2317", SubmitCode.VALIDATE_CONSISTENT);
        VERIFY_MAPPING.put("2318", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2319", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2320", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2321", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("2324", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2325", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("2326", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2327", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2329", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2330", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2334", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("2341", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2342", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("2343", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("2344", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2345", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("2400", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2401", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2402", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2403", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("2404", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("2405", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("4001", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("4002", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("4003", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("4004", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("4005", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("4006", SubmitCode.CHANNEL_ERROR);
        VERIFY_MAPPING.put("5000", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("5101", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("5102", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("5103", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("5104", SubmitCode.VALIDATE_UNSUPPORT);
        VERIFY_MAPPING.put("5105", SubmitCode.VALIDATE_UNSUPPORT);
    }
}
