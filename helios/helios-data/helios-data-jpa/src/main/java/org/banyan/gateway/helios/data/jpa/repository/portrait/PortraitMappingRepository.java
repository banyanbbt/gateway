package org.banyan.gateway.helios.data.jpa.repository.portrait;

import org.banyan.gateway.helios.data.jpa.domain.portrait.PortraitMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 画像映射repository
 * @date 2018-03-02 10:25:29
 */
@Repository
public interface PortraitMappingRepository extends CrudRepository<PortraitMapping, String> {

    /**
     * 查找所有in portrait的映射关系
     * @param portraits
     * @return
     */
    List<PortraitMapping> findByPortraitIn(List<String> portraits);

    /**
     * 根据渠道查找所有的映射关系
     * @param iface
     * @return
     */
    List<PortraitMapping> findByIface(String iface);


}
