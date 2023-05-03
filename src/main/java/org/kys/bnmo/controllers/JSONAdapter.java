package org.kys.bnmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JSONAdapter implements Adapter {
    private final ObjectMapper objectMapper;

    public JSONAdapter() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public <T> ArrayList<T> readFile(String filePath, Class<T> type) {
        ArrayList<T> objectList = new ArrayList<>();

        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            objectList = objectMapper.readValue(new File(filePath), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objectList;
    }

    @Override
    public void writeFile(String filePath, ArrayList<?> data) {
        try {
            objectMapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
