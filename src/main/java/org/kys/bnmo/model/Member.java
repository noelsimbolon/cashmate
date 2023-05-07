package org.kys.bnmo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

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

    public Member() {
        this("", "", "");
    }

    public Member(UUID customerID, @NotNull String name, @NotNull String phoneNumber, @NotNull String memberLevel) {
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

    public void setPoints(int points) {
        this.points = points;
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
    }

    public void deactivate() {
        if (this.status.equals("Active")) {
            this.status = "Inactive";
        }
    }

    public @NotNull String getMemberLevel() {
        return memberLevel;
    }

    public void promote() {
        if (memberLevel.equals("Member")) {
            memberLevel = "VIP";
        }
    }

    public void demote() {
        if (memberLevel.equals("VIP")) {
            memberLevel = "Member";
        }
    }
}
