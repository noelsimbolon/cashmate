package org.kys.bnmo.model;

import java.util.UUID;

public class VIP extends Member {
    private final double DISCOUNT_RATE = 0.1;

    public VIP(UUID customerID, String name, String phoneNumber) {
        super(customerID, name, phoneNumber);
    }
    public VIP(String name, String phoneNumber) {
        super(name, phoneNumber);
    }

    public double getDiscount() {
        return DISCOUNT_RATE;
    }
}