package org.banyan.gateway.helios.data.jpa.service.product;

import org.banyan.gateway.helios.data.jpa.domain.product.Product;
import org.banyan.gateway.helios.data.jpa.repository.portrait.PortraitRepository;
import org.banyan.gateway.helios.data.jpa.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc product service
 * @date 2018-01-13 10:46:01
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PortraitRepository portraitRepository;

    /**
     * 根据主键获取
     * @param product
     * @return
     */
    public Product get(String product) {
        return this.productRepository.findOne(product);
    }

    /**
     * 根据code查询Product
     * @param product
     * @param status
     * @return
     */
    public Product findByProductAndStatus(String product, boolean status) {
        return this.productRepository.findByProductAndStatus(product, status);
    }

    /**
     * 判断画像列表是否都在表中
     * @param portraits
     * @return
     */
    public boolean isContainsPortraits(Set<String> portraits) {
        boolean flag = false;
        if (!CollectionUtils.isEmpty(portraits)) {
            int len = portraitRepository.countByPortraitIn(portraits);
            if (portraits.size() == len) {
                flag = true;
            }
        }

        return flag;
    }
}
