package org.banyan.gateway.helios.data.jpa.service.account;

import org.banyan.gateway.helios.data.jpa.domain.account.AccountIp;
import org.banyan.gateway.helios.data.jpa.repository.account.AccountIpRepository;
import org.banyan.gateway.helios.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 账户ip service
 * @date 2018-01-12 11:25:26
 */
@Service
public class AccountIpService {
    @Autowired
    private AccountIpRepository accountIpRepository;

    /**
     * 根据账号名查询Account的ip白名单，用逗号拼接隔开返回
     * @param account
     * @return
     */
    public List<String> findByAccount(String account) {
        List<AccountIp> accountIps = this.accountIpRepository.findByAccount(account);
        List<String> ips = new ArrayList<>();
        accountIps.forEach(accountIp -> {
            String ip = accountIp.getRegex();
            if (StringUtil.isNotBlank(ip)) {
                ips.add(ip);
            }
        });
        return ips;
    }

}
