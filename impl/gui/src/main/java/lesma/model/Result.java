package lesma.model;

import java.util.Date;
import java.util.GregorianCalendar;

public class Result {

	private Date time;
	
	public Result(){
		time = GregorianCalendar.getInstance().getTime();
	}

	public Result(Date time) {
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
