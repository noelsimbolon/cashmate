package org.kys.bnmo.controllers;

import org.kys.bnmo.helpers.plugins.PluginLoader;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.model.Order;
import org.kys.bnmo.plugins.base.PluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderController {
    private final DataStore dataStore;
    private final String fileName;

    public OrderController() {
        dataStore = new DataStore();
        fileName = "order";
    }

    private void processGetData(List<Order> orders) {
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.runClasses(new PluginService(null , null, new Modifiable(null, null, orders, true)));

    }

    private void processSetData(List<Order> orders) {
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.runClasses(new PluginService(null , null, new Modifiable(null, null, orders, false)));
    }

    public ArrayList<Order> fetchAll() {
        ArrayList<Order> data = dataStore.readData(fileName, Order.class);
        processGetData(data);
        return data;
    }

    public ArrayList<Order> fetchByID(int id) {
        return (ArrayList<Order>) fetchAll().stream()
                .filter(c -> c.getOrderID() == id)
                .collect(Collectors.toList());
    }

    public void save(ArrayList<Order> data) {
        processSetData(data);
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
