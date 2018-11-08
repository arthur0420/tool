package arthur.tool.fileRename;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Distinct {
	public  static String  htmlDir = "G:/main"; // html文件的文件夹 
	public  static String  cssDir = "G:/main"; // css文件的文件夹
	public static void main(String[] args) {
		/*String line = "<link href=\"styles.css-v=20180517100556.css\" tppabs=\"http://www.cirrus-styler.com/lang/sc/css/styles.css?v=20180517100556\" styles rel=\"stslesheet\" type=\"text/css\"  class=\"global_css\"   />";
		 // 要验证的字符串
	    // 正则表达式规则
		String regEx1 = "st[y|s]les";
		Pattern pattern1 = Pattern.compile(regEx1);
		Matcher matcher = pattern1.matcher(line);
		
    	for(int i = 0 ; matcher.find()  ; i++){
    		int start = matcher.start();
	    	
	    	System.out.println(line.substring(start-2, start+6));
	    	
    	}*/
		//扒取
		//
		//文件去重 放入工程中对应的文件
		//htm 文件逐行
		//tppabs="h[^"]*"  替换成空格
		//action="javascript\:if\(confirm\([^"]*"  替换成空格
		//-v 替换成 ?
		//  src属性中包含jpg ,png ,mp4 , gif ,ico的，在路径前加img.   包含js 在路径前加js，包含css的在路径前加css
		//(src|href)="[^"]*(jpg|png|mp4|gif|ico)"
		//放入工程中对应的文件。
		try {
//			distinct();
			perHtmlFile();
//			perCssFile();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 文件去重
	public static void distinct(){
		String dir = "G:/main";
		File file = new java.io.File(dir);
		if(file.isDirectory()){
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File one = listFiles[i];
				String name = one.getPath();
				if(name.contains("-v")){
					System.out.println(name);
					int index = name.indexOf("-v");
					name = name.substring(0, index);
					System.out.println(name);
					one.renameTo(new File(name));
					one.deleteOnExit();
				}
			}
		}
	}
	//css 文件遍历
	public static void perCssFile() throws Exception{
		File file = new java.io.File(cssDir);
		if(file.isDirectory()){
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File one = listFiles[i];
				String name = one.getPath();
				if(name.contains("css")){
					perLineViaCss(one);
				}
			}
		}
	}
	public static void perLineViaCss(File f) throws Exception{
		String name = f.getName();
		File tmpFile = new File(cssDir+"/tmp.css");
		tmpFile.createNewFile();
		BufferedReader br = new BufferedReader(new FileReader(f));
		BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
		String regEx4 = "-v";
		Pattern pattern4 = Pattern.compile(regEx4);
		
		String regEx5 = "url\\([^\\)]*(jpg|png|mp4|gif|ico)\\)";
		Pattern pattern5 = Pattern.compile(regEx5);
		String regEx6 = "url\\(\"[^\"]*(jpg|png|mp4|gif|ico)\"\\)";
		Pattern pattern6 = Pattern.compile(regEx6);
		String line = "";
		while((line = br.readLine())!=null){
			
			Matcher matcher = pattern4.matcher(line);
		    if(matcher.find()){
		    	line= matcher.replaceAll("?");
		    }
		    matcher = pattern5.matcher(line);
		    if(matcher.find()){ // 素材都在 img里面
		    	System.out.println(line);
		    	int start = matcher.start();
		    	int index = line.indexOf("url(", start);
		    	String pre = line.substring(0, index+4);
		    	String back = line.substring(index+4);
		    	line= pre+"../img/"+back;
		    	System.out.println(line);
		    }
		    matcher = pattern6.matcher(line);
		    if(matcher.find()){ // 素材都在 img里面
		    	System.out.println(line);
		    	int start = matcher.start();
		    	int index = line.indexOf("url(\"", start);
		    	String pre = line.substring(0, index+5);
		    	String back = line.substring(index+5);
		    	line= pre+"../img/"+back;
		    	System.out.println(line);
		    }
		    bw.write(line);
		    bw.newLine();
		    bw.flush();
		}
		//url\([^\)]*(jpg|png|mp4|gif|ico)\)
		br.close();
		bw.close();
		f.delete();
		File file = new File(cssDir+"/"+name);
		System.out.println(name);
		boolean renameTo = tmpFile.renameTo(file);
		System.out.println(renameTo);
	}
	//html文件遍历
	public static void perHtmlFile() throws Exception{
		
		File file = new java.io.File(htmlDir);
		if(file.isDirectory()){
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File one = listFiles[i];
				String name = one.getPath();
				if(name.contains("htm")){
					perLineViaHtml(one);
				}
			}
		}
	}
	public static void perLineViaHtml(File f) throws Exception{
		String name = f.getName();
		File tmpFile = new File(htmlDir+"/tmp.htm");
		tmpFile.createNewFile();
		BufferedReader br = new BufferedReader(new FileReader(f));
		BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
		
		
		String regEx1 = "tppabs=\"h[^\"]*\"";
		String regEx2 = "action=\"javascript\\:if\\(confirm\\([^\"]*\"";
		String regEx3 = "href=\"javascript\\:if\\(confirm\\([^\"]*\"";
		String regEx4 = "-v";
	    Pattern pattern1 = Pattern.compile(regEx1);
	    Pattern pattern2 = Pattern.compile(regEx2);
	    Pattern pattern3 = Pattern.compile(regEx3);
	    Pattern pattern4 = Pattern.compile(regEx4);
		
	    
	    String regEx5 = "(src|href)=\"[^\"]*(jpg|png|mp4|gif|ico)\"";
		String regEx6 = "(src|href)=\"[^\"]*js\"";
		String regEx7 = "(src|href)=\"[^\"]*css\"";
	    Pattern pattern5 = Pattern.compile(regEx5);
	    Pattern pattern6 = Pattern.compile(regEx6);
	    Pattern pattern7 = Pattern.compile(regEx7);
		
		
		String line = "";
		while((line = br.readLine())!=null){
			
		    Matcher matcher = pattern1.matcher(line);
		    if(matcher.find()){
		    	line= matcher.replaceAll(" ");
		    }
		    matcher = pattern2.matcher(line);
		    if(matcher.find()){
		    	line= matcher.replaceAll(" ");
		    }
		    matcher = pattern3.matcher(line);
		    if(matcher.find()){
		    	line= matcher.replaceAll(" ");
		    }
		    matcher = pattern4.matcher(line);
		    if(matcher.find()){
		    	line= matcher.replaceAll("?");
		    }
		    
		    matcher = pattern5.matcher(line);
		    while(matcher.find()){ // 素材都在 img里面
	    		int start = matcher.start();
		    	int index = line.indexOf("=\"", start);
		    	String pre = line.substring(0, index+2);
		    	String back = line.substring(index+2);
		    	line= pre+"img\\"+back;
		    }
		    
		    matcher = pattern6.matcher(line);
		    if(matcher.find()){ // js都在 js里面
		    	int start = matcher.start();
		    	int index = line.indexOf("=\"", start);
		    	String pre = line.substring(0, index+2);
		    	String back = line.substring(index+2);
		    	line= pre+"js\\"+back;
		    }
		    matcher = pattern7.matcher(line);
		    if(matcher.find()){ // css 都在css里面
		    	int start = matcher.start();
		    	int index = line.indexOf("=\"", start);
		    	String pre = line.substring(0, index+2);
		    	String back = line.substring(index+2);
		    	line= pre+"css\\"+back;
		    }
		    bw.write(line);
		    bw.newLine();
		    bw.flush();
		}
		br.close();
		bw.close();
		f.delete();
		File file = new File(htmlDir+"/"+name);
		System.out.println(name);
		boolean renameTo = tmpFile.renameTo(file);
		System.out.println(renameTo);
	}
}



