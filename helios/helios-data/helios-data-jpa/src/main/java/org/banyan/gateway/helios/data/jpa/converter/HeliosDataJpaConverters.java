package org.banyan.gateway.helios.data.jpa.converter;

import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.data.jpa.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * HeliosDataJpaConverters
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月01日 16:31:00
 */
public class HeliosDataJpaConverters {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeliosDataJpaConverters.class);
    @Converter(autoApply = true)
    public static class DataTableTypeConverter implements AttributeConverter<DataTableType, String> {
        @Override
        public String convertToDatabaseColumn(DataTableType dataTableType) {
            return null != dataTableType ? dataTableType.name() : null;
        }

        @Override
        public DataTableType convertToEntityAttribute(String dataTableType) {
            return StringUtils.hasText(dataTableType) ? DataTableType.valueOf(dataTableType) : null;
        }
    }

    @Converter(autoApply = true)
    public static class AccountGradeConverter implements AttributeConverter<AccountGrade, String> {

        @Override
        public String convertToDatabaseColumn(AccountGrade grade) {
            return null != grade ? grade.name() : null;
        }

        @Override
        public AccountGrade convertToEntityAttribute(String grade) {
            return StringUtils.hasText(grade) ? AccountGrade.valueOf(grade) : null;
        }
    }

    @Converter(autoApply = true)
    public static class AccountStatusConverter implements AttributeConverter<AccountStatus, String> {
        @Override
        public String convertToDatabaseColumn(AccountStatus status) {
            return null != status ? status.name() : null;
        }

        @Override
        public AccountStatus convertToEntityAttribute(String status) {
            return StringUtils.hasText(status) ? AccountStatus.valueOf(status) : null;
        }
    }

    @Converter(autoApply = true)
    public static class ProductFeeTypeConverter implements AttributeConverter<ProductFeeType, String> {
        @Override
        public String convertToDatabaseColumn(ProductFeeType feeType) {
            return null != feeType ? feeType.name() : null;
        }

        @Override
        public ProductFeeType convertToEntityAttribute(String feeType) {
            return StringUtils.hasText(feeType) ? ProductFeeType.valueOf(feeType) : null;
        }
    }

    @Converter(autoApply = true)
    public static class SourceTypeConverter implements AttributeConverter<SourceType, String> {
        @Override
        public String convertToDatabaseColumn(SourceType type) {
            return null != type ? type.name() : null;
        }

        @Override
        public SourceType convertToEntityAttribute(String type) {
            return StringUtils.hasText(type) ? SourceType.valueOf(type) : null;
        }
    }

    @Converter(autoApply = true)
    public static class EncryptTypeConverter implements AttributeConverter<EncryptType, String> {
        @Override
        public String convertToDatabaseColumn(EncryptType encryptType) {
            return null != encryptType ? encryptType.name() : null;
        }

        @Override
        public EncryptType convertToEntityAttribute(String encrypt) {
            return StringUtils.hasText(encrypt) ? EncryptType.valueOf(encrypt) : null;
        }
    }

    @Converter(autoApply = true)
    public static class ConfigPolicyConverter implements AttributeConverter<ConfigPolicy, String> {
        @Override
        public String convertToDatabaseColumn(ConfigPolicy configPolicy) {
            return null != configPolicy ? configPolicy.name() : null;
        }

        @Override
        public ConfigPolicy convertToEntityAttribute(String policy) {
            return StringUtils.hasText(policy) ? ConfigPolicy.valueOf(policy) : null;
        }
    }

    @Converter(autoApply = true)
    public static class SetConverter implements AttributeConverter<Set<String>, String> {
        @Override
        public String convertToDatabaseColumn(Set<String> collection) {
            StringBuilder sb = null;
            if (!CollectionUtils.isEmpty(collection)) {
                sb = new StringBuilder();

                for (String item : collection) {
                    if (null != item) {
                        sb.append(item).append(Constants.COMMA);
                    }
                }
            }

            String result = null;
            if (null != sb && sb.length() > 0) {
                result = sb.substring(0, sb.length() - 1);
            }
            return result;
        }

        @Override
        public Set<String> convertToEntityAttribute(String str) {
            Set<String> set = null;
            if (StringUtils.hasText(str)) {
                String[] array = str.split(Constants.COMMA);
                for (int i = 0; i < array.length; i++) {
                    try {
                        String portrait = array[i].trim();
                        if (null == set) {
                            set = new HashSet<String>();
                        }
                        if (StringUtils.hasText(portrait)) {
                            set.add(portrait);
                        }
                    } catch (Exception e) {
                        LOGGER.info("Convert String To Set ERROR, Deprecated", e);
                    }
                }
            }
            return set;
        }
    }
}
