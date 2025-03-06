package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

@SuppressWarnings("serial")
public class ProjectGUI extends JFrame {
	private Distributor distributor;
	private JComboBox<Journal> journalComboBox;
	private JComboBox<Subscriber> subscriberComboBox;
	private JComboBox<Subscription> subscriptionComboBox;
	
    public ProjectGUI() {
        // Set up JFrame properties
        setTitle("Journal Distribution System");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        distributor = new Distributor();
   
        JButton generalOperationsButton = new JButton("General Operations");
        JButton addJournalButton = new JButton("Journal Operations");
        JButton subscriptionButton = new JButton("Subscriber Operations");
        JButton exitButton = new JButton("Exit");

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5); // Padding
        constraints.anchor = GridBagConstraints.CENTER; // Center the components

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(addJournalButton, constraints);

        constraints.gridy = 1;
        panel.add(subscriptionButton, constraints);
        
        constraints.gridy = 2;
        panel.add(generalOperationsButton, constraints);
      
        constraints.gridy = 3;
        panel.add(exitButton, constraints);

        add(panel);

        generalOperationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showGeneralOperationsMenu();
            }
        });


        addJournalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showJournalsMenu();
            }
        });

        subscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showSubscriptionMenu();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    // Journal Menu Methods
    private void showJournalsMenu() {
        JDialog dialog = new JDialog(this, "Journal Menu", true);

        JButton addJournalButton = new JButton("Add Journal");
        JButton showAllJournalsButton = new JButton("Show All Journals");
        JButton backToMenuButton = new JButton("Back to Menu");
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(addJournalButton, constraints);

        constraints.gridy = 1;
        panel.add(showAllJournalsButton, constraints);
        
        constraints.gridy = 2;
        panel.add(backToMenuButton, constraints);
        
        addJournalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddJournalDialog();
            }
        });

        showAllJournalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllJournals();
            }
        });
        
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); 
            }
        });
        
        dialog.add(panel);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void showAllJournals() {
        String message = "Registered Journals:\n";

        if (distributor.getJournals().isEmpty()) {
            message +="No journals registered yet!";
        } else {
        	Iterator<Map.Entry<String, Journal>> i = distributor.getJournals().entrySet().iterator();
    		
    		while(i.hasNext()) {
    			Map.Entry<String, Journal> pair = (Map.Entry<String, Journal>) i.next();
       			message += "- "+pair.getValue().getName()+"\n";
    		}
        }
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(message);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "All Journals", JOptionPane.PLAIN_MESSAGE);
    }
    

	private void showAddJournalDialog() {
        JDialog dialog = new JDialog(this, "Add Journal", true);

        JTextField nameField = new JTextField(15);
        JTextField issnField = new JTextField(15);
        JTextField frequencyField = new JTextField(5);
        JTextField issuePriceField = new JTextField(10);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Journal Name:"), constraints);
        constraints.gridy = 1;
        panel.add(new JLabel("ISSN:"), constraints);
        constraints.gridy = 2;
        panel.add(new JLabel("Frequency:"), constraints);
        constraints.gridy = 3;
        panel.add(new JLabel("Issue Price:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameField, constraints);
        constraints.gridy = 1;
        panel.add(issnField, constraints);
        constraints.gridy = 2;
        panel.add(frequencyField, constraints);
        constraints.gridy = 3;
        panel.add(issuePriceField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(saveButton, constraints);

        constraints.gridy = 5;
        panel.add(cancelButton, constraints);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(nameField, issnField, frequencyField, issuePriceField)) {
                    String name = nameField.getText();
                    String issn = issnField.getText();
                    int frequency = Integer.parseInt(frequencyField.getText());
                    double issuePrice = Double.parseDouble(issuePriceField.getText());

                    Journal newJournal = new Journal(name, issn, frequency, issuePrice);
                    distributor.addJournal(newJournal);

                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private boolean validateFields(JTextField... fields) {
        // Not: kullanıcı tüm alanları doldurmalı yoksa constructor hata verir
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    // Subscriber Menu Methods
    private void showSubscriptionMenu() {
        JDialog dialog = new JDialog(this, "Subscription Operations Menu", true);

        JButton individualSubscriptionButton = new JButton("Save Individual Subscriber");
        JButton corporateSubscriptionButton = new JButton("Save Corporate Subscriber");
        JButton showAllSubscribersButton = new JButton("Show All Subscribers");
        JButton startSubscriptionButton = new JButton("      Create Subscription      ");
        JButton paymentButton = new JButton("      Do the Payment      ");     
        JButton billingInfoButton = new JButton("      Billing Info      ");
        JButton backToMenuButton = new JButton("Back to Menu");
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(individualSubscriptionButton, constraints);

        constraints.gridy = 1;
        panel.add(corporateSubscriptionButton, constraints);
        
        constraints.gridy = 2;
        panel.add(showAllSubscribersButton, constraints);

        constraints.gridy = 3;
        panel.add(startSubscriptionButton, constraints);
        
        constraints.gridy = 4;
        panel.add(paymentButton, constraints);
        
        constraints.gridy = 5;
        panel.add(billingInfoButton, constraints);
        
        constraints.gridy = 6;
        panel.add(backToMenuButton, constraints);
        
        individualSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIndividualSubscriptionDialog();
            }
        });

        corporateSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showCorporationSubscriptionDialog();
            }
        });
        
        showAllSubscribersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showAllSubscribers();
            }
        });
        
        startSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showStartSubscriptionDialog();
            }
        });
        
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showPaymentDialog();
            }
        });
        
        billingInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showBillingInfoDialog();
            }
        });
        
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); 
            }
        });

        dialog.add(panel);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showIndividualSubscriptionDialog() {
        JDialog dialog = new JDialog(this, "Save Individual Subscriber", true);
        
        JTextField nameField = new JTextField(20);
        JTextField addressField = new JTextField(40);
        JTextField creditCardNumberField = new JTextField(15);
        JTextField expireMonthField = new JTextField(2);
        JTextField expireYearField = new JTextField(4);
        JTextField ccvField = new JTextField(3);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Name"), constraints);
        constraints.gridy = 1;
        panel.add(new JLabel("Address:"), constraints);
        constraints.gridy = 2;
        panel.add(new JLabel("Credit Card Number:"), constraints);
        constraints.gridy = 3;
        panel.add(new JLabel("Expire Month:"), constraints);
        constraints.gridy = 4;
        panel.add(new JLabel("Expire Year:"), constraints);
        constraints.gridy = 5;
        panel.add(new JLabel("CCV:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameField, constraints);
        constraints.gridy = 1;
        panel.add(addressField, constraints);
        constraints.gridy = 2;
        panel.add(creditCardNumberField, constraints);
        constraints.gridy = 3;
        panel.add(expireMonthField, constraints);
        constraints.gridy = 4;
        panel.add(expireYearField, constraints);
        constraints.gridy = 5;
        panel.add(ccvField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        panel.add(saveButton, constraints);

        constraints.gridy = 7;
        panel.add(cancelButton, constraints);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(nameField,addressField,creditCardNumberField, expireMonthField, expireYearField, ccvField)) {
                	String name = nameField.getText();
                	String address = addressField.getText();
                	String creditCardNumber = creditCardNumberField.getText();
                    int expireMonth = Integer.parseInt(expireMonthField.getText());
                    int expireYear = Integer.parseInt(expireYearField.getText());
                    int ccv = Integer.parseInt(ccvField.getText());

                    Subscriber individualSubscription = new Individual(name,address,creditCardNumber,expireMonth,expireYear,ccv);
                
                    distributor.addSubscriber(individualSubscription);

                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void showCorporationSubscriptionDialog() {
        JDialog dialog = new JDialog(this, "Save Corporation Subscriber", true);
        
        JTextField nameField = new JTextField(20);
        JTextField addressField = new JTextField(40);
        JTextField bankCodeField = new JTextField(15);
        JTextField bankNameField = new JTextField(20);
        JTextField issueDayField = new JTextField(4);
        JTextField issueMonthField = new JTextField(4);
        JTextField issueYearField = new JTextField(4);
        JTextField accountNumberField = new JTextField(3);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Name"), constraints);
        constraints.gridy = 1;
        panel.add(new JLabel("Address:"), constraints);
        constraints.gridy = 2;
        panel.add(new JLabel("Bank Code:"), constraints);
        constraints.gridy = 3;
        panel.add(new JLabel("Bank Name:"), constraints);
        constraints.gridy = 4;
        panel.add(new JLabel("Issue Day:"), constraints);
        constraints.gridy = 5;
        panel.add(new JLabel("Issue Month:"), constraints);
        constraints.gridy = 6;
        panel.add(new JLabel("Issue Year:"), constraints);
        constraints.gridy = 7;
        panel.add(new JLabel("Account Number:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameField, constraints);
        constraints.gridy = 1;
        panel.add(addressField, constraints);
        constraints.gridy = 2;
        panel.add(bankCodeField, constraints);
        constraints.gridy = 3;
        panel.add(bankNameField, constraints);
        constraints.gridy = 4;
        panel.add(issueDayField, constraints);
        constraints.gridy = 5;
        panel.add(issueMonthField, constraints);
        constraints.gridy = 6;
        panel.add(issueYearField, constraints);
        constraints.gridy = 7;
        panel.add(accountNumberField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        panel.add(saveButton, constraints);

        constraints.gridy = 9;
        panel.add(cancelButton, constraints);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(nameField,addressField, bankCodeField,bankNameField, issueDayField,issueMonthField,issueYearField,accountNumberField)) {
                	String name = nameField.getText();
                	String address = addressField.getText();
                	int bankCode = Integer.parseInt(bankCodeField.getText());
                	String bankName = bankNameField.getText();
                	int issueDay = Integer.parseInt(issueDayField.getText());
                	int issueMonth = Integer.parseInt(issueMonthField.getText());
                	int issueYear = Integer.parseInt(issueYearField.getText());
                    int accountNumber = Integer.parseInt(accountNumberField.getText());

                    Subscriber CorporationSubscriber = new Corporation(name,address,bankCode,bankName,issueDay,issueMonth,issueYear,accountNumber);
                
                    distributor.addSubscriber(CorporationSubscriber);

                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showAllSubscribers() {
        String message = "Registered Subscribers:\n";

        if (distributor.getSubscribers().isEmpty()) {
            message += "There are no registered Subscribers yet.";
        } else {
        	for(Subscriber s : distributor.getSubscribers()) {
        		String objClass;
        		if (s instanceof Individual) {
        			objClass= "Individual Subscriber";

                } else {
                	objClass= "Corporation Subscriber";
                }
        		message += "- "+objClass+" : "+s.getName()+"\n";
        	}
        }
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(message);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "All Subscribers", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void showStartSubscriptionDialog() {
        JDialog dialog = new JDialog(this, "Subscription Creation Menu", true);
        
        JTextField startMonthField = new JTextField(4);
        JTextField startYearField = new JTextField(4);
        JTextField copiesField = new JTextField(4);
        
     // Mevcut dergi ve aboneleri alalım
        journalComboBox = new JComboBox<>(distributor.getJournals().values().toArray(new Journal[0]));
        journalComboBox.setRenderer(new JournalComboBoxRenderer());
        
        subscriberComboBox = new JComboBox<>(distributor.getSubscribers().toArray(new Subscriber[0]));
        subscriberComboBox.setRenderer(new SubscriberComboBoxRenderer());
        
        JButton saveButton = new JButton("Create Subscription");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Choose Journal:"), constraints);
        constraints.gridy = 1;
        panel.add(new JLabel("Choose Subscriber:"), constraints);
        constraints.gridy = 2;
        panel.add(new JLabel("Journal Copies:"), constraints);
        constraints.gridy = 3;
        panel.add(new JLabel("Subscription Start Month:"), constraints);
        constraints.gridy = 4;
        panel.add(new JLabel("Subscription Start Year:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(journalComboBox, constraints);
        constraints.gridy = 1;
        panel.add(subscriberComboBox, constraints);
        constraints.gridy = 2;
        panel.add(copiesField, constraints);
        constraints.gridy = 3;
        panel.add(startMonthField, constraints);
        constraints.gridy = 4;
        panel.add(startYearField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(saveButton, constraints);

        constraints.gridy = 6;
        panel.add(cancelButton, constraints);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Journal selectedJournal = (Journal) journalComboBox.getSelectedItem();
            	Subscriber selectedSubscriber = (Subscriber) subscriberComboBox.getSelectedItem();
            	
            	if (validateFields(copiesField,startMonthField,startYearField) && selectedJournal !=null && selectedSubscriber !=null) {
                	int copies = Integer.parseInt(copiesField.getText());
                	int startMonth = Integer.parseInt(startMonthField.getText());
                	int startYear = Integer.parseInt(startYearField.getText());

                	DateInfo date = new DateInfo(startMonth,startYear);
                	
                    Subscription newSubscription = new Subscription(date,copies,selectedJournal,selectedSubscriber);
                
                    distributor.addSubscription(selectedJournal.getIssn(), selectedSubscriber, newSubscription);
                    
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void showPaymentDialog() {
        JDialog dialog = new JDialog(this, "Payment Menu", true);
        
        JTextField paymentField = new JTextField(4);
        
     // Subscriptionları alalım
        Vector<Subscription> subscriptions = new Vector<>();
        Iterator<Map.Entry<String, Journal>> i = distributor.getJournals().entrySet().iterator();
		
		while(i.hasNext()) {
			Map.Entry<String, Journal> pair = (Map.Entry<String, Journal>) i.next();
			
			for(Subscription s : pair.getValue().getSubscriptions()) {
				subscriptions.add(s);
			}
		}
		
        subscriptionComboBox = new JComboBox<>(subscriptions.toArray(new Subscription[0]));
        subscriptionComboBox.setRenderer(new SubscriptionComboBoxRenderer());
        
        JButton payButton = new JButton("Increase Payment");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Choose Subscription:"), constraints);
        constraints.gridy = 1;
        panel.add(new JLabel("Enter Payment Amount:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(subscriptionComboBox, constraints);
        
        constraints.gridy = 1;
        panel.add(paymentField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(payButton, constraints);

        constraints.gridy = 6;
        panel.add(cancelButton, constraints);

        payButton.addActionListener(new ActionListener() {
            @Override
        public void actionPerformed(ActionEvent e) {
        	Subscription selectedSubscription = (Subscription) subscriptionComboBox.getSelectedItem();
        
        	if (validateFields(paymentField) && selectedSubscription !=null){

            	double amount = Double.parseDouble(paymentField.getText());
            	selectedSubscription.acceptPayment(amount);

            	
                JOptionPane.showMessageDialog(dialog, "Payment has been successfully made.", "Payment Success", JOptionPane.INFORMATION_MESSAGE ) ;
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    // Billing Info Menu
    private void showBillingInfoDialog() {
        JDialog dialog = new JDialog(this, "Billing Info Menu", true);

        subscriberComboBox = new JComboBox<>(distributor.getSubscribers().toArray(new Subscriber[0]));
        subscriberComboBox.setRenderer(new SubscriberComboBoxRenderer());
        
        JButton showButton = new JButton("Show Billing Info");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;

        panel.add(new JLabel("Choose Subscriber:"), constraints);
        constraints.gridy = 2;

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(subscriberComboBox, constraints);
 
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(showButton, constraints);

        constraints.gridy = 2;
        panel.add(cancelButton, constraints);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Subscriber selectedSubscriber = (Subscriber) subscriberComboBox.getSelectedItem();
            	
            	if ( selectedSubscriber !=null) {
                    JOptionPane.showMessageDialog(dialog, selectedSubscriber.getBillingInformation(), "Billing Info", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    // Box Rendererlar
    class JournalComboBoxRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Journal) {
                Journal journal = (Journal) value;
                setText(journal.getName());
            }

            return this;
        }
    }
    
    class SubscriberComboBoxRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Individual) {
            	Individual subscriber = (Individual) value;
                setText("Individual Subscriber: "+subscriber.getName());
            }else if (value instanceof Corporation) {
            	Corporation subscriber = (Corporation) value;
                setText("Corporation Subscriber: "+subscriber.getName());
            }

            return this;
        }
    }
    
    class SubscriptionComboBoxRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            Subscription subscription = (Subscription) value;
            if (subscription.getSubscriber() instanceof Individual) {
                setText("Individual Subscriber: "+subscription.getSubscriber().getName()+"-"+subscription.getJournal().getName());
            }else if (subscription.getSubscriber() instanceof Corporation) {
                setText("Corporation Subscriber: "+subscription.getSubscriber().getName()+"-"+subscription.getJournal().getName());
            }

            return this;
        }
    }
    
    // General Operations Menu
    private void showGeneralOperationsMenu() {
        JDialog dialog = new JDialog(this, "General Operations Menu", true);

        JButton listAllSendingOrdersButton = new JButton("List All Sending Orders");
        JButton listIncompletePaymentsButton = new JButton("List Incomplete Payments");
        JButton listSubscriptionsNameButton = new JButton("List Subscription By Name");
        JButton listSubscriptionsISSNButton = new JButton("List Subscription By Journal");
        JButton showPaymentButton = new JButton("Report: Total Received Payments");
        JButton showExpiredSubscriptionButton = new JButton("Report: Expired Subscriptions");
        JButton saveStateButton = new JButton("Save State");
        JButton loadStateButton = new JButton("Load State");
        JButton backToMenuButton = new JButton("Back to Menu");
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(listAllSendingOrdersButton, constraints);

        constraints.gridy = 1;
        panel.add(listIncompletePaymentsButton, constraints);
        
        constraints.gridy = 2;
        panel.add(listSubscriptionsNameButton, constraints);
        
        constraints.gridy = 3;
        panel.add(listSubscriptionsISSNButton, constraints);
        
        constraints.gridy = 4;
        panel.add(showPaymentButton, constraints);
        
        constraints.gridy = 5;
        panel.add(showExpiredSubscriptionButton, constraints);
        
        constraints.gridy = 6;
        panel.add(saveStateButton, constraints);
        
        constraints.gridy = 7;
        panel.add(loadStateButton, constraints);
        
        
        constraints.gridy = 8;
        panel.add(backToMenuButton, constraints);
        
        listAllSendingOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showAllSendingOrdersMenu();
            }
        });

        listIncompletePaymentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showIncompletePayments();
            }
        });
        
        listSubscriptionsNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showSubscribersByNameMenu();
            }
        });
        
        listSubscriptionsISSNButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showSubscribersByISSNMenu();
            }
        });
        
        showPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ReceivedPaymentsMenu();
            }
        });
        
        showExpiredSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ExpiredSubscriptionMenu();
            }
        });
        
        saveStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	saveStateMenu();
            }
        });
        
        loadStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	loadStateMenu();
            }
        });
        
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); 
            }
        });

        dialog.add(panel);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void showAllSendingOrdersMenu() {
    	JDialog dialog = new JDialog(this, "All Sending Orders", true);
        
        JTextField monthField = new JTextField(4);
        JTextField yearField = new JTextField(4);
       
        JButton showButton = new JButton("Show");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Month:"), constraints);
        constraints.gridy = 1;
        panel.add(new JLabel("Year:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(monthField, constraints);
        constraints.gridy = 1;
        panel.add(yearField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(showButton, constraints);
        constraints.gridy = 3;
        panel.add(cancelButton, constraints);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(monthField,yearField)) {
                	
                	int month = Integer.parseInt(monthField.getText());
                	int year = Integer.parseInt(yearField.getText());

                	showAllSendingOrders(month,year);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
}
    
    private void showAllSendingOrders(int month,int year) {
        String message = distributor.listAllSendingOrders(month, year);
    	
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(message);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "All Sending Orders", JOptionPane.PLAIN_MESSAGE);
    }   
    
    private void showIncompletePayments() {
        String message = distributor.listIncompletePayments();
    	
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(message);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "All Incomplete Payments", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void showSubscribersByNameMenu() {
    	JDialog dialog = new JDialog(this, "Show Subscribers", true);
        
        JTextField nameField = new JTextField(4);
       
        JButton showButton = new JButton("List");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Subscriber Name:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(showButton, constraints);
        constraints.gridy = 2;
        panel.add(cancelButton, constraints);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(nameField)) {
                	
                	String name = nameField.getText();

                	showSubscriptionsByName(name);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
} 
    
    private void showSubscriptionsByName(String name) {
        String message = distributor.listSubscriptionsByName(name);
    	
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(message);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "List Subscriptions By Name", JOptionPane.PLAIN_MESSAGE);
    } 
    
    private void showSubscribersByISSNMenu() {
    	JDialog dialog = new JDialog(this, "Show Subscriptions", true);
        
        JTextField issnField = new JTextField(4);
       
        JButton showButton = new JButton("List");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Journal ID (ISSN):"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(issnField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(showButton, constraints);
        constraints.gridy = 2;
        panel.add(cancelButton, constraints);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(issnField)) {
                	
                	String issn = issnField.getText();

                	showSubscriptionsByISSN(issn);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
} 
    
    private void showSubscriptionsByISSN(String ISSN) {
        String message = distributor.listSubscriptionsByJournal(ISSN);
    	
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setText(message);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "List Subscriptions By ISSN", JOptionPane.PLAIN_MESSAGE);
    } 
    
    // Report 
    private void ExpiredSubscriptionMenu() {
    	JDialog dialog = new JDialog(this, "Report Menu: Expired Subscriptions", true);
        
        JTextField monthField = new JTextField(4);
        JTextField yearField = new JTextField(4);
        
        JButton showButton = new JButton("Show");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Ending Month:"), constraints);
        
        constraints.gridy = 1;
        panel.add(new JLabel("Ending Year:"), constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(monthField, constraints);
        
        constraints.gridy = 1;
        panel.add(yearField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(showButton, constraints);
        constraints.gridy = 3;
        panel.add(cancelButton, constraints);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(monthField,yearField)) {
                	int month = Integer.parseInt(monthField.getText());
                	int year = Integer.parseInt(yearField.getText());
                	
                	distributor.setParam1(month);
                	distributor.setParam2(year);
                	distributor.setProcess(1);
                	
                	Thread myThread = new Thread(distributor);
                	myThread.start();
                	
                	try {
                	    myThread.join();
                	} catch (InterruptedException error) {
                	    error.printStackTrace();
                	}
                	
                	String message = distributor.getReportMessage();
                	JTextArea textArea = new JTextArea(20, 40);
                    textArea.setText(message);
                    textArea.setEditable(false);

                    JScrollPane scrollPane = new JScrollPane(textArea);

                    JOptionPane.showMessageDialog(null, scrollPane, "All Expired Subscriptions", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
}
    //report 2
    private void ReceivedPaymentsMenu() {
    	JDialog dialog = new JDialog(this, "Report Menu: Received Payments", true);
        
        JTextField startYearField = new JTextField(4);
        JTextField endYearField = new JTextField(4);
        
        JButton showButton = new JButton("Show");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Starting Year:"), constraints);
        
        constraints.gridy = 1;
        panel.add(new JLabel("Ending Year:"), constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(startYearField, constraints);
        
        constraints.gridy = 1;
        panel.add(endYearField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(showButton, constraints);
        constraints.gridy = 3;
        panel.add(cancelButton, constraints);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(startYearField,endYearField)) {
                	int startYear = Integer.parseInt(startYearField.getText());
                	int endYear = Integer.parseInt(endYearField.getText());
                	
                	distributor.setParam1(startYear);
                	distributor.setParam2(endYear);
                	distributor.setProcess(2);
                	
                	Thread myThread = new Thread(distributor);
                	myThread.start();
                	
                	try {
                	    myThread.join();
                	} catch (InterruptedException error) {
                	    error.printStackTrace();
                	}
                	
                	String message = distributor.getReportMessage();
                	JTextArea textArea = new JTextArea(20, 40);
                    textArea.setText(message);
                    textArea.setEditable(false);

                    JScrollPane scrollPane = new JScrollPane(textArea);

                    JOptionPane.showMessageDialog(null, scrollPane, "All Expired Subscriptions", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
}
    
    
    // state
    private void saveStateMenu() {
    	JDialog dialog = new JDialog(this, "Save State", true);
        
        JTextField nameField = new JTextField(30);
       
        JButton showButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("File Name (sample.txt):"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(showButton, constraints);
        constraints.gridy = 2;
        panel.add(cancelButton, constraints);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(nameField)) {
                	String name = nameField.getText();
                	String reportMessage = distributor.saveState(name);
                	fileReport(reportMessage);
                	dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
}
    
    private void loadStateMenu() {
    	JDialog dialog = new JDialog(this, "Load State", true);
        
        JTextField nameField = new JTextField(30);
       
        JButton showButton = new JButton("Read");
        JButton cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("File Name (sample.txt):"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(showButton, constraints);
        constraints.gridy = 2;
        panel.add(cancelButton, constraints);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields(nameField)) {
                	
                	String name = nameField.getText();
                	String reportMessage = distributor.loadState(name);
                	fileReport(reportMessage);
                	dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
    } 
    
    private void fileReport(String text) {
        StringBuilder message = new StringBuilder(text);
        JOptionPane.showMessageDialog(this, message.toString(), "File Operations FeedBack", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProjectGUI().setVisible(true);
            }
        });
    }
}
