 package jenpack;

import java.io.File;
import java.io.StringWriter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

//import org.apache.commons.configuration.XMLConfiguration;

public class XMLParserClass {
	XMLConfiguration xml;

	public XMLParserClass(String xmlName) throws ConfigurationException {
		// TODO Auto-generated constructor stub
		xml=new XMLConfiguration(new File(xmlName));
	}
	
	public void getXMLProperty(String p) {
		System.out.println("Property Name:"+p+"\tProperty Value:"+xml.getString(p).toString());
	}
	
	public void setXMLProperty(String p,String value) throws ConfigurationException {
		getXMLProperty(p);
		xml.setProperty(p, value);
		System.out.println("Changed");
		getXMLProperty(p);
		xml.save();
	}
	
	public void getXMlOfJob(String jobName) {
		
	}
	
	
	
	public String convertXMlToString() throws ConfigurationException {
		StringWriter s=new StringWriter();
		xml.save(s);
		System.out.println(s.toString());
		return s.toString();
	}
	
	public static void main(String[] args) throws Exception {
		//XMLParserClass x=new XMLParserClass("D:\\Java eclipse\\workspace\\SimpleJspWeb\\src\\main\\resources\\config.xml");
		//x.getXMLProperty("country");
		XMLParserClass x1=new XMLParserClass("D:\\Java eclipse\\\\workspace\\\\SimpleJspWeb\\\\src\\\\main\\\\resources\\\\configsimple.xml");
		//x1.getXMLProperty("builders.hudson.tasks.BatchFile.command");
		//x1.getXMLProperty("disabled");
		//x1.setXMLProperty("disabled", "false");
		x1.getXMLProperty("builders.hudson..tasks..BatchFile.command");
		String s="java -version\n"
				+ "mvn -version";
		x1.setXMLProperty("builders.hudson..tasks..BatchFile.command", s);
		x1.convertXMlToString();
	}
	
}
