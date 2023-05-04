package org.kys.bnmo.components.tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import org.kys.bnmo.components.bases.TableBuilder;
import org.kys.bnmo.helpers.Table.TableData;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.Member;
import org.kys.bnmo.model.VIP;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MembershipTab extends TabContainer {

    private static final TableBuilder tableBuilder = new TableBuilder();
    // private static final CustomerController customerController = new CustomerController();
    @Override
    protected Pane getContent() {

        // TODO: Fill table with customer data in DataStore
        // List<Customer> customers = customerController.fetchAll();

        // Create temporary customer data
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                // Create normal customer
                Customer c = new Customer();
                customers.add(c);
            } else if (i % 3 == 1) {
                // Create member customer
                Member c = new Member("Member " + i, "08123456789");
                customers.add(c);
            } else {
                // Create VIP customer
                VIP c = new VIP("VIP " + i, "08123456789");
                customers.add(c);
            }
        }

        System.out.println(customers);

        // Table heading
        List<String> tableHeadings = new ArrayList<>(Arrays.asList("Customer ID", "Name", "Phone", "Status", "Class", "Action"));

        // List of table content
        List<List<String>> tableContent = new ArrayList<>();

        // List of context menus for action column
        List<ContextMenu> contextMenus = new ArrayList<>();

        for (Customer customer : customers) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(customer.getCustomerID()));
            if (customer instanceof Member) {
                row.add(((Member) customer).getName());
                row.add(((Member) customer).getPhoneNumber());
                row.add(((Member) customer).getStatus());
                row.add(customer.getCustomerClass());
            } else {
                row.add("N/A");
                row.add("N/A");
                row.add("N/A");
                row.add(customer.getCustomerClass());
            }

            tableContent.add(row);

            // Action menu
            ContextMenu menu;
            if (customer instanceof Member) {
                MenuItem item1 = new MenuItem("Edit");
                MenuItem item2;
                if (((Member) customer).getStatus().equals("Active")) {
                    item2 = new MenuItem("Deactivate");
                } else {
                    item2 = new MenuItem("Activate");
                }

                if (customer instanceof VIP) {
                    // Add event listener
                    item1.setOnAction(e -> {
                        System.out.println("Edit VIP");
                    });
                    menu = new ContextMenu(item1, item2);
                } else {
                    MenuItem item3 = new MenuItem("Promote");
                    menu = new ContextMenu(item1, item2, item3);
                }
            } else {
                MenuItem item1 = new MenuItem("Apply Membership");
                menu = new ContextMenu(item1);
            }
            contextMenus.add(menu);
        }

        // Set table data
        TableData tableData = new TableData(tableHeadings, tableContent, null, contextMenus);
        List<Integer> indices = new ArrayList<>();
        indices.add(1);
        tableBuilder.setTableData(tableData, indices);

        // Add search bar
        tableBuilder.addSearchBar();

        // Set content column alignments as center left
        for (int i = 0; i < tableHeadings.size(); i++) {
            tableBuilder.setColumnAlignment(i, Pos.CENTER_LEFT);
        }

        // Set ID and action alignment as center
        tableBuilder.setColumnAlignment(0, Pos.CENTER);
        tableBuilder.setColumnAlignment(tableHeadings.size() - 1, Pos.CENTER);

        // Set the root
        Pane root = tableBuilder.getAndResetComponent();

        VBox.setVgrow(root, Priority.ALWAYS);
        return root;
    }

    @Override
    protected void additionalAction()
    {
//        Button backButton = new Button();
//        new IconButtonHelper().setButtonGraphic(backButton, "/icon/BackArrow.png", 20, 20);
//        backButton.getStyleClass().add("back-button");
//        getHeader().getChildren().add(0, backButton);

        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Customer List");
    }
}
