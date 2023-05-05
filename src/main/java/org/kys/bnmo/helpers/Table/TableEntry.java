package org.kys.bnmo.helpers.Table;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TableEntry {

    @Getter
    private final List<TableCell> columns;

    @Getter
    @Setter
    private EventHandler<MouseEvent> addHandler;

    @Getter
    @Setter
    private ContextMenu contextMenu;

    public TableEntry() {
        this.columns = new ArrayList<>();
    }

    public TableEntry(@NotNull List<String> strings) {
        this.columns = new ArrayList<>();
        this.addHandler = null;
        for (var str : strings)
            this.columns.add(new TableCell(str));
    }

    public TableEntry(@NotNull List<String> strings, EventHandler<MouseEvent> addHandler) {
        this.columns = new ArrayList<>();
        this.addHandler = addHandler;
        for (var str : strings)
            this.columns.add(new TableCell(str));
    }

    public TableEntry(@NotNull List<String> strings, Image image, int imageIdx) {
        this.columns = new ArrayList<>();
        this.addHandler = null;
        for (int i = 0; i < strings.size(); i++) {
            if (i == imageIdx)
                this.columns.add(new TableCell(strings.get(i), image));
            else
                this.columns.add(new TableCell(strings.get(i)));
        }
    }

    public TableEntry(@NotNull List<String> strings, Image image, int imageIdx, EventHandler<MouseEvent> addHandler) {
        this.columns = new ArrayList<>();
        this.addHandler = addHandler;
        for (int i = 0; i < strings.size(); i++) {
            if (i == imageIdx)
                this.columns.add(new TableCell(strings.get(i), image));
            else
                this.columns.add(new TableCell(strings.get(i)));
        }
    }

    public void addImage(Image image, int imageIdx) {
        this.columns.get(imageIdx).setImage(image);
    }
}
