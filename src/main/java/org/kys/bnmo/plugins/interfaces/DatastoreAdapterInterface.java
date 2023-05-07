package org.kys.bnmo.plugins.interfaces;
import java.util.ArrayList;

public interface DatastoreAdapterInterface {

    public void writeConfigAPI(String filename, ArrayList<String> contents) throws Exception;

    public ArrayList<String> loadConfigAPI(String filename) throws Exception;

}
