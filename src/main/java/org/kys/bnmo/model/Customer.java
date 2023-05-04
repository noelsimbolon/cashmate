package org.kys.bnmo.model;

import java.util.ArrayList;
import java.util.UUID;

public class Customer {
    private final UUID customerID;
    
    // Transaction history
    private ArrayList<Transaction> transactionHistory;

    public Customer() {
        // Set random id
        this.customerID = UUID.randomUUID();
    }

    public Customer(UUID customerID) {
        // Set predefined id
        this.customerID = customerID;
    }

    public UUID getCustomerID() {
        return this.customerID;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return this.transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }

    private Member applyMembership(String name, String phoneNumber) {
        return new Member(name, phoneNumber);

        // Warning: this will assign new ID to the customer
    }
}
