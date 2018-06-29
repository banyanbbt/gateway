package org.banyan.gateway.helios.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * JacksonUtil
 * Jackson JSON 工具类
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月20日 17:02:00
 */
public class JacksonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

    private static ObjectMapper objectMapperStatic = new ObjectMapper();

    /**
     * 对象转为JSON字符串
     *
     * @param object 待转换对象
     * @return JSON字符串
     */
    public static String toJson(Object object) {
        return toJson(objectMapperStatic, object);
    }

    /**
     * 对象转为JSON字符串
     *
     * @param object 待转换对象
     * @param objectMapper objectMapper
     * @return JSON字符串
     */
    public static String toJson(ObjectMapper objectMapper, Object object) {
        String str = null;
        if (null != object) {
            try {
                str = objectMapper.writer().writeValueAsString(object);
            } catch (IOException e) {
                LOGGER.info("对象转为JSON字符串失败{}", e.getMessage());
            }
        }
        return str;
    }

    /**
     * JSON字符串转为对象
     *
     * @param <T> 泛型
     * @param valueType 目标对象的类型
     * @param json JSON字符串
     * @return object 目标对象(转换失败返回 null)
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        return toObject(objectMapperStatic, json, valueType);
    }

    /**
     * JSON字符串转为对象
     *
     * @param <T> 泛型
     * @param objectMapper objectMapper
     * @param json JSON字符串
     * @param valueType 目标对象的类型
     * @return object 目标对象(转换失败返回 null)
     */
    public static <T> T toObject(ObjectMapper objectMapper, String json, Class<T> valueType) {
        T object = null;
        if (null != json) {
            try {
                object = objectMapper.readValue(json, valueType);
            } catch (Exception e) {
                LOGGER.info("JSON字符串转为对象{}", e.getMessage());
            }
        }
        return object;
    }

    /**
     * JSON字符串转为泛型
     *
     * @param <T> 泛型
     * @param json JSON字符串
     * @param valueTypeRef 目标对象的类型
     * @return T 目标对象(转换失败返回 null)
     */
    public static <T> T toObject(String json, TypeReference<?> valueTypeRef) {
        return toObject(objectMapperStatic, json, valueTypeRef);
    }

    /**
     * JSON字符串转为泛型
     *
     * @param objectMapper objectMapper
     * @param <T> 泛型
     * @param json JSON字符串
     * @param valueTypeRef 目标对象的类型
     * @return T 目标对象(转换失败返回 null)
     */
    public static <T> T toObject(ObjectMapper objectMapper, String json, TypeReference<?> valueTypeRef) {
        T object = null;
        if (null != json) {
            try {
                object = objectMapper.readValue(json, valueTypeRef);
            } catch (IOException e) {
                LOGGER.info("JSON字符串转为对象{}", e.getMessage());
            }
        }
        return object;
    }

    /**
     * JSON字符串转为泛型
     *
     * @param <T> 泛型
     * @param src InputStream
     * @param valueType 目标对象的类型
     * @return T 目标对象(转换失败返回 null)
     */
    public static <T> T toObject(InputStream src, Class<T> valueType) {
        return toObject(objectMapperStatic, src, valueType);
    }

    /**
     * JSON字符串转为泛型
     *
     * @param objectMapper objectMapper
     * @param <T> 泛型
     * @param src InputStream
     * @param valueType 目标对象的类型
     * @return T 目标对象(转换失败返回 null)
     */
    public static <T> T toObject(ObjectMapper objectMapper, InputStream src, Class<T> valueType) {
        T object = null;
        if (null != src) {
            try {
                object = objectMapper.readValue(src, valueType);
            } catch (IOException e) {
                LOGGER.info("JSON字符串转为对象{}", e.getMessage());
            }
        }
        return object;
    }

    /**
     * JSON字符串转为泛型
     *
     * @param <T> 泛型
     * @param src InputStream
     * @param valueTypeRef 目标对象的类型
     * @return T 目标对象(转换失败返回 null)
     */
    public static <T> T toObject(InputStream src, TypeReference<T> valueTypeRef) {
        return toObject(objectMapperStatic, src, valueTypeRef);
    }

    /**
     * JSON字符串转为泛型
     * @param objectMapper objectMapper
     * @param <T> 泛型
     * @param src InputStream
     * @param valueTypeRef 目标对象的类型
     * @return T 目标对象(转换失败返回 null)
     */
    public static <T> T toObject(ObjectMapper objectMapper, InputStream src, TypeReference<T> valueTypeRef) {
        T object = null;
        if (null != src) {
            try {
                object = objectMapper.readValue(src, valueTypeRef);
            } catch (IOException e) {
                LOGGER.info("JSON字符串转为对象{}", e.getMessage());
            }
        }
        return object;
    }

    /**
     * 获取 objectMapper
     *
     * @return objectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapperStatic;
    }
}
