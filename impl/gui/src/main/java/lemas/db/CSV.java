package lemas.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import lemas.exception.LemasException;
import lemas.model.Project;

public class CSV {

	private static Project project;
	private static String folder;
	private static String sufix;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static PrintWriter out = null;

	public static void start(Project _project) {
		project = _project;
		folder = (new File(project.getProperties().get("ARFF").toString())).getParent();
		sufix = getSufix();
	}

	public static void stop() {
		project = null;
		out.close();
		out = null;
	}

	public static void save(String fileName, int executions, double round, double value) {
		if (project == null) {
			throw new LemasException("Modo CSV não iniciado!");
		}
		try {
			String timer = format.format(((Calendar) GregorianCalendar.getInstance()).getTime());
			String file = folder + File.separatorChar + fileName + "_" + sufix + ".csv";
			if (out == null) {
				out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			}
			out.println((executions + ";" + (int) round + (";" + value).replace(".", ",") + ";" + timer));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getSufix() {
		SimpleDateFormat f = new SimpleDateFormat("HH_mm_ss");
		return f.format(((Calendar) GregorianCalendar.getInstance()).getTime());
	}

}
