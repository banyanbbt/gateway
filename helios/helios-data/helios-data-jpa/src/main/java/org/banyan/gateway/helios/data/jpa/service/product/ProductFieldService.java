package org.banyan.gateway.helios.data.jpa.service.product;

import org.banyan.gateway.helios.data.jpa.dto.ProductFieldDto;
import org.banyan.gateway.helios.data.jpa.repository.product.ProductFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc product field service
 * @date 2018-01-13 10:46:01
 */
@Service
public class ProductFieldService {
    @Autowired
    private ProductFieldRepository productFieldRepository;

    public boolean existsByProductAndField(String product, String field) {
        return this.productFieldRepository.existsByProductAndField(product, field);
    }

    /**
     * 查询该产品所有的参数
     * @param product
     * @return
     */
    public List<ProductFieldDto> findByProduct(String product) {
        List<ProductFieldDto> list = this.productFieldRepository.findDtoByProduct(product);

        Map<String, ProductFieldDto> map = new HashMap<>();
        list.forEach(productFieldDto -> {
            ProductFieldDto item = map.get(productFieldDto.getField());
            if (null == item || item.getId() > productFieldDto.getId()) {
                map.put(productFieldDto.getField(), productFieldDto);
            }
        });
        List<ProductFieldDto> dbFields = new ArrayList<>(map.values());
        dbFields.sort(Comparator.comparing(ProductFieldDto::getId));

        List<ProductFieldDto> fields = new ArrayList<>();
        if (!dbFields.isEmpty()) {
            Map<Integer, ProductFieldDto> fieldMap = new HashMap<>();
            for (ProductFieldDto field : dbFields) {
                fieldMap.put(field.getId(), field);
            }

            // 组装children
            ProductFieldDto parent = null;
            List<ProductFieldDto> children = null;
            for (ProductFieldDto field : fieldMap.values()) {
                Integer parentField = field.getParentId();
                if (null != parentField) {
                    parent = fieldMap.get(parentField);
                    if (null != parent) {
                        children = parent.getChildren();

                        if (null == children) {
                            children = new ArrayList<>();
                            parent.setChildren(children);
                        }
                        children.add(field);
                    }
                } else {
                    fields.add(field);
                }
            }
        }

        return fields;
    }
}
