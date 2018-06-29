package org.banyan.gateway.helios.data.jpa.repository.portrait;

import org.banyan.gateway.helios.data.jpa.domain.portrait.Portrait;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * PortraitRepository
 * 画像指标
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月07日 21:20:00
 */
@Repository
public interface PortraitRepository extends CrudRepository<Portrait, String> {
    /**
     * 统计画像列表在表中个数
     * @param portraits
     * @return
     */
    int countByPortraitIn(Collection<String> portraits);
}
