package org.banyan.gateway.helios.data.jpa.repository.account;

import org.banyan.gateway.helios.data.jpa.domain.account.AccountConfig;
import org.banyan.gateway.helios.data.jpa.domain.pk.AccountConfigPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * AccountConfigRepository
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月06日 17:06:00
 */
@Repository
public interface AccountConfigRepository extends CrudRepository<AccountConfig, AccountConfigPk> {

    Set<AccountConfig> findByAccountAndProduct(String account, String product);

    Set<AccountConfig> findByAccountAndProductAndConfigIn(String account, String product, Set<String> configs);
}
