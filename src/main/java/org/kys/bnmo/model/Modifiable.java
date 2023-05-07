package org.kys.bnmo.model;

import org.jetbrains.annotations.Nullable;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Transaction;

import java.util.List;

public class Modifiable {

    public List<Transaction> transactions;
    public List<InventoryItem> inventoryItems;
    public boolean get;
    public Modifiable(
            @Nullable List<Transaction> transactions,
            @Nullable List<InventoryItem> inventoryItems,
            boolean get
            )
    {
        this.transactions = transactions;
        this.inventoryItems = inventoryItems;
        this.get = get;
    }
}
