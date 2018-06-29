package org.banyan.gateway.helios.data.jpa.domain.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * AccountProduct PK
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月03日 16:01:00
 */
@Embeddable
public class AccountProductPk implements Serializable {
    private static final long serialVersionUID = 3634401809708545528L;
    @Column(name = "account", nullable = false)
    private String account;
    // 产品标记
    @Column(name = "product", nullable = false)
    private String product;

    public String getAccount() {
        return account;
    }

    public AccountProductPk setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public AccountProductPk setProduct(String product) {
        this.product = product;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountProductPk that = (AccountProductPk) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, product);
    }
}
