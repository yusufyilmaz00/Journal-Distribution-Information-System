package project;

import junit.framework.TestCase;

public class TestCorporation extends TestCase{
	Corporation s1;
	
	protected void setUp() {
		s1 = new Corporation("Mehmet Bey","Turkey",123,"İş Bankası",12,01,2024,1111111);
	}
	
	public void testgetBankCodeShouldReturn123() {
		assertEquals(123,s1.getBankCode());
	}
	
	public void testgetGetBankNameShouldReturnIsBankasi() {
		assertEquals("İş Bankası",s1.getBankName());
	}
}
