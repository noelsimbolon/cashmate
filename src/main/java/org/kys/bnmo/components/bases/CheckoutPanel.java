package org.kys.bnmo.components.bases;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentFactory;

// TESTING
class Item {
    public String name;
    public double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
// TESTING

public class CheckoutPanel implements ComponentFactory {

    private VBox root;

    public CheckoutPanel() {}

    public Pane getComponent() {
        // Initialize root
        root = new VBox();

        var checkoutPanelBuilder = new CheckoutPanelBuilder();
        checkoutPanelBuilder.addButtonAndTextField("Enter customer name");
        checkoutPanelBuilder.addCustomerDropdown("Select customer", null);
        // you can also create items in the customer dropdown when providing the second argument like the following
        // checkoutPanelBuilder.addCustomerDropdown("Select customer", new String[] {"Customer 1", "Jojo", "Fio"});

        checkoutPanelBuilder.addItemScrollPane();
        checkoutPanelBuilder.addCheckoutButton();

        // Add the checkout panel to the root
        root.getChildren().add(checkoutPanelBuilder.getAndResetComponent());

        // This method must be called to add event handler to the plusButton
        // and the call is AFTER the root is initialized
        addPlusButtonEventHandler();

        // TODO:
        //  - Add event handlers for quantity spinner and checkout button to update the checkout price
        //  - Implement customer text field recommendation
        //  - Most event handlers and business logic is not implemented yet, must wait for data store

        // TESTING
        HBox item1 = createItemCard(new Item("Item 1", 60000), "Rp");
        HBox item2 = createItemCard(new Item("Item 2", 60000), "Rp");
        HBox item3 = createItemCard(new Item("Item 3", 60000), "Rp");
        HBox item4 = createItemCard(new Item("Item 4", 60000), "Rp");
        addItemCard(item1);
        addItemCard(item2);
        addItemCard(item3);
        addItemCard(item4);
        removeItemCard(item3);
        // TESTING

        return root;
    }

    // Add event handler for plusButton that adds a new customer
    // to the dropdown when the button is clicked.
    // This method must only be called then the root is initialized.
    private void addPlusButtonEventHandler() {
        var checkoutPanelContainer = (VBox) root.lookup("#checkout-panel-container");

        if (checkoutPanelContainer != null) {
            var plusButton = (Button) checkoutPanelContainer.lookup("#plus-button");
            var customerTextField = (TextField) checkoutPanelContainer.lookup("#customer-text-field");

            if (plusButton != null && customerTextField != null) {
                // This event handler might want to be refactored in the future
                // to implement more validation.
                plusButton.setOnAction(event -> {
                    String newCustomer = customerTextField.getText().trim();
                    if (!newCustomer.isEmpty()) {
                        addCustomerDropdownItem(newCustomer);
                        customerTextField.clear();
                    }
                    customerTextField.clear();
                });
            }
        }
    }

    public void addCustomerDropdownItem(String item) {
        var checkoutPanelContainer = (VBox) root.lookup("#checkout-panel-container");

        if (checkoutPanelContainer != null) {
            var customerDropdown = (ComboBox<String>) checkoutPanelContainer.lookup("#customer-dropdown");

            if (customerDropdown != null) {
                customerDropdown.getItems().add(item);
            }
        }
    }

    public void removeCustomerDropdownItem(String item) {
        var checkoutPanelContainer = (VBox) root.lookup("#checkout-panel-container");

        if (checkoutPanelContainer != null) {
            var customerDropdown = (ComboBox<String>) checkoutPanelContainer.lookup("#customer-dropdown");

            if (customerDropdown != null) {
                customerDropdown.getItems().remove(item);
            }
        }
    }

    private HBox createItemCard(Item item, String currency) {
        // Loop through the itemList and create a food frame for each item
        var roundedRectangle = new Button();
        roundedRectangle.setId("rounded-rectangle");

        // Labels to hold the item name and its price
        var nameLabel = new Label(item.name);
        nameLabel.setId("item-name-label");
        var priceLabel = new Label(currency + item.price);
        priceLabel.setId("item-price-label");

        // VBox to hold the labels
        var itemDescription = new VBox();
        itemDescription.getChildren().addAll(nameLabel, priceLabel);
        itemDescription.setAlignment(Pos.CENTER_LEFT);
        itemDescription.setId("item-description");

        // HBox to contain the itemDescription VBox
        var itemDescriptionContainer = new HBox();
        itemDescriptionContainer.getChildren().addAll(itemDescription);
        itemDescriptionContainer.setAlignment(Pos.CENTER_LEFT);
        itemDescriptionContainer.setId("item-description-container");

        // Spinner that controls the quantity of the item
        var quantitySpinner = new Spinner<Integer>(1, 99, 1, 1);
        quantitySpinner.setId("quantity-spinner");

        var quantitySpinnerContainer = new HBox(quantitySpinner);
        quantitySpinnerContainer.setAlignment(Pos.CENTER_RIGHT);
        quantitySpinnerContainer.setId("quantity-spinner-container");

        var itemCard = new HBox();
        itemCard.setAlignment(Pos.CENTER_LEFT);
        itemCard.setId("item-container");
        HBox.setHgrow(itemCard, Priority.ALWAYS);
        HBox.setHgrow(itemDescriptionContainer, Priority.ALWAYS);
        HBox.setHgrow(quantitySpinnerContainer, Priority.NEVER);

        // Add the labels and the spinner to the item frame
        itemCard.getChildren().addAll(roundedRectangle, itemDescriptionContainer, quantitySpinnerContainer);
        itemDescriptionContainer.setMaxWidth(Double.MAX_VALUE);
        itemCard.setMaxWidth(Double.MAX_VALUE);

        return itemCard;
    }

    // This method might want to be refactored to adjust
    // with the data structure.
    public void addItemCard(HBox itemCard) {
        var checkoutPanelContainer = (VBox) root.lookup("#checkout-panel-container");

        if (checkoutPanelContainer != null) {
            var itemScrollPane = (ScrollPane) checkoutPanelContainer.lookup("#item-scroll-pane");

            if (itemScrollPane != null) {
                var items = (VBox) itemScrollPane.getContent().lookup("#items");

                if (items != null) {
                    // Add the item frame to the container
                    items.getChildren().add(itemCard);
                }
            }
        }

        updateCheckoutPrice();
    }

    // This method might want to be refactored to adjust
    // with the data structure.
    public void removeItemCard(HBox itemCard) {
        var checkoutPanelContainer = (VBox) root.lookup("#checkout-panel-container");

        if (checkoutPanelContainer != null) {
            var itemScrollPane = (ScrollPane) checkoutPanelContainer.lookup("#item-scroll-pane");

            if (itemScrollPane != null) {
                var items = (VBox) itemScrollPane.getContent().lookup("#items");

                if (items != null) {
                    // Add the item frame to the container
                    items.getChildren().remove(itemCard);
                }
            }
        }

        updateCheckoutPrice();
    }

    private void updateCheckoutPrice() {
        // Implement the update of the checkout price
    }
}
