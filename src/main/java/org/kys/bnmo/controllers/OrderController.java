package org.kys.bnmo.controllers;

import org.kys.bnmo.model.*;
import org.kys.bnmo.helpers.plugins.PluginLoader;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.model.Order;
import org.kys.bnmo.plugins.base.PluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderController {
    private final DataStore dataStore;
    private final String fileName;
    private final InventoryItemController inventoryItemController = new InventoryItemController();

    public OrderController() {
        dataStore = new DataStore();
        fileName = "order";
    }

    private void processGetData(List<Order> orders)
    {
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.runClasses(new PluginService(null , null, new Modifiable(null, null, orders, true)));

    }

    private void processSetData(List<Order> orders)
    {
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.runClasses(new PluginService(null , null, new Modifiable(null, null, orders, false)));

    }

    public ArrayList<Order> fetchAll() {
        List<UnpopulatedOrder> unpopulatedOrders = dataStore.readData(fileName, UnpopulatedOrder.class);
        ArrayList<InventoryItem> dataItems = inventoryItemController.readInventoryItems();
        ArrayList<Order> orders = new ArrayList<>();
        for (UnpopulatedOrder up : unpopulatedOrders) {
            InventoryItem inventoryItem = inventoryItemController.getInventoryItemByUUID(dataItems, up.getItemID());

            Order order = new Order(up.getOrderID(), inventoryItem, up.getPurchasePrice(), up.getQuantity());
            orders.add(order);
        }

        return orders;
    }

    public ArrayList<Order> fetchByID(UUID id) {
        return (ArrayList<Order>) fetchAll().stream()
                .filter(c -> c.getOrderID().equals(id))
                .collect(Collectors.toList());
    }

    public void save(ArrayList<Order> data) {
        ArrayList<UnpopulatedOrder> ups = data.stream().map(o -> {
            return new UnpopulatedOrder(o.getOrderID(), o.getItem().getItemID(), o.getPurchasePrice(), o.getQuantity());
        }).collect(Collectors.toCollection(ArrayList::new));

        dataStore.writeData(fileName, ups);
    }

    public void deleteById(UUID id) {
        ArrayList<Order> data = fetchAll();

        for (Order order: data) {
            if (order.getOrderID().equals(id)) {
                data.remove(order);
                break;
            }
        }

        save(data);
    }
}
