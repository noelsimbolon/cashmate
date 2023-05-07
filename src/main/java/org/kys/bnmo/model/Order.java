package org.kys.bnmo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;

import java.util.UUID;

public class Order {
    @Getter
    private int orderID;

    private static int orderCount = 0;

    @Getter
    private InventoryItem item;
    @Getter
    private int quantity;

    public Order(InventoryItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.orderID = orderCount;
        orderCount++;
    }

    public Order(int orderID,InventoryItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.orderID = orderID;
    }
}
