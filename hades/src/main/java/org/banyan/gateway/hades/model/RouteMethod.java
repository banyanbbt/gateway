package org.banyan.gateway.hades.model;

import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RouteMethod
 * 路由对象
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月14日 10:28:00
 */
public class RouteMethod {
    // 接口
    private String iface;
    // 方法名
    private String methodName;
    // 对象类名
    private Class<?> beanType;
    // 方法
    private Method method;
    // 方法参数
    private MethodParameter[] parameters;

    public RouteMethod(String iface, String methodName, Class<?> beanType) {
        this.iface = iface;
        this.methodName = methodName;
        this.beanType = beanType;

        List<Method> methods = new ArrayList<>();
        ReflectionUtils.doWithMethods(beanType, m -> {
            if (m.getName().equals(methodName)) {
                methods.add(m);
            }
        });
        Assert.notEmpty(methods, "无法获取接口方法");
        // TODO 无法结果同一个 重载 的问题
        this.method = methods.get(0);
        initMethodParameters();
    }

    private void initMethodParameters() {
        int count = this.method.getParameterTypes().length;
        MethodParameter[] result = new MethodParameter[count];
        for (int i = 0; i < count; i++) {
            MethodParameter parameter = new MethodParameter(this.method, i);
            GenericTypeResolver.resolveParameterType(parameter, this.beanType);
            result[i] = parameter;
        }
        parameters = result;
    }

    public String getIface() {
        return iface;
    }

    public RouteMethod setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public RouteMethod setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public RouteMethod setBeanType(Class<?> beanType) {
        this.beanType = beanType;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public RouteMethod setMethod(Method method) {
        this.method = method;
        return this;
    }

    public MethodParameter[] getParameters() {
        return parameters;
    }

    public RouteMethod setParameters(MethodParameter[] parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public String toString() {
        return "RouteMethod{" +
                "iface='" + iface + '\'' +
                ", methodName='" + methodName + '\'' +
                ", beanType=" + beanType.getName() +
                '}';
    }
}

