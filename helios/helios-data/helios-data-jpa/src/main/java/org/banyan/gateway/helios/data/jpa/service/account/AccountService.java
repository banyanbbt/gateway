package org.banyan.gateway.helios.data.jpa.service.account;

import org.banyan.gateway.helios.data.jpa.domain.account.Account;
import org.banyan.gateway.helios.data.jpa.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 账户service
 * @date 2018-01-12 11:25:26
 */
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * 根据账号名查询Account对象
     * @param account
     * @return
     */
    public Account get(String account) {
        return this.accountRepository.findOne(account);
    }

    @Transactional
    public void deductMoney(String account, BigDecimal balance) {
        Account user = get(account);
        if (null != user && null != user.getBalance()) {
            accountRepository.deductMoney(account, balance);
        }
    }
}
