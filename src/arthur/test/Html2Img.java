package arthur.test;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import gui.ava.html.image.generator.HtmlImageGenerator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Html2Img {
	public static HashMap<String,String> css = new HashMap();  
	static{
		css.put("send-box", "margin-bottom:10px;width: 100%;height: auto;display: flex;justify-content: flex-start; align-items: flex-start;");
		css.put("receive-box","margin-bottom:10px;width: 100%;height: auto;display: flex;justify-content: flex-end;align-items: flex-end;");
		css.put("send-img-box","width: 10%;height: auto;border-radius: 50%;margin:0 10px;");
		css.put("send-content-box","max-width: 70%;font-size: 16px;box-sizing: border-box;padding: 10px 12.5px 12.5px 12.5px;border-radius: 7px;background-color: #ffffff;word-wrap: break-word;line-height: 1.313;position: relative;");
	}
/*	send-box {
		}
		*/
	
	
	
	public static void main(String[] args) throws Exception {
		/*
		URL resource = Html2Img.class.getClassLoader().getResource("article.json");
		File fromFile = new File(resource.getFile());
		
		BufferedReader br = new BufferedReader(new FileReader(fromFile));
		String line = null;
		String jsonStr = "";
		while((line = br.readLine())!=null) {
			jsonStr +=line;
		}
		jsonStr = jsonStr.replaceAll("\"class", "\"class1");
		Document document = DocumentHelper.createDocument();
		Element addElement = document.addElement("div");
		if(jsonStr.charAt(0) == '[') {
			JSONArray ja = JSONArray.fromString(jsonStr);
			paserJsonToXml(addElement,null,ja);
		}else {
			JSONObject json = JSONObject.fromObject(jsonStr); 
			paserJsonToXml(addElement,json,null); 
		}
		String asXML = document.asXML();
		System.out.println(asXML);*/
		
		generateOutput();
//		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//		imageGenerator.loadHtml(document.asXML());
//		imageGenerator.saveAsImage("d://hello-world.png");
		
		
	}
static void generateOutput() throws Exception {
		
		URL resource = Html2Img.class.getClassLoader().getResource("index.html");
		File fromFile = new File(resource.getFile());
			String s=fromFile.getAbsolutePath();
			s="file:"+s;
		//load the webpage into the editor
		//JEditorPane ed = new JEditorPane(new URL("http://www.google.com"));
		JEditorPane ed = new JEditorPane();
		ed.setPage(s);
		ed.setSize(1000,1000);
		
		//create a new image
		BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(),
		                                        BufferedImage.TYPE_INT_ARGB);
 
		//paint the editor onto the image
		SwingUtilities.paintComponent(image.createGraphics(), 
		                              ed, 
		                              new JPanel(), 
		                              0, 0, image.getWidth(), image.getHeight());
		//save the image to file
		ImageIO.write((RenderedImage)image, "png", new File("d://html.png"));
	}
	
	public static void paserJsonToXml(Element e,JSONObject json,JSONArray ja) {
		if(json != null) {
			if(json.containsKey("attrs")) {
				JSONObject attrs = json.getJSONObject("attrs");
				Set<Entry> entrySet = attrs.entrySet();
				for(Entry<String,String> entry: entrySet ) {
					
					String key = entry.getKey();
					String value = entry.getValue();
					System.out.println(key+":"+value);
					if(key.equals("class1")) {
						String classToStyle = classToStyle(value);
						System.out.println(classToStyle);
						if(classToStyle!=null) {
							Attribute attribute = e.attribute("style");
							if(attribute!=null) {
								String stringValue = attribute.getStringValue();
								stringValue = stringValue+classToStyle;
								attribute.setValue(stringValue);
//								e.add(attribute);
							}else {
								e.addAttribute("style", classToStyle);
							}
						}
					}else {
						if(key.equals("style")) {
							Attribute attribute = e.attribute("style");
							if(attribute!=null) {
								String stringValue = attribute.getStringValue();
								stringValue = stringValue+value;
								attribute.setValue(stringValue);
//								e.add(attribute);
							}else {
								e.addAttribute("style", value);
							}
						}else {
							e.addAttribute(key,value);
						}
					}
				}
				if(json.containsKey("children")) {
					JSONArray jsonArray = json.getJSONArray("children");
					if(jsonArray.size()>0) {
						paserJsonToXml(e,null,jsonArray);
					}
				}
			}
		} else if(ja !=null) {
			for(int i = 0 ; i< ja.size(); i++) {
				JSONObject j = ja.getJSONObject(i);
				if(j.containsKey("name") ) {
					Element addElement = e.addElement(j.getString("name"));
					paserJsonToXml(addElement, j, null);
				}else if(j.containsKey("type") && j.getString("type").equals("text")) {
					e.addText(j.getString("text"));
				}
			}
		}
	}
	public static String classToStyle(String cla) {
		String string = css.get(cla);
		return string;
	}
}
