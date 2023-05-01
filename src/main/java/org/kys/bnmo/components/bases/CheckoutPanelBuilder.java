package org.kys.bnmo.components.bases;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentBuilder;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class CheckoutPanelBuilder extends ComponentBuilder {

    private VBox inputFields;

    @Override
    public void reset() {
        VBox root = new VBox();
        root.setId("checkout-panel-container");

        inputFields = new VBox();
        inputFields.setId("checkout-panel-input-fields");

        root.getChildren().add(inputFields);

        StyleLoadHelper helper = new StyleLoadHelper("/styles/CheckoutPanel.css");
        helper.load(root);

        setRoot(root);
    }

    public void addButtonAndTextField(String placeholder) {
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

    public void addCustomerDropdown(String placeholder, String[] items) {
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

    public void addItemScrollPane() {
        // Create a scroll pane to contain all the checkout items
        var itemsScrollPane = new ScrollPane();
        itemsScrollPane.setId("item-scroll-pane");
        itemsScrollPane.setFitToWidth(true);

        // The vertical scrollbar is shown as needed, but the horizontal scrollbar is never shown
        itemsScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        itemsScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        // Create a container to hold the item frames
        var items = new VBox();
        items.setId("items");
        items.setAlignment(Pos.TOP_CENTER);

        // Set the content of the item scroll pane to the item frame container
        itemsScrollPane.setContent(items);

        // Add the item scroll pane
        inputFields.getChildren().add(itemsScrollPane);
    }

    public void addCheckoutButton() {
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
}
