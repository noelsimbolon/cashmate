package org.kys.bnmo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Order {
    @Getter
    private UUID orderID;

    private static int orderCount = 0;

    @Getter
    @Setter
    private InventoryItem item;
    @Getter
    @Setter
    private int quantity;
    @Getter
    @Setter
    private double purchasePrice;

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
