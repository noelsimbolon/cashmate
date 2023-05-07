package org.kys.bnmo.plugins.adapters;

import org.kys.bnmo.controllers.CustomerController;
import org.kys.bnmo.controllers.InventoryItemController;
import org.kys.bnmo.controllers.MemberController;
import org.kys.bnmo.controllers.TransactionController;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Member;
import org.kys.bnmo.model.Transaction;
import org.kys.bnmo.plugins.interfaces.ControllerAdapterInterface;

import java.util.List;

public class ControllerAdapter implements ControllerAdapterInterface {

    private static final CustomerController customerController = new CustomerController();

    private static final MemberController memberController = new MemberController();
    private static final InventoryItemController inventoryItemController = new InventoryItemController();
    private static final TransactionController transactionController = new TransactionController();
    private static final DatastoreAdapter datastore = new DatastoreAdapter();
    @Override
    public List<Member> getMembers() {
        return memberController.fetchAll();
    }

    @Override
    public List<InventoryItem> getItems() {
        return inventoryItemController.readInventoryItems();
    }

    @Override
    public List<Customer> getCustomers() {
        return customerController.fetchAll();

    }
    @Override
    public List<Transaction> getTransactions() {
        return transactionController.fetchAll();
    }
    @Override
    public DatastoreAdapterInterface getDatastore() {
        return datastore;
    }

}
