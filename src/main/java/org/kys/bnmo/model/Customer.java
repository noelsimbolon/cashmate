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
    private final UUID customerID;

    public Customer() {
        this.customerID = UUID.randomUUID();
    }

    public Customer(UUID customerID) {
        // Set predefined id
        this.customerID = customerID;
    }

    public String getMemberLevel() {
        return "Customer";
    }
}
