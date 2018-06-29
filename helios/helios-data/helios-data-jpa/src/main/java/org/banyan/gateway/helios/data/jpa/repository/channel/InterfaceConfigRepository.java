package org.banyan.gateway.helios.data.jpa.repository.channel;

import org.banyan.gateway.helios.data.jpa.domain.channel.InterfaceConfig;
import org.banyan.gateway.helios.data.jpa.domain.pk.InterfaceConfigPk;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * InterfaceConfigRepository
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月19日 15:33:00
 */
public interface InterfaceConfigRepository extends CrudRepository<InterfaceConfig, InterfaceConfigPk> {
    Set<InterfaceConfig> findByIface(String iface);

    List<InterfaceConfig> findByIfaceIn(List<String> ifaces);
}
