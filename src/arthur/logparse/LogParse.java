package arthur.logparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class LogParse {
	
	public static void main(String[] args) {
		File f= new File("d://logs/appserver.log");
		try {
			BufferedReader br=  new BufferedReader(new  FileReader(f));
			String line = null;
			HashMap<String, Integer> map = new HashMap();
			HashMap<String, Integer> c = new HashMap();
			while((line = br.readLine())!=null) {
				int indexOf = line.indexOf("调用功能号");
				if(indexOf!=-1) {
					String funcStr = line.substring(indexOf+5,indexOf+5+4 );
					int indexOf2 = line.indexOf("花费时间");
					if(indexOf2 == -1) {
						continue;
					}
					String timeS = line.substring(indexOf2+5);
					Integer time = Integer.parseInt(timeS);
					if(map.containsKey(funcStr)) {
						Integer integer2 = c.get(funcStr);
						c.put(funcStr, ++integer2);
						
						Integer integer = map.get(funcStr);
						map.put(funcStr,integer+time);
					}else {
						map.put(funcStr, time);
						c.put(funcStr,1);
					}
				}
			}
			Iterator<Entry<String, Integer>> iterator = map.entrySet().iterator();
			ArrayList<Fu> list = new ArrayList();
			
			while(iterator.hasNext()) {
				Entry<String, Integer> next = iterator.next();
				Integer value = next.getValue();
				String key = next.getKey();
				Integer integer = c.get(key);
				Double pj = (value *1.0  / integer );
				String p= (pj+"");
//				System.out.println(key+":"+value+","+integer+","+pj);
//				System.out.println(key+":"+(value/integer));
				list.add(new Fu(key,(value/integer)));
			}
			Collections.sort(list,  new Comparator<Fu>() {

				@Override
				public int compare(Fu o1, Fu o2) {
					if(o1.pjz>o2.pjz)
						return -1;
					else
						return 1;
				}
				
			});;
			
			for(int i = 0 ; i<list.size(); i++) {
				Fu fu = list.get(i);
				System.out.println(fu.funcNo+":"+fu.pjz+";");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
class Fu{
	public String funcNo;
	public Integer pjz;
	public Fu(String funcNo,int pjz) {
		this.funcNo = funcNo;
		this.pjz = pjz;
	}
}