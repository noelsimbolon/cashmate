package org.kys.bnmo.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Order;
import org.kys.bnmo.model.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

class TransactionControllerTest {

    private static TransactionController transactionController;
    private static OrderController orderController;
    private static CustomerController customerController;
    private static MemberController memberController;
    private static InventoryItemController inventoryItemController;

    @BeforeAll
    static void setUp() throws IOException {
        DataStore dataStore = new DataStore();
        String dataStoreTestFolderPath = (new File("src\\test\\data")).getAbsolutePath();
        dataStore.setFolderPath(dataStoreTestFolderPath, false);
        dataStore.setFileFormat("json", false);

        transactionController = new TransactionController();
        orderController = new OrderController();
        customerController = new CustomerController();
        memberController = new MemberController();
        inventoryItemController = new InventoryItemController();
    }

    @Test
    void fetchAllSave() {
        // Arrange
        var transactions = new ArrayList<Transaction>();
        var orders = new ArrayList<Order>();
        var items = new ArrayList<InventoryItem>();
        var customers = new ArrayList<Customer>();
        var customer = new Customer();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        var order1 = new Order(item1, 20000.0, 10);
        var order2 = new Order(item2, 20000.0, 5);
        orders.add(order1);
        orders.add(order2);
        items.add(item1);
        items.add(item2);
        customers.add(customer);
        var transaction = new Transaction(customer, orders, 25000.0, new Date(), 5000);
        transactions.add(transaction);

        // Act
        customerController.save(customers);
        orderController.save(orders);
        inventoryItemController.writeInventoryItems(items);
        transactionController.save(transactions);
        ArrayList<Transaction> fetchedTransactions = transactionController.fetchAll();

        // Assert
        Assertions.assertEquals(transaction.getTransactionID(), fetchedTransactions.get(0).getTransactionID());
    }

    @Test
    void fetchByID() {
        // Arrange
        var transactions = new ArrayList<Transaction>();
        var orders = new ArrayList<Order>();
        var items = new ArrayList<InventoryItem>();
        var customers = new ArrayList<Customer>();
        var customer = new Customer();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        var order1 = new Order(item1, 20000.0, 10);
        var order2 = new Order(item2, 20000.0, 5);
        orders.add(order1);
        orders.add(order2);
        items.add(item1);
        items.add(item2);
        customers.add(customer);
        var transaction = new Transaction(customer, orders, 25000.0, new Date(), 5000);
        transactions.add(transaction);

        // Act
        customerController.save(customers);
        orderController.save(orders);
        inventoryItemController.writeInventoryItems(items);
        transactionController.save(transactions);
        ArrayList<Transaction> fetchedTransactions = transactionController.fetchByID(transaction.getTransactionID());

        // Assert
        Assertions.assertEquals(transaction.getTransactionID(), fetchedTransactions.get(0).getTransactionID());
    }

    @Test
    void fetchByCustomerID() {
        // Arrange
        var transactions = new ArrayList<Transaction>();
        var orders = new ArrayList<Order>();
        var items = new ArrayList<InventoryItem>();
        var customers = new ArrayList<Customer>();
        var customer = new Customer();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        var order1 = new Order(item1, 20000.0, 10);
        var order2 = new Order(item2, 20000.0, 5);
        orders.add(order1);
        orders.add(order2);
        items.add(item1);
        items.add(item2);
        customers.add(customer);
        var transaction = new Transaction(customer, orders, 25000.0, new Date(), 5000);
        transactions.add(transaction);

        // Act
        customerController.save(customers);
        orderController.save(orders);
        inventoryItemController.writeInventoryItems(items);
        transactionController.save(transactions);
        ArrayList<Transaction> fetchedTransactions = transactionController.fetchByCustomerID(customer.getCustomerID());

        // Assert
        Assertions.assertEquals(transaction.getTransactionID(), fetchedTransactions.get(0).getTransactionID());
    }

    @Test
    void deleteById() {
        // Arrange
        var transactions = new ArrayList<Transaction>();
        var orders = new ArrayList<Order>();
        var items = new ArrayList<InventoryItem>();
        var customers = new ArrayList<Customer>();
        var customer = new Customer();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        var order1 = new Order(item1, 20000.0, 10);
        var order2 = new Order(item2, 20000.0, 5);
        orders.add(order1);
        orders.add(order2);
        items.add(item1);
        items.add(item2);
        customers.add(customer);
        var transaction = new Transaction(customer, orders, 25000.0, new Date(), 5000);
        transactions.add(transaction);

        // Act
        customerController.save(customers);
        orderController.save(orders);
        inventoryItemController.writeInventoryItems(items);
        transactionController.save(transactions);
        transactionController.deleteById(transaction.getTransactionID());
        ArrayList<Transaction> fetchedTransactions = transactionController.fetchAll();

        // Assert
        assert fetchedTransactions.size() == 0;
    }
}