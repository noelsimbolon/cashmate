package org.kys.bnmo.model;

public class VIP extends Member {
    private final double DISCOUNT_RATE = 0.1;

    public VIP(String name, String phoneNumber) {
        super(name, phoneNumber);
    }

    public double getDiscount() {
        return DISCOUNT_RATE;
    }

    private Member demote() {
        return new Member(this.getName(), this.getPhoneNumber());
    }
}