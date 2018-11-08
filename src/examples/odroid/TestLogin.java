package examples.odroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class TestLogin {
		public static void main(String[] args) {
			HashMap map = new HashMap();
			List list =  new ArrayList();
			
			map.put("a", list);
			
			list.add("1");
			list.add("2");
			list.add("3");
			System.out.println(map);
			Collections.reverse(list);
			
			System.out.println(map);
		}
}
