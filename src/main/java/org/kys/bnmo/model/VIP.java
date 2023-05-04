package org.kys.bnmo.model;

import java.util.UUID;

public class VIP extends Member {

    public VIP(int customerID, String name, String phoneNumber) {
        super(customerID, name, phoneNumber);
    }
    public VIP(String name, String phoneNumber) {
        super(name, phoneNumber);
    }

    @Override
    public String getCustomerClass() {
        return "VIP";
    }

    public double getDiscount() {
        return 0.1;
    }
}