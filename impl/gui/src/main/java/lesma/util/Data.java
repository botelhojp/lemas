package lesma.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import lesma.model.LMASchema;
import lesma.model.Project;
import lesma.model.Result;
import lesma.model.Workspace;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

public class Data {

	public static String loadFileToStr(File file) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			while (br.ready()) {
				sb.append(br.readLine()).append("\n");
			}
			br.close();
		} catch (IOException e) {
                    Message.error(e.getMessage(), null);
		} 
		return sb.toString();
	}

	public static void projectToFile(Project project, String filePath) {
		try {
			project.getResults().add(new Result());
			project.getResults().add(new Result());	
                        project.setSaveIn(filePath);
			File file = new File(filePath);
			XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			out = new IndentingXMLStreamWriter(out);
			out.writeStartDocument();
			{
				out.writeStartElement(LMASchema.TAG_PROJECT);
				{
					

					out.writeStartElement(LMASchema.TAG_IP);
					{
						out.writeCharacters(project.getIp());
					}
					out.writeEndElement();
					
					out.writeStartElement(LMASchema.TAG_CONTAINER);
					{
						out.writeCharacters(project.getConteiner());
					}
					out.writeEndElement();
					
					out.writeStartElement(LMASchema.TAG_MONITOR);
					{
						out.writeCharacters(project.isMonitor()?"true":"false");
					}
					out.writeEndElement();					
					
					out.writeStartElement(LMASchema.TAG_TRUSTMODEL);
					{
						out.writeCharacters(project.getTrustmodel());
					}
					out.writeEndElement();					

					out.writeStartElement(LMASchema.TAG_CLASS);
					{
						out.writeCharacters(project.getClazz());
					}
					out.writeEndElement();
					out.writeStartElement(LMASchema.TAG_ARFF);
					{
						out.writeCharacters(project.getARFF());
					}
					out.writeEndElement();
					out.writeStartElement(LMASchema.TAG_RESULTS);
					{
						for (Result result : project.getResults()) {
							out.writeStartElement(LMASchema.TAG_RESULT);
							{
								out.writeStartElement(LMASchema.TAG_TIME);
								{
									out.writeCharacters(Workspace.dateFormat.format(result.getTime()));
								}
								out.writeEndElement();
							}
							out.writeEndElement();
						}
					}
					out.writeEndElement();
				}
				out.writeEndElement();
			}
			out.writeEndDocument();
			out.close();
		} catch (Exception e) {
			Message.error(e.getMessage(), null);
			e.printStackTrace();
		}

	}

	public static Project fileToProject(File file) {
		try {
			Project project = new Project();
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(file));
			while (streamReader.hasNext()) {
				streamReader.next();
				if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
					if (streamReader.getLocalName().equals(LMASchema.TAG_TRUSTMODEL)){
						project.setTrustmodel(streamReader.getElementText());
					}
					if (streamReader.getLocalName().equals(LMASchema.TAG_CLASS)){
						project.setClazz(streamReader.getElementText());
					}
					if (streamReader.getLocalName().equals(LMASchema.TAG_ARFF)){
						project.setARFF(streamReader.getElementText());
					}				
					if (streamReader.getLocalName().equals(LMASchema.TAG_TIME)){						
						project.getResults().add(new Result(Workspace.dateFormat.parse(streamReader.getElementText())));
					}
				}
			}
			streamReader.close();
			return project;
		} catch (Exception e) {
			Message.error(e.getMessage(), null);
			e.printStackTrace();
		}
		return null;
	}

}
