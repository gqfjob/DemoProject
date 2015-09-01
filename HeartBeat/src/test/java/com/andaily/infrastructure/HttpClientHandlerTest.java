package com.andaily.infrastructure;

import com.andaily.domain.log.FrequencyMonitorLog;
import com.andaily.domain.shared.GuidGenerator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Shengzhao Li
 */
public class HttpClientHandlerTest {


    @Test(enabled = false)
    public void testHandleAndGenerateFrequencyMonitorLog() throws Exception {
        String url = "http://wdcy.cc";

        HttpClientHandler httpClientHandler = new HttpClientHandler(url);
        FrequencyMonitorLog monitorLog = httpClientHandler.handleAndGenerateFrequencyMonitorLog();

        assertNotNull(monitorLog);
        assertTrue(monitorLog.normal());
        System.out.println(monitorLog.costTime());

        //test post
        String postUrl = "http://wdcy.cc/login.htm";
        HttpClientPostHandler postHandler = new HttpClientPostHandler(postUrl);

        monitorLog = postHandler.handleAndGenerateFrequencyMonitorLog();

        assertNotNull(monitorLog);
        assertTrue(monitorLog.normal());
        System.out.println(monitorLog.costTime());


        //test https
        String httpsUrl = "https://andaily.com/d/";
        HttpClientHandler httpClientHandler2 = new HttpClientHandler(httpsUrl);
        final FrequencyMonitorLog monitorLog2 = httpClientHandler2.handleAndGenerateFrequencyMonitorLog();

        assertNotNull(monitorLog2);
        assertTrue(monitorLog2.normal());
        System.out.println(monitorLog2.costTime());

        //test content type, params
        String ctUrl = "http://localhost:7777/hb/test.hb";
        HttpClientHandler clientHandler = new HttpClientHandler(ctUrl);
        clientHandler.contentType("application/json");
        clientHandler.addRequestParam("guid", GuidGenerator.generate());

        final FrequencyMonitorLog log = clientHandler.handleAndGenerateFrequencyMonitorLog();

        assertNotNull(log);
        assertTrue(log.normal());
        System.out.println(log.costTime());

    }
}