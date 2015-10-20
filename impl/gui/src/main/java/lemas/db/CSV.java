package lemas.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CSV {

	public static void save(String fileName, int executions, double round, double value) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timer = f.format(((Calendar) GregorianCalendar.getInstance()).getTime());
			String file =  System.getProperty("user.home") + File.separatorChar + fileName + ".csv";
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		    out.println((executions + ";" + (int)round + (";" + value).replace(".", ",") + ";" + timer));
		    out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
}
