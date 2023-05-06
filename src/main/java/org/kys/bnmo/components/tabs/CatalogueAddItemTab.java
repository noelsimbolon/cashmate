package org.kys.bnmo.components.tabs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.controllers.InventoryItemController;
import org.kys.bnmo.helpers.views.IconButtonHelper;
import org.kys.bnmo.model.InventoryItem;

import java.util.ArrayList;

public class CatalogueAddItemTab extends TabContainer {
    private static final FormBuilder formBuilder = new FormBuilder();
    private static final IconButtonHelper iconButtonHelper = new IconButtonHelper();
    private static final InventoryItemController inventoryItemController = new InventoryItemController();

    // State saver
    private final StringProperty name;
    private final Property<String> category;
    private final StringProperty stock;
    private final StringProperty price;
    private final StringProperty purchasePrice;
    EventHandler<ActionEvent> backButtonAction;
    EventHandler<ActionEvent> saveButtonAction;

    public CatalogueAddItemTab(EventHandler<ActionEvent> backButtonAction) {
        this.backButtonAction = backButtonAction;
        this.name = new SimpleStringProperty();
        this.category = new SimpleStringProperty();
        this.stock = new SimpleStringProperty();
        this.price = new SimpleStringProperty();
        this.purchasePrice = new SimpleStringProperty();

        this.saveButtonAction = (event) -> {
            var newInventoryItem = new InventoryItem(
                    name.getValue(),
                    category.getValue(),
                    Integer.parseInt(stock.getValue()),
                    Integer.parseInt(price.getValue()),
                    Integer.parseInt(purchasePrice.getValue()),
                    category.getValue().toLowerCase() + ".png"
            );

            ArrayList<InventoryItem> inventoryItems = inventoryItemController.readInventoryItems();

            inventoryItems.add(newInventoryItem);

            inventoryItemController.writeInventoryItems(inventoryItems);
        };
    }

    @Override
    protected Pane getContent() {
        // Initialize form to add item
        formBuilder.addTitle("Item Details");
        formBuilder.addTextBox("Name", "Enter name", name);
        formBuilder.addDropdown("Category", "Select category", new String[]{"Food", "Beverage", "Stationery", "Medicine"}, category);
        formBuilder.addTextBox("Stock", "Enter stock", stock);
        formBuilder.addTextBox("Price", "Enter price", price);
        formBuilder.addTextBox("Purchase Price", "Enter purchase price", purchasePrice);
        formBuilder.addButton("Save", saveButtonAction);

        return formBuilder.getAndResetComponent();
    }

    @Override
    protected void additionalAction() {
        Button backButton = new Button();
        iconButtonHelper.setButtonGraphic(backButton, "/icon/BackArrow.png", 20, 20);
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(backButtonAction);
        getHeader().getChildren().add(0, backButton);

        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Add Item");
    }
}
