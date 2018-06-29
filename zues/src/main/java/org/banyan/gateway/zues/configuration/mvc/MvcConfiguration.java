package org.banyan.gateway.zues.configuration.mvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.banyan.gateway.zues.web.interceptor.ZuesErrorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc mvc配置
 * @date 2018-01-11 17:31:45
 */
@Configuration
public class MvcConfiguration {

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    public HttpMessageConverters getJacksonHttpMessageConverters() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN));
        return new HttpMessageConverters(converter);
    }

    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes();
    }

    @Bean
    public ZuesErrorController zuesErrorController(ErrorAttributes errorAttributes) {
        return new ZuesErrorController(errorAttributes, this.serverProperties.getError());
    }

}
