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
    private String itemName;

    @Getter
    @NotNull
    private String category;

    @Getter
    @NotNull
    private Integer stock;

    @Getter
    @NotNull
    private Integer price;

    @Getter
    @NotNull
    private Integer purchasePrice;

    @Getter
    @NotNull
    private String imageFileName;

    public InventoryItem() {
        this("", "", 0, 0, 0, "");
    }

    public InventoryItem(@NotNull String itemName, @NotNull String category, int stock, int price, int purchasePrice, @NotNull String imageFileName) {
        this.itemID = UUID.randomUUID();
        this.itemName = itemName;
        this.category = category;
        this.stock = stock;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.imageFileName = imageFileName;
    }

    public void setItemName(@NotNull(value = "Item name cannot be null.") String itemName) {
        this.itemName = itemName;
    }

    public void setCategory(@NotNull(value = "Category cannot be null.") String category) {
        this.category = category;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }

        this.stock = stock;
    }

    public void addStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }

        this.stock += stock;
    }

    public void removeStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }

        this.stock -= stock;
    }

    public void setPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void setPurchasePrice(int purchasePrice) {
        if (purchasePrice < 0) {
            throw new IllegalArgumentException("Purchase price cannot be negative.");
        }
        this.purchasePrice = purchasePrice;
    }

    public void setImageFileName(@NotNull(value = "Image cannot be null.") String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
