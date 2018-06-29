package org.banyan.gateway.helios.data.jpa.repository.account;

import org.banyan.gateway.helios.data.jpa.domain.account.AccountProduct;
import org.banyan.gateway.helios.data.jpa.domain.pk.AccountProductPk;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc account product repository
 * @date 2017-12-27 09:31:48
 */
@Repository
public interface AccountProductRepository extends CrudRepository<AccountProduct, AccountProductPk> {

    /**
     * 根据账号id查询
     * @param account
     * @param product
     * @param status
     * @return
     */
    AccountProduct findByAccountAndProductAndStatus(String account, String product, boolean status);

    @Modifying
    @Query("UPDATE AccountProduct a SET a.usedCount = a.usedCount + 1 WHERE a.account = ?1 AND a.product = ?2")
    void increaseUsedCount(String account, String product);
}
