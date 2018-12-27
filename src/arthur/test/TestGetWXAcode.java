package arthur.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class TestGetWXAcode {
	
	
	public static void main(String[] args) throws Exception {
		UHttpClient.init();
		String type = "client_credential";
		String appid = "wxe69e36171867e9c1";
		String secret = "2fa67efc674feb9c77217f78d914af02";
		String token = "15_N6vHzdV9x2ZYKNcMul9gp5leKUyvhRPBQkLZAAsac99zL0kA9hr1zwil8YktSfVapwQfE7u4lxWVx_v6XLKyf8grSeEPi834UyQsSI4Kib-rofCCs-VJc6IrG-8ZO7yfFNTMaaOwi13vtyP8SLCjADAOHY";	
		String sendTemplateUrl  = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+token;
		JSONObject param = new JSONObject();
		//
		param.put("touser", "oEOlG46vIXwOzCtiIs2XAYz_3W9k");
		param.put("template_id", "K-N4xzKw7r54Ca9uc3ws5vTy6asjma_1gdUrxnlxP4Y");
		param.put("form_id", "1541658795427");
		
		
		ByteArrayOutputStream postByJsonStr = UHttpClient.postByJsonStr(sendTemplateUrl, param.toString());
		String string = new String(postByJsonStr.toByteArray());
		
		System.out.println(string);
		
//		getToken();
		
	}
	
	public static void getToken() {
		
		String type = "client_credential";
		String appid = "wxe69e36171867e9c1";
		String secret = "2fa67efc674feb9c77217f78d914af02";
		
		
		String tokenurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+type+"&appid="+appid+"&secret="+secret;
		
		String string = UHttpClient.get(tokenurl);
		JSONObject s = JSONObject.fromObject(string);
		
		System.out.println(s);
		
	}
	
}
