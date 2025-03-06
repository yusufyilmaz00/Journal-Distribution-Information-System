package project;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Journal implements Serializable {
	private String name;
	private String issn;
	private int frequency;
	private double issuePrice;
	private ArrayList<Subscription> subscriptions;
	
	public Journal(String name, String issn, int frequency, double issuePrice) {
		this.name = name;
		this.issn = issn;
		this.frequency = frequency;
		this.issuePrice = issuePrice;
		subscriptions = new ArrayList<>();
	}
	//getter & setter methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public double getIssuePrice() {
		return issuePrice;
	}

	public void setIssuePrice(double issuePrice) {
		this.issuePrice = issuePrice;
	}
	
	public ArrayList<Subscription> getSubscriptions() {
		return subscriptions;
	}
	
	//other methods
	public void addSubscription(Subscription s) {
		for(Subscription sub : subscriptions) {
			if(sub.getSubscriber().equals(s.getSubscriber())) {
				s.setCopies(s.getCopies()+1);
				return;
			}
		}
		subscriptions.add(s);
	}
	
}
