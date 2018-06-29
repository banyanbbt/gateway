package org.banyan.gateway.helios.data.jpa.repository.product;

import org.banyan.gateway.helios.data.jpa.domain.product.ProductField;
import org.banyan.gateway.helios.data.jpa.dto.ProductFieldDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc product field repository
 * @date 2017-12-27 09:31:48
 */
@Repository
public interface ProductFieldRepository extends CrudRepository<ProductField, Integer> {

    /**
     * 根据productId查询，查询出的domain直接转成dto
     * @param product
     * @return
     */
    @Query(value = "select new org.banyan.gateway.helios.data.jpa.dto.ProductFieldDto(pf.id, f.field, f.name, pf.parentId, pf.required, f.minLength, f.maxLength, f.array, f.type, f.regex, f.encrypt) from " +
            "org.banyan.gateway.helios.data.jpa.domain.product.ProductField pf, org.banyan.gateway.helios.data.jpa.domain.product.Field f where pf.field=f.field and pf.product=?1")
    List<ProductFieldDto> findDtoByProduct(String product);

    boolean existsByProductAndField(String product, String field);
}