package org.banyan.gateway.hades.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ProtoInterfaceService
 * 获取dubbo接口服务
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月14日 11:47:00
 */
@Service
public class ProtoInterfaceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoInterfaceService.class);
    private static final String PATH = "org/banyan/gateway/helios/proto/iface/product/";

    private static Map<String, Class<?>> interfaces = null;

    /**
     * 根据接口名获取接口的class
     */
    public Class<?> getInterface(String key) {
        if (null == interfaces) {
            synchronized (this) {
                if (null == interfaces) {
                    interfaces = new HashMap<>();
                    try {
                        loadInterfaces();
                    } catch (IOException e) {
                        LOGGER.info("加载接口异常", e);
                    }
                }
            }
        }

        return interfaces.get(key);
    }

    /**
     * 加载
     */
    private void loadInterfaces() throws IOException {

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(PATH) + "**/*.class";
        Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                Class<?> cls = loadClass(metadataReader);
                if (null != cls) {
                    interfaces.put(cls.getSimpleName(), cls);
                }
            }
        }

    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
    }

    /**
     * 加载类
     * 需要提供类名与是否初始化的标志
     * 初始化是指是否执行静态代码块
     * @param metadataReader metadataReader
     * @return Class<?> 返回类型
     */
    private Class<?> loadClass(MetadataReader metadataReader) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(metadataReader.getClassMetadata().getClassName(), false, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载类失败 loadClass。", e);
        }
        return clazz;
    }

    private ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
