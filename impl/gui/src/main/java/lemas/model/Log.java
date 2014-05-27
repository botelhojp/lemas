package lemas.model;

public class Log {
		
	public void info(String msg){
	}

	
	public void debug(String msg){
		info(msg);
	}
	
	public void erro(String msg){
		info(msg);
	}

	
}
