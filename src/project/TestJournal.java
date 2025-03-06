package project;

import junit.framework.TestCase;

public class TestJournal extends TestCase {
	
	Journal j1;
	Individual s1;
	Subscription sub1;
	DateInfo date;
	
	protected void setUp() {
		j1 = new Journal("Bilim Teknik","1234",12,25);
		s1 = new Individual("Aziz Nesin","Turkey","1011",12,2024,111);
		sub1 = new Subscription(date,1,j1,s1);
		date = new DateInfo(1,2024);
	}
	
	public void testaddSubscriptionShouldEqualOne(){
		j1.addSubscription(sub1);
		assertEquals(1,sub1.getCopies());
	}
	
	public void testaddSubscriptionShouldEqualThree(){
		j1.addSubscription(sub1);
		j1.addSubscription(sub1);
		j1.addSubscription(sub1);
		assertEquals(3,sub1.getCopies());
	}
	public void testaddSubscriptionShouldEqualZero(){
		j1.addSubscription(null);
		// sub1's copies count initialized as 1
		// and method did not increase copies count
		assertEquals(1,sub1.getCopies());
	}
	
}
