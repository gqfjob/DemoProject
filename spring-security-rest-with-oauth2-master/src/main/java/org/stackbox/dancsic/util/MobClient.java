package org.stackbox.dancsic.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stackbox on 15/6/27.
 */
public class MobClient {

    //Mob的key
    private static String MOB_KEY = "73ed14d6d039";

    private static String MOB_VALIDATE_URL = "https://api.sms.mob.com/sms/verify";


    public static Integer validate(String phone, String zone, String code) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(MOB_VALIDATE_URL);

        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("appkey", MOB_KEY));
        postParameters.add(new BasicNameValuePair("phone", phone));
        postParameters.add(new BasicNameValuePair("zone", zone));
        postParameters.add(new BasicNameValuePair("code", code));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(postParameters));

            HttpResponse response = httpClient.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode retNode = mapper.readValue(responseBody, JsonNode.class);

                return retNode.get("status").asInt();
            } else {
                return 701;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return 700;
        }
    }
}

