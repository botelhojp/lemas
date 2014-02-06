package lesma.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

}
