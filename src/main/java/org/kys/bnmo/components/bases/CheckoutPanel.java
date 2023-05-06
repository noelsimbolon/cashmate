package org.kys.bnmo.components.bases;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.ComponentFactory;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;

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
    private VBox inputFields;

    public CheckoutPanel() {
    }

    @NotNull
    public Pane getComponent() {
        // Initialize root
        root = new VBox();

        // Initialize checkout panel container
        VBox checkoutPanelContainer = new VBox();
        checkoutPanelContainer.setId("checkout-panel-container");

        // Initialize input fields
        inputFields = new VBox();
        inputFields.setId("checkout-panel-input-fields");

        // Add input fields to check out panel container
        checkoutPanelContainer.getChildren().add(inputFields);

        // Load CSS
        StyleLoadHelper helper = new StyleLoadHelper("/styles/CheckoutPanel.css");
        helper.load(checkoutPanelContainer);

        addButtonAndTextField("Enter customer name");
        addCustomerDropdown("Select customer", null);
        // you can also create items in the customer dropdown when providing the second argument like the following
        // addCustomerDropdown("Select customer", new String[] {"Customer 1", "Jojo", "Fio"});

        addItemScrollPane();
        addDiscount(20000, "Rp");
        addCheckoutButton();

        // Add the checkout panel to the root
        root.getChildren().add(checkoutPanelContainer);

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

    private void addButtonAndTextField(String placeholder) {
        // Container to hold the plusButton and customerTextField
        var buttonAndTextFieldContainer = new HBox();

        // Create label for button
        var plusLabel = new Label("+");
        plusLabel.setId("plus-label");

        // Create button
        var plusButton = new Button();
        plusButton.setId("plus-button");
        plusButton.setGraphic(plusLabel);

        // Create a text field
        TextField customerTextField = new TextField();
        customerTextField.setPromptText(placeholder);
        customerTextField.setId("customer-text-field");
        HBox.setHgrow(customerTextField, Priority.ALWAYS);

        // Add plusButton and customerTextField to buttonAndTextFieldContainer
        buttonAndTextFieldContainer.getChildren().addAll(plusButton, customerTextField);
        buttonAndTextFieldContainer.setId("button-and-text-field-container");

        inputFields.getChildren().add(buttonAndTextFieldContainer);
    }

    private void addCustomerDropdown(String placeholder, String[] items) {
        // Container to hold the customer dropdown
        HBox customerDropdownContainer = new HBox();
        HBox.setHgrow(customerDropdownContainer, Priority.ALWAYS);

        // Dropdown
        var customerDropdown = new ComboBox<String>();
        customerDropdown.setPromptText(placeholder);
        customerDropdown.setId("customer-dropdown");
        if (items != null) {
            customerDropdown.getItems().addAll(items);
        }

        // Bind the preferred width of the dropdown container to the available space
        DoubleBinding customerDropdownContainerWidth = Bindings.createDoubleBinding(customerDropdownContainer::getWidth,
                customerDropdownContainer.widthProperty());

        customerDropdown.prefWidthProperty().bind(customerDropdownContainerWidth);

        // Add customerDropdown to customerDropdownContainer
        customerDropdownContainer.getChildren().addAll(customerDropdown);
        customerDropdownContainer.setId("customer-dropdown-container");

        // Add customerDropdownContainer to inputFields
        inputFields.getChildren().add(customerDropdownContainer);
    }

    private void addItemScrollPane() {
        // Create a scroll pane to contain all the checkout items
        var itemsScrollPane = new ScrollPane();
        itemsScrollPane.setId("item-scroll-pane");
        itemsScrollPane.setFitToWidth(true);

        // The vertical scrollbar is shown as needed, but the horizontal scrollbar is never shown
        itemsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        itemsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Create a container to hold the item frames
        var items = new VBox();
        items.setId("items");
        items.setAlignment(Pos.TOP_CENTER);

        // Set the content of the item scroll pane to the item frame container
        itemsScrollPane.setContent(items);

        // Add the item scroll pane
        inputFields.getChildren().add(itemsScrollPane);
    }

    private void addCheckoutButton() {
        // Create button
        var checkoutButton = new Button("Charge");
        checkoutButton.setId("checkout-button");
        checkoutButton.setAlignment(Pos.CENTER);
        checkoutButton.setMaxWidth(Double.MAX_VALUE);

        // Create a HBox to contain the checkout button
        var checkoutButtonContainer = new VBox(checkoutButton);
        HBox.setHgrow(checkoutButtonContainer, Priority.ALWAYS);

        // Add components to inputFields
        inputFields.getChildren().add(checkoutButtonContainer);
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

    private void addCustomerDropdownItem(String item) {
        var checkoutPanelContainer = (VBox) root.lookup("#checkout-panel-container");

        if (checkoutPanelContainer != null) {
            var customerDropdown = (ComboBox<String>) checkoutPanelContainer.lookup("#customer-dropdown");

            if (customerDropdown != null) {
                customerDropdown.getItems().add(item);
            }
        }
    }

    private void removeCustomerDropdownItem(String item) {
        var checkoutPanelContainer = (VBox) root.lookup("#checkout-panel-container");

        if (checkoutPanelContainer != null) {
            var customerDropdown = (ComboBox<String>) checkoutPanelContainer.lookup("#customer-dropdown");

            if (customerDropdown != null) {
                customerDropdown.getItems().remove(item);
            }
        }
    }

    private @NotNull HBox createItemCard(@NotNull Item item, String currency) {
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
    private void addItemCard(HBox itemCard) {
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
    private void removeItemCard(HBox itemCard) {
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

    private void addDiscount(double discount, String currency) {
        var discountContainer = new HBox();

        var discountLabel = new Label("Discounts:");
        discountLabel.setId("discount-label");

        var spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        var discountAmount = new Label( currency + discount);
        discountAmount.setId("discount-amount");

        discountContainer.getChildren().addAll(discountLabel, spacer, discountAmount);
        inputFields.getChildren().add(discountContainer);
    }

    private void updateCheckoutPrice() {
        // Implement the update of the checkout price
    }
}
