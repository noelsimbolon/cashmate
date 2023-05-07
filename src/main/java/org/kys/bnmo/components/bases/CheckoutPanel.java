package org.kys.bnmo.components.bases;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.controllers.CustomerController;
import org.kys.bnmo.controllers.InventoryItemController;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Member;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class CheckoutPanel extends VBox {
    private final VBox inputFields;

    private class TemporaryOrder {
        @Getter
        private InventoryItem item;
        @Getter
        private IntegerProperty quantity;

        public TemporaryOrder(InventoryItem item, int quantity) {
            this.item = item;
            this.quantity = new SimpleIntegerProperty(quantity);
        }
    }

    private class TemporaryBill {
        @Getter
        private final List<TemporaryOrder> orders;
        @Getter
        private final Customer customer;

        public TemporaryBill(Customer customer) {
            orders = new ArrayList<>();
            this.customer = customer;
        }
    }

    private List<TemporaryBill> temporaryBills;
    private List<Member> members;
    private ComboBox<String> customerDropdown;
    private VBox checkoutPanelContainer;
    private Button checkoutButton;
    private Text discountAmountLabel;

    public CheckoutPanel(List<Member> members) {
        super();
        this.members = new ArrayList<>(members);
        this.temporaryBills = new ArrayList<>();

        // Initialize checkout panel container
        checkoutPanelContainer = new VBox();
        checkoutPanelContainer.setId("checkout-panel-container");

        // Initialize input fields
        inputFields = new VBox();
        inputFields.setId("checkout-panel-input-fields");

        // Add input fields to check out panel container
        checkoutPanelContainer.getChildren().add(inputFields);

        // Load CSS
        StyleLoadHelper helper = new StyleLoadHelper("/styles/CheckoutPanel.css");
        helper.load(checkoutPanelContainer);

        addButtonAndTextField();
        customerDropdown = new ComboBox<>();
        addCustomerDropdown();
        // you can also create items in the customer dropdown when providing the second argument like the following
        // addCustomerDropdown("Select customer", new String[] {"Customer 1", "Jojo", "Fio"});

        addItemScrollPane();

        var discountContainer = new HBox();

        var discountLabel = new Text("Discounts:");
        discountLabel.setId("discount-label");

        var spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        discountAmountLabel = new Text("Rp" + 0);
        discountAmountLabel.setId("discount-amount");

        discountContainer.getChildren().addAll(discountLabel, spacer, discountAmountLabel);
        inputFields.getChildren().add(discountContainer);

        checkoutButton = new Button("Charge");
        addCheckoutButton();
        checkoutButton.setOnMouseClicked(e -> {
            checkoutCurrentBill();
        });

        // Add the checkout panel to the root
        this.getChildren().add(checkoutPanelContainer);
    }

    private void addButtonAndTextField() {
        // Container to hold the plusButton and customerTextField
        var buttonAndTextFieldContainer = new HBox();

        // Create label for button
        var plusLabel = new Label("+");
        plusLabel.setId("plus-label");

        // Create a combo box
        ComboBox<String> customerComboBox = new ComboBox<>();
        customerComboBox.getItems().addAll(members.stream().map(Member::getName).toList());
        customerComboBox.setEditable(true);
        customerComboBox.setPromptText("Enter customer name");
        customerComboBox.setId("customer-editable");
        customerComboBox.getStyleClass().add("customer-dropdown");

        HBox.setHgrow(customerComboBox, Priority.ALWAYS);
        customerComboBox.setMaxWidth(Double.MAX_VALUE);

        // Create button
        var plusButton = new Button();
        plusButton.setId("plus-button");
        plusButton.setGraphic(plusLabel);
        plusButton.setOnMouseClicked(e -> {
            if (customerComboBox.getValue() == null)
                customerComboBox.setValue("");
            int memberIdx = IntStream.range(0, members.size())
                    .filter(i -> customerComboBox.getValue().equalsIgnoreCase(members.get(i).getName()))
                    .findFirst()
                    .orElse(-1);
            int existsIdx = IntStream.range(0, customerDropdown.getItems().size())
                    .filter(i -> customerComboBox.getValue().equalsIgnoreCase(customerDropdown.getItems().get(i)))
                    .findFirst()
                    .orElse(-1);
            if (memberIdx != -1 && existsIdx == -1) {
                temporaryBills.add(new TemporaryBill(members.get(memberIdx)));
                customerDropdown.getItems().add(members.get(memberIdx).getName());
                customerDropdown.getSelectionModel().select(customerDropdown.getItems().size() - 1);
                customerComboBox.setValue(null);
            } else if (existsIdx == -1) {
                Customer customer = new Customer();
                temporaryBills.add(new TemporaryBill(customer));
                customerDropdown.getItems().add("Customer-" + customer.getCustomerID());
                customerDropdown.getSelectionModel().select(customerDropdown.getItems().size() - 1);
                customerComboBox.setValue(null);
            } else if (memberIdx != -1) {
                customerDropdown.getSelectionModel().select(existsIdx);
                customerComboBox.setValue(null);
            }
        });

        // Add plusButton and customerTextField to buttonAndTextFieldContainer
        buttonAndTextFieldContainer.getChildren().addAll(plusButton, customerComboBox);
        buttonAndTextFieldContainer.setId("button-and-text-field-container");

        inputFields.getChildren().add(buttonAndTextFieldContainer);
    }

    private void addCustomerDropdown() {
        // Container to hold the customer dropdown
        HBox customerDropdownContainer = new HBox();
        HBox.setHgrow(customerDropdownContainer, Priority.ALWAYS);

        // Dropdown
        customerDropdown.setPromptText("Select customer");
        customerDropdown.setId("customer-dropdown");
        customerDropdown.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == null || !oldValue.equalsIgnoreCase(newValue)) {
                ScrollPane itemScrollPane = (ScrollPane) checkoutPanelContainer.lookup("#item-scroll-pane");
                VBox cardBox = (VBox) itemScrollPane.getContent();

                cardBox.getChildren().clear();
                temporaryBills.get(customerDropdown.getSelectionModel().getSelectedIndex())
                        .getOrders()
                        .forEach(order -> {
                            addItemCard(createItemCard(order, "Rp"));
                        });
                updateCheckoutPrice();
            }
        });

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
        checkoutButton.setId("checkout-button");
        checkoutButton.setAlignment(Pos.CENTER);
        checkoutButton.setMaxWidth(Double.MAX_VALUE);

        // Create a HBox to contain the checkout button
        var checkoutButtonContainer = new VBox(checkoutButton);
        HBox.setHgrow(checkoutButtonContainer, Priority.ALWAYS);

        // Add components to inputFields
        inputFields.getChildren().add(checkoutButtonContainer);
    }

    private void addCustomerDropdownItem(String item) {
        var checkoutPanelContainer = (VBox) this.lookup("#checkout-panel-container");

        if (checkoutPanelContainer != null) {
            var customerDropdown = (ComboBox<String>) checkoutPanelContainer.lookup("#customer-dropdown");

            if (customerDropdown != null) {
                customerDropdown.getItems().add(item);
            }
        }
    }

    private void removeCustomerDropdownItem(String item) {
        var checkoutPanelContainer = (VBox) this.lookup("#checkout-panel-container");

        if (checkoutPanelContainer != null) {
            var customerDropdown = (ComboBox<String>) checkoutPanelContainer.lookup("#customer-dropdown");

            if (customerDropdown != null) {
                customerDropdown.getItems().remove(item);
            }
        }
    }

    private @NotNull HBox createItemCard(@NotNull CheckoutPanel.TemporaryOrder order, String currency) {
        var itemCard = new HBox();
        itemCard.setAlignment(Pos.CENTER_LEFT);
        itemCard.setId("item-container");
        HBox.setHgrow(itemCard, Priority.ALWAYS);


        // Loop through the itemList and create a food frame for each item
        var roundedRectangle = new Button();
        roundedRectangle.setId("rounded-rectangle");

        // Labels to hold the item name and its price
        var nameLabel = new Label(order.item.getItemName());
        nameLabel.setId("item-name-label");
        var priceLabel = new Label();
        priceLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            int quantity = order.getQuantity().get();
            double price = order.getItem().getPrice();
            return String.format("%s%d", currency, Math.round(quantity * order.getItem().getPrice()));
        }, order.getQuantity()));
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
        var quantitySpinner = new Spinner<Integer>(0, 99, order.getQuantity().get(), 1);
        quantitySpinner.setId("quantity-spinner");
        quantitySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == 0) {
                temporaryBills.get(customerDropdown.getSelectionModel().getSelectedIndex()).getOrders().remove(order);
                var items = (VBox) this.lookup("#items");
                items.getChildren().remove(itemCard);
            }
            order.getQuantity().set(newValue);
            updateCheckoutPrice();
        });

        var quantitySpinnerContainer = new HBox(quantitySpinner);
        quantitySpinnerContainer.setAlignment(Pos.CENTER_RIGHT);
        quantitySpinnerContainer.setId("quantity-spinner-container");
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
        var itemScrollPane = (ScrollPane) checkoutPanelContainer.lookup("#item-scroll-pane");

        if (itemScrollPane != null) {
            var items = (VBox) itemScrollPane.getContent().lookup("#items");

            if (items != null) {
                // Add the item frame to the container
                items.getChildren().add(itemCard);
            }
        }

        updateCheckoutPrice();
    }

    private void addDiscount(double discount, String currency) {

    }

    private void updateCheckoutPrice() {
        Locale localeID = new Locale("in", "ID");

        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(localeID);
        int amount = 0;
        for (TemporaryOrder order : temporaryBills.get(customerDropdown.getSelectionModel().getSelectedIndex()).getOrders()) {
            amount += order.quantity.get() * order.item.getPrice();
        }
        int initial = amount;

        Customer customer = temporaryBills.get(customerDropdown.getSelectionModel().getSelectedIndex()).customer;
        if (customer instanceof Member member) {
            amount -= Math.min(amount, member.getPoints());
            if (member.getMemberLevel().equalsIgnoreCase("vip"))
                amount *= 0.9;
        }

        discountAmountLabel.setText("Rp" + (initial - amount));

        String formattedAmount = rupiahFormat.format(amount);

        checkoutButton.setText("Charge: " + formattedAmount);
    }

    public void addItem(InventoryItem item) {
        if (customerDropdown.getItems().size() == 0) {
            Customer customer = new Customer();
            temporaryBills.add(new TemporaryBill(customer));
            customerDropdown.getItems().add("Customer-" + customer.getCustomerID());
            customerDropdown.getSelectionModel().select(customerDropdown.getItems().size() - 1);
        }

        List<TemporaryOrder> orders = temporaryBills.get(customerDropdown.getSelectionModel().getSelectedIndex()).getOrders();
        int existsIdx = IntStream.range(0, orders.size())
                .filter(i -> item.equals(orders.get(i).getItem()))
                .findFirst()
                .orElse(-1);

        if (existsIdx == -1) {
            int currentIdx = customerDropdown.getSelectionModel().getSelectedIndex();
            TemporaryOrder order = new TemporaryOrder(item, 1);
            temporaryBills.get(currentIdx).getOrders().add(order);
            addItemCard(createItemCard(order, "Rp"));
        } else {
            var items = (VBox) checkoutPanelContainer.lookup("#items");
            Spinner<Integer> spinner = (Spinner<Integer>) items.getChildren().get(existsIdx).lookup("#quantity-spinner");
            spinner.increment();
        }
    }

    private void checkoutCurrentBill() {
//        TemporaryBill currentBill = temporaryBills.get(customerDropdown.getSelectionModel().getSelectedIndex());
//
//        CustomerController customerController = new CustomerController();
//        List<Customer> dataStoreCustomers = customerController.fetchByID(currentBill.getCustomer().getCustomerID());
//
//        InventoryItemController inventoryItemController = new InventoryItemController();
//        List<InventoryItem> dataStoreItems = inventoryItemController.
    }
}
