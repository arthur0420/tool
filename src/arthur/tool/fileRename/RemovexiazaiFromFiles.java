package arthur.tool.fileRename;

import java.io.File;


public class RemovexiazaiFromFiles {
		
	
	public static void main(String[] args) {
		String dir = "H:/privateH5/cirrus/resource";
		File file = new java.io.File(dir);
		if(file.isDirectory()){
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File one = listFiles[i];
				String name = one.getPath();
				if(name.contains("下载")){
					System.out.println(name);
					int index = name.indexOf("下载");
					name = name.substring(0, index-1);
					System.out.println(name);
					one.renameTo(new File(name));
				}
			}
		}
	}
}

