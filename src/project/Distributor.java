package project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

@SuppressWarnings("serial")
public class Distributor implements Serializable, Runnable {
	
	private Hashtable<String,Journal> journals;
	//Journal ISSN, Journal 
	private Vector<Subscriber> subscribers;
	private volatile boolean reportRunning;
	private int param1=0,param2=0,process=1;
	private String reportMessage;
	
	public Distributor() {
		journals = new Hashtable<>();
		subscribers = new Vector<>();
		reportRunning = false;
		
	}
	
	//getter methods
	public Hashtable<String, Journal> getJournals() {
		return journals;
	}
	public Vector<Subscriber> getSubscribers() {
		return subscribers;
	}
	
	public boolean isReportRunning() {
		return reportRunning;
	}
	
	private synchronized void setReportRunning(boolean value) {
        reportRunning = value;
        if (!value) {
            notifyAll();
        }
    }
	
	// getter & setter for the param1 , param2, param3
	public int getParam1() {
		return param1;
	}
	public void setParam1(int param1) {
		this.param1 = param1;
	}
	public int getParam2() {
		return param2;
	}
	public void setParam2(int param2) {
		this.param2 = param2;
	}
	public int getProcess() {
		return process;
	}
	public void setProcess(int process) {
		this.process = process;
	}
	public String getReportMessage() {
		return reportMessage;
	}
	public void setReportMessage(String reportMessage) {
		this.reportMessage = reportMessage;
	}

	//add & search methods
	public boolean addJournal(Journal journal) {
		if(journal == null || searchJournal(journal.getIssn()) !=null) {
			return false;
		}
		else {
			journals.put(journal.getIssn(), journal);
			return true;
		}
	}
	
	

	public Journal searchJournal(String issn) {
		if(issn ==null) {
			return null;
		}
		
		return journals.get(issn);
	}
	
	public boolean addSubscriber(Subscriber s) {
		if( s==null || searchSubscriber(s.getName()) != null && searchSubscriber(s.getName()).equals(s) ) { 
			return false;
		}
		subscribers.add(s);
		return true;
	}
	
	public Subscriber searchSubscriber(String name) {
		for(Subscriber mysub : subscribers) {
			if(mysub.getName().equals(name)) {
				return mysub;
			}
		}
		return null;
	}
	
	public boolean addSubscription(String issn, Subscriber subscriber, Subscription subscription) {
		if(searchJournal(issn) == null || subscriber ==null || subscription ==null || issn != subscription.getJournal().getIssn() || subscriber != subscription.getSubscriber() || (subscriber.getName())==null){
			return false;
		}else {
			searchJournal(issn).addSubscription(subscription);
			return true;
		}
	}
	
	//list methods
	public String listAllSendingOrders(int month,int year ) {
		String message ="";
		Iterator<Map.Entry<String, Journal>> i = journals.entrySet().iterator();
		
		while(i.hasNext()) {
			Map.Entry<String, Journal> pair = (Map.Entry<String, Journal>) i.next();
			
			String ISSN = pair.getValue().getIssn();
			message += listSendingOrder(ISSN,month,year);
			System.out.println();
		}
		return message;
	}
	
	public String listSendingOrder(String issn,int month, int year ) {
		Journal j = searchJournal(issn);
		String message="";
		if(j ==null) {
			return message;
		}
		message += "# "+j.getName()+" Journal Orders:\n-------------------------\n";
		System.out.println("\n"+j.getName()+" Journal Orders:\n-------------------------");
		for(Subscription mysub : j.getSubscriptions()) {
			DateInfo date = mysub.getDates();
			
			if( (year == date.getStartYear() && month >= date.getStartMonth() ) || ( year == date.getEndYear() && month <= date.getEndMonth()) ) {
				for(int i=1; i <= j.getFrequency() ;i++) {
					if(mysub.canSend(i)) {
						message +="- Issue:"+i+" of "+j.getName()+" Journal can be sent to "+mysub.getSubscriber().getName()+" .\n";
						System.out.println("Issue:"+i+" of "+j.getName()+" Journal can be sent to "+mysub.getSubscriber().getName()+" .");
					}
				}
				message +="\n";
			}
		}
		message +="\n";
		return message;
	}
	
	public String listIncompletePayments() {
		String message ="";
		Iterator<Map.Entry<String, Journal>> i = journals.entrySet().iterator();
		
		while(i.hasNext()) {
			Map.Entry<String, Journal> pair = (Map.Entry<String, Journal>) i.next();
			Journal journal = pair.getValue();
			message +="# "+journal.getName()+" Journal:\n-------------------------\n";
			System.out.println("# "+journal.getName()+" Journal:\n-------------------------\n");
			for(Subscription mysub : journal.getSubscriptions()) {
				for(int j=1; j <= journal.getFrequency() ;j++) {
					if(mysub.canSend(j)==false) {
						message += "Issue:"+j+" of "+journal.getName()+" Journal has not been paid by "+mysub.getSubscriber().getName()+" yet.\n";
						System.out.println("Issue:"+j+" of "+journal.getName()+" Journal has not been paid by "+mysub.getSubscriber().getName()+" yet.");
					}
				}
				message +="\n";
				System.out.println("");
			}
			message +="\n";
			System.out.println();
		}
		return message;
	}
	
	
	public String listSubscriptionsByName(String subscriberName) {
		String message="";
		if(subscriberName ==null || searchSubscriber(subscriberName)== null) {
			message +="The subscriber you were looking for was not found.\n";
			System.out.println("The subscriber you were looking for was not found.");
			return message;
		}else {
			Subscriber mysub = searchSubscriber(subscriberName);
			Iterator<Map.Entry<String, Journal>> i = journals.entrySet().iterator();
			message +="List of Journals subscribed by "+subscriberName+":\n";
			message += "----------------------------\n";
			System.out.println("List of Journals subscribed by "+subscriberName+":");
			System.out.println("----------------------------");
			
			while(i.hasNext()) {
				Map.Entry<String, Journal> pair = (Map.Entry<String, Journal>) i.next();
				
				for(Subscription s : pair.getValue().getSubscriptions()) {
					if(s.getSubscriber().equals(mysub)) {
						message +=subscriberName+" is a subscriber of the "+pair.getValue().getName()+" journal.\n";
						System.out.println(subscriberName+" is a subscriber of the "+pair.getValue().getName()+" journal.");
					}
				}
			}
			return message;
		}
	}
	
	public String listSubscriptionsByJournal(String issn) {
		String message="";
		if(searchJournal(issn) ==null) {
			message += "The journal you were looking for was not found.\n";
			System.out.println("The journal you were looking for was not found.");
			return message;
		}else {
			message += searchJournal(issn).getName()+" Journal Subscriber List:\n";
			message += "----------------------------\n";
			System.out.println(searchJournal(issn).getName()+" Journal Subscriber List:");
			System.out.println("----------------------------");
			for(Subscription s : searchJournal(issn).getSubscriptions()) {
				message += "# "+s.getSubscriber().getName()+"\n";
				System.out.println(s.getSubscriber().getName());
			}
			return message;
		}
	}
	
	//report methods
	
	public void run() {
		report();
	}
	
	public void report() {
        setReportRunning(true);
        
        if(process ==1) {
        	// belirli bir tarihten sonra biten abonelikler = ay ve yıl alır
            setReportMessage(reportExpiredSubscriptions(param1, param2)); 
        }else if(process ==2) {
        	// belirli bir yıl aralığında alınan ödemeler  = başlangıç yılı - bitiş yılı alır
        	setReportMessage(reportReceivedPayments(param1, param2)); 
        }
        setReportRunning(false);
    }
	
	public String reportExpiredSubscriptions(int endingMonth,int endingYear) {
		Iterator<Map.Entry<String, Journal>> i = journals.entrySet().iterator();
		String message ="";
		message +="# Subscriptions ending after "+endingMonth+" / "+endingYear+" :\n";
		System.out.println("# Subscriptions ending after "+endingMonth+"/"+endingYear+" :");
		while(i.hasNext()) {
			Map.Entry<String, Journal> pair = (Map.Entry<String, Journal>) i.next();
			
			for(Subscription s : pair.getValue().getSubscriptions()) {
				if(s.getDates().getEndYear() >= endingMonth && s.getDates().getEndYear() == endingYear) {
					if(s.getSubscriber() instanceof Individual) {
						message += "- (Individual) "+s.getSubscriber().getName()+"'s "+s.getJournal().getName()+" Journal subscription will expire.\n";
						System.out.println("- (Individual) "+s.getSubscriber().getName()+"'s "+s.getJournal().getName()+" Journal subscription will expire.");
					}else {
						message += "- (Corporation) "+s.getSubscriber().getName()+"'s subscription will expire.\n";
						System.out.println("- (Corporation) "+s.getSubscriber().getName()+"'s subscription will expire.");
					}
				}
			}
		}
		return message;
	}
	
	public String reportReceivedPayments(int startYear, int endYear) {
		Iterator<Map.Entry<String, Journal>> i = journals.entrySet().iterator();
		double total =0;
		String message ="";
		message +="# "+startYear+"-"+endYear+" Total Received Payments:\n";
		System.out.println("# "+startYear+"-"+endYear+" Total Received Payments:");
		while(i.hasNext()) {
			Map.Entry<String, Journal> pair = (Map.Entry<String, Journal>) i.next();
			for(Subscription s : pair.getValue().getSubscriptions()) {
				DateInfo date = s.getDates();
				
				if( ( (date.getStartYear() >= startYear && date.getEndYear() <= endYear) || (date.getEndYear() <= endYear) ) && s.getPayment().getReceivedPayment() >0) {
					total += s.getPayment().getReceivedPayment();
					if(s.getSubscriber() instanceof Individual) {
						message += "- (Individual) "+s.getSubscriber().getName()+" paid "+s.getPayment().getReceivedPayment()+" TL for "+s.getJournal().getName()+" Journal.\n";
						System.out.println("- (Individual) "+s.getSubscriber().getName()+" paid "+s.getPayment().getReceivedPayment()+" TL for "+s.getJournal().getName()+" Journal.");
					}else {
						message += "- (Corporation) "+s.getSubscriber().getName()+" paid "+s.getPayment().getReceivedPayment()+" TL for "+s.getJournal().getName()+" Journal.\n";
						System.out.println("- (Corporation) "+s.getSubscriber().getName()+" paid "+s.getPayment().getReceivedPayment()+" TL for "+s.getJournal().getName()+" Journal.");
					}
				}	
			}
		}
		message +="\nTotal Payment: "+total+".";
		System.out.println("\nTotal Payment: "+total);
		return message;
	}
	
	
	
    public synchronized String saveState(String fileName) {
        while (isReportRunning()) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        String message="";
        try (ObjectOutputStream printer = new ObjectOutputStream(new FileOutputStream(fileName))) {
            printer.writeObject(this);
            printer.close();
            message += "Distributor object has been saved to " + fileName+"\n";
            System.out.println("Distributor object has been saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public synchronized String loadState(String fileName) {
        String message ="";
    	while (isReportRunning()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(fileName))) {
            Distributor loadedDistributor = (Distributor) reader.readObject();
            this.journals = loadedDistributor.journals;
            this.subscribers = loadedDistributor.subscribers;
            reader.close();
            message += "Distributor object has been loaded from " + fileName+"\n";
            System.out.println("Distributor object has been loaded from " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }
	
}
