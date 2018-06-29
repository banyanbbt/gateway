package org.banyan.gateway.helios.data.jpa.domain.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * AccountConfig PK
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月03日 15:59:00
 */
@Embeddable
public class AccountConfigPk implements Serializable {
    private static final long serialVersionUID = 7996491063905303511L;
    @Column(name = "account", nullable = false)
    private String account;
    // 配置键
    @Column(name = "config", nullable = false)
    private String config;
    // 产品列表
    @Column(name = "product", nullable = false)
    private String product;

    public String getAccount() {
        return account;
    }

    public AccountConfigPk setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getConfig() {
        return config;
    }

    public AccountConfigPk setConfig(String config) {
        this.config = config;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public AccountConfigPk setProduct(String product) {
        this.product = product;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountConfigPk that = (AccountConfigPk) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(config, that.config) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, config, product);
    }
}
