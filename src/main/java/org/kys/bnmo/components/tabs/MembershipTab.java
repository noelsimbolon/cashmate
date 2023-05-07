package org.kys.bnmo.components.tabs;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import org.kys.bnmo.components.bases.TableBuilder;
import org.kys.bnmo.controllers.CustomerController;
import org.kys.bnmo.controllers.MemberController;
import org.kys.bnmo.events.NavigationHandler;
import org.kys.bnmo.helpers.views.tables.TableData;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.Member;

import java.util.*;
import java.util.stream.IntStream;

public class MembershipTab extends TabContainer {

    private static final TableBuilder tableBuilder = new TableBuilder();

    private static final CustomerController customerController = new CustomerController();
    private static final MemberController memberController = new MemberController();

    private final NavigationHandler memberActionHandler;
    private final EventHandler<ActionEvent> backHandler;
    public MembershipTab(NavigationHandler memberActionHandler, EventHandler<ActionEvent> backHandler)
    {
        this.memberActionHandler = memberActionHandler;
        this.backHandler = backHandler;
    }
    @Override
    protected Pane getContent() {

        // Fetch customer data
        ArrayList<Customer> customers = customerController.fetchAll();

        // Concatenate with member data
        ArrayList<Member> members = memberController.fetchAll();
        customers.addAll(members);

        // Table heading
        List<String> tableHeadings = new ArrayList<>(Arrays.asList("Customer ID", "Name", "Phone", "Status", "Level", "Points", "Action"));

        // List of table content
        List<List<String>> tableContent = new ArrayList<>();

        // List of context menus for action column
        List<ContextMenu> contextMenus = new ArrayList<>();

        for (Customer customer : customers) {
            List<String> row = new ArrayList<>();
            row.add(customer.getCustomerID().toString());
            if (customer instanceof Member) {
                row.add(((Member) customer).getName());
                row.add(((Member) customer).getPhoneNumber());
                row.add(((Member) customer).getStatus());
                row.add(customer.getMemberLevel());
                row.add(String.valueOf(((Member) customer).getPoints()));
            } else {
                row.add("N/A");
                row.add("N/A");
                row.add("N/A");
                row.add(customer.getMemberLevel());
                row.add("0");
            }

            tableContent.add(row);

            // Action menu
            ContextMenu menu;
            if (customer instanceof Member) {
                UUID editedCustomerID = customer.getCustomerID();
                int editedCustomerIndex = IntStream.range(0, members.size()).filter(i -> members.get(i).getCustomerID().equals(editedCustomerID)).findFirst().orElse(-1);

                // Set menu item action
                // Edit button
                MenuItem item1 = new MenuItem("Edit");
                item1.setOnAction(e -> {
                    memberActionHandler.getEventHandler(
                            new MemberFormTab("Edit member", (Member) customer, backHandler)
                    ).handle(e);
                });

                // Activate/Deactivate button
                MenuItem item2;
                if (((Member) customer).getStatus().equals("Active")) {
                    item2 = new MenuItem("Deactivate");
                    item2.setOnAction(e -> {

                        // If customer is not found, then do not save but refresh the tab
                        if (editedCustomerIndex == -1) {
                            System.out.println("Customer not found");
                            memberActionHandler.getEventHandler(
                                    new MembershipTab(memberActionHandler, backHandler)
                            ).handle(e);
                        }

                        // Get customer from list
                        Member editedCustomer = members.get(editedCustomerIndex);

                        // Delete customer from list
                        members.remove(editedCustomerIndex);

                        // Update customer status
                        editedCustomer.deactivate();

                        // Add customer back to list
                        members.add(editedCustomerIndex, editedCustomer);

                        // Save to DataStore
                        memberController.save(members);

                        // Refresh tab
                        memberActionHandler.getEventHandler(
                                new MembershipTab(memberActionHandler, backHandler)
                        ).handle(e);
                    });
                } else {
                    item2 = new MenuItem("Activate");
                    item2.setOnAction(e -> {

                        // If customer is not found, then do not save but refresh the tab
                        if (editedCustomerIndex == -1) {
                            System.out.println("Customer not found");
                            memberActionHandler.getEventHandler(
                                    new MembershipTab(memberActionHandler, backHandler)
                            ).handle(e);
                        }

                        // Get customer from list
                        Member editedCustomer = members.get(editedCustomerIndex);

                        // Delete customer from list
                        members.remove(editedCustomerIndex);

                        // Update customer status
                        editedCustomer.activate();

                        // Add customer back to list
                        members.add(editedCustomerIndex, editedCustomer);

                        // Save to DataStore
                        memberController.save(members);

                        // Refresh tab
                        memberActionHandler.getEventHandler(
                                new MembershipTab(memberActionHandler, backHandler)
                        ).handle(e);
                    });
                }

                // Transaction history button
                // TODO: Show transaction history
                MenuItem item4 = new MenuItem("Transaction History");
                item4.setOnAction(e -> {
                    memberActionHandler.getEventHandler(
                            new HistoryTab(customer.getCustomerID(), memberActionHandler, backHandler)
                    ).handle(e);
                });

                // Promote/Demote button
                MenuItem item3;
                if (customer.getMemberLevel().equals("VIP")) {
                    item3 = new MenuItem("Demote");
                    item3.setOnAction(e -> {
                        // If customer is not found, then do not save but refresh the tab
                        if (editedCustomerIndex == -1) {
                            System.out.println("Customer not found");
                            memberActionHandler.getEventHandler(
                                    new MembershipTab(memberActionHandler, backHandler)
                            ).handle(e);
                        }

                        // Get customer from list
                        Member editedCustomer = members.get(editedCustomerIndex);

                        // Delete customer from list
                        members.remove(editedCustomerIndex);

                        // Update customer member level
                        editedCustomer.demote();

                        // Add customer back to list
                        members.add(editedCustomerIndex, editedCustomer);

                        // Save to DataStore
                        memberController.save(members);

                        // Refresh tab
                        memberActionHandler.getEventHandler(
                                new MembershipTab(memberActionHandler, backHandler)
                        ).handle(e);
                    });
                } else {
                    item3 = new MenuItem("Promote");
                    item3.setOnAction(e -> {
                        // If customer is not found, then do not save but refresh the tab
                        if (editedCustomerIndex == -1) {
                            System.out.println("Customer not found");
                            memberActionHandler.getEventHandler(
                                    new MembershipTab(memberActionHandler, backHandler)
                            ).handle(e);
                        }

                        // Get customer from list
                        Member editedCustomer = members.get(editedCustomerIndex);

                        // Delete customer from list
                        members.remove(editedCustomerIndex);

                        // Update customer member level
                        editedCustomer.promote();

                        // Add customer back to list
                        members.add(editedCustomerIndex, editedCustomer);

                        // Save to DataStore
                        memberController.save(members);

                        // Refresh tab
                        memberActionHandler.getEventHandler(
                                new MembershipTab(memberActionHandler, backHandler)
                        ).handle(e);
                    });
                }

                // Add menu items to menu
                menu = new ContextMenu(item1, item2, item3, item4);

            } else {
                MenuItem item1 = new MenuItem("Apply Membership");
                item1.setOnAction(e -> {
                    memberActionHandler.getEventHandler(
                            new MemberFormTab("Apply membership", customer.getCustomerID(), backHandler)
                    ).handle(e);
                });

                MenuItem item2 = new MenuItem("Transaction History");
                item2.setOnAction(e -> {
                    memberActionHandler.getEventHandler(
                            new HistoryTab(customer.getCustomerID(), memberActionHandler, backHandler)
                    ).handle(e);
                });

                menu = new ContextMenu(item1, item2);
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

        // Set ID, action, and points alignment as center
        tableBuilder.setColumnAlignment(0, Pos.CENTER);
        tableBuilder.setColumnAlignment(tableHeadings.size() - 1, Pos.CENTER);
        tableBuilder.setColumnAlignment(tableHeadings.size() - 2, Pos.CENTER);

        // Set the root
        Pane root = tableBuilder.getAndResetComponent();

        VBox.setVgrow(root, Priority.ALWAYS);
        return root;
    }

    @Override
    protected void additionalAction()
    {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Customer List");
    }
}
