package org.banyan.gateway.helios.data.jpa.repository.product;

import org.banyan.gateway.helios.data.jpa.domain.pk.ProductConfigPk;
import org.banyan.gateway.helios.data.jpa.domain.product.ProductConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ProductConfigRepository
 * 用户配置表
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月16日 14:44:00
 */
@Repository
public interface ProductConfigRepository extends CrudRepository<ProductConfig, ProductConfigPk> {
    /**
     * 根据产品编号查找配置
     * @param product
     * @return
     */
    List<ProductConfig> findByProduct(String product);
}
