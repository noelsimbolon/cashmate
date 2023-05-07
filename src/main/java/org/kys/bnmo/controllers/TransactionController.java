package org.kys.bnmo.controllers;

import org.kys.bnmo.model.Transaction;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionController {
    private final DataStore dataStore;
    private final String fileName;

    public TransactionController() {
        dataStore = new DataStore();
        fileName = "transaction";
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
}
