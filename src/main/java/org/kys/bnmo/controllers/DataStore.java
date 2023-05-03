package org.kys.bnmo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataStore {
    private String format;
    private Adapter adapter;

    private static final Map<String, Adapter> adapterMap = new HashMap<>() {{
        put("json", new JSONAdapter());
        put("obj", new OBJAdapter());
        put("xml", new XMLAdapter());
    }};

    public DataStore() {
        format = null;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
        this.adapter = getSuitableAdapter();
    }

    private Adapter getSuitableAdapter() {
        if (format == null) throw new NullPointerException("File Format hasn't been initialized yet");
        if (!adapterMap.containsKey(format)) throw new IllegalArgumentException(format + " is not supported");

        return adapterMap.get(format);
    }

    public <T> ArrayList<T> readData(String filePath, Class<T> dataType) {
        return adapter.readFile(filePath, dataType);
    }

    public void writeData(String filePath, ArrayList<?> data) {
        adapter.writeFile(filePath, data);
    }
}
