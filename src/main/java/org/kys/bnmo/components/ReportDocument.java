package org.kys.bnmo.components;

import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class ReportDocument implements ComponentFactory {
    private ScrollPane root;
    @Override
    public Parent getComponent() {
        root = new ScrollPane();
        VBox pages = new VBox();
        ReportPage reportPageFactory = new ReportPage();

        for (int i = 0; i < 2; i++)
        {
            reportPageFactory.addRows();
            pages.getChildren().add(reportPageFactory.getAndResetComponent());
        }

        pages.getStyleClass().add("document");

        root.setContent(pages);

        StyleLoadHelper helper = new StyleLoadHelper(
                "/styles/document.css", "/styles/report.css");
        helper.load(root);

        return root;
    }
}
