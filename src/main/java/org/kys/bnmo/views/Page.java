package org.kys.bnmo.views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.AddMember;
import org.kys.bnmo.components.BillDisplay;
import org.kys.bnmo.components.ComponentFactory;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class Page implements ComponentFactory {
    private Parent navBar;
    private TabPane tabPane;
    private Parent currentPage;
    @Override
    public Parent getComponent() {
        HBox root = new HBox();

        navBar = new VBox();
        tabPane = new TabPane();

        navBar.getStyleClass().add("navbar");

        Tab tab = new Tab("Membership");
        tab.setContent(new AddMember().getComponent());

        Tab tab2 = new Tab("Bill[id]");
        tab2.setContent(new BillDisplay().getComponent());

        tabPane.getTabs().addAll(tab2, tab);

        root.getChildren().addAll(navBar, tabPane);
        root.getStyleClass().add("page");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/page.css");
        helper.load(root);

        return root;
    }
}
