package org.kys.bnmo.plugins.interfaces;

import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Member;
import org.kys.bnmo.model.Transaction;
import org.kys.bnmo.plugins.adapters.DatastoreAdapterInterface;

import java.util.List;

public interface ControllerAdapterInterface {
    public List<Member> getMembers();
    public List<InventoryItem> getItems();
    public List<Customer> getCustomers();
    public List<Transaction> getTransactions();
    public DatastoreAdapterInterface getDatastore();

}
