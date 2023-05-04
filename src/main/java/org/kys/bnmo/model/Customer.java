package org.kys.bnmo.model;

import java.util.ArrayList;
import java.util.UUID;

public class Customer {
    // Customer count
    public static int customerCount = 0;
    private final int customerID;
    
    // Transaction history
    private ArrayList<Transaction> transactionHistory;

    public Customer() {
        // Set incremental id
        customerCount++;
        this.customerID = customerCount;
    }

    public Customer(int customerID) {
        // Set predefined id
        this.customerID = customerID;
    }

    public int getCustomerID() {
        return this.customerID;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return this.transactionHistory;
    }

    public String getCustomerClass() {
        return "Customer";
    }

    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }

    private Member applyMembership(String name, String phoneNumber) {
        return new Member(this.customerID, name, phoneNumber);
    }
}
