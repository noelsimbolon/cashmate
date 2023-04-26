package org.kys.bnmo.views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentFactory;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class Page implements ComponentFactory {
    private Parent navBar;
    private Parent tabPane;
    private Parent currentPage;
    @Override
    public Parent getComponent() {
        HBox root = new HBox();

        navBar = new VBox();
        tabPane = new TabPane();

        root.getChildren().addAll(new Label("TesstTTT"));
        root.getStyleClass().add("page");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/page.css");
        helper.load(root);

        return root;
    }
}
