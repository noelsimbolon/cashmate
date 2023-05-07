package org.kys.bnmo.model;

import lombok.Getter;

import java.util.UUID;

public class UnpopulatedOrder {
    @Getter
    private UUID orderID;

    @Getter
    private UUID itemID;
    @Getter
    private int quantity;
    @Getter
    private Double purchasePrice;

    public UnpopulatedOrder() {
        this.orderID = UUID.randomUUID();
        this.itemID = UUID.randomUUID();
        this.quantity = 0;
        this.purchasePrice = 0.0;
    }

    public UnpopulatedOrder(UUID orderID, UUID itemID, Double purchasePrice, int quantity) {
        this.orderID = orderID;
        this.itemID = itemID;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
    }
}
