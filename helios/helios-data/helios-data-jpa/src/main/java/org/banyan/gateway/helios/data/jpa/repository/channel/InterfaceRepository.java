package org.banyan.gateway.helios.data.jpa.repository.channel;

import org.banyan.gateway.helios.data.jpa.domain.channel.Interface;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * InterfaceRepository
 * 渠道第三方提供的接口
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月07日 20:47:00
 */
@Repository
public interface InterfaceRepository extends CrudRepository<Interface, String> {
    /**
     * 统计第三方接口列表在表中个数
     * @param ifaces
     * @return
     */
    int countByIfaceIn(Collection<String> ifaces);

    List<Interface> findByIfaceIn(Collection<String> ifaces);
}