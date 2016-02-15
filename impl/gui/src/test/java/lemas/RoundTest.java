package lemas;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class RoundTest {
	
	@Before
	public void setup(){
		Round.getInstance().clear();
		Round.getInstance().setRange(3);
	}
	

	@Test
	public void getInstance() {
		Round r = Round.getInstance();
		r.update("31/01/2015");
		assertNotNull(r.getCurrentTime());
	}
	
	@Test
	public void getStartTime() {
		Round r = Round.getInstance();
		r.update("31/03/2015");
		
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(r.getStartTime());
		assertEquals(2015, c.get(Calendar.YEAR));
		assertEquals(31, c.get(Calendar.DAY_OF_MONTH));
		assertEquals(02, c.get(Calendar.MONTH));
	}
	
	
	@Test
	public void getUpdate() {
		Round r = Round.getInstance();
		r.update("29/03/2015");
		r.update("30/03/2015");
		r.update("31/03/2015");
		Calendar c = GregorianCalendar.getInstance();		
		c.setTime(r.getStartTime());
		assertEquals(29, c.get(Calendar.DAY_OF_MONTH));
		c.setTime(r.getCurrentTime());
		assertEquals(31, c.get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void getRound() {
		Round r = Round.getInstance();		
		assertEquals(0, r.getRound());
		
		r.update("26/03/2015");
		assertEquals(1, r.getRound());
		assertEquals(false, r.changed());
		
		r.update("27/03/2015");
		assertEquals(1, r.getRound());
		assertEquals(false, r.changed());
		
		r.update("28/03/2015");
		assertEquals(1, r.getRound());
		assertEquals(false, r.changed());
		
		r.update("29/03/2015");
		assertEquals(2, r.getRound());
		assertEquals(true, r.changed());
		r.update("30/03/2015");
		assertEquals(2, r.getRound());
		assertEquals(false, r.changed());
		r.update("31/03/2015");		
		assertEquals(2, r.getRound());
		assertEquals(false, r.changed());
		
		r.update("01/02/2015");
		assertEquals(3, r.getRound());
		assertEquals(true, r.changed());
	}
}
