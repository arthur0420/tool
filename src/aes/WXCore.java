package aes;
 
import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONObject;
 
 
 
/**
 * 封装对外访问方法
 * @author liuyazhuang
 *
 */
public class WXCore {
	
	private static final String WATERMARK = "watermark";
	private static final String APPID = "appid";
	/**
	 * 解密数据
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String appId, String encryptedData, String sessionKey, String iv){
		String result = "";
		try {
			AES aes = new AES();  
		    byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));  
		    if(null != resultByte && resultByte.length > 0){  
		        result = new String(WxPKCS7Encoder.decode(resultByte));  
		    	JSONObject jsonObject = JSONObject.fromObject(result);
		    	String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
		    	if(!appId.equals(decryptAppid)){
		    		result = "";
		    	}else {
		    		System.out.println("yes");
		    	}
	        }  
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
	    return result;
	}
	
	public static void main(String[] args) throws Exception{
//	   String appId = "wx4f4bc4dec97d474b";
//	   String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
//	   String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
//	   String iv = "r7BXXKkLb8qrSNn05n0qiA==";
	   String appId = "wxde4a210e1426c902"; 
	   String encryptedData = "2c8ZZVn/ORKVfm0aesYGudEMPHm3ir0QdVhTZ1I3nX9yB9TZbk/DITl8Wb/mExzooSwxt5HzxgFIbIqXkzAjKKgUgfE5H1Go3FwXOxlwo8qFkil3+IX7AvFLOs33MB83pp7i1e0ti0Ws7ITghrppGIhkFnJVFOP4DQFRzhHfMKlH5UOkdgdvprEolszHodUuMnsdK+O2SsOpjAcWvrJJV00f22gDNLvhnjAbf4AOYual1Hr7e69+TuBgPP3ZxO3UbVy4jtL+/rGRXS6ipcP5QByCl4RcukzQo27v1srildCs2ySxhfdUrGCEpujikIHg4gF3AyhcABYQebIQOaPc2pynebrey8wnG6CGSjZbOrSxfrVk36pckuNii+PYumRqVhBVULcA2hzkinI8I5PE78mHtqliXzxhEptArLfZeuJ21ait3dFLWySIgeSVkSt31luYSysdqy75B9w+0iMV0A0wpqeo5N/cEdxarYGkr6I=";
	   String sessionKey = "RJRHvuEGjv9fvy+d1svvfA==";
	   String iv = "A2xXCCCpBEM+7SEtnuI6Sg==";
       System.out.println(decrypt(appId, encryptedData, sessionKey, iv));
    }
}
