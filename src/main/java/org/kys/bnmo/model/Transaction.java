package org.kys.bnmo.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Transaction implements Serializable {

    @Getter
    private final int transactionID;

    private static int transactionCount = 0;

    @Getter
    private Customer customer;

    @Getter
    private Double totalPrice;

    @Getter
    private Date date;

    @Getter
    private List<Order> orders;

    @Getter
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
