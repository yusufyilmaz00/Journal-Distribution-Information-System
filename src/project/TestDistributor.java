package project;

import junit.framework.TestCase;
	
public class TestDistributor extends TestCase {
	Distributor d;
	Journal j1,j2,j3;
	Subscriber s1,s2,s3;
	Subscription sub1;
	DateInfo date;
	
	protected void setUp() {
		d = new Distributor();
		date = new DateInfo(1,2024);
		j1 = new Journal("Bilim Teknik","1234",12,25);
		j2 = new Journal("Bilim Çocuk","2345",12,30);
		j3 = new Journal("Meraklı Minik","3456",12,30);
		s1 = new Individual("Aziz Nesin","Turkey","1011",12,2024,111);
		s2 = new Individual("Aziz Nesin","Turkey","1012",12,2025,222);
		s3 = new Individual("Aziz Nesin","ABD","1013",12,2026,333);
		sub1 = new Subscription(date,1,j1,s1);
	}
	
	//addJournal : boolean
	public void testaddJournalShouldReturnTrue( ) {
		assertTrue(d.addJournal(j1));
	}
	
	public void testaddJournalShouldReturnFalse( ) {
		d.addJournal(j1);
		assertFalse(d.addJournal(j1));
	}
	
	public void testaddJournalShouldReturnFalse2( ) {
		assertFalse(d.addJournal(null));
	}
	
	//searchJournal : Journal
	public void testsearchJournalShouldEqualJournal( ) {
		d.addJournal(j1);
		assertEquals(j1,d.searchJournal(j1.getIssn()));
	}
	
	public void testsearchJournalShouldEqualNull( ) {
		assertNull(d.searchJournal(j1.getIssn()));
	}
	
	public void testsearchJournalShouldEqualNull2( ) {
		assertNull(d.searchJournal(null));
	}
	
	//addSubscriber : boolean
	public void testaddSubscriberShouldReturnTrue( ) {
		assertTrue(d.addSubscriber(s1));
	}
	
	public void testaddSubscriberShouldReturnTrue2( ) {
		d.addSubscriber(s1);
		assertTrue(d.addSubscriber(s2));
	}
	
	public void testaddSubscriberShouldReturnFalse( ) {
		d.addSubscriber(s1);
		assertFalse(d.addSubscriber(s1));
	}
	
	public void testaddSubscriberShouldReturnFalse2( ) {
		assertFalse(d.addSubscriber(null));
	}
	
	//searchSubscriber : Subscriber
	public void testsearchSubscriberShouldEqualSubscriber( ) {
		d.addSubscriber(s1);
		assertEquals(s1,d.searchSubscriber(s1.getName()));
	}
	
	public void testsearchSubscriberShouldEqualNull( ) {
		assertNull(d.searchSubscriber(s1.getName()));
	}
	
	public void testsearchSubscriberShouldEqualNull2( ) {
		assertNull(d.searchSubscriber(null));
	}
	
	public void testsearchSubscriberSameNameSearch( ) {
		// same names but different objects
		d.addSubscriber(s1);
		d.addSubscriber(s3);
		assertFalse(s2==d.searchSubscriber("Aziz Nesin"));
	}
	
	//addSubscription : boolean
	public void testaddSubscriptionShouldReturnTrue() {
		d.addJournal(j1);
		d.addSubscriber(s1);
		
		assertTrue(d.addSubscription("1234",s1, sub1));
	}
	
	public void testaddSubscriptionShouldEqualTwo() {
		d.addJournal(j1);
		d.addSubscriber(s1);
		d.addSubscription("1234",s1, sub1);
		d.addSubscription("1234",s1, sub1);
		assertEquals(2,sub1.getCopies());
	}
	
	public void testaddSubscriptionShouldReturnFalse() {
		d.addJournal(j1);
		d.addSubscriber(s1);
		assertFalse(d.addSubscription("1234",s1, null));
	}
	
	public void testaddSubscriptionShouldReturnFalse2() {
		d.addJournal(j1);
		assertFalse(d.addSubscription("1234",null, sub1));
	}
	
	public void testaddSubscriptionShouldReturnFalse3() {
		d.addJournal(j1);
		assertFalse(d.addSubscription(null,s1, sub1));
	}
	
	public void testaddSubscriptionDifferentParameter1() {
		d.addJournal(j1);
		d.addJournal(j2);
		d.addSubscriber(s1);
		//sub1 created with j1 and s1 parameter
		assertFalse(d.addSubscription("2345", s1, sub1));
	}
	
	public void testaddSubscriptionDifferentParameter2() {
		d.addJournal(j1);
		d.addSubscriber(s1);
		d.addSubscriber(s2);
		//sub1 created with j1 and s1 parameter
		assertFalse(d.addSubscription("1234", s2, sub1));
	}
}
