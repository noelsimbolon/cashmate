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
    private Double purchasePrice;

    public Order() {
        this(null, 0.0, 0);
    }

    public Order(InventoryItem item, Double purchasePrice, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.orderID = UUID.randomUUID();
        this.purchasePrice = purchasePrice;
    }

    public Order(UUID orderID, InventoryItem item, Double purchasePrice, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.orderID = orderID;
        this.purchasePrice = purchasePrice;
    }
}
