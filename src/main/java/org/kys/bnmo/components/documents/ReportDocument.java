package org.kys.bnmo.components.documents;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.ComponentFactory;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class ReportDocument implements ComponentFactory {

    private static final ReportPage reportPageFactory = new ReportPage();

    @Override
    @NotNull
    public Pane getComponent() {

        VBox root = new VBox();

        ScrollPane scrollPane = new ScrollPane();
        VBox pages = new VBox();

        for (int i = 0; i < 2; i++)
        {
            reportPageFactory.addRows();
            pages.getChildren().add(reportPageFactory.getAndResetComponent());
        }

        pages.getStyleClass().add("document");

        scrollPane.setContent(pages);

        StyleLoadHelper helper = new StyleLoadHelper(
                "/styles/document.css", "/styles/report.css");
        helper.load(scrollPane);

        root.getChildren().add(scrollPane);

        return root;
    }
}
