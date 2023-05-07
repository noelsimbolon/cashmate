package org.kys.bnmo;

import org.kys.bnmo.controllers.DataStore;
import org.kys.bnmo.controllers.TransactionController;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Order;
import org.kys.bnmo.model.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataStoreDriver {
    public static void main(String[] args) {
        DataStore dataStore = new DataStore();

        try {
            // Load and Set Folder Path
            // Work
            dataStore.loadConfig();
            String dataTestFolderPath = (new File("test\\data")).getAbsolutePath();
            dataStore.setFolderPath(dataTestFolderPath, false);

            // Read and Write Image
            // Work
            // BufferedImage image = dataStore.readImage(filename);
            // dataStore.writeImage(filename, image);

            // TODO: Write and Read Data
            // Inventory Item
            // Work
            ArrayList<InventoryItem> items = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                items.add(new InventoryItem("item " + (i + 1), "Food" , 10, 250, 100, "food.png"));
            }

            dataStore.setFileFormat("xml", false);
            dataStore.writeData("inventory-item", items);
            ArrayList<InventoryItem> readItems = dataStore.readData("inventory-item", InventoryItem.class);

            for (int i = 0; i < 5; i++) {
                System.out.println(items.get(i).getItemID());
                System.out.println(readItems.get(i).getItemID());
            }

            // Customer
            // Belum Work
             ArrayList<Customer> customers = new ArrayList<>();
             for (int i = 0; i < 5; i++) {
                 customers.add(new Customer());
             }

             dataStore.writeData("customer", customers);
             ArrayList<Customer> readCustomers = dataStore.readData("customer", Customer.class);
             for (int i = 0; i < 5; i++) {
                 System.out.println(customers.get(i).getCustomerID());
                 System.out.println(readCustomers.get(i).getCustomerID());
             }

             ArrayList<Transaction> transactions = new ArrayList<>();
             Order o1 = new Order(items.get(0), (double) Math.round(items.get(0).getPrice()), 3);
             Order o2 = new Order(items.get(4), (double) Math.round(items.get(4).getPrice()), 1);
             Order o3 = new Order(items.get(2), (double) Math.round(items.get(2).getPrice()), 5);
             List<Order> orders = new ArrayList<>(Arrays.asList(o1, o2, o3));

            Transaction transaction = new Transaction(customers.get(1), orders, 4500, new Date(), 1000);
            transactions.add(transaction);

            ArrayList<Order> emptyOrders = new ArrayList<>();
            dataStore.writeData("order", emptyOrders);

            TransactionController transactionController = new TransactionController();
            ArrayList<Transaction> emptyTransaction = new ArrayList<>();
             transactionController.save(emptyTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
