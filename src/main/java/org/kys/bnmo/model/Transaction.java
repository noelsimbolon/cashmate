package org.kys.bnmo.model;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    private final UUID transactionID;
    private Customer customer;
    private InventoryItem item;
    private int quantity;
    private int totalPrice;
    private Date date;

    public Transaction(Customer customer, InventoryItem item, int quantity, int totalPrice, Date date) {
        this.transactionID = UUID.randomUUID();
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    // Getters
    public UUID getTransactionID() {
        return transactionID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public InventoryItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Date getDate() {
        return date;
    }

}
