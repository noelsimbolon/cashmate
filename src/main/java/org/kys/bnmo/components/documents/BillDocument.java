package org.kys.bnmo.components.documents;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentFactory;
import org.kys.bnmo.helpers.loaders.StyleLoadHelper;

public class BillDocument implements ComponentFactory {

    private static final BillPageBuilder billPageBuilder = new BillPageBuilder();
    @Override
    public Pane getComponent() {

        ScrollPane scrollPane = new ScrollPane();
        VBox pages = new VBox();

        for (int i = 0; i < 2; i++)
        {
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
