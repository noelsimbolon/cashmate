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
import org.kys.bnmo.controllers.InventoryItemController;
import org.kys.bnmo.events.NavigationHandler;
import org.kys.bnmo.helpers.views.tables.TableData;
import org.kys.bnmo.model.InventoryItem;

import java.beans.DefaultPersistenceDelegate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CatalogueTab extends TabContainer {
    private Pane root;
    private static final TableBuilder tableBuilder = new TableBuilder();
    private final NavigationHandler itemHandler;
    private final EventHandler<ActionEvent> backHandler;
    private static final InventoryItemController inventoryItemController = new InventoryItemController();

    public CatalogueTab(NavigationHandler itemHandler, EventHandler<ActionEvent> backHandler) {
        this.itemHandler = itemHandler;
        this.backHandler = backHandler;
    }

    @Override
    protected Pane getContent() {
        try {
            inventoryItemController.loadDataStore();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read inventory items from data store
        ArrayList<InventoryItem> inventoryItems = inventoryItemController.readInventoryItems();

        // Initialize objects
        List<String> heading = new ArrayList<>(Arrays.asList("Item", "Category", "Stock", "Price", "Purchase Price", "Actions"));
        List<Image> images = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        List<ContextMenu> contextMenus = new ArrayList<>();

        // This loop fill the rows with data
        for (InventoryItem inventoryItem : inventoryItems) {
            List<String> row = new ArrayList<>();

            row.add(inventoryItem.getItemName());
            row.add(inventoryItem.getCategory());
            row.add(inventoryItem.getStock().toString());
            row.add(inventoryItem.getPrice().toString());
            row.add(inventoryItem.getPurchasePrice().toString());

            data.add(row);

            var itemImage = new Image(Objects.requireNonNull(getClass().getResource(
                    "/categories/" + inventoryItem.getImageFileName()
            )).toExternalForm(), 34, 34, false, true);

            images.add(itemImage);

            MenuItem edit = new MenuItem("Edit");
            edit.setOnAction(itemHandler.getEventHandler(
                    new CatalogueEditItemTab(inventoryItem.getItemID(), backHandler),
                    "Catalogue"
            ));

            MenuItem delete = new MenuItem("Delete");
            delete.setOnAction(itemHandler.getEventHandler(
                    new CatalogueDeleteConfirmationTab(inventoryItem.getItemID(), backHandler),
                    "Catalogue"
            ));

            ContextMenu cm = new ContextMenu(edit, delete);
            contextMenus.add(cm);
        }

        if (!inventoryItems.isEmpty()) {
            // Set table data
            TableData inventoryData = new TableData(heading, data, images, 0, null, contextMenus);
            tableBuilder.setTableData(inventoryData, List.of(0, 1, 3));

            // Add search bar
            tableBuilder.addSearchBar();
        }

        // Add Item button with its event handler
        tableBuilder.addAddItemButton("Add Item", itemHandler.getEventHandler(
                new CatalogueAddItemTab(backHandler),
                "Catalogue"
        ));

        // Set column alignment for header
        tableBuilder.setColumnAlignment(0, Pos.CENTER);

        // Table to hold all inventory items
        root = tableBuilder.getAndResetComponent();

        VBox.setVgrow(root, Priority.ALWAYS);
        return root;
    }

    @Override
    protected void additionalAction() {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Catalogue");
    }
}
