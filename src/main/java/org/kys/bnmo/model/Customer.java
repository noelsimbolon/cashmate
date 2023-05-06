package org.kys.bnmo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable {

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

    public String getMemberLevel() {
        return "Customer";
    }
}
