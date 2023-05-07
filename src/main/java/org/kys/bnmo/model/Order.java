package org.kys.bnmo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;

import java.util.UUID;

public class Order {
    @Getter
    private UUID orderID;

    private static int orderCount = 0;

    @Getter
    private InventoryItem item;
    @Getter
    private int quantity;
    @Getter
    private int purchasePrice;

    public Order(InventoryItem item, int purchasePrice, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.orderID = UUID.randomUUID();
        this.purchasePrice = purchasePrice;
    }

    public Order(UUID orderID, InventoryItem item, int purchasePrice, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.orderID = orderID;
        this.purchasePrice = purchasePrice;
    }
}
