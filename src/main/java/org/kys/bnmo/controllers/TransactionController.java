package org.kys.bnmo.controllers;

import org.kys.bnmo.helpers.plugins.PluginLoader;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.model.Transaction;
import org.kys.bnmo.plugins.base.PluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionController {
    private final DataStore dataStore;
    private final String fileName;

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
        return dataStore.readData(fileName, Transaction.class);
    }

    public ArrayList<Transaction> fetchByID(UUID uuid) {
        return (ArrayList<Transaction>) fetchAll().stream()
                .filter(t -> t.getTransactionID().equals(uuid))
                .collect(Collectors.toList());
    }

    public void save(ArrayList<Transaction> data) {
        dataStore.writeData(fileName, data);
    }

    public void deleteById(UUID uuid) {
        ArrayList<Transaction> data = fetchAll();

        for (Transaction transaction: data) {
            if (transaction.getTransactionID().equals(uuid)) {
                data.remove(transaction);
                break;
            }
        }

        save(data);
    }
}
