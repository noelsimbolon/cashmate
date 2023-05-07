package org.kys.bnmo.components.tabs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.controllers.InventoryItemController;
import org.kys.bnmo.helpers.views.IconButtonHelper;
import org.kys.bnmo.model.InventoryItem;

import java.util.ArrayList;
import java.util.UUID;

public class CatalogueDeleteConfirmationTab extends TabContainer {
    private static final FormBuilder formBuilder = new FormBuilder();
    private static final IconButtonHelper iconButtonHelper = new IconButtonHelper();
    private static final InventoryItemController inventoryItemController = new InventoryItemController();
    private final InventoryItem deletedItem;  // The item that is being deleted
    EventHandler<ActionEvent> backButtonAction;
    EventHandler<ActionEvent> confirmationButtonAction;

    public CatalogueDeleteConfirmationTab(@NotNull UUID deletedItemID, EventHandler<ActionEvent> backButtonAction) {
        ArrayList<InventoryItem> inventoryItems = inventoryItemController.readInventoryItems();

        this.deletedItem = inventoryItemController.getInventoryItemByUUID(inventoryItems, deletedItemID);
        this.backButtonAction = backButtonAction;

        this.confirmationButtonAction = (event) -> {
            if (deletedItem != null) {
                inventoryItems.remove(deletedItem);
                inventoryItemController.writeInventoryItems(inventoryItems);
            }
            backButtonAction.handle(event);
        };
    }

    @Override
    protected Pane getContent() {
        // Initialize confirmation form
        formBuilder.addTitle("Are you sure you want to delete the item?");
        formBuilder.addButton("Yes", confirmationButtonAction);
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
        addHeaderTitle("Delete Confirmation");
    }
}
