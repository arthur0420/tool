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
//		appid=wxe69e36171867e9c1
//				secret=2fa67efc674feb9c77217f78d914af02
		// {"access_token":"15_Nt9l_wizBuVh7t4GamDmpVNfUuXHFx9zEb3nJTscmELdvuitLDyAsDgtWWot3nxYsbJCyK1rupwhWLpgIfBpM7iQhJV9wqdiVB3beFUtmvYD-DjXed1BXJxFMEiUH1Bf5c09rD3zC4njfNM_PYDgAHAWFK","expires_in":7200}
//		String token =  "15_Nt9l_wizBuVh7t4GamDmpVNfUuXHFx9zEb3nJTscmELdvuitLDyAsDgtWWot3nxYsbJCyK1rupwhWLpgIfBpM7iQhJV9wqdiVB3beFUtmvYD-DjXed1BXJxFMEiUH1Bf5c09rD3zC4njfNM_PYDgAHAWFK";
//		String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token;
//		JSONObject param = new JSONObject();
////		param.put("token", token);
//		param.put("scene", "28");
//		param.put("page","pages/clientb/clientb");
//		param.put("width",280);
//		param.put("auto_color", true);
//		
//		ByteArrayOutputStream post = UHttpClient.post(url,param.toString());
//		File f = new File("d://qrcode.jpg");
//		FileOutputStream fos = new FileOutputStream(f);
//		fos.write(post.toByteArray());
//		fos.flush();
//		fos.close();
		getToken();
		
	}
	
	public static void getToken() {
		
		String type = "client_credential";
		String appid = "wxe69e36171867e9c1";
		String secret = "2fa67efc674feb9c77217f78d914af02";
		
		
		String tokenurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+type+"&appid="+appid+"&secret="+secret;
		
		String string = UHttpClient.get(tokenurl);
		JSONObject s = JSONObject.fromObject(string);
		System.out.println(s.getInt("expires_in"));
	}
	
}
