package org.kys.bnmo.controllers;

import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.Order;
import org.kys.bnmo.helpers.plugins.PluginLoader;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.model.Transaction;
import org.kys.bnmo.model.UnpopulatedTransaction;
import org.kys.bnmo.plugins.base.PluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionController {
    private final DataStore dataStore;
    private final String fileName;
    private final OrderController orderController = new OrderController();
    private final CustomerController customerController = new CustomerController();

    public TransactionController() {
        dataStore = new DataStore();
        fileName = "transaction";
    }

    private void processGetData(List<Transaction> transactions)
    {
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.runClasses(new PluginService(null , null, new Modifiable(transactions,null, true)));
    }

    private void processSetData(List<Transaction> transactions)
    {
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.runClasses(new PluginService(null , null, new Modifiable(transactions, null, false)));

    }

    public ArrayList<Transaction> fetchAll() {
        List<UnpopulatedTransaction> unpopulatedTransactions = dataStore.readData(fileName, UnpopulatedTransaction.class);
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (UnpopulatedTransaction ut : unpopulatedTransactions) {
            List<Order> orders = new ArrayList<>();
            for (int orderID : ut.getOrderIDs()) {
                orders.add(orderController.fetchByID(orderID).get(0));
            }

            Customer customer = customerController.fetchByID(ut.getCustomerID()).get(0);

            Transaction transaction = new Transaction(ut.getTransactionID(), customer, orders, ut.getTotalPrice(), ut.getDate(), ut.getDiscount());
            transactions.add(transaction);
        }

        return transactions;
    }

    public ArrayList<Transaction> fetchByID(int uuid) {
        return (ArrayList<Transaction>) fetchAll().stream()
                .filter(t -> t.getTransactionID() == uuid)
                .collect(Collectors.toList());
    }

    public void save(ArrayList<Transaction> data) {
        ArrayList<Order> orders = orderController.fetchAll();

        ArrayList<UnpopulatedTransaction> uts = data.stream().map(t -> {
            List<Integer> orderIDs = new ArrayList<>();

            for (Order order: t.getOrders()) {
                orderIDs.add(order.getOrderID());
                if (orders.stream().filter(dataOrder -> dataOrder.getOrderID() == order.getOrderID()).toList().size() == 0)
                    orders.add(order);
            }

            return new UnpopulatedTransaction(t.getTransactionID(), t.getCustomer().getCustomerID(), orderIDs, t.getTotalPrice(), t.getDate(), t.getDiscount());
        }).collect(Collectors.toCollection(ArrayList::new));
        orderController.save(orders);

        dataStore.writeData(fileName, uts);
    }

    public void deleteById(int uuid) {
        ArrayList<Transaction> data = fetchAll();

        for (Transaction transaction: data) {
            if (transaction.getTransactionID() == uuid) {
                data.remove(transaction);
                break;
            }
        }

        save(data);
    }
}
