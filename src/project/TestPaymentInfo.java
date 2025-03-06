package project;

import junit.framework.TestCase;

public class TestPaymentInfo extends TestCase{
	PaymentInfo payment;
	
	protected void setUp() {
		payment = new PaymentInfo();
	}
	
	public void testgetReceivedPaymentShouldReturn0() {
		assertEquals(0.0,payment.getReceivedPayment());
	}
	
	public void testgetReceivedPaymentShouldReturn50() {
		payment.increasePayment(50);
		assertEquals(50.0,payment.getReceivedPayment());
	}
	
	public void testgetDiscountShouldReturn20(){
		assertEquals(20.0,payment.getDiscountRatio());
	}
}



	