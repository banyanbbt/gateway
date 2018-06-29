package org.banyan.gateway.helios.data.jpa.repository.product;

import org.banyan.gateway.helios.data.jpa.domain.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc product repository
 * @date 2017-12-27 09:31:48
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    /**
     * 根据账号查询
     * @param product
     * @param status
     * @return
     */
    Product findByProductAndStatus(String product, boolean status);
}
