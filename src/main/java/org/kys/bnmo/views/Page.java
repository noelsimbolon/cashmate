package org.kys.bnmo.views;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.kys.bnmo.components.*;
import org.kys.bnmo.components.bases.Navbar;
import org.kys.bnmo.components.tabs.AddMemberTab;
import org.kys.bnmo.components.tabs.BillTab;
import org.kys.bnmo.components.tabs.ReportTab;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class Page implements ComponentFactory {
    private Parent navBar;
    private TabPane tabPane;
    private Parent currentPage;
    @Override
    public Pane getComponent() {
        HBox root = new HBox();

        navBar = new Navbar().getComponent();
        tabPane = new TabPane();

        navBar.getStyleClass().add("navbar");

        Tab tab = new Tab("Membership");
        tab.setContent(new AddMemberTab().getAndResetComponent());

        Tab tab2 = new Tab("Bill[id]");
        tab2.setContent(new BillTab().getAndResetComponent());

        Tab tab3 = new Tab("Report[id]");
        tab3.setContent(new ReportTab().getAndResetComponent());

        tabPane.getTabs().addAll(tab3, tab2, tab);

        root.getChildren().addAll(navBar, tabPane);
        root.getStyleClass().add("page");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/page.css");
        helper.load(root);

        return root;
    }
}
