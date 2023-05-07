package org.kys.bnmo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;

public class Order {
    @Getter
    private InventoryItem item;
    @Getter
    private int quantity;

    public Order(InventoryItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
