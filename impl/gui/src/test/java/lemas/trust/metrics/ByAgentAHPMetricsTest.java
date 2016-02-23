package lemas.trust.metrics;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

public class ByAgentAHPMetricsTest {

	@Test
	public void testNota_f1() {
		AHPRiskValueMetrics m = new  AHPRiskValueMetrics();
		Assert.assertEquals(10.0, m.nota_f1(50, 24), 0.0);
		Assert.assertEquals(10.0, m.nota_f1(50, 25), 0.0);
		Assert.assertEquals(7.33, round(m.nota_f1(50, 45), 2), 0.0);
		Assert.assertEquals(0.13, round(m.nota_f1(50, 99), 2), 0.0);
		Assert.assertEquals(0.0, m.nota_f1(50, 100), 0.0);
		Assert.assertEquals(0.0, m.nota_f1(50, 101), 0.0);
		
		
		Assert.assertEquals(8.89, round(m.nota_f1(15, 10),2), 0.0);
		Assert.assertEquals(4.44, round(m.nota_f1(15, 20),2), 0.0);
		Assert.assertEquals(5.78, round(m.nota_f1(15, 17),2), 0.0);
	}
	
	
	@Test
	public void testNota_f2() {
		AHPRiskValueMetrics m = new  AHPRiskValueMetrics();
		Assert.assertEquals(9.33, round(m.nota_f2(600, 560),2), 0.0);
		Assert.assertEquals(10.0, round(m.nota_f2(600, 600),2), 0.0);
		Assert.assertEquals(1.67, round(m.nota_f2(600, 100),2), 0.0);
		
	}
	
	@Test
	public void testNota_f3() {
		AHPRiskValueMetrics m = new  AHPRiskValueMetrics();
		Assert.assertEquals(10.0, round(m.nota_f3(40, 50),2), 0.0);
		Assert.assertEquals(7.67, round(m.nota_f3(40, 43),2), 0.0);
		Assert.assertEquals(8.67, round(m.nota_f3(40, 46),2), 0.0);
		Assert.assertEquals(5.0, round(m.nota_f3(40, 35),2), 0.0);
		
	}
	
	@Test
	public void min() {
		AHPRiskValueMetrics m = new  AHPRiskValueMetrics();
		Assert.assertEquals(-4.0, m.min(5.0, 6.9, -4.0), 0);		
		Assert.assertEquals(5.0, m.min(5.0, 6.9), 0);
	}
	
	@Test
	public void max() {
		AHPRiskValueMetrics m = new  AHPRiskValueMetrics();
		Assert.assertEquals(6.9, m.max(5.0, 6.9, -4.0), 0);		
		Assert.assertEquals(5.0, m.max(2.3, 1.9, 5.0), 0);
	}
	
	
	@Test
	public void comparacao() {
		AHPRiskValueMetrics m = new  AHPRiskValueMetrics();
		Assert.assertEquals(1.0, round(m.comparacao(8.67, 8.67, 7.33),2), 0);
		Assert.assertEquals(1.22, round(m.comparacao(8.67, 8.94, 7.33),2), 0);		
		Assert.assertEquals(1.62, round(m.comparacao(7.22, 7.19, 4.44),2), 0);
		
	}
	
	@Test
	public void br() {
		AHPRiskValueMetrics m = new  AHPRiskValueMetrics();
		Assert.assertEquals("R=B", m.br(5.9, 5.9));
		Assert.assertEquals("B/R", m.br(5.9, 5.8));
		Assert.assertEquals("R/B", m.br(5.7, 5.8));
		
	}
	
	
	private double round(double value, int scale){
		return (new BigDecimal( value).setScale(scale, RoundingMode.HALF_EVEN)).doubleValue();
	}

}
