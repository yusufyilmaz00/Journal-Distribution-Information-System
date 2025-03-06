package project;

import junit.framework.TestCase;

public class TestIndividual extends TestCase{
	Individual s1;
	
	protected void setUp() {
		s1 = new Individual("Aziz Nesin","Turkey","1011",12,2024,111);
	}
	
	public void testgetNameShouldReturnAzizNesin() {
		assertEquals("Aziz Nesin",s1.getName());
	}
	
	public void testgetGetAdressShouldReturnTurkey() {
		assertEquals("Turkey",s1.getAddress());
	}
	
}
