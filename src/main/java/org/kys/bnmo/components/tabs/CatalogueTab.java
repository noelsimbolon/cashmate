package org.kys.bnmo.components.tabs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.bases.TableBuilder;
import org.kys.bnmo.helpers.Table.TableData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CatalogueTab extends TabContainer {

    private static final TableBuilder tableBuilder = new TableBuilder();

    @Override
    protected Pane getContent() {
        // Initialize objects
        var image = new Image(Objects.requireNonNull(getClass().getResource("/categories/pastry.png")).toExternalForm(), 34, 34, false, true);
        List<String> heading = new ArrayList<>(Arrays.asList("Item", "Category", "Stock", "Price", "Purchase Price", "Actions"));
        List<Image> images = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        List<ContextMenu> contextMenus = new ArrayList<>();

        // This loop fill the rows with data
        for (int i = 0; i < 15; i++) {
            List<String> row = Arrays.asList("Croissant", "Pastry", "2", "Rp20.000,00", "Rp10.000,00");
            List<String> copied = new ArrayList<>(row);
            data.add(copied);
            images.add(image);

            MenuItem edit = new MenuItem("Edit");
            edit.setOnAction(actionEvent -> System.out.println("Edit Pressed."));

            MenuItem delete = new MenuItem("Delete");
            delete.setOnAction(actionEvent -> System.out.println("Delete Pressed."));

            ContextMenu cm = new ContextMenu(edit, delete);
            contextMenus.add(cm);
        }

        // Set table data
        TableData inventoryItems = new TableData(heading, data, images, 0, null, contextMenus);
        tableBuilder.setTableData(inventoryItems, List.of(1, 2, 4));

        // Add Item button with its event handler
        tableBuilder.addAddItemButton("Add Item", e -> {
            System.out.println("Add Item pressed.");
        });

        // Add search bar
        tableBuilder.addSearchBar();

        // Set column alignment for header
        tableBuilder.setColumnAlignment(0, Pos.CENTER);

        // Table to hold all inventory items
        Pane root = tableBuilder.getAndResetComponent();

        VBox.setVgrow(root, Priority.ALWAYS);
        return root;
    }

    @Override
    protected void additionalAction()
    {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Catalogue");
    }
}
