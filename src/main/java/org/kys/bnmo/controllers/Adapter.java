package org.kys.bnmo.controllers;

import java.util.ArrayList;

public interface Adapter {
    <T> ArrayList<T> readFile(String filePath, Class<T> type);
    void writeFile(String filePath, ArrayList<?> data);
}
