package org.kys.bnmo.model;

import org.jetbrains.annotations.Nullable;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Transaction;

import java.util.List;

public class Modifiable {

    public class TotalPrice {
        public double amount;
        public boolean prioritize;
        TotalPrice(double amount, boolean prioritize)
        {
            this.amount = amount;
            this.prioritize = prioritize;
        }

    }
    @Nullable
    public List<Transaction> transactions;
    @Nullable
    public List<InventoryItem> inventoryItems;
    @Nullable
    public List<Order> orders;
    @Nullable
    public TotalPrice totalPrice;
    public boolean get;
    public Modifiable(
            @Nullable List<Transaction> transactions,
            @Nullable List<InventoryItem> inventoryItems,
            @Nullable List<Order> orders,
            boolean get
            )
    {
        this.transactions = transactions;
        this.inventoryItems = inventoryItems;
        this.orders = orders;
        this.get = get;
        this.totalPrice = null;
    }

    public Modifiable(
            double total, boolean prioritize
    )
    {
        this.transactions = null;
        this.inventoryItems = null;
        this.orders = null;
        this.get = false;
        this.totalPrice = new TotalPrice(total, prioritize);
    }
}
