package project;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PaymentInfo implements Serializable{
	private final double discountRatio = 20;
	private double receivedPayment;
	//receivedPayment her ödeme yapıldığında artacak
	public PaymentInfo() {
		this.receivedPayment = 0;
	}
	
	
	public double getDiscountRatio() {
		return discountRatio;
	}

	public double getReceivedPayment() {
		return receivedPayment;
	}

	public void increasePayment(double amount) {
		this.receivedPayment += amount;
	}
	
	
}
