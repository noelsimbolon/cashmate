package org.kys.bnmo.model;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class Customer {

    @Getter
    @NotNull
    private final UUID customerID;
    
    @Getter
    private ArrayList<Transaction> transactionHistory;

    public Customer() {
        // Set random id
        this.customerID = UUID.randomUUID();
    }

    public Customer(UUID customerID) {
        // Set predefined id
        this.customerID = customerID;
    }

    public String getCustomerClass() {
        return "Customer";
    }

    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }

    @NotNull
    private Member applyMembership(String name, String phoneNumber) {
        return new Member(this.customerID, name, phoneNumber);
    }
}
