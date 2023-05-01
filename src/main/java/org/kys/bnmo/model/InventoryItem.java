package org.kys.bnmo.model;

import java.util.UUID;
import javafx.scene.image.Image;

public class InventoryItem {
    private final UUID itemID;
    private int stock;
    private String itemName;
    private int price;
    private String category;
    private Image image;

    public InventoryItem(int stock, String itemName, int price, String category, Image image) {
        this.itemID = UUID.randomUUID();
        this.stock = stock;
        this.itemName = itemName;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    // Getters and Setters
    public UUID getItemID() {
        return itemID;
    }

    public int getStock() {
        return stock;
    }

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        if (itemName == null) {
            throw new IllegalArgumentException("Item name cannot be null");
        }
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        this.category = category;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        this.image = image;
    }
}
