package org.kys.bnmo.model;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class Customer {

    @Getter
    private final int customerID;

    // Customer count
    public static int customerCount = 0;

    public Customer() {
        // Set incremental id
        customerCount++;
        this.customerID = customerCount;
    }

    public Customer(int customerID) {
        // Set predefined id
        this.customerID = customerID;
    }

    public String getCustomerClass() {
        return "Customer";
    }

    @NotNull
    private Member applyMembership(String name, String phoneNumber) {
        return new Member(this.customerID, name, phoneNumber);
    }
}
