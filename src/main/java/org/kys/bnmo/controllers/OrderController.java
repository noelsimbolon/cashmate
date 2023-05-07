package org.kys.bnmo.controllers;

import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.Order;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class OrderController {
    private final DataStore dataStore;
    private final String fileName;

    public OrderController() {
        dataStore = new DataStore();
        fileName = "order";
    }

    public ArrayList<Order> fetchAll() {
        return dataStore.readData(fileName, Order.class);
    }

    public ArrayList<Order> fetchByID(int id) {
        return (ArrayList<Order>) fetchAll().stream()
                .filter(c -> c.getOrderID() == id)
                .collect(Collectors.toList());
    }

    public void save(ArrayList<Order> data) {
        dataStore.writeData(fileName, data);
    }

    public void deleteById(int id) {
        ArrayList<Order> data = fetchAll();

        for (Order order: data) {
            if (order.getOrderID() == id) {
                data.remove(order);
                break;
            }
        }

        save(data);
    }
}
