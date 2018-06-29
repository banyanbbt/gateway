package org.banyan.gateway.helios.data.jpa.service.channel;

import org.banyan.gateway.helios.data.jpa.domain.channel.Interface;
import org.banyan.gateway.helios.data.jpa.domain.channel.InterfaceConfig;
import org.banyan.gateway.helios.data.jpa.dto.InterfaceDto;
import org.banyan.gateway.helios.data.jpa.repository.channel.InterfaceConfigRepository;
import org.banyan.gateway.helios.data.jpa.repository.channel.InterfaceRepository;
import org.banyan.gateway.helios.data.jpa.service.common.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * InterfaceService
 * 渠道第三方提供的接口服务
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月19日 15:49:00
 */
@Service
public class InterfaceService {
    @Autowired
    private InterfaceRepository interfaceRepository;
    @Autowired
    private InterfaceConfigRepository interfaceConfigRepository;
    @Autowired
    private ConfigService configService;

    /**
     * 判断第三方列表是否都在表中
     * @param ifaces
     * @return
     */
    public boolean isContainsInterfaces(Set<String> ifaces) {
        boolean flag = false;
        if (!CollectionUtils.isEmpty(ifaces)) {
            int len = this.interfaceRepository.countByIfaceIn(ifaces);
            if (ifaces.size() == len) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 按顺序获取接口配置
     * @param ifaces
     * @return
     */
    public List<InterfaceDto> findByIfaces(List<String> ifaces) {
        List<InterfaceDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ifaces)) {
            List<Interface> interfaces = this.interfaceRepository.findByIfaceIn(ifaces);
            if (!CollectionUtils.isEmpty(interfaces)) {
                Map<String, Map<String, String>> interfaceConfigMap = interfaceConfigMap(ifaces);
                result = interfaces.stream()
                        .map(InterfaceService.this::convertInterfaceDto)
                        .filter(Objects::nonNull)
                        .map(interfaceDto -> interfaceDto.setInterfaceConfigs(interfaceConfigMap.get(interfaceDto.getIface())))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        }
        return result;
    }

    /**
     * 获取Interface Config并转换成Map
     * @param ifaces
     * @return iface -> config -> value
     */
    private Map<String, Map<String, String>> interfaceConfigMap(List<String> ifaces) {
        Map<String, Map<String, String>> interfaceConfigMap = null;
        List<InterfaceConfig> interfaceConfigs = this.interfaceConfigRepository.findByIfaceIn(ifaces);
        Map<String, String> configMap = this.configService.configMap();

        if (!CollectionUtils.isEmpty(interfaceConfigs)) {
            interfaceConfigMap = interfaceConfigs.stream()
                    .filter(x -> nonNull(x.getIface()) && nonNull(x.getConfig()) && (nonNull(x.getValue()) || nonNull(configMap.get(x.getConfig()))))
                    .collect(Collectors.groupingBy(InterfaceConfig::getIface, Collectors.toMap(InterfaceConfig::getConfig, interfaceConfig -> Objects.nonNull(interfaceConfig.getValue()) ? interfaceConfig.getValue() : configMap.get(interfaceConfig.getConfig()))));
        } else {
            interfaceConfigMap = new HashMap<>();
        }
        return interfaceConfigMap;
    }

    private InterfaceDto convertInterfaceDto(Interface iface) {
        InterfaceDto interfaceDto = null;
        if (null != iface) {
            interfaceDto = new InterfaceDto();

            interfaceDto.setIface(iface.getIface());
            interfaceDto.setName(iface.getName());
            interfaceDto.setChannel(iface.getChannel());
            interfaceDto.setApiUrl(iface.getApiUrl());
            interfaceDto.setPrice(iface.getPrice());
            interfaceDto.setHighestPrice(iface.getHighestPrice());
            interfaceDto.setLowestPrice(iface.getLowestPrice());
            interfaceDto.setStatus(iface.isStatus());
            interfaceDto.setTimeout(iface.getTimeout());
            interfaceDto.setDescription(iface.getDescription());
            interfaceDto.setCreatedBy(iface.getCreatedBy());
            interfaceDto.setCreateTime(iface.getCreateTime());
            interfaceDto.setUpdateTime(iface.getUpdateTime());
        }

        return interfaceDto;
    }
}