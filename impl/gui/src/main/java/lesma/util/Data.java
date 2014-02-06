package lesma.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lesma.model.Project;

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

	public static void saveLma(Project project, String file) {
		OutputStream outputStream;
		try {

			Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

			Transformer t = TransformerFactory.newInstance().newTransformer();

			Element a = d.createElement("a");
			Element b = d.createElement("b");

			a.appendChild(b);

			d.appendChild(a);

			t.setParameter(OutputKeys.INDENT, "yes");

			ByteArrayOutputStream s = new ByteArrayOutputStream();

			t.transform(new DOMSource(d), new StreamResult(s));

			System.out.println(new String(s.toByteArray()));
	

			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(xmlFile)));
			bufferedWriter.write(xmlString);
			bufferedWriter.flush();
			bufferedWriter.close();

		} catch (FileNotFoundException e) {
			Message.error(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			Message.error(e.getMessage());
			e.printStackTrace();
		} catch (XMLStreamException e) {
			Message.error(e.getMessage());
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			Message.error(e.getMessage());
			e.printStackTrace();
		}

	}

}
