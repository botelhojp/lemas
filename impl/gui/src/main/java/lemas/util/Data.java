package lemas.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import lemas.model.LMASchema;
import lemas.model.LemasLog;
import lemas.model.Project;
import lemas.model.Result;
import lemas.model.Workspace;
import openjade.core.OpenJadeException;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

public class Data {
	
	private static SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat dt2 = new SimpleDateFormat("yyyyMMdd");

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
			project.setSaveIn(filePath);
			File file = new File(filePath);
			XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			out = new IndentingXMLStreamWriter(out);
			out.writeStartDocument();
			{
				out.writeStartElement(LMASchema.TAG_PROJECT);
				{
					

					out.writeStartElement(LMASchema.TAG_HOST);
					{
						out.writeCharacters(project.getHost());
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
					out.writeStartElement(LMASchema.TAG_LOADING);
					{
						out.writeCharacters(project.getLoading());
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
			LemasLog.erro(e);
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
					if (streamReader.getLocalName().equals(LMASchema.TAG_HOST)){
						project.setHost(streamReader.getElementText());
					}
					if (streamReader.getLocalName().equals(LMASchema.TAG_CONTAINER)){
						project.setConteiner(streamReader.getElementText());
					}					
					if (streamReader.getLocalName().equals(LMASchema.TAG_MONITOR)){
						project.setMonitor(Boolean.parseBoolean(streamReader.getElementText()));
					}
					if (streamReader.getLocalName().equals(LMASchema.TAG_TRUSTMODEL)){
						project.setTrustmodel(streamReader.getElementText());
					}
					if (streamReader.getLocalName().equals(LMASchema.TAG_CLASS)){
						project.setClazz(streamReader.getElementText());
					}
					if (streamReader.getLocalName().equals(LMASchema.TAG_ARFF)){
						project.setARFF(streamReader.getElementText());
					}		
					if (streamReader.getLocalName().equals(LMASchema.TAG_LOADING)){
						project.setLoading(streamReader.getElementText());
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
			LemasLog.erro(e);
		}
		return null;
	}
	
	public static Date strToDate(String date) {
		try {
			return dt.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String dateToStr(Date date) {
		return dt.format(date);
	}
	
	
	public static int strToIteration(String date) {
		Date d = strToDate(date);
		return Integer.parseInt(dt2.format(d));
	}

	public static Double strToDouble(String value) {
		try {
			return Double.parseDouble(value.replaceAll(",", "."));
		} catch (NumberFormatException e) {
			return 0.0;
		}

	}

	public static int strToValue(String value) {
		if (value.equals("pos")){
			return 1;
		}
		if (value.equals("neu")){
			return 0;
		}
		if (value.equals("neg")){
			return -1;
		}
		throw new OpenJadeException("Tipo [" + value + "] invalido");
	}
	
	public static String exceptionToStr (Throwable ex){
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

}
