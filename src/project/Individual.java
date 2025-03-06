package project;

@SuppressWarnings("serial")
public class Individual extends Subscriber{
	private String creditCardNr;
	private int expireMonth;
	private int expireYear;
	private int CCV;
	
	public Individual(String name, String address,String creditCardNr,int expireMonth,int expireYear,int CCV) {
		super(name, address);
		this.creditCardNr = creditCardNr;
		this.expireMonth = expireMonth;
		this.expireYear = expireYear;
		this.CCV = CCV;
	}
	//getter & setter methods
	public String getCreditCardNr() {
		return creditCardNr;
	}
	public void setCreditCardNr(String creditCardNr) {
		this.creditCardNr = creditCardNr;
	}
	public int getExpireMonth() {
		return expireMonth;
	}
	public void setExpireMonth(int expireMonth) {
		this.expireMonth = expireMonth;
	}
	public int getExpireYear() {
		return expireYear;
	}
	public void setExpireYear(int expireYear) {
		this.expireYear = expireYear;
	}
	public int getCCV() {
		return CCV;
	}
	public void setCCV(int cCV) {
		CCV = cCV;
	}

	@Override
	public String getBillingInformation() {
		String message ="";
		message += "Individual Subscriber Informations:\n"+getName()+", Adress: "+getAddress()+"\n";
		message += "Card Informations:\n"+"Card Number: "+creditCardNr+"\nExpire Date: "+expireMonth+"/"+expireYear+"\n";
		message += "CCV: "+CCV;
		return message;
	}
	
}
