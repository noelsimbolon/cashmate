package org.kys.bnmo.controllers;

import org.kys.bnmo.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderController {
    private final DataStore dataStore;
    private final String fileName;
    private final InventoryItemController inventoryItemController = new InventoryItemController();

    public OrderController() {
        dataStore = new DataStore();
        fileName = "order";
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

    public ArrayList<Order> fetchByID(int id) {
        return (ArrayList<Order>) fetchAll().stream()
                .filter(c -> c.getOrderID() == id)
                .collect(Collectors.toList());
    }

    public void save(ArrayList<Order> data) {
        ArrayList<UnpopulatedOrder> ups = data.stream().map(o -> {
            return new UnpopulatedOrder(o.getOrderID(), o.getItem().getItemID(), o.getPurchasePrice(), o.getQuantity());
        }).collect(Collectors.toCollection(ArrayList::new));

        dataStore.writeData(fileName, ups);
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
