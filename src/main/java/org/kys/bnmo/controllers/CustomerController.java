package org.kys.bnmo.controllers;

import org.kys.bnmo.model.Customer;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomerController {
    private final DataStore dataStore;
    private final String fileName;

    public CustomerController() {
        dataStore = new DataStore();
        fileName = "customer";
    }

    public ArrayList<Customer> fetchAll() {
        return dataStore.readData(fileName, Customer.class);
    }

    public ArrayList<Customer> fetchByID(UUID id) {
        return (ArrayList<Customer>) fetchAll().stream()
                .filter(c -> c.getCustomerID().equals(id))
                .collect(Collectors.toList());
    }

    public void save(ArrayList<Customer> data) {
        dataStore.writeData(fileName, data);
    }

    public void deleteById(UUID id) {
        ArrayList<Customer> data = fetchAll();

        for (Customer customer: data) {
            if (customer.getCustomerID().equals(id)) {
                data.remove(customer);
                break;
            }
        }

        save(data);
    }
}
