package org.kys.bnmo.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Transaction implements Serializable {

    @Getter
    private final UUID transactionID;

    @Getter
    private Customer customer;

    @Getter
    private InventoryItem item;

    @Getter
    private int quantity;

    @Getter
    private Double totalPrice;

    @Getter
    private Date date;


    public Transaction() {
        this(new Customer(), new InventoryItem(), 0, 0, new Date());
    }

    public Transaction(Customer customer, InventoryItem item, int quantity, int totalPrice, Date date) {
        this.transactionID = UUID.randomUUID();
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
    }
}
