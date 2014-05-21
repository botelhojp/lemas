package lemas.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {
	
	static private DecimalFormat forma0 = new DecimalFormat("###");	
	static private DecimalFormat forma2 = new DecimalFormat("###.##");	
	static private DecimalFormat forma3 = new DecimalFormat("###.###");	
	static private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static double getDouble0(double valor) {
		try {
			return forma0.parse(forma0.format(valor)).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Retora uma data no formato String na forma dd/mm/aaaa
	 * @param data
	 * @return
	 */
	public static String getDate(Date data) {
		return dateFormat.format(data);
	}


	public static double getDouble2(double valor) {
		try {
			return forma2.parse(forma2.format(valor)).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static double getDouble3(double valor) {
		try {
			return forma3.parse(forma3.format(valor)).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static void main(String args[]) {  
	      System.out.println("Boot class path: " + System.getProperty("sun.boot.class.path"));  
	      System.out.println("Extension class path: " + System.getProperty("java.ext.dirs"));  
	      System.out.println("AppClassPath: " + System.getProperty("java.class.path"));  
	  
	      Format i=new Format();  
	      System.out.println("\nBoot CL: " + java.lang.Object.class.getClassLoader());  
	      System.out.println("App ClassLoader: " + i.getClass().getClassLoader());  
	   }  


	
}
