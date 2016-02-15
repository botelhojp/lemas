package lemas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Round {
	
	private SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	private Date currentTime;
	private Date startTime;
	private int range;
	private int round;
	private int count;
	private boolean changed;
	
	private static Round instance = new Round();
	
	private Round(){
		clear();
	}
	
	public void clear() {
		currentTime = null;
		startTime = null;
		range = 1;
		round = 0;
		count = 0;
		changed = false;
	}

	public static Round getInstance(){
		return instance;
	}
	
	public void update(String date){
		try {
			changed = (count == range);
			if (changed){
				round++;
				count = 0;
				changed = true;
			}
			currentTime = dt.parse(date);
			if (startTime == null){
				startTime = dt.parse(date);
				round = 1;
			}
			count++;
		} catch (ParseException e) {
			throw new RuntimeException("erro no parse da data: " + date, e);
		}
	}

	public int getRound() {
		return round;
	}

	public int getRange() {
		return range;
	}

	public Date getCurrentTime() {
		return currentTime;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public boolean changed() {
		return changed;
	}

}
