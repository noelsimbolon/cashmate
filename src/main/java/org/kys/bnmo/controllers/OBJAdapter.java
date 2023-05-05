package org.kys.bnmo.controllers;

import java.io.*;
import java.util.ArrayList;

public class OBJAdapter implements Adapter {

    @Override
    public <T> ArrayList<T> readFile(String filePath, Class<T> type) {
        ArrayList<T> objectList = new ArrayList<>();

        try {
            ObjectInputStream readStream = new ObjectInputStream(new FileInputStream(filePath));
            objectList = (ArrayList<T>) readStream.readObject();
            readStream.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return objectList;
    }

    @Override
    public void writeFile(String filePath, ArrayList<?> data) {
        try {
            ObjectOutputStream writeStream = new ObjectOutputStream(new FileOutputStream(filePath));
            writeStream.writeObject(data);
            writeStream.flush();
            writeStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
