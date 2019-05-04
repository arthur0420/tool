package arthur.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class TestDom4j {
	
	  public static Document parse(File a) throws DocumentException {
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(a);
	        return document;
	    }
	  public static void main(String[] args) throws MalformedURLException, DocumentException {
		  URL resource = Html2Img.class.getClassLoader().getResource("text");
			File fromFile = new File(resource.getFile());
		 Document doc = parse(fromFile);
		 Element rootElement = doc.getRootElement();
		 
		 for (int i = 0, size = rootElement.nodeCount(); i < size; i++) {
		        Node node = rootElement.node(i);
		        if (node instanceof Element) {
		            System.out.println(node.getName());
		        }else {
		        	System.out.println(node);
		        }
		    }
		 
	}
}
