package arthur.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

public class ReduceImgTest {
    public static void main(String[] args) {
    	long start = System.currentTimeMillis();
    	long one = 0;
    	long two = 0;
    	try {
    		File imgFile = new File("H://cb.jpg");
    		FileInputStream fis = new FileInputStream(imgFile);
    		
    		BufferedImage sourceImg =ImageIO.read(fis);
    		int height = sourceImg.getHeight();
    		Double rate1 = 540.0 / height;
    		Double rate2 = 75.0 /height;
    		if(rate1 >1) rate1 = 1.0;
    		if(rate2 >1 ) rate2 =1.0;
    		
    		Builder<BufferedImage> of = Thumbnails.of(sourceImg);
    		of.scale(rate1)
    		.outputQuality(1).outputFormat("jpg")
    		.toFile("H://cb");
    		
    		
    		of = Thumbnails.of(sourceImg);
    		of.scale(rate2)
    		.outputQuality(1).outputFormat("jpg")
    		.toFile("H://cbs");
    		
    		System.out.println(one+"one,"+(System.currentTimeMillis()-start));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}