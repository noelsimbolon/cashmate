package org.kys.bnmo.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class InventoryItem implements Serializable {

    @Getter
    @NotNull
    private final UUID itemID;

    @Getter
    @NotNull
    private Integer stock;

    @Getter
    @NotNull
    private String itemName;

    @Getter
    @NotNull
    private Integer price;

    @Getter
    @NotNull
    private String category;

    @Getter
    @NotNull
    private String imageFileName;

    public InventoryItem() {
        this(0, "", 0, "", "");
    }

    public InventoryItem(int stock, @NotNull String itemName, int price, @NotNull String category, @NotNull String imageFileName) {
        this.itemID = UUID.randomUUID();
        this.stock = stock;
        this.itemName = itemName;
        this.price = price;
        this.category = category;
        this.imageFileName = imageFileName;
    }

    // Getters and Setters
    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        this.stock = stock;
    }

    public void addStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        this.stock += stock;
    }

    public void removeStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        this.stock -= stock;
    }

    public void setItemName(@NotNull(value = "Item name cannot be null.") String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setCategory(@NotNull(value = "Category cannot be null.") String category) {
        this.category = category;
    }

    public void setImageFileName(@NotNull(value = "Image cannot be null.") String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
