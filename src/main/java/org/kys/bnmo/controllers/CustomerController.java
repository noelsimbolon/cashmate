package org.kys.bnmo.controllers;

import org.kys.bnmo.model.Customer;

import java.util.ArrayList;

public class CustomerController {
    // TODO: Nyesuain sama model baru kalau udah dipush

    private DataStore dataStore;
    private String fileName;

    public CustomerController() {
        dataStore = new DataStore();
        fileName = "customer";
    }

//    public ArrayList<Customer> fetchAll() {
//        return dataStore.readData("")
//    }
}
