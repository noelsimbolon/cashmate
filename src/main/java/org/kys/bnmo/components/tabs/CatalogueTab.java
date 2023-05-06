package org.kys.bnmo.components.tabs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.bases.TableBuilder;
import org.kys.bnmo.controllers.DataStore;
import org.kys.bnmo.events.NavigationHandler;
import org.kys.bnmo.helpers.views.tables.TableData;
import org.kys.bnmo.model.InventoryItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CatalogueTab extends TabContainer {

    private static final TableBuilder tableBuilder = new TableBuilder();

    private static DataStore dataStore = new DataStore();

    private NavigationHandler itemHandler;

    private EventHandler<ActionEvent> backHandler;

    public CatalogueTab(NavigationHandler itemHandler, EventHandler<ActionEvent> backHandler) {
        this.itemHandler = itemHandler;
        this.backHandler = backHandler;
    }

    @Override
    protected Pane getContent() {
        try {
            dataStore.loadConfig();
            String dataTestFolderPath = (new File("test\\data")).getAbsolutePath();
            dataStore.setFolderPath(dataTestFolderPath, false);
            ArrayList<InventoryItem> inventoryItems = dataStore.readData("inventory-item", InventoryItem.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize objects
        List<String> heading = new ArrayList<>(Arrays.asList("Item", "Category", "Stock", "Price", "Purchase Price", "Actions"));
        List<Image> images = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        List<ContextMenu> contextMenus = new ArrayList<>();

        // This loop fill the rows with data
        for (int i = 0; i < 15; i++) {
            // TESTING
            var item = new InventoryItem("Croissant",
                    "Food",
                    10,
                    20000,
                    10000,
                    "food.png");
            // TESTING

            List<String> row = new ArrayList<>();
            row.add(item.getItemName());
            row.add(item.getCategory());
            row.add(String.valueOf(item.getStock()));
            row.add(String.valueOf(item.getPrice()));
            row.add(String.valueOf(item.getPurchasePrice()));

            List<String> copied = new ArrayList<>(row);
            data.add(copied);

            var image = new Image(Objects.requireNonNull(getClass().getResource("/categories/" + item.getImageFileName())).toExternalForm(), 34, 34, false, true);

            images.add(image);


            MenuItem edit = new MenuItem("Edit");
            edit.setOnAction(itemHandler.getEventHandler(
                    new CatalogueEditItemTab(item, backHandler),
                    "Catalogue"
            ));

            MenuItem delete = new MenuItem("Delete");
            delete.setOnAction(actionEvent -> System.out.println("Delete Pressed."));

            ContextMenu cm = new ContextMenu(edit, delete);
            contextMenus.add(cm);
        }

        // Set table data
        TableData inventoryItems = new TableData(heading, data, images, 0, null, contextMenus);
        tableBuilder.setTableData(inventoryItems, List.of(0, 1, 3));

        // Add Item button with its event handler
        tableBuilder.addAddItemButton("Add Item", itemHandler.getEventHandler(
                new CatalogueAddItemTab(backHandler),
                "Catalogue"
        ));

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
    protected void additionalAction() {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Catalogue");
    }
}
