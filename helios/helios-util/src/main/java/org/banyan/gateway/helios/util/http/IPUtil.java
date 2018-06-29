package org.banyan.gateway.helios.util.http;

import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc IPV4工具包
 * @date 2017-12-25 09:31:48
 */
public class IPUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(IPUtil.class);

    public static final List<String> WHITE_IP = new ArrayList<String>();
    static {
        // 开发环境或者测试环境的ip
        WHITE_IP.add("127.0.0.1");
        WHITE_IP.add("0.0.0.0");
        WHITE_IP.add("0:0:0:0:0:0:0:1");

        // 公司公网ip
//        WHITE_IP.add("xx.xx.xx.xx");
    }

    public static boolean isWhiteIP(String ip) {
        return match(WHITE_IP, ip);
    }

    public static boolean match(String expectIp, String ip) {
        List<String> list = new LinkedList<>();
        Collections.addAll(list, expectIp.split(Constants.COMMA));
        return match(list, ip);
    }

    public static boolean match(List<String> ips, String ip) {
        if (StringUtil.isBlank(ip)) {
            return false;
        }

        LOGGER.info("白名单认证:" + ip);
        try {
            ip = ip.trim();

            for (String _ip : ips) {
                String ipPattern = "^" + _ip.replaceAll("\\*", "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])").replaceAll("\\.", "\\\\.") + "$";
                if (ip.matches(ipPattern)) {
                    return true;
                }
            }
        } catch (Throwable e) {
            LOGGER.info("白名单认证遇到异常", e);
//            return false;
        }

        return false;
    }

}
