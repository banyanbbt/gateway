package org.banyan.gateway.helios.data.jpa.domain.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ConfigPk
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月07日 16:28:00
 */
@Embeddable
public class ProductConfigPk implements Serializable {
    private static final long serialVersionUID = 3452398974682878291L;
    // 参数名
    @Column(name = "config", nullable = false)
    private String config;
    // 产品标记
    @Column(name = "product", nullable = false)
    private String product;

    public String getConfig() {
        return config;
    }

    public ProductConfigPk setConfig(String config) {
        this.config = config;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public ProductConfigPk setProduct(String product) {
        this.product = product;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductConfigPk configPk = (ProductConfigPk) o;
        return Objects.equals(config, configPk.config) &&
                Objects.equals(product, configPk.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(config, product);
    }
}
