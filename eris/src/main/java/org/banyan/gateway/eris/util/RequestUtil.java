package org.banyan.gateway.eris.util;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.StopWatch;
import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.util.DateUtil;
import org.banyan.gateway.helios.util.StringUtil;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.security.PublicKey;
import java.util.*;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 请求调用工具类
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/27 11:41
 */
public class RequestUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);

    private static final String NAMESPACE_URL = "http://xx.xx.xx.com/";
    private static final String NAMESPACE_PREFIX = "tns";
    private static final String OMELEMENT_INVOKESERVICE = "invokeService";
    private static final String OMELEMENT_GETPUBLICKEY = "getPublicKey";
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public String invokeService(String address, Map<String, String> params, Interface interfaze) {
        StopWatch sw = new StopWatch();
        sw.start();

        String key = getKey();
        String message = parseRequest(params);
        Options options = getOptions(address);
        SecretKey sk;
        String encryptedResult;

        String thirdparty = interfaze.getChannel().symbol();
        String iface = interfaze.getIface();
        try {
            ServiceClient client = new ServiceClient();
            client.setOptions(options);
            String publicKey = getPublicKey(client);

            PublicKey pk = CryptoUtils.getRSAPublicKey(Base64.decodeBase64(publicKey));
            sk = CryptoUtils.createDESKey(key.getBytes(DEFAULT_CHARSET));
            String encryptedKey = Base64.encodeBase64String(CryptoUtils.rsaEncrypt(pk, key.getBytes(DEFAULT_CHARSET)));
            String encryptedMessage = Base64.encodeBase64String(CryptoUtils.desEncrypt(sk, message.getBytes(DEFAULT_CHARSET)));

            List<String> args = new ArrayList<>();
            args.add(encryptedKey);
            args.add(encryptedMessage);

            OMElement invokeService = getOMElement(OMELEMENT_INVOKESERVICE, args);
            options.setAction(OMELEMENT_INVOKESERVICE);
            OMElement result = client.sendReceive(invokeService);
            encryptedResult = result.getFirstElement().getText();

            client.cleanupTransport();
        } catch (AxisFault axisFault) {
            return null;
        } finally {
            sw.stop();
            long time = sw.getTime();
            LOGGER.info("第三方:{}, 接口: {}, 请求耗时: {}ms", thirdparty, iface, time);
        }
        return new String(CryptoUtils.desDecrypt(sk, Base64.decodeBase64(encryptedResult)), DEFAULT_CHARSET);
    }


    private String getKey() {
        StringBuilder sb = new StringBuilder().append(DateUtil.getDateTime()).append("_").append(UUID.randomUUID().toString());
        return sb.toString();
    }

    private String parseRequest(Map<String, String> params) {
        // date 数据
        Element data = DocumentHelper.createElement("data");

        Element root = DocumentHelper.createElement("request");
        Element meta = root.addElement("meta");

        Set<String> keys = params.keySet();

        for (String key : keys) {
            if ("username".equalsIgnoreCase(key) || "password".equalsIgnoreCase(key) || "service".equalsIgnoreCase(key)) {
                meta.addElement(key).addText(params.get(key));
            } else {
                String value = params.get(key);
                if (StringUtil.isNotBlank(value)) {
                    data.addElement(key).addText(value);
                }
            }
        }
        root.addEntity("", data.asXML());

        return root.asXML();
    }

    private Options getOptions(String address) {
        Options options = new Options();
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        options.setTimeOutInMilliSeconds(2 * 60 * 1000);
        options.setAction("getPublicKey");
        EndpointReference endpoint = new EndpointReference(address);
        options.setTo(endpoint);
        return options;
    }

    private String getPublicKey(ServiceClient client) throws AxisFault {
        OMElement getPublicKey = getOMElement(OMELEMENT_GETPUBLICKEY, null);

        client.getOptions().setAction(OMELEMENT_GETPUBLICKEY);
        OMElement result = client.sendReceive(getPublicKey);

        client.cleanupTransport();
        return result.getFirstElement().getText();
    }

    private OMElement getOMElement(String localName, List<String> args) {
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = factory.createOMNamespace(NAMESPACE_URL, NAMESPACE_PREFIX);
        OMElement element = factory.createOMElement(localName, namespace);
        if (OMELEMENT_INVOKESERVICE.equals(localName)) {
            OMElement arg0 = factory.createOMElement("arg0", null);
            arg0.addChild(factory.createOMText(arg0, args.get(0)));
            element.addChild(arg0);
            OMElement arg1 = factory.createOMElement("arg1", null);
            arg1.addChild(factory.createOMText(arg1, args.get(1)));
            element.addChild(arg1);
        }
        return element;
    }
}
