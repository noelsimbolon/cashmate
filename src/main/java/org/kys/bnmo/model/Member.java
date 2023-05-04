package org.kys.bnmo.model;

import java.util.UUID;

// import lombok.NonNull;

public class Member extends Customer {
    // @NonNull
    private String name;
    // @NonNull
    private String phoneNumber;
    private int points;
    // @NonNull
    private String status; // "Active" or "Inactive"

    public Member (UUID customerID, String name, String phoneNumber) {
        super(customerID);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = 0;
        this.status = "Active";
    }

    public Member(String name, String phoneNumber) {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = 0;
        this.status = "Active";
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public int getPoints() {
        return this.points;
    }

    public String getStatus() {
        return this.status;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void deductPoints(int points) {
        this.points -= points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void activate() {
        if (this.status.equals("Inactive")) {
            this.status = "Active";
        }

        // TODO: throw exception if status is already active
    }

    public void deactivate() {
        if (this.status.equals("Active")) {
            this.status = "Inactive";
        }

        // TODO: throw exception if status is already inactive
    }

    @Override
    public String getCustomerClass() {
        return "Member";
    }

    private VIP promote() {
        return new VIP(this.getCustomerID(), this.name, this.phoneNumber);
    }
}
