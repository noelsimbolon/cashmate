package org.kys.bnmo.components.documents;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.interfaces.ComponentFactory;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;

public class BillDocument implements ComponentFactory {

    // TODO : TRANSACTION CONTROLLER
    private static final BillPageBuilder billPageBuilder = new BillPageBuilder();
    private int customerID;
    public BillDocument(int customerID)
    {
        this.customerID = customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    @Override
    @NotNull
    public Pane getComponent() {

        ScrollPane scrollPane = new ScrollPane();
        VBox pages = new VBox();

        // TODO: GET DATA FROM TRANSACTION CONTROLLER
        // TODO: LOOP FOR EVERY 10 ENTRIES
        for (int i = 0; i < 2; i++)
        {
            // TODO: PASS ARRAYLIST OF TRANSACTION MODELS INSIDE ADDROWS (MAX 10 LENGTH)
            billPageBuilder.addRows();
            billPageBuilder.addSummary();
            pages.getChildren().add(billPageBuilder.getAndResetComponent());
        }

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
