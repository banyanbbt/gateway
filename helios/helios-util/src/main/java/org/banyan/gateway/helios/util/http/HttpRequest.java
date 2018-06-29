package org.banyan.gateway.helios.util.http;

import org.apache.commons.lang.time.StopWatch;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.common.Interface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc http请求
 * @date 2017-12-25 09:31:48
 */
public class HttpRequest {

    private static Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    private static PoolingHttpClientConnectionManager cm = null;

    private static final int TIMEOUT = 120 * 1000;
    private static RequestConfig config = null;

    static {
        config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).build();
        LayeredConnectionSocketFactory sslsf = null;
        try {
            X509TrustManager x509mgr = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            // 信任所有
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            sslContext.init(null, new TrustManager[] { x509mgr }, null);
            sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            LOGGER.info("创建SSL连接失败", e);
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
    }

    private static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }

    /**
     * 无第三方数据源的get请求
     * @param url
     * @param headers
     * @return
     */
    private static String get(String url, Header ... headers) {
        StopWatch sw = new StopWatch();
        sw.start();
        String response = null;
        // 创建默认的httpClient实例
        CloseableHttpClient httpClient = HttpRequest.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        // 发送get请求
        try {
            // 用get方法发送http请求
            HttpGet get = new HttpGet(url);
            get.setConfig(config);
            for (Header header : headers) {
                get.addHeader(header);
            }
            LOGGER.info("执行get请求, url: " + get.getURI());

            httpResponse = httpClient.execute(get);
            response = getResponse(httpResponse);
        } catch (IOException e) {
            LOGGER.info("{}请求失败, {}", e);
        } finally {
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                    sw.stop();
                    LOGGER.info("请求耗时: {}", sw.getTime());
                } catch (IOException e) {
                    LOGGER.info("关闭response失败, {}", e);
                }
            }
        }
        return response;
    }

    /**
     * 带请求头请求
     * @param url 请求地址
     * @param interfaze 接口
     * @param headers 请求头
     * @return
     */
    private static String get(String url, Interface interfaze, Header ... headers) {
        StopWatch sw = new StopWatch();
        sw.start();
        String thirdparty = interfaze.getChannel().symbol();
        String iface = interfaze.getIface();
        String response = null;
        // 创建默认的httpClient实例
        CloseableHttpClient httpClient = HttpRequest.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        // 发送get请求
        try {
            // 用get方法发送http请求
            HttpGet get = new HttpGet(url);
            for (Header header : headers) {
                get.addHeader(header);
            }
            get.setConfig(config);
            LOGGER.info("执行get请求, uri: " + get.getURI());

            httpResponse = httpClient.execute(get);
            response = getResponse(httpResponse);
            if (null == response) {
                // http响应码不是200
            }
        } catch (IOException e) {
            LOGGER.info("{}请求失败, {}", thirdparty, e);
        } finally {
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                    sw.stop();
                    long time = sw.getTime();
                    LOGGER.info("第三方:{}, 接口: {}, 请求耗时: {}ms", thirdparty, iface, time);
                } catch (IOException e) {
                    LOGGER.info("关闭response失败, {}", e);
                }
            }
        }
        return response;
    }

    /**
     * 无header的get请求
     * @param url
     * @param param
     * @param interfaze
     * @return
     */
    public static String get(String url, String param, Interface interfaze) {
        try {
            return get(url + URLEncoder.encode(param, "UTF-8"), interfaze);
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("url 编码失败, {}", e);
            return null;
        }
    }

    /**
     * get请求
     * @param url
     * @param param
     * @param interfaze
     * @param headers
     * @return
     */
    public static String get(String url, String param, Interface interfaze, Header ... headers) {
        try {
            return get(url + URLEncoder.encode(param, "UTF-8"), interfaze, headers);
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("url 编码失败, {}", e);
            return null;
        }
    }

    /**
     * 无三方源get请求不带header
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Map<String, String> params) {
        try {
            return get(url + Constants.QUESTION_MARK + EntityUtils.toString(new UrlEncodedFormEntity(packageParams(params), Constants.CHARSET_UTF8)));
        } catch (IOException e) {
            LOGGER.info("url 编码失败, {}", e);
            return null;
        }
    }


    /**
     * 无三方源get请求
     * @param url 地址
     * @param params 参数
     * @return 返回结果
     */
    public static String get(String url, Map<String, String> params, Header ... headers) {
        try {
            return get(url + Constants.QUESTION_MARK + EntityUtils.toString(new UrlEncodedFormEntity(packageParams(params), Constants.CHARSET_UTF8)), headers);
        } catch (IOException e) {
            LOGGER.info("url 编码失败, {}", e);
            return null;
        }
    }

    /**
     * get请求不带请求头
     * @param url
     * @param params
     * @param iface
     * @return
     */
    public static String get(String url, Map<String, String> params, Interface iface) {
        try {
            return get(url + Constants.QUESTION_MARK + EntityUtils.toString(new UrlEncodedFormEntity(packageParams(params), Constants.CHARSET_UTF8)), iface);
        } catch (IOException e) {
            LOGGER.info("url 编码失败, {}", e);
            return null;
        }
    }

    /**
     * get请求带请求头
     * @param url
     * @param params
     * @param iface
     * @param headers
     * @return
     */
    public static String get(String url, Map<String, String> params, Interface iface, Header ... headers) {
        try {
            return get(url + Constants.QUESTION_MARK + EntityUtils.toString(new UrlEncodedFormEntity(packageParams(params), Constants.CHARSET_UTF8)), iface, headers);
        } catch (IOException e) {
            LOGGER.info("url 编码失败, {}", e);
            return null;
        }
    }

    /**
     * 封装请求参数
     * @param params
     * @return
     */
    private static List<NameValuePair> packageParams(Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<>();
        params.forEach((key, value) -> pairs.add(new BasicNameValuePair(key, value)));
        return pairs;
    }

    /**
     * Post body json请求不带header
     * @param url
     * @param data
     * @param interfaze
     * @return
     */
    public static String post(String url, String data, Interface interfaze) {
        Charset charset = Constants.CHARSET_UTF8;
        StringEntity entity = new StringEntity(data, charset);
        entity.setContentType("application/json");
        return post(url, entity, interfaze);
    }

    /**
     * Post body json请求
     * @param url
     * @param data
     * @param interfaze
     * @param headers
     * @return
     */
    public static String post(String url, String data, Interface interfaze, Header... headers) {
        Charset charset = Constants.CHARSET_UTF8;
        StringEntity entity = new StringEntity(data, charset);
        entity.setContentType("application/json");
        return post(url, entity, interfaze, headers);
    }

    /**
     * 表单Post请求不带header
     * @param url
     * @param map
     * @param interfaze
     * @return
     */
    public static String post(String url, Map<String, String> map, Interface interfaze) {
        List<NameValuePair> params = new ArrayList<>();
        map.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
        return post(url, new UrlEncodedFormEntity(params, Constants.CHARSET_UTF8), interfaze);
    }


    /**
     * 表单Post请求
     * @param url
     * @param map
     * @param interfaze
     * @return
     */
    public static String post(String url, Map<String, String> map, Interface interfaze, Header... headers) {
        List<NameValuePair> params = new ArrayList<>();
        map.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
        return post(url, new UrlEncodedFormEntity(params, Constants.CHARSET_UTF8), interfaze, headers);
    }

    /**
     * post请求
     * @param url
     * @param entity
     * @param interfaze
     * @return
     */
    private static String post(String url, HttpEntity entity, Interface interfaze, Header... headers) {
        StopWatch sw = new StopWatch();
        sw.start();
        String thirdparty = interfaze.getChannel().symbol();
        String iface = interfaze.getIface();
        String response = null;
        // 创建默认的httpClient实例
        CloseableHttpClient httpClient = HttpRequest.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(config);
            post.setEntity(entity);
            for (Header header : headers) {
                post.addHeader(header);
            }

            httpResponse = httpClient.execute(post);
            response = getResponse(httpResponse);
            if (null == response) {
                // http响应码不是200
            }
        } catch (IOException e) {
            LOGGER.info("{}请求失败, {}", thirdparty, e);
        } finally {
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                    sw.stop();
                    long time = sw.getTime();
                    LOGGER.info("第三方:{}, 接口: {}, 请求耗时: {}ms", thirdparty, iface, time);
                } catch (IOException e) {
                    LOGGER.info("关闭response失败, {}", e);
                }
            }
        }
        return response;
    }

    /**
     * 从httpResponse中获取请求结果
     * @param httpResponse
     * @return
     * @throws IOException
     */
    private static String getResponse(CloseableHttpResponse httpResponse) throws IOException {
        // response实体
        HttpEntity entity = httpResponse.getEntity();
        if (null != entity) {
            String response = EntityUtils.toString(entity);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            LOGGER.info("当前请求返回码: {}", statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                // 成功
                return response;
            } else {
                LOGGER.info("请求失败，response：{}", response);
                return null;
            }
        }
        return null;
    }
}

