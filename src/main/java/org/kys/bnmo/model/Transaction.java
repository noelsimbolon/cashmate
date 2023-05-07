package org.kys.bnmo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Transaction implements Serializable {

    @Getter
    @Setter
    private final UUID transactionID;

    @Getter
    @Setter
    private Customer customer;

    @Getter
    @Setter
    private InventoryItem item;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private Double totalPrice;

    @Getter
    @Setter
    private Date date;


    public Transaction() {
        this(new Customer(), new InventoryItem(), 0, 0, new Date());
    }

    public Transaction(Customer customer, InventoryItem item, int quantity, double totalPrice, Date date) {
        this.transactionID = UUID.randomUUID();
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
    }
}
