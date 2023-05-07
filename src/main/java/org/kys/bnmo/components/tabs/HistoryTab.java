package org.kys.bnmo.components.tabs;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.bases.TableBuilder;
import org.kys.bnmo.controllers.TransactionController;
import org.kys.bnmo.events.NavigationHandler;
import javafx.scene.layout.Pane;
import org.kys.bnmo.helpers.views.IconButtonHelper;
import org.kys.bnmo.helpers.views.tables.TableData;
import org.kys.bnmo.model.Transaction;

import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class HistoryTab  extends TabContainer {
    private static final TableBuilder tableBuilder = new TableBuilder();
    private static final TransactionController transactionController = new TransactionController();
    private static final IconButtonHelper iconButtonHelper = new IconButtonHelper();
    private final NavigationHandler historyActionHandler;
    private final EventHandler<ActionEvent> backHandler;
    private final UUID customerId;
    private final String customerName;
    public HistoryTab(UUID customerId, NavigationHandler historyActionHandler, EventHandler<ActionEvent> backHandler) {
        this.customerId = customerId;
        this.customerName = "Customer #" + customerId.toString().substring(0, 8) + "...";
        this.historyActionHandler = historyActionHandler;
        this.backHandler = backHandler;
    }

    public HistoryTab(UUID customerId, String customerName, NavigationHandler historyActionHandler, EventHandler<ActionEvent> backHandler) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.historyActionHandler = historyActionHandler;
        this.backHandler = backHandler;
    }

    @Override
    protected Pane getContent() {
        // Fetch transaction data
        ArrayList<Transaction> transactions = transactionController.fetchByCustomerID(customerId);

        // Sort by transaction ID
//        transactions.sort((t1, t2) -> t2.getTransactionID() - t1.getTransactionID());

        // Table headings
        List<String> tableHeadings = new ArrayList<>(Arrays.asList("Transaction ID", "Date", "Total Price", "Discount", "Action"));

        // List of table content
        List<List<String>> tableContent = new ArrayList<>();

        // List of context menu items
        List<ContextMenu> contextMenuItems = new ArrayList<>();

        for (Transaction transaction : transactions) {
            List<String> row = new ArrayList<>();

            row.add(String.valueOf(transaction.getTransactionID()));
            row.add(transaction.getDate().toString());
            row.add(String.valueOf(transaction.getTotalPrice()));
            row.add(String.valueOf(transaction.getDiscount()));

            tableContent.add(row);

            // Action menu
            MenuItem viewBill = new MenuItem("View Bill");

            viewBill.setOnAction(e -> {
                historyActionHandler.getEventHandler(
                        new BillTab(transaction.getTransactionID()),
                        "Bill"
                ).handle(e);
            });

            // Add action menu to context menu items
            ContextMenu contextMenu = new ContextMenu(viewBill);
            contextMenuItems.add(contextMenu);
        }

        // Set table data
        TableData tableData = new TableData(tableHeadings, tableContent, null, contextMenuItems);
        List<Integer> indices = new ArrayList<>();
        indices.add(1);
        tableBuilder.setTableData(tableData, indices);

        // Add search bar
        tableBuilder.addSearchBar();

        // Set content column alignments as center left
        for (int i = 0; i < tableHeadings.size(); i++) {
            tableBuilder.setColumnAlignment(i, Pos.CENTER_LEFT);
        }

        // Set ID, and actions alignment as center
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
        Button backButton = new Button();
        iconButtonHelper.setButtonGraphic(backButton, "/icon/BackArrow.png", 20, 20);
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(backHandler);
        getHeader().getChildren().add(0, backButton);

        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Transaction History for " + customerName);
    }
}
