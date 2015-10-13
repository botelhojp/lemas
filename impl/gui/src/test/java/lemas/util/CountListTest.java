package lemas.util;

import org.junit.Test;

import junit.framework.Assert;

public class CountListTest {

	@Test
	public void countSemTamanho() {
		CountList l = new CountList();		
		Assert.assertEquals(0.0, l.total());
		Assert.assertEquals(0.0, l.avg());
		l.add(3.0);		
		l.add(3.0);
		l.add(3.0);
		Assert.assertEquals(9.0, l.total());
		Assert.assertEquals(3.0, l.avg());
	}
	
	@Test
	public void countTamanho() {
		CountList l = new CountList(2);		
		Assert.assertEquals(0.0, l.total());
		Assert.assertEquals(0.0, l.avg());
		l.add(1.0);		
		l.add(2.0);
		l.add(3.0);
		l.add(5.0);
		l.add(4.0);
		Assert.assertEquals(9.0, l.total());
		Assert.assertEquals(4.5, l.avg());
	}
	
	@Test
	public void countTamanhoSemEstouro() {
		CountList l = new CountList(10);		
		Assert.assertEquals(0.0, l.total());
		Assert.assertEquals(0.0, l.avg());
		l.add(1.0);		
		l.add(2.0);
		l.add(3.0);
		l.add(6.0);
		Assert.assertEquals(12.0, l.total());
		Assert.assertEquals(3.0, l.avg());
	}

}
