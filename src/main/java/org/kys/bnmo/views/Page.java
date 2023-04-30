package org.kys.bnmo.views;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import org.kys.bnmo.components.*;
import org.kys.bnmo.components.bases.Navbar;
import org.kys.bnmo.components.tabs.AddMemberTab;
import org.kys.bnmo.helpers.StyleLoadHelper;

import java.util.List;

public class Page extends ComponentBuilder {
    private Parent navBar;
    private TabPane tabPane;
    private Parent currentPage;

    public void addTab(Parent content, String title)
    {
        Tab tab = new Tab(title);
        tab.setContent(content);
        tab.setId(title);

        tabPane.getTabs().add(tab);
    }

    @Override
    public void reset() {
        HBox root = new HBox();

        navBar = new Navbar().getComponent();
        tabPane = new TabPane();

        navBar.getStyleClass().add("navbar");

        root.getChildren().addAll(navBar, tabPane);
        root.getStyleClass().add("page");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/page.css");
        helper.load(root);
        root.setId("parent-tab-pane");

        setRoot(root);
    }

}
