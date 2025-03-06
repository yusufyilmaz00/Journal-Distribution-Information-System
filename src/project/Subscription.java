package project;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Subscription implements Serializable{
	private final DateInfo dates;
	private PaymentInfo payment;
	private int copies;
	private final Journal journal;
	private final Subscriber subscriber;
	
	public Subscription(DateInfo dates, int copies, Journal journal, Subscriber subscriber) {
		this.dates = dates;
		this.copies = copies;
		this.journal = journal;
		this.subscriber = subscriber;
		this.payment = new PaymentInfo();
	}
	//getter & setter methods
	public PaymentInfo getPayment() {
		return payment;
	}
	public void setPayment(PaymentInfo payment) {
		this.payment = payment;
	}
	public int getCopies() {
		return copies;
	}
	public void setCopies(int copies) {
		this.copies = copies;
	}
	public DateInfo getDates() {
		return dates;
	}
	public Journal getJournal() {
		return journal;
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}
	
	// other methods
	public void acceptPayment(double amount) {
		payment.increasePayment(amount);
	}
	
	public boolean canSend(int issueMonth) {
		if(issueMonth > journal.getFrequency() || payment.getReceivedPayment() < (issueMonth * journal.getIssuePrice() * (100.0 - payment.getDiscountRatio() ) /100.0 )) {
			return false;
		}
		return true;
	}
}
