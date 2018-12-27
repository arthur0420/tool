package arthur.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

public class TestByte {
		public static void main(String[] args) throws IOException {
			
			JSONObject a=  JSONObject.fromObject("{'class':'123','a':'b'}");
//			a.put("class", "123");
			System.out.println(a.get("a"));
			System.out.println(a.get("class"));
			
			
		}
		
}
