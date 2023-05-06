package org.kys.bnmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLAdapter implements Adapter {

    private final XmlMapper xmlMapper;

    public XMLAdapter() {
        xmlMapper = new XmlMapper();
    }

    @Override
    public <T> ArrayList<T> readFile(String filePath, Class<T> type) {
        ArrayList<T> objectList = new ArrayList<>();

        try {
            CollectionType listType = xmlMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            objectList = xmlMapper.readValue(new File(filePath), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objectList;
    }

    @Override
    public void writeFile(String filePath, ArrayList<?> data) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            xmlMapper.writeValue(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
