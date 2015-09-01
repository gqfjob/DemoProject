package com.andaily.infrastructure;

import com.andaily.domain.log.FrequencyMonitorLog;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Wrapper HttpClient operations
 * Default request method: GET
 *
 * @author Shengzhao Li
 */
public class HttpClientHandler {

    /*
    * Available content Types
    * */
    public static final List<ContentType> CONTENT_TYPES = Arrays.asList(
            ContentType.TEXT_PLAIN, ContentType.TEXT_HTML,
            ContentType.TEXT_XML, ContentType.APPLICATION_XML,
            ContentType.APPLICATION_SVG_XML, ContentType.APPLICATION_XHTML_XML,
            ContentType.APPLICATION_ATOM_XML,
            ContentType.APPLICATION_JSON);


    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpClientHandler.class);
    //Convert mill seconds to second unit
    protected static final int MS_TO_S_UNIT = 1000;
    //Normal http response code
    protected static final int NORMAL_RESPONSE_CODE = 200;
    //https prefix
    protected static final String HTTPS = "https";

    protected static HttpsTrustManager httpsTrustManager = new HttpsTrustManager();

    protected String url;

    protected int maxConnectionSeconds = 0;

    protected String contentType;

    protected Map<String, String> requestParams = new HashMap<>();

    public HttpClientHandler(String url) {
        this.url = url;
    }

    public HttpClientHandler maxConnectionSeconds(int maxConnectionSeconds) {
        this.maxConnectionSeconds = maxConnectionSeconds;
        return this;
    }

    public HttpClientHandler addRequestParam(String key, String value) {
        this.requestParams.put(key, value);
        return this;
    }

    public HttpClientHandler contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public FrequencyMonitorLog handleAndGenerateFrequencyMonitorLog() {
        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog();

        final long start = System.currentTimeMillis();
        try {
            final CloseableHttpResponse response = sendRequest();

            monitorLog.normal(isNormal(response))
                    .responseSize(responseSize(response))
                    .costTime(costTime(start));

            response.close();
        } catch (Exception e) {
            monitorLog.costTime(costTime(start)).normal(false)
                    .remark(e.getClass().getSimpleName() + ": " + e.getMessage());
            LOGGER.debug("Send request to url[" + url + "] failed", e);
        }

        return monitorLog;
    }

    protected long responseSize(CloseableHttpResponse response) {
        return response.getEntity().getContentLength();
    }

    protected long costTime(long start) {
        return System.currentTimeMillis() - start;
    }

    protected boolean isNormal(CloseableHttpResponse response) {
        return response.getStatusLine().getStatusCode() == NORMAL_RESPONSE_CODE;
    }

    protected CloseableHttpResponse sendRequest() throws Exception {
        HttpUriRequest request = retrieveHttpRequest();
        setContentType(request);

        CloseableHttpClient client = retrieveHttpClient();
        return client.execute(request);
    }

    private void setContentType(HttpUriRequest request) {
        if (StringUtils.isNotEmpty(this.contentType)) {
            request.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
            LOGGER.debug("Set HttpUriRequest[{}] contentType: {}", request, contentType);
        }
    }

    protected CloseableHttpClient retrieveHttpClient() {
        final RequestConfig requestConfig = requestConfig();
        if (isHttps()) {
            //Support SSL
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(createSSLContext());
            return HttpClients.custom().setDefaultRequestConfig(requestConfig).setSSLSocketFactory(sslConnectionSocketFactory).build();
        } else {
            return HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        }
    }

    private RequestConfig requestConfig() {
        final int maxConnMillSeconds = this.maxConnectionSeconds * MS_TO_S_UNIT;
        return RequestConfig.custom()
                .setSocketTimeout(maxConnMillSeconds)
                .setConnectTimeout(maxConnMillSeconds).build();
    }


    private SSLContext createSSLContext() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new HttpsTrustManager[]{httpsTrustManager}, null);
            return sslContext;
        } catch (Exception e) {
            throw new IllegalStateException("Create SSLContext error", e);
        }
    }

    protected boolean isHttps() {
        return url.toLowerCase().startsWith(HTTPS);
    }

    private HttpUriRequest retrieveHttpRequest() {
        final RequestBuilder builder = createRequestBuilder();
        addRequestParams(builder);
        return builder.setUri(url).build();
    }

    protected void addRequestParams(RequestBuilder builder) {
        final Set<String> keySet = requestParams.keySet();
        for (String key : keySet) {
            builder.addParameter(key, requestParams.get(key));
        }
    }

    protected RequestBuilder createRequestBuilder() {
        return RequestBuilder.get();
    }


    /**
     * Default X509TrustManager implement
     */
    private static class HttpsTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            //ignore
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            //ignore
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}