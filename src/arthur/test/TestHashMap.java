package arthur.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class TestHashMap {
	
	static HashMap<String, String>  map = new HashMap();
	public static void main(String[] args) {
		for(int i = 0 ; i< 100 ;i++) {
			map.put("id"+i, "i"+i);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
				
				while(iterator.hasNext()) {
					Entry<String, String> entry =  iterator.next();
					System.out.println(entry.getValue());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					map.put("id"+101,"1");
				}
			}
		}).start();;
	}
}
