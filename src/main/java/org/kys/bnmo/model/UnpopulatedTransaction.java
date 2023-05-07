package org.kys.bnmo.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UnpopulatedTransaction {
    @Getter
    private final int transactionID;

    @Getter
    private int customerID;

    @Getter
    private Double totalPrice;

    @Getter
    private Date date;

    @Getter
    private List<Integer> orderIDs;

    @Getter
    private int discount;

    public UnpopulatedTransaction() {
        this(0, 0, new ArrayList<>(), 0, new Date(), 0);
    }

    public UnpopulatedTransaction(int transactionID, int customerID, List<Integer> orderIDs, double totalPrice, Date date, int discount) {
        this.transactionID = transactionID;
        this.customerID = customerID;
        this.discount = discount;
        this.orderIDs = new ArrayList<>(orderIDs);
        this.totalPrice = totalPrice;
        this.date = date;
    }
}
