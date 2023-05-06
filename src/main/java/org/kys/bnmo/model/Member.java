package org.kys.bnmo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    @NotNull
    private String memberLevel; // "Member" or "VIP"

    public Member(int customerID, @NotNull String name, @NotNull String phoneNumber, @NotNull String memberLevel) {
        super(customerID);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = 0;
        this.status = "Active";
        this.memberLevel = memberLevel;
    }

    public Member(@NotNull String name, @NotNull String phoneNumber, @NotNull String memberLevel) {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.points = 0;
        this.status = "Active";
        this.memberLevel = memberLevel;
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

    public @NotNull String getMemberLevel() {
        return memberLevel;
    }

    private void promote() {
        if (memberLevel.equals("Member")) {
            memberLevel = "VIP";
        }
    }

    private void demote() {
        if (memberLevel.equals("VIP")) {
            memberLevel = "Member";
        }
    }
}
