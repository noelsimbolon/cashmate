package org.kys.bnmo.model;

import lombok.Getter;

import java.util.UUID;

public class UnpopulatedOrder {
    @Getter
    private int orderID;

    @Getter
    private UUID itemID;
    @Getter
    private int quantity;
    @Getter
    private int purchasePrice;

    public UnpopulatedOrder() {
        this.orderID = 0;
        this.itemID = UUID.randomUUID();
        this.quantity = 0;
        this.purchasePrice = 0;
    }

    public UnpopulatedOrder(int orderID, UUID itemID, int purchasePrice, int quantity) {
        this.orderID = orderID;
        this.itemID = itemID;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
    }
}
