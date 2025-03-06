package project;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Subscriber implements Serializable{
	private String name;
	private String address;
	
	public Subscriber(String name, String address) {
		this.name = name;
		this.address = address;
	}
	// getter & setter methods
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public abstract String getBillingInformation();
}
