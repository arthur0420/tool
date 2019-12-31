package compressImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

public class CompressImage { 
	
	
	public static void compressImgTitle(File f) {
		 try {
	    		long now = System.currentTimeMillis();
	    		ImageIO.setUseCache(false);
	    		BufferedImage sourceImg =ImageIO.read(f);
	    		int height = sourceImg.getHeight();
	    		Double rate1 = 140.0 / height;
	    		if(rate1 >1) rate1 = 1.0;
	    		Builder<BufferedImage> of = Thumbnails.of(sourceImg);
	    		of.scale(rate1)
	    		.outputQuality(0.7).outputFormat("jpg")
	    		.toFile(f);
	    		System.out.println("compressImgTitle img time "+ (System.currentTimeMillis() - now));
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	public static void main(String[] args) {
		File f =new File("/opt/uploadfile/articleHtml");
		File[] listFiles = f.listFiles();
		List<File> titleList = new ArrayList<File>();
		for(int i = 0 ; i<listFiles.length ;i++) {
			File dateDir = listFiles[i];
			String dateName = dateDir.getName();
			if(dateDir.isDirectory()) {
				File[] listFiles2 = dateDir.listFiles();
				for(int j = 0 ; j< listFiles2.length;j++) {
					File theArticleDir = listFiles2[j];
					String uuid = theArticleDir.getName();
					File titleJpg = new File("/opt/uploadfile/articleHtml/"+dateName+"/"+uuid+"/title.jpg");
					if(titleJpg.exists() && titleJpg.isFile()) {
						titleList.add(titleJpg);
					}else {
						System.out.println(titleJpg.getAbsolutePath());
					}
				}
			}
		}
		for(int i = 0 ; i<titleList.size();i++) {
			File one = titleList.get(i);
			compressImgTitle(one);
		}
	}
}
