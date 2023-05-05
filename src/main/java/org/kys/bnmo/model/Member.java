package org.kys.bnmo.model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class Member extends Customer {

    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    @NotNull
    private String phoneNumber;

    @Getter
    private int points;

    @Getter
    @NotNull
    private String status; // "Active" or "Inactive"

    public Member(int customerID, @NotNull String name, @NotNull String phoneNumber) {
        super(customerID);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = 0;
        this.status = "Active";
    }

    public Member(@NotNull String name, @NotNull String phoneNumber) {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = 0;
        this.status = "Active";
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void deductPoints(int points) {
        this.points -= points;
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

    @NotNull
    private VIP promote() {
        return new VIP(this.getCustomerID(), this.name, this.phoneNumber);
    }
}
