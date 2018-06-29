package org.banyan.gateway.poseidon.service;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.banyan.gateway.helios.common.ThirdpartyFee;
import org.banyan.gateway.helios.data.jpa.domain.account.AccountProduct;
import org.banyan.gateway.helios.data.jpa.domain.channel.Interface;
import org.banyan.gateway.helios.data.jpa.domain.product.Product;
import org.banyan.gateway.helios.data.jpa.repository.channel.InterfaceRepository;
import org.banyan.gateway.helios.data.jpa.repository.product.ProductRepository;
import org.banyan.gateway.helios.data.jpa.service.account.AccountProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * FeeService
 * fee
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月23日 16:38:00
 */
@Service
public class FeeService {
    @Autowired
    private InterfaceRepository interfaceRepository;
    @Autowired
    private AccountProductService accountProductService;
    @Autowired
    private ProductRepository productRepository;

    /**
     * 获取产品价格
     * @param account
     * @param product
     * @param price
     * @return
     */
    public BigDecimal getProductFee(String account, String product, BigDecimal price, int cut) {
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(product)) {
            AccountProduct accountProduct = this.accountProductService.findByAccountAndProduct(account, product);
            if (null != accountProduct) {
                if (null == price) {
                    // 获取价格
                    price = accountProduct.getPrice();
                    if (null == price) {
                        Product prod = this.productRepository.findOne(product);
                        if (null != prod) {
                            price = prod.getPrice();
                        }
                    }

                    if (null != price && cut > 0) {
                        price = price.multiply(new BigDecimal(cut));
                    } else {
                        price = null;
                    }
                }

                if (null != price) {
                    // 判断上线、判断下线
                    BigDecimal highestPrice = accountProduct.getHighestPrice();
                    BigDecimal lowestPrice = accountProduct.getLowestPrice();

                    if (null != highestPrice && price.compareTo(highestPrice) > 0) {
                        price = highestPrice;
                    } else if (null != lowestPrice && price.compareTo(lowestPrice) < 0) {
                        price = lowestPrice;
                    }
                }
            }

        }
        return price;
    }

    /**
     * 获取第三方接口成本
     * @param iface
     * @param price
     * @return
     */
    public BigDecimal getInterfaceFee(String iface, BigDecimal price) {
        if (StringUtils.isNotBlank(iface)) {
            Interface repository = this.interfaceRepository.findOne(iface);

            if (null != repository) {
                if (null == price) {
                    price = repository.getPrice();
                }

                BigDecimal highestPrice = repository.getHighestPrice();
                BigDecimal lowestPrice = repository.getLowestPrice();
                if (null != price) {
                    // 判断上线、判断下线
                    if (null != highestPrice && price.compareTo(highestPrice) > 0) {
                        price = highestPrice;
                    } else if (null != lowestPrice && price.compareTo(lowestPrice) < 0) {
                        price = lowestPrice;
                    }
                }
            }
        }
        return price;
    }

    public Set<String> handleMultiThirdpartyFee(List<ThirdpartyFee> thirdpartyFees) {
        Set<String> interfaces = new HashSet<>();

        if (!CollectionUtils.isEmpty(thirdpartyFees)) {
            for (ThirdpartyFee thirdpartyFee : thirdpartyFees) {
                if (null != thirdpartyFee && BooleanUtils.isTrue(thirdpartyFee.getFee())) {
                    BigDecimal price = thirdpartyFee.getPrice();
                    String iface = thirdpartyFee.getIface();
                    if (StringUtils.isNotBlank(iface)) {
                        interfaces.add(iface);
                    }
                    thirdpartyFee.setPrice(this.getInterfaceFee(iface, price));
                }
            }
        }

        return interfaces;
    }
}
