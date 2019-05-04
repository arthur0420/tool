package arthur.test;

import java.util.UUID;

public class Length {
	public static void main(String[] args) {
		String replaceAll = UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println(replaceAll.length());
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println((""+currentTimeMillis).length());
		
	}
}
