package org.kys.bnmo.plugins.adapters;

import org.kys.bnmo.controllers.DataStore;
import org.kys.bnmo.plugins.interfaces.DatastoreAdapterInterface;

import java.util.ArrayList;

public class DatastoreAdapter implements DatastoreAdapterInterface {
    DataStore datastore;

    public DatastoreAdapter()
    {
        datastore = new DataStore();
    }

    @Override
    public void writeConfigAPI(String filename, ArrayList<String> contents) throws Exception {
        datastore.writeConfigAPI(filename, contents);
    }

    @Override
    public ArrayList<String> loadConfigAPI(String filename) throws Exception {
        return datastore.loadConfigAPI(filename);
    }
}
