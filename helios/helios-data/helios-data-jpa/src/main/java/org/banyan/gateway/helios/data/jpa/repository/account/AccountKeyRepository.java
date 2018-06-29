package org.banyan.gateway.helios.data.jpa.repository.account;

import org.banyan.gateway.helios.data.jpa.domain.account.AccountKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc account key repository
 * @date 2017-12-27 09:31:48
 */
@Repository
public interface AccountKeyRepository extends CrudRepository<AccountKey, String> {

    /**
     * 根据账号id查询
     * @param account
     * @param available
     * @return
     */
    List<AccountKey> findByAccountAndAvailable(String account, boolean available);

}
