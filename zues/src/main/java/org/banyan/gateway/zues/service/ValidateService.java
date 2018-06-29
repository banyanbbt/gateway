package org.banyan.gateway.zues.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.banyan.common.codec.service.EncryptService;
import org.banyan.gateway.helios.common.ConfigKey;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.data.jpa.domain.account.Account;
import org.banyan.gateway.helios.data.jpa.domain.account.AccountProduct;
import org.banyan.gateway.helios.data.jpa.domain.product.Product;
import org.banyan.gateway.helios.data.jpa.dto.AccountKeyDto;
import org.banyan.gateway.helios.data.jpa.dto.InterfaceDto;
import org.banyan.gateway.helios.data.jpa.dto.ProductConfigDto;
import org.banyan.gateway.helios.data.jpa.dto.ProductFieldDto;
import org.banyan.gateway.helios.data.jpa.model.AccountStatus;
import org.banyan.gateway.helios.data.jpa.model.ConfigPolicy;
import org.banyan.gateway.helios.data.jpa.model.EncryptType;
import org.banyan.gateway.helios.data.jpa.service.account.*;
import org.banyan.gateway.helios.data.jpa.service.channel.InterfaceService;
import org.banyan.gateway.helios.data.jpa.service.product.ProductFieldService;
import org.banyan.gateway.helios.data.jpa.service.product.ProductService;
import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.banyan.gateway.helios.util.codec.MD5Util;
import org.banyan.gateway.helios.util.codec.RSAUtil;
import org.banyan.gateway.helios.util.http.IPUtil;
import org.banyan.gateway.zues.dto.Request;
import org.banyan.gateway.zues.message.Record;
import org.banyan.gateway.zues.web.validator.Validator;
import org.banyan.gateway.zues.web.validator.ValidatorFactory;
import org.banyan.gateway.zues.web.validator.ValidatorFactory.ValidateType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.banyan.gateway.helios.common.ConfigKey.PORTRAIT_KEY;
import static org.banyan.gateway.helios.common.ConfigKey.ROUTING_KEY;
import static org.banyan.gateway.helios.common.SubmitCode.ArgsFailedDetail.*;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 对请求做验证
 * @date 2018-01-17 11:54:52
 */
