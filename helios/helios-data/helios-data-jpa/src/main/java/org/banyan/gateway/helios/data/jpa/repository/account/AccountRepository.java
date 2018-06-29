package org.banyan.gateway.helios.data.jpa.repository.account;

import org.banyan.gateway.helios.data.jpa.domain.account.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc account repository
 * @date 2017-12-27 09:31:48
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance - ?2 WHERE a.account = ?1")
    void deductMoney(String account, BigDecimal balance);
}
