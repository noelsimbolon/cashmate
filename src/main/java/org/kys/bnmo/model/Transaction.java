package org.kys.bnmo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Transaction implements Serializable {

    @Getter
    @Setter
    private final int transactionID;

    private static int transactionCount = 0;

    @Getter
    @Setter
    private Customer customer;

    @Getter
    @Setter
    private Double totalPrice;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private List<Order> orders;

    @Getter
    @Setter
    private int discount;

    public Transaction(Customer customer, List<Order> orders, double totalPrice, Date date, int discount) {
        this.orders = orders;
        this.discount = discount;
        this.transactionID = transactionCount;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.date = date;
        transactionCount++;
    }

    public Transaction(int transactionID, Customer customer, List<Order> orders, double totalPrice, Date date, int discount) {
        this.orders = orders;
        this.discount = discount;
        this.transactionID = transactionID;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.date = date;
    }
}
