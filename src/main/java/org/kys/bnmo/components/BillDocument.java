package org.kys.bnmo.components;

import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class BillDocument implements ComponentFactory {

    @Override
    public Pane getComponent() {

        ScrollPane scrollPane = new ScrollPane();
        VBox pages = new VBox();
        BillPageBuilder billPageBuilder = new BillPageBuilder();

        for (int i = 0; i < 2; i++)
        {
            billPageBuilder.addRows();
            billPageBuilder.addSummary();
            pages.getChildren().add(billPageBuilder.getAndResetComponent());
        }

        pages.getStyleClass().add("document");

        scrollPane.setContent(pages);

        StyleLoadHelper helper = new StyleLoadHelper(
                "/styles/document.css", "/styles/bill.css");
        helper.load(scrollPane);

        VBox root = new VBox();

        root.getChildren().add(scrollPane);
        root.getStyleClass().add("tab-content");

        return root;
    }
}
