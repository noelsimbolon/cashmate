package org.kys.bnmo.components.documents;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.interfaces.ComponentFactory;
import org.kys.bnmo.controllers.TransactionController;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;
import org.kys.bnmo.model.Transaction;

import java.util.ArrayList;

public class ReportDocument implements ComponentFactory {

    private static final ReportPage reportPageFactory = new ReportPage();

    private static final TransactionController transactionController = new TransactionController();

    @Override
    @NotNull
    public Pane getComponent() {

        VBox root = new VBox();

        ScrollPane scrollPane = new ScrollPane();
        VBox pages = new VBox();

        // Fetch all transactions and split them into chunks of 10
        ArrayList<Transaction> transactions = transactionController.fetchAll();
        var transactionChunks = new ArrayList<ArrayList<Transaction>>();
        var currentChunk = new ArrayList<Transaction>();

        int count = 0;

        for (int i = 0; i < transactions.size(); i++) {
            currentChunk.add(transactions.get(i));
            count++;

            if (count == 10 || i == transactions.size() - 1) {
                transactionChunks.add(currentChunk);
                currentChunk = new ArrayList<>();
                count = 0;
            }
        }


        for (ArrayList<Transaction> chunk : transactionChunks)
        {
            reportPageFactory.addRows(chunk);
            pages.getChildren().add(reportPageFactory.getAndResetComponent());
        }

        pages.getStyleClass().add("document");

        scrollPane.setContent(pages);

        StyleLoadHelper helper = new StyleLoadHelper(
                "/styles/document.css");
        helper.load(scrollPane);

        root.getChildren().add(scrollPane);

        return root;
    }
}
