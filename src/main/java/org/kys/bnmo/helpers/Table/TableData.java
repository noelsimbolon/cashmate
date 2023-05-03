package org.kys.bnmo.helpers.Table;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class TableData {
    private List<TableEntry> entries;
    private TableEntry heading;

    public TableData() {
        this.entries = new ArrayList<>();
        this.heading = new TableEntry();
    }

    public TableData(List<String> heading) {
        this.entries = new ArrayList<>();
        this.heading = new TableEntry(heading);
    }

    public TableData(List<String> heading, List<List<String>> content) {
        this.entries = new ArrayList<>();
        this.heading = new TableEntry(heading);
        for (var row: content) {
            TableEntry tableEntry = new TableEntry();
            for (var text: row) {
                tableEntry.getColumns().add(new TableCell(text));
            }
            entries.add(tableEntry);
        }
    }

    public TableData(List<String> heading, List<List<String>> content, List<EventHandler<MouseEvent>> handlers) {
        this.entries = new ArrayList<>();
        this.heading = new TableEntry(heading);
        for (int i = 0;i < content.size();i++) {
            List<String> row = content.get(i);
            TableEntry tableEntry = new TableEntry();
            for (var text: row) {
                tableEntry.getColumns().add(new TableCell(text));
            }
            tableEntry.setAddHandler(handlers.get(i));
            entries.add(tableEntry);
        }
    }

    public TableData(List<String> heading, List<List<String>> content, List<EventHandler<MouseEvent>> handlers, List<ContextMenu> contextMenus) {
        this.entries = new ArrayList<>();
        this.heading = new TableEntry(heading);
        for (int i = 0;i < content.size();i++) {
            List<String> row = content.get(i);
            TableEntry tableEntry = new TableEntry();
            for (var text: row) {
                tableEntry.getColumns().add(new TableCell(text));
            }
            if (contextMenus != null)
                tableEntry.setContextMenu(contextMenus.get(i));
            if (handlers != null)
                tableEntry.setAddHandler(handlers.get(i));
            entries.add(tableEntry);
        }
    }

    public TableData(List<String> heading, List<List<String>> content, List<Image> images, int imageColumnIndex) {
        this.entries = new ArrayList<>();
        this.heading = new TableEntry(heading);
        for (var row: content) {
            TableEntry tableEntry = new TableEntry();
            for (var text: row) {
                tableEntry.getColumns().add(new TableCell(text));
            }
            entries.add(tableEntry);
        }

        addImages(images, imageColumnIndex);
    }


    public TableData(List<String> heading, List<List<String>> content, List<Image> images, int imageColumnIndex, List<EventHandler<MouseEvent>> handlers, List<ContextMenu> contextMenus) {
        this.entries = new ArrayList<>();
        this.heading = new TableEntry(heading);
        for (int i = 0;i < content.size();i++) {
            List<String> row = content.get(i);
            TableEntry tableEntry = new TableEntry();
            for (var text: row) {
                tableEntry.getColumns().add(new TableCell(text));
            }
            if (contextMenus != null)
                tableEntry.setContextMenu(contextMenus.get(i));
            if (handlers != null)
                tableEntry.setAddHandler(handlers.get(i));
            entries.add(tableEntry);
        }

        addImages(images, imageColumnIndex);
    }

    public void addImages(List<Image> images, int columnIndex) {
        for (int i = 0; i < entries.size(); i++) {
            if (i < images.size())
                entries.get(i).getColumns().get(columnIndex).setImage(images.get(i));
        }
    }

    public List<TableEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TableEntry> entries) {
        this.entries = entries;
    }

    public TableEntry getHeading() {
        return heading;
    }

    public void setHeading(TableEntry heading) {
        this.heading = heading;
    }
}