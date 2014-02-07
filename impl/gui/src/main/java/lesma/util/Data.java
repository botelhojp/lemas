package lesma.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import lesma.model.Project;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

public class Data {

	public static String loadFileToStr(File file) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			while (br.ready()) {
				sb.append(br.readLine()).append("\n");
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void saveLma(Project project, String filePath) {		
		try {
			File file = new File(filePath);
			XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter( new FileOutputStream(file), "utf-8"));
			out = new IndentingXMLStreamWriter(out);
			out.writeStartDocument();
			out.writeStartElement("doc");
			out.writeStartElement("title");
			out.writeCharacters("Document Title");
			out.writeEndElement();
			out.writeEndElement();
			out.writeEndDocument();
			out.close();
		} catch (Exception e) {
			Message.error(e.getMessage());			
			e.printStackTrace();
		}

	}

}
