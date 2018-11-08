package arthur.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;



public class UHttpClient {	
	private static final Logger log = Logger.getLogger(UHttpClient.class);
	private static BasicCookieStore cookieStore ;
	private static PoolingHttpClientConnectionManager phcm ;
	private static CloseableHttpClient httpclient ;
	private static DefaultConnectionKeepAliveStrategy myStrategy ;
	private static RequestConfig rc ;
	public static void init(){
		
		rc = RequestConfig.custom().setConnectionRequestTimeout(5000).setSocketTimeout(5000).setConnectTimeout(5000).build();
		myStrategy =  new DefaultConnectionKeepAliveStrategy(){
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				long keepAlive = super.getKeepAliveDuration(response, context);
				if(keepAlive !=-1){
					return keepAlive;
				}
				return 10* 1000;
			}
		};
		cookieStore = new BasicCookieStore();
		phcm = new PoolingHttpClientConnectionManager();
		phcm.setMaxTotal(200);
		phcm.setDefaultMaxPerRoute(20);
		
		HttpClientBuilder custom = HttpClients.custom();
		httpclient = custom
				.setConnectionManager(phcm). // 连接池
				setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)). // 重试 handler
				setKeepAliveStrategy(myStrategy). // 长连接 策略
				setDefaultCookieStore(cookieStore).build(); // cookiestore*/
	}
	public static  String  get(String url){
		String returnStr =null;
		CloseableHttpResponse response = null ;
        try {
            HttpGet httpGet = new HttpGet(url);
            response  = httpclient.execute(httpGet);
            StatusLine httpStatus = response.getStatusLine();
            int statusCode = httpStatus.getStatusCode();
            if( statusCode== 200){
            	HttpEntity entity = response.getEntity();
            	ByteArrayOutputStream bao = new ByteArrayOutputStream();
            	entity.writeTo(bao);
            	EntityUtils.consume(entity);
            	returnStr = new String(bao.toByteArray(),"UTF-8");
            	log.info("请求成功 ，url:"+url);
            }else{
            	log.info("请求失败 code:"+statusCode+",url:"+url);
            	returnStr = "-1";
            }
        }catch(Exception e){
        	log.error(e);
        	e.printStackTrace();
        	returnStr = "-1";
        }finally{
        	if(response!=null){
        		try {
					response.close();
				} catch (IOException e) {
				}
        	}
        }
        return returnStr;
	}
	
	public static ByteArrayOutputStream getFile(String url) {
		ByteArrayOutputStream r =null;
		CloseableHttpResponse response = null ;
        try {
            HttpGet httpGet = new HttpGet(url);
            response  = httpclient.execute(httpGet);
            StatusLine httpStatus = response.getStatusLine();
            int statusCode = httpStatus.getStatusCode();
            if( statusCode== 200){
            	HttpEntity entity = response.getEntity();
            	ByteArrayOutputStream bao = new ByteArrayOutputStream();
            	entity.writeTo(bao);
            	EntityUtils.consume(entity);
            	r = bao;
            	log.info("请求成功 ，url:"+url);
            }else{
            	log.info("请求失败 code:"+statusCode+",url:"+url);
            	r =null;
            }
        }catch(Exception e){
        	log.error(e);
        	e.printStackTrace();
        	r = null;
        }finally{
        	if(response!=null){
        		try {
					response.close();
				} catch (IOException e) {
				}
        	}
        }
		return r;
	}
	public static void main(String[] args) {
	}

	public static ByteArrayOutputStream postByJsonStr(String url,String param) throws Exception{
		
			   	
	            RequestBuilder post = RequestBuilder.post();
	            HttpUriRequest login = null;
	            
	            post.setUri(new URI(url));
	            StringEntity se = new StringEntity(param);
	            post.setEntity(se);
	            login = post.build();
	            CloseableHttpResponse response2 = httpclient.execute(login);
	            try {
	                HttpEntity entity = response2.getEntity();
	                System.out.println("post: " + response2.getStatusLine());
	                ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                
	                entity.writeTo(baos);
	                EntityUtils.consume(entity);
	                
	                return baos;
	            } finally {
	                response2.close();
	            }
		}
}
