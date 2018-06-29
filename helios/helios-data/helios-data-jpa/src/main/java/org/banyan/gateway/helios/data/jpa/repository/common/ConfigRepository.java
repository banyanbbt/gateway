package org.banyan.gateway.helios.data.jpa.repository.common;

import org.banyan.gateway.helios.data.jpa.domain.common.Config;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * ConfigRepository
 * 配置表
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月06日 17:01:00
 */
@Repository
public interface ConfigRepository extends CrudRepository<Config, String> {
}
