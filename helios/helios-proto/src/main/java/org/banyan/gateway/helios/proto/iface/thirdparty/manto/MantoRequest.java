package org.banyan.gateway.helios.proto.iface.thirdparty.manto;

import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.common.Operator;
import org.banyan.gateway.helios.common.RequestField;
import org.banyan.gateway.helios.proto.dto.product.InterfaceConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 请求体
 * @date 2018-06-27 18:21:16
 */
public class MantoRequest {
    private String productId;

    private Interface interfaze;

    private InterfaceConfig interfaceConfig;

    private Operator operator;

    private Map<RequestField, Object> parameters;

    public MantoRequest() {
    }

    public MantoRequest(String productId) {
        this.productId = productId;
    }

    public MantoRequest(String productId, Interface interfaze) {
        this.productId = productId;
        this.interfaze = interfaze;
    }

    public MantoRequest(String productId, Interface interfaze, InterfaceConfig interfaceConfig) {
        this.productId = productId;
        this.interfaze = interfaze;
        this.interfaceConfig = interfaceConfig;
    }

    public String getProductId() {
        return productId;
    }

    public MantoRequest setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public Map<RequestField, Object> getParameters() {
        return parameters;
    }

    public MantoRequest setParameters(Map<RequestField, Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    public Interface getInterfaze() {
        return interfaze;
    }

    public MantoRequest setInterfaze(Interface interfaze) {
        this.interfaze = interfaze;
        return this;
    }

    public InterfaceConfig getInterfaceConfig() {
        return interfaceConfig;
    }

    public MantoRequest setInterfaceConfig(InterfaceConfig interfaceConfig) {
        this.interfaceConfig = interfaceConfig;
        return this;
    }

    public Operator getOperator() {
        return operator;
    }

    public MantoRequest setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public MantoRequest addParameter(RequestField field, Object value) {
        if (null == parameters) {
            parameters = new HashMap<>();
        }
        this.parameters.put(field, value);
        return this;
    }
}
