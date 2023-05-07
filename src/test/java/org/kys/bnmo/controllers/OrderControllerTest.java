package org.kys.bnmo.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Order;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class OrderControllerTest {

    private static OrderController orderController;
    private static InventoryItemController inventoryItemController;

    @BeforeAll
    static void setUp() throws IOException {
        DataStore dataStore = new DataStore();
        String dataStoreTestFolderPath = (new File("src\\test\\data")).getAbsolutePath();
        dataStore.setFolderPath(dataStoreTestFolderPath, false);
        dataStore.setFileFormat("json", false);

        orderController = new OrderController();
        inventoryItemController = new InventoryItemController();
    }

    @Test
    void fetchAll() {
        // Arrange
        var orders = new ArrayList<Order>();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        var order1 = new Order(item1, 20000.0, 10);
        var order2 = new Order(item2, 20000.0, 5);
        orders.add(order1);
        orders.add(order2);

        // Act
        orderController.save(orders);
        ArrayList<Order> fetchedOrders = orderController.fetchAll();

        // Asert
        Assertions.assertEquals(order1.getOrderID(), fetchedOrders.get(0).getOrderID());
        Assertions.assertEquals(order2.getOrderID(), fetchedOrders.get(1).getOrderID());
    }

    @Test
    void fetchByID() {
        // Arrange
        var orders = new ArrayList<Order>();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        var order1 = new Order(item1, 20000.0, 10);
        var order2 = new Order(item2, 20000.0, 5);
        orders.add(order1);
        orders.add(order2);

        // Act
        orderController.save(orders);
        ArrayList<Order> fetchedOrders = orderController.fetchByID(order2.getOrderID());

        // Asert
        Assertions.assertEquals(order2.getOrderID(), fetchedOrders.get(0).getOrderID());
    }

    @Test
    void save() {
        // Arrange
        var orders = new ArrayList<Order>();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        var order1 = new Order(item1, 20000.0, 10);
        var order2 = new Order(item2, 20000.0, 5);
        orders.add(order1);
        orders.add(order2);

        // Act
        orderController.save(orders);
        ArrayList<Order> fetchedOrders = orderController.fetchAll();

        // Asert
        Assertions.assertEquals(order1.getOrderID(), fetchedOrders.get(0).getOrderID());
        Assertions.assertEquals(order2.getOrderID(), fetchedOrders.get(1).getOrderID());
    }

    @Test
    void deleteById() {
        // Arrange
        var orders = new ArrayList<Order>();
        var items = new ArrayList<InventoryItem>();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        var order1 = new Order(item1, 20000.0, 10);
        var order2 = new Order(item2, 20000.0, 5);
        orders.add(order1);
        orders.add(order2);
        items.add(item1);
        items.add(item2);

        // Act
        inventoryItemController.writeInventoryItems(items);
        orderController.save(orders);
        orderController.deleteById(order2.getOrderID());
        ArrayList<Order> fetchedOrders = orderController.fetchAll();

        // Asert
        Assertions.assertEquals(order1.getOrderID(), fetchedOrders.get(0).getOrderID());
    }
}
