package org.kys.bnmo.model;

import java.util.UUID;

public class Customer {
    private UUID customerID;

    public Customer() {
        // Set random id
        this.customerID = UUID.randomUUID();
    }

    public Customer(UUID customerID) {
        this.customerID = customerID;
    }

    public UUID getCustomerID() {
        return this.customerID;
    }

    private Member applyMembership(String name, String phoneNumber) {
        return new Member(name, phoneNumber);

        // Warning: this will assign new ID to the customer
    }
}
