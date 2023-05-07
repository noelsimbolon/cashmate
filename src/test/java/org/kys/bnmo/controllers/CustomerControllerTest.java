package org.kys.bnmo.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kys.bnmo.model.Customer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


class CustomerControllerTest {

    private static DataStore dataStore;
    private static CustomerController customerController;

    @BeforeAll
    static void setUp() throws IOException {
        dataStore = new DataStore();
        String dataStoreTestFolderPath = (new File("src\\test\\data")).getAbsolutePath();
        dataStore.setFolderPath(dataStoreTestFolderPath, false);
        dataStore.setFileFormat("json", false);

        customerController = new CustomerController();
    }

    @Test
    void fetchAll() {
        // Arrange
        var customers = new ArrayList<Customer>();
        var customer1 = new Customer();
        var customer2 = new Customer();
        customers.add(customer1);
        customers.add(customer2);

        // Act
        dataStore.writeData("customer", customers);
        ArrayList<Customer> fetchedCustomers = customerController.fetchAll();

        // Asert
        Assertions.assertEquals(customer1.getCustomerID(), fetchedCustomers.get(0).getCustomerID());
        Assertions.assertEquals(customer2.getCustomerID(), fetchedCustomers.get(1).getCustomerID());
    }

    @Test
    void fetchByID() {
        // Arrange
        var customers = new ArrayList<Customer>();
        var customer1 = new Customer();
        var customer2 = new Customer();
        customers.add(customer1);
        customers.add(customer2);

        // Act
        dataStore.writeData("customer", customers);
        ArrayList<Customer> fetchedCustomers = customerController.fetchByID(customer1.getCustomerID());

        // Asert
        Assertions.assertEquals(customer1.getCustomerID(), fetchedCustomers.get(0).getCustomerID());
    }

    @Test
    void save() {
        // Arrange
        var customers = new ArrayList<Customer>();
        var customer1 = new Customer();
        var customer2 = new Customer();
        customers.add(customer1);
        customers.add(customer2);

        // Act
        customerController.save(customers);
        ArrayList<Customer> fetchedCustomers = dataStore.readData("customer", Customer.class);

        // Asert
        Assertions.assertEquals(customer1.getCustomerID(), fetchedCustomers.get(0).getCustomerID());
        Assertions.assertEquals(customer2.getCustomerID(), fetchedCustomers.get(1).getCustomerID());
    }

    @Test
    void deleteById() {
        // Arrange
        var customers = new ArrayList<Customer>();
        var customer1 = new Customer();
        var customer2 = new Customer();
        customers.add(customer1);
        customers.add(customer2);

        // Act
        dataStore.writeData("customer", customers);
        customerController.deleteById(customer1.getCustomerID());
        ArrayList<Customer> fetchedCustomers = dataStore.readData("customer", Customer.class);

        // Asert
        Assertions.assertEquals(customer2.getCustomerID(), fetchedCustomers.get(0).getCustomerID());
    }
}
