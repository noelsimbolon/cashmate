package org.kys.bnmo.components.tabs;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.bases.CheckoutPanel;
import org.kys.bnmo.components.bases.TableBuilder;
import org.kys.bnmo.controllers.InventoryItemController;
import org.kys.bnmo.controllers.MemberController;
import org.kys.bnmo.helpers.views.tables.TableData;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Member;

import java.util.*;

public class CashierTab extends TabContainer {

    private static final TableBuilder tableBuilder = new TableBuilder();

    @Override
    protected Pane getContent() {
        HBox tableWrapper = new HBox();
        HBox.setHgrow(tableWrapper, Priority.ALWAYS);

        List<Member> members = new ArrayList<>();
        members.add(new Member("Fio", "+62-812-3456-891", "Member"));
        members.add(new Member("Jojo", "+62-812-3456-891", "Member"));
        members.add(new Member("Agus", "+62-812-3456-891", "VIP"));
        CheckoutPanel checkoutPanel = new CheckoutPanel(members);
        checkoutPanel.setOnCheckout(() -> {
            reloadTable(checkoutPanel, tableWrapper);
        });

        reloadTable(checkoutPanel, tableWrapper);

        HBox root = new HBox(tableWrapper, checkoutPanel);

        root.setPrefWidth(Double.MAX_VALUE);
        VBox.setVgrow(root, Priority.ALWAYS);
        root.getStyleClass().add("tab-row");
        return root;
    }

    private void reloadTable(CheckoutPanel checkoutPanel, Pane wrapper) {
        wrapper.getChildren().clear();
        TableData tableData = loadTableData(checkoutPanel);

        tableBuilder.setTableData(tableData, List.of(0, 1, 3));

        // Add search bar
        tableBuilder.addSearchBar();

        // Set column alignment for header
        tableBuilder.setColumnAlignment(0, Pos.CENTER);

        HBox root = new HBox();

        Pane itemTable = tableBuilder.getAndResetComponent();

        itemTable.getStyleClass().add("tab-cashier-table");
        checkoutPanel.getStyleClass().add("tab-checkout-panel");

        HBox.setHgrow(itemTable, Priority.ALWAYS);
        wrapper.getChildren().add(itemTable);
    }

    @NotNull
    private TableData loadTableData(CheckoutPanel checkoutPanel) {
        InventoryItemController inventoryItemController = new InventoryItemController();

        try {
            inventoryItemController.loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read inventory items from data store
        ArrayList<InventoryItem> inventoryItems = inventoryItemController.readInventoryItems();

        // Initialize objects
        List<String> heading = new ArrayList<>(Arrays.asList("Item", "Category", "Stock", "Price", "Actions"));
        List<Image> images = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        List<EventHandler<MouseEvent>> handlers = new ArrayList<>();

        // This loop fill the rows with data
        for (InventoryItem inventoryItem : inventoryItems) {
            List<String> row = new ArrayList<>();

            row.add(inventoryItem.getItemName());
            row.add(inventoryItem.getCategory());
            row.add(inventoryItem.getStock().toString());
            row.add(inventoryItem.getPrice().toString());

            data.add(row);

            var itemImage = new Image(Objects.requireNonNull(getClass().getResource(
                    "/categories/" + inventoryItem.getImageFileName()
            )).toExternalForm(), 34, 34, false, true);

            images.add(itemImage);

            handlers.add(e -> {
                checkoutPanel.addItem(inventoryItem);
            });
        }

        return new TableData(heading, data, images, 0, handlers, null);
    }

    @Override
    protected void additionalAction() {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Cashier");
    }
}
