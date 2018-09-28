package org.banyan.gateway.eris.util;

import com.alibaba.fastjson.JSONObject;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 结果解析工具类
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/27 13:14
 */
public class StaxonUtil {
    private static final Logger LOGGER = Logger.getLogger(StaxonUtil.class);

    /**
     * json string convert to xml string
     */
    public static String json2xml(String json, JsonXMLConfig config) {
        StringReader input = new StringReader(json);
        StringWriter output = new StringWriter();
        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            writer = new PrettyXMLEventWriter(writer);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return output.toString();
    }

    /**
     * xml string convert to json string
     */
    public static String xml2json(String xml, JsonXMLConfig config) {
        StringReader input = new StringReader(xml);
        StringWriter output = new StringWriter();
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return output.toString();
    }

    public static JSONObject xml2jsonObject(String xml, JsonXMLConfig config) {
        String jsonStr = xml2json(xml, config);
        return JSONObject.parseObject(jsonStr);
    }
}