@Service
public class ValidateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateService.class);

    // jackson的解析，方便做参数的验证
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RSAUtil rsaUtil;
    @Autowired
    private RsaKeysService rsaKeysService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountIpService accountIpService;
    @Autowired
    private AccountKeyService accountKeyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountProductService accountProductService;
    @Autowired
    private ProductFieldService productFieldService;
    @Autowired
    private AccountConfigService accountConfigService;
    @Autowired
    private InterfaceService interfaceService;

    /**
     * 验证参数
     * 01. 从request获取相关参数并校验
     * 02. 判断账户是否存在
     * 03. 判断账户余额
     * 04. ip白名单验证
     * 05. 获取密钥对并验证
     * 06. 验签
     * 07. 对客户传递过来的数据进行解密
     * 08. 判断是否有标准字段
     * 09. 验证产品是否存在
     * 10. 验证账户是否有权限访问该产品
     * 11. 验证账户访问产品的次数是否超过上限
     * 12. 验证产品的参数是否传递以及验证产品传递的参数格式是否正确
     * 13. 验证产品配置是否合法
     * 14. 验证用户质变配置
     * 15. 验证接口配置
     * 16. 添加产品的服务到配置中
     * @param record
     */
    public boolean validateRequest(Record record) {
        Request request = record.getRequest();
        String ip = record.getIp();

        // 参数获取
        String account = request.getAccount();
        String data = request.getData();
        String sign = request.getSign();

        // 01 有字段为空或者不存在
        if (StringUtils.isBlank(account) || StringUtils.isBlank(data) || StringUtils.isBlank(sign)) {
            LOGGER.info("获取数据出错, account、data和sign有一个或者多个字段未传");
            record.setSubmiCode(SubmitCode.ARGS_FAILED, RECORD_FAILED);
            return false;
        }

        // 02 获取账户并校验
        Account user = this.accountService.get(account);
        if (!this.validateAccount(user)) {
            LOGGER.info("账户不存在或者禁用");
            record.setSubmiCode(SubmitCode.ACCOUNT_FAILED);
            return false;
        }
        record.setAccount(account);

        // 03. 判断账户余额
        if (null == user.getBalance() || BigDecimal.ZERO.compareTo(user.getBalance()) >= 0) {
            LOGGER.info("账户：{}余额不足", user.getAccount());
            record.setSubmiCode(SubmitCode.FEE_FAILED);
            return false;
        }

        // 04 ip白名单验证
        if (!this.validateAccountIp(account, ip)) {
            LOGGER.info("请求ip没有访问权限");
            record.setSubmiCode(SubmitCode.IP_FAILED);
            return false;
        }

        // 05 获取key并验证
        AccountKeyDto accountKey = this.accountKeyService.findByAccount(account);

        if (null == accountKey) {
            LOGGER.info("请检查account_key中账号{}对应的密钥信息是否已配置，或是否在有效期内，或是否可用", account);
            record.setSubmiCode(SubmitCode.ENCRYPT_FAILED);
            return false;
        }
        // 获取banyan私钥，用来解密request数据
        PrivateKey privateKey = rsaKeysService.getBanyanPrivateKey(accountKey.getEncryptionDto());
        // 获取customer公钥，用来加密response数据
        PublicKey publicKey = rsaKeysService.getCustomerPublicKey(account, accountKey.getPublicKey());
        record.setPublicKey(publicKey).setPrivateKey(privateKey);
        if (null == privateKey || null == publicKey) {
            // TODO: NEED NOTIFY
            LOGGER.info("账户{}公私钥信息有空，请运营同志们检查配置", account);
            record.setSubmiCode(SubmitCode.ENCRYPT_FAILED);
            return false;
        }

        // 06 验签
        if (!this.validateSign(account, data, sign)) {
            LOGGER.info("验签失败");
            record.setSubmiCode(SubmitCode.SIGN_FAILED);
            return false;
        }

        // 07 对客户传过来的数据进行解密和解析
        try {
            String decryptData = this.rsaUtil.decrypt(privateKey, data);
            JsonNode jsonNode = objectMapper.readTree(decryptData);
            record.setDecryptData(decryptData);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.put(Constants.ACCOUNT, account);
                objectNode.put(Constants.GID, record.getGid());

                record.setObjectNode(objectNode);
            }
        } catch (Exception e) {
            LOGGER.info("解密或者解析数据出错", e);
        }

        String customerId = null;
        String product = null;
        // 08 判断是否有标准字段，为customerId和productId
        ObjectNode objectNode = record.getObjectNode();
        if (null == objectNode || objectNode.isNull()) {
            record.setSubmiCode(SubmitCode.DATA_FAILED);
            return false;
        } else if (!objectNode.hasNonNull(Constants.CUSTOMER_ID) || StringUtils.isBlank(customerId = objectNode.get(Constants.CUSTOMER_ID).asText())) {
            LOGGER.info("缺少标准字段: {}", Constants.CUSTOMER_ID);
            record.setSubmiCode(SubmitCode.ARGS_FAILED, CUSTOMER_ID_FAILED);
            return false;
        } else if (!objectNode.hasNonNull(Constants.PRODUCT_ID) || StringUtils.isBlank(product = objectNode.get(Constants.PRODUCT_ID).asText())) {
            LOGGER.info("缺少标准字段: {}", Constants.PRODUCT_ID);
            record.setSubmiCode(SubmitCode.ARGS_FAILED, PRODUCT_FAILED);
            return false;
        }

        // 09 验证产品是否存在
        record.setCustomerId(customerId);
        Product prod = this.productService.findByProductAndStatus(product, true);
        if (null == prod) {
            LOGGER.info("账户{}请求的产品{}不存在", account, product);
            record.setSubmiCode(SubmitCode.PRODUCT_FAILED);
            return false;
        }
        record.setProduct(product);

        // 10 验证账户是否有权限访问产品
        AccountProduct accountProduct = this.accountProductService.findByAccountAndProduct(account, product);
        if (null == accountProduct) {
            LOGGER.info("请检查账号{}对应的产品{}访问信息是否已配置，或是否在有效期内，或是否可用", account, product);
            record.setSubmiCode(SubmitCode.ACCESS_FAILED);
            return false;
        }

        // TODO: 使用次数减1, 可以放到计费项目中
        // 11 验证产品的访问是否超过限制
        if ((accountProduct.getUsedCount() >= accountProduct.getLimitCount()) && accountProduct.getLimitCount() != -1) {
            LOGGER.info("账号{}对应的产品{}访问次数达到上限, 限制的次数为:{}", account, product, accountProduct.getLimitCount());
            record.setSubmiCode(SubmitCode.CONCURRENCY_FAILED);
            return false;
        }

        // 12 验证产品的参数是否传递以及验证产品传递的参数格式是否正确
        List<ProductFieldDto> productFieldDtos = this.productFieldService.findByProduct(product);
        if (CollectionUtils.isEmpty(productFieldDtos)) {
            // 没有配置产品的参数
            LOGGER.info("产品{}没有配置参数，请检查配置", product);
        } else {
            try {
                String name = this.validateParams(objectNode, productFieldDtos);
                if (StringUtils.isNotBlank(name)) {
                    record.setSubmiCode(SubmitCode.ARGS_FAILED, MessageFormat.format(CUSTOM_FAILED.getMsg(), name));
                    return false;
                }
            } catch (Exception e) {
                LOGGER.info("解析数据出错", e);
                record.setSubmiCode(SubmitCode.DATA_FAILED);
                return false;
            }
        }

        // 13 验证产品配置
        Set<ProductConfigDto> configDtos = accountConfigService.findByAccountAndProduct(record.getAccount(), record.getProduct());
        if (!this.validateProductConfig(configDtos, record)) {
            // TODO: NEED NOTIFY
            LOGGER.info("产品{}有配置异常，请运营同志们检查配置", product);
            record.setSubmiCode(SubmitCode.PRODUCT_CONFIG_FAILED);
            return false;
        }

        // 14 验证用户指标配置
        if (!this.validateAccountPortraitConfig(record)) {
            LOGGER.info("产品{}有配置异常，请运营同志们检查配置", product);
            record.setSubmiCode(SubmitCode.PORTRAIT_FAILED);
            return false;
        }

        // 15 验证接口配置
        if (!this.validateInterfaceConfig(record)) {
            LOGGER.info("产品{}对应的接口配置异常，请运营同志们检查配置", product);
            record.setSubmiCode(SubmitCode.PRODUCT_CONFIG_FAILED);
            return false;
        }

        // 16 添加产品的服务到配置中
        record.getHadesConfig().put(ConfigKey.PRODUCT_INTERFACE_KEY.getConfig(), prod.getServiceMethod());
        return true;
    }

    /**
     * 验证产品输入参数是否正确
     * @param objectNode
     * @param fields
     * @return boolean
     */
    private String validateParams(ObjectNode objectNode, List<ProductFieldDto> fields) {
        for (ProductFieldDto field : fields) {
            String key = field.getField();
            String type = field.getType();

            String name = this.validAndEncrypt(objectNode, field);
            if (StringUtils.isNotBlank(name)) {
                return name;
            }

            // 处理子类
            JsonNode itemNode = objectNode.get(key);
            if (null != field.getChildren() && ValidateType.OBJECT == ValidateType.valueOf(type)) {
                ArrayNode arrayNode = itemNode.isArray() ? (ArrayNode) itemNode : objectNode.arrayNode().add(itemNode);

                Iterator<JsonNode> iterator = arrayNode.iterator();
                while (iterator.hasNext()) {
                    JsonNode item = iterator.next();
                    name = this.validateParams((ObjectNode) item, field.getChildren());
                    if (StringUtils.isNotBlank(name)) {
                        // 如果参数不对
                        return field.getName() + Constants.JIAN_TOU + name;
                    }
                }
            }
        }

        return null;
    }

    /**
     * 单个参数验证
     * @param field
     * @param parentNode
     * @return String 字段名
     */
    private String validAndEncrypt(ObjectNode parentNode, ProductFieldDto field) {
        String fieldName = null;
        boolean flag = false;

        ArrayNode encryptNode = null;
        ValidateType validateType = null;

        boolean isRequired = field.isRequired();
        boolean isArray = field.isArray();

        String key = field.getField();
        JsonNode jsonNode = parentNode.get(key);
        if (!isRequired && (null == jsonNode || jsonNode.isNull() || (jsonNode.isTextual() && StringUtils.isEmpty(jsonNode.asText())))) {
            // null
            flag = true;
        } else if (isRequired && null == jsonNode || jsonNode.isNull()) {
            // node is Null
        } else {
            // 字段填写了，判断格式是否正确
            String type = field.getType();
            Integer min = field.getMinLength();
            Integer max = field.getMaxLength();
            String regex = field.getRegex();

            try {
                validateType = ValidateType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                LOGGER.info("获取参数验证类型出错", e);
            }

            Validator validator = ValidatorFactory.getValidator(validateType);
            if (null == validator) {
                // 当前不支持的验证类型
                LOGGER.info("不支持验证类型为{}的验证，默认返回true", type);
            }

            LOGGER.debug("字段：{}，类型：{}，正则{}，内容：{}。", key, type, regex, jsonNode);
            if (!isArray ^ jsonNode.isArray()) {
                // 如果是字段定义为非数组，转为数组
                ArrayNode arrayNode = jsonNode.isArray() ? (ArrayNode) jsonNode : parentNode.arrayNode().add(jsonNode);
                encryptNode = parentNode.arrayNode();

                Iterator<JsonNode> iterator = arrayNode.iterator();
                while (iterator.hasNext()) {
                    JsonNode item = iterator.next();

                    if (validator.valid(key, item, min, max, regex)) {
                        flag = true;
                        if (null != field.getEncryptType() && ValidateType.STRING == validateType) {
                            encryptNode.add(EncryptService.encrypt(item.asText(), convert(field.getEncryptType())));
                        }
                    } else {
                        // 数组中有格式不对的
                        LOGGER.info("字段：{}，类型：{}，格式不对", key, type);
                        flag = false;
                        break;
                    }
                }
            } else {
                // 字段定义为数组，但是接受的数据不是数组
                LOGGER.info("字段：{}，类型：{}，字段定义为数组，但是接受的数据不是数组。", key, type);
            }
        }

        if (!flag) {
            fieldName = field.getName();
        } else if (null != field.getEncryptType() && ValidateType.STRING == validateType && null != encryptNode) {
            if (isArray) {
                parentNode.replace(key, encryptNode);
            } else {
                parentNode.replace(key, encryptNode.get(0));
            }
        }
        return fieldName;
    }

    /**
     * 验证账户存在性和可用性
     * @param account
     * @return
     */
    private boolean validateAccount(Account account) {
        if (null == account || account.getStatus() != AccountStatus.ACTIVATED) {
            return false;
        }
        return true;
    }

    /**
     * 验证账户报备的ip白名单和内置白名单
     * @param account
     * @param ip
     * @return
     */
    private boolean validateAccountIp(String account, String ip) {
        boolean flag = false;

        List<String> ips = this.accountIpService.findByAccount(account);
        if (IPUtil.isWhiteIP(ip)) {     // 内置的白名单
            flag = true;
            LOGGER.info("检测账号：{}使用的是【内置】白名单：{}", account, ip);
        } else if (IPUtil.match(ips, ip)) {    // 客户报备的ip
            flag = true;
            LOGGER.info("检测账号：{}使用的是【报备】白名单：{}", account, ip);
        } else {
            LOGGER.info("检测账号：{}使用的是为不信任的IP：{}", account, ip);
        }

        return flag;
    }

    /**
     * 验证签名
     * @param account
     * @param data
     * @param sign
     * @return
     */
    private boolean validateSign(String account, String data, String sign) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.ACCOUNT).append(account).append(Constants.DATA).append(data);
        return sign.equalsIgnoreCase(MD5Util.md5Upper(sb.toString()));
    }


    /**
     * 验证配置是否合法
     * 处理account_config配置是否在config中
     * @param configDtos
     * @param record
     * @return
     */
    private boolean validateProductConfig(Set<ProductConfigDto> configDtos, Record record) {
        if (!CollectionUtils.isEmpty(configDtos)) {
            HadesConfig configs = record.getHadesConfig();

            // 处理account_config配置是否在config中
            for (ProductConfigDto configDto : configDtos) {
                if (!verfyConfig(record.getAccount(), record.getProduct(), configDto, configs)) {
                    return false;
                }
            }

            // 检测 PORTRAIT_KEY 值的合法性
            String isPortrait = configs.get(ConfigKey.IS_PORTRAIT_KEY.getConfig());
            if (StringUtils.isNotBlank(isPortrait)) {
                if (!Constants.TRUE_STRING.equalsIgnoreCase(isPortrait)) {
                    // 非指标产品移除指标配置
                    configs.remove(PORTRAIT_KEY.getConfig());
                    return true;
                }

                record.setPortrait(true);
                String portraits = configs.get(PORTRAIT_KEY.getConfig());
                if (StringUtils.isNotBlank(portraits)) {
                    Set<String> set = Sets.newHashSet(portraits.split(Constants.COMMA));
                    record.setPortraits(set);
                    if (!productService.isContainsPortraits(set)) {
                        LOGGER.info("账号{}产品{}配置的指标不在指标库中", record.getAccount(), record.getProduct());
                        return false;
                    }
                } else {
                    LOGGER.info("账号{}产品{}未配置的指标", record.getAccount(), record.getProduct());
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 验证用户指标配置
     * @param record
     * @return
     */
    private boolean validateAccountPortraitConfig(Record record) {
        boolean flag = true;
        boolean hasPortraitField = this.productFieldService.existsByProductAndField(record.getProduct(), Constants.PORTRAITS);

        if (hasPortraitField && record.isPortrait()) {
            Set<String> portraits = record.getPortraits();

            // 验证用户索引是否正确
            ObjectNode objectNode = record.getObjectNode();
            JsonNode jsonNode = objectNode.get(Constants.PORTRAITS);
            // 用户指标参数有值的情况
            if (null == jsonNode || jsonNode.isNull() || !jsonNode.isArray()) {
                if (CollectionUtils.isEmpty(portraits)) {
                    objectNode.remove(Constants.PORTRAITS);
                } else {
                    ArrayNode arrayNode = objectNode.putArray(Constants.PORTRAITS);
                    portraits.forEach(arrayNode::add);
                }
            } else {
                ArrayNode arrayNode = (ArrayNode) jsonNode;

                Set<String> acPortraits = new HashSet<>();
                for (JsonNode node : arrayNode) {
                    String item = node.asText();
                    if (!CollectionUtils.isEmpty(portraits)) {
                        if (!portraits.contains(item)) {
                            flag = false;
                            break;
                        }
                    } else {
                        acPortraits.add(item);
                    }
                }

                // 验证指标是否在数据库中
                if (flag && CollectionUtils.isEmpty(portraits)) {
                    if (!productService.isContainsPortraits(acPortraits)) {
                        LOGGER.info("账号{}产品{}请求参数的指标不在指标库中", record.getAccount(), record.getProduct());
                        flag = false;
                    }
                }
            }
        }

        return flag;
    }

    /**
     * 验证接口配置
     * @param record
     * @return
     */
    private boolean validateInterfaceConfig(Record record) {
        HadesConfig configs = record.getHadesConfig();

        // 检测 ROUTING_KEY 值的合法性
        String ifaces = configs.get(ConfigKey.ROUTING_KEY.getConfig());
        if (StringUtils.isNotBlank(ifaces)) {
            String[] array = ifaces.split(Constants.COMMA_JING_HAO_REGEX);
            if (!interfaceService.isContainsInterfaces(Sets.newHashSet(array))) {
                LOGGER.info("账号{}产品{}配置的路由策略不在第三发数据接口中", record.getAccount(), record.getProduct());
                return false;
            }

            List<InterfaceDto> interfaceDtos = interfaceService.findByIfaces(Lists.newArrayList(array));
            if (!CollectionUtils.isEmpty(interfaceDtos)) {
                record.setRouteConfigs(interfaceDtos.stream().map(interfaceDto -> new RouteConfig()
                            .setIface(interfaceDto.getIface())
                            .setName(interfaceDto.getName())
                            .setChannel(interfaceDto.getChannel())
                            .setApiUrl(interfaceDto.getApiUrl())
                            .setPrice(interfaceDto.getPrice())
                            .setHighestPrice(interfaceDto.getHighestPrice())
                            .setLowestPrice(interfaceDto.getLowestPrice())
                            .setStatus(interfaceDto.isStatus())
                            .setTimeout(interfaceDto.getTimeout())
                            .setDescription(interfaceDto.getDescription())
                            .setInterfaceConfigs(interfaceDto.getInterfaceConfigs())
                ).collect(Collectors.toCollection(ArrayList::new)));
            }
        }
        return true;
    }

    /**
     * 合并config 和 account_config
     * @param configDto
     * @param hadesConfig
     * @return
     */
    private boolean verfyConfig(String account, String product, ProductConfigDto configDto, HadesConfig hadesConfig) {
        boolean flag = false;
        String key = configDto.getConfig();
        String accountValue = configDto.getAccountValue();
        String configValue = configDto.getConfigValue();

        if (StringUtils.isBlank(configValue)) {                     // 错误配置，丢弃
            flag = true;
            LOGGER.info("产品{}配置键{}的默认配置为空", product, key);
        } else if (ConfigPolicy.NONE == configDto.getPolicy()) { // 如果是不可更新，使用默认配置
            flag = true;
            hadesConfig.put(key, configValue);
            LOGGER.info("账号{}产品{}配置键{}，忽略用户配置，强制使用默认配置", account, product, key);
        } else if (ConfigPolicy.REPLACE == configDto.getPolicy()) {
            flag = true;
            hadesConfig.put(key, StringUtils.isNotBlank(accountValue) ? accountValue : configValue);
        } else if (ConfigPolicy.CONTAINS == configDto.getPolicy()) {
            boolean hasPortrait = PORTRAIT_KEY.getConfig().equals(key);
            if (StringUtils.isBlank(accountValue)) {
                // 指标为All，客户可访问所有接口
                if (!PORTRAIT_KEY.getConfig().equals(key) || !Constants.PORTRAIT_ALL.equalsIgnoreCase(configValue)) {
                    hadesConfig.put(key, configValue);
                }
                flag = true;
                LOGGER.info("账号{}没有配置产品{}配置键{}，使用默认配置", account, product, key);
            } else {
                // configValue 和 accountValue 都不为空
                boolean configFlag = Constants.PORTRAIT_ALL.equalsIgnoreCase(configValue);
                boolean acConfigFlag = Constants.PORTRAIT_ALL.equalsIgnoreCase(accountValue);

                if (hasPortrait && configFlag && acConfigFlag) {         // is portrait and config == ALL and ac_config == ALL
                    flag = true;
                    LOGGER.info("账号{}产品{}配置键{}，使用全量指标ALL", account, product, key);
                } else if (hasPortrait && configFlag && !acConfigFlag) { // is portrait and config == ALL and ac_config != ALL
                    flag = true;
                    hadesConfig.put(key, accountValue);
                    LOGGER.info("账号{}产品{}配置键{}，默认配置ALL，使用用户配置指标", account, product, key);
                } else if (hasPortrait && !configFlag && acConfigFlag) { // is portrait and config != ALL and ac_config == ALL
                    flag = true;
                    LOGGER.info("账号{}产品{}配置键{}，用户配置ALL，使用默认指标", account, product, key);
                    hadesConfig.put(key, configValue);
                } else {                                                // (is portrait and config != ALL and ac_config != ALL) AND isnot portrait
                    Set<String> set = null;
                    List<String> list = null;
                    if (ROUTING_KEY.getConfig().equals(key)) {
                        set = Sets.newHashSet(configValue.split(Constants.COMMA_JING_HAO_REGEX));
                        list = Lists.newArrayList(accountValue.split(Constants.COMMA_JING_HAO_REGEX));
                    } else {
                        set = Sets.newHashSet(configValue.split(Constants.COMMA));
                        list = Lists.newArrayList(accountValue.split(Constants.COMMA));
                    }
                    if (!this.containsAll(set, list)) {
                        LOGGER.info("账号{}产品{}配置键{}，用户配置指标不在默认中", account, product, key);
                        return false;
                    }
                    hadesConfig.put(key, accountValue);
                    LOGGER.info("账号{}产品{}配置键{}，使用用户配置", account, product, key);
                    flag = true;
                }
            }
        }

        return flag;
    }

    public org.banyan.common.codec.service.EncryptType convert(EncryptType encryptType) {
        org.banyan.common.codec.service.EncryptType encrypt = null;
        if (null == encryptType) {
            // NULL
        } else if (encryptType == EncryptType.CARD) {
            encrypt = org.banyan.common.codec.service.EncryptType.CARD;
        } else if (encryptType == EncryptType.CID) {
            encrypt = org.banyan.common.codec.service.EncryptType.ID_CARD;
        } else if (encryptType == EncryptType.MOBILE) {
            encrypt = org.banyan.common.codec.service.EncryptType.MOBILE;
        } else if (encryptType == EncryptType.NAME) {
            encrypt = org.banyan.common.codec.service.EncryptType.OTHER;
        }

        return encrypt;
    }

    private boolean containsAll(Collection<?> source, Collection<?> candidates) {
        if (CollectionUtils.isEmpty(source) || CollectionUtils.isEmpty(candidates)) {
            return false;
        }

        for (Object candidate : candidates) {
            if (!source.contains(candidate)) {
                return false;
            }
        }
        return true;
    }
}
