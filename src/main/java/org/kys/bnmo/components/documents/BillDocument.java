package org.kys.bnmo.components.documents;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.ComponentFactory;
import org.kys.bnmo.controllers.TransactionController;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Order;
import org.kys.bnmo.model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BillDocument implements ComponentFactory {

    // TODO : TRANSACTION CONTROLLER
    private static final BillPageBuilder billPageBuilder = new BillPageBuilder();
    private static final TransactionController transactionController = new TransactionController();
    @Setter
    private int transactionID;

    public BillDocument(int transactionID) {
        this.transactionID = transactionID;
    }

    @Override
    @NotNull
    public Pane getComponent() {

        ScrollPane scrollPane = new ScrollPane();
        VBox pages = new VBox();

        // TODO: GET DATA FROM TRANSACTION CONTROLLER
        ArrayList<Transaction> transactions = transactionController.fetchAll();
        Transaction transaction = transactionController.fetchByID(transactionID).get(0);

        billPageBuilder.setTransaction(transaction);

        int orderIdx = 0;
        for (Order order : transaction.getOrders()) {
            if (orderIdx % 10 == 0) {
                if (orderIdx != 0) {
                    pages.getChildren().add(billPageBuilder.getAndResetComponent());
                }
                billPageBuilder.addHeaders();
                billPageBuilder.addFooters();
                billPageBuilder.addSummary();
                billPageBuilder.setTransaction(transaction);
            }
            billPageBuilder.addRows(order);
            orderIdx++;
        }
        pages.getChildren().add(billPageBuilder.getAndResetComponent());


        pages.getStyleClass().add("document");

        scrollPane.setContent(pages);

        StyleLoadHelper helper = new StyleLoadHelper(
                "/styles/document.css");
        helper.load(scrollPane);

        VBox root = new VBox();

        root.getChildren().add(scrollPane);

        return root;
    }
}
