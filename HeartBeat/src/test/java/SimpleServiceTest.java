import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class SimpleServiceTest {  
  
    public static void main(String[] args) throws ClientProtocolException, IOException {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpPost httpPost = new HttpPost("http://123.57.230.238:8080/appapi/contents/listened");  
       
        String encoderJson = "{\"subjectId\":\"apple1000000\",\"subjectType\":2,\"contentId\":1020}";
		StringEntity se = new StringEntity(encoderJson );
		 se.setContentEncoding("UTF-8");
	      se.setContentType("application/json");//发送json数据需要设置contentType
        httpPost.setEntity(se);
        
        CloseableHttpResponse response = httpclient.execute(httpPost);  
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(response.getEntity());// 返回json格式：

            System.out.println(result);
          }
          
    }  
    
}