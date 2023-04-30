package org.kys.bnmo.views;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import org.kys.bnmo.components.*;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class Page implements ComponentFactory {
    private Parent navBar;
    private TabPane tabPane;
    private Parent currentPage;
    @Override
    public Parent getComponent() {
        HBox root = new HBox();

        navBar = new Navbar().getComponent();
        tabPane = new TabPane();

        navBar.getStyleClass().add("navbar");

        Tab tab = new Tab("Membership");
        tab.setContent(new AddMember().getComponent());

        Tab tab2 = new Tab("Bill[id]");
        tab2.setContent(new BillDocument().getComponent());

        Tab tab3 = new Tab("Report[id]");
        tab3.setContent(new ReportDocument().getComponent());

        tabPane.getTabs().addAll(tab3, tab2, tab);

        root.getChildren().addAll(navBar, tabPane);
        root.getStyleClass().add("page");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/page.css");
        helper.load(root);

        return root;
    }
}
