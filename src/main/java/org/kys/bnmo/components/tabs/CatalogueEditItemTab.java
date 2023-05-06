package org.kys.bnmo.components.tabs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.helpers.views.IconButtonHelper;
import org.kys.bnmo.model.InventoryItem;

public class CatalogueEditItemTab extends TabContainer {

    private static final FormBuilder formBuilder = new FormBuilder();

    private static final IconButtonHelper iconButtonHelper = new IconButtonHelper();


    EventHandler<ActionEvent> backButtonAction;
    EventHandler<ActionEvent> saveButtonAction;
    private InventoryItem editedItem;  // The item that is being edited

    // State saver
    private final StringProperty name;
    private final Property<String> category;
    private final StringProperty stock;
    private final StringProperty price;
    private final StringProperty purchasePrice;

    public CatalogueEditItemTab(@NotNull InventoryItem editedItem, EventHandler<ActionEvent> backButtonAction) {
        this.backButtonAction = backButtonAction;
        this.editedItem = editedItem;
        this.name = new SimpleStringProperty(editedItem.getItemName());
        this.category = new SimpleStringProperty(editedItem.getCategory());
        this.stock = new SimpleStringProperty(editedItem.getStock().toString());
        this.price = new SimpleStringProperty(editedItem.getPrice().toString());
        this.purchasePrice = new SimpleStringProperty(editedItem.getPurchasePrice().toString());

        this.saveButtonAction = (event) -> {
            System.out.println("Item Name: " + name.getValue());
            System.out.println("Category: " + category.getValue());
            System.out.println("Stock: " + stock.getValue());
            System.out.println("Price: " + price.getValue());
            System.out.println("Purchase Price: " + purchasePrice.getValue());
        };
    }

    @Override
    protected Pane getContent() {
        // Initialize form to edit item
        formBuilder.addTitle("Item Details");
        formBuilder.addTextBox("Name", "Enter name", name);
        formBuilder.addDropdown("Category", "Select category", new String[] {"Food", "Beverage", "Stationery", "Medicine"}, category);
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
        addHeaderTitle("Edit Item");
    }
}
