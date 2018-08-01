package org.banyan.gateway.helios.data.hbase.mapper;

import org.banyan.gateway.helios.data.hbase.dto.MobileCards;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.util.StringUtil;
import org.banyan.spring.boot.starter.hbase.api.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 手机号卡号对应关系
 * @date 2017-12-27 09:31:48
 */
public class MobileCardsRowMapper implements RowMapper<MobileCards> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileCardsRowMapper.class);

    private static byte[] COLUMNFAMILY = "f".getBytes();
    private static byte[] NAME = "cards".getBytes();

    @Override
    public MobileCards mapRow(Result result, int rowNum) {
        MobileCards mobileCards = new MobileCards();
        mobileCards.setMobile(Bytes.toString(result.getRow()));
        String cards = Bytes.toString(result.getValue(COLUMNFAMILY, NAME));
        if (StringUtil.isNotBlank(cards)) {
            mobileCards.setCards(Arrays.asList(cards.split(Constants.COMMA)));
        }
        return mobileCards;
    }
}
