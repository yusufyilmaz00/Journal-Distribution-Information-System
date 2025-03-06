package project;

import junit.framework.TestCase;

public class TestSubscription extends TestCase {
	
	DateInfo date;
	Journal j1;
	Individual s1;
	Subscription sub1;
	
	protected void setUp() {
		date = new DateInfo(1,2024);
		j1 = new Journal("Bilim Teknik","1234",12,25);
		s1 = new Individual("Aziz Nesin","Turkey","1011",12,2024,111);
		sub1 = new Subscription(date,1,j1,s1);
	}
	
	public void testacceptPaymentShouldIncrease100() {
		sub1.acceptPayment(100);
		assertEquals(100.0,sub1.getPayment().getReceivedPayment());
	}
	
	public void testcanSendShouldReturnTrue() {
		sub1.acceptPayment(20);
		assertTrue(sub1.canSend(1));
	}
	
	public void testcanSendShouldReturnFalse() {
		// received payment is 0
		assertFalse(sub1.canSend(1));
	}
	
	public void testcanSendShouldReturnFalse2() {
		sub1.acceptPayment(30);
		sub1.acceptPayment(15);
		sub1.acceptPayment(12.9);
		// received payment should be 60 , if less then 60 return false
		assertFalse(sub1.canSend(3));
	}
	
}
