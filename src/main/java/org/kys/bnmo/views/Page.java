package org.kys.bnmo.views;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.kys.bnmo.components.*;
import org.kys.bnmo.components.bases.Navbar;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class Page extends ComponentBuilder {
    private Pane navBar;
    private TabPane tabPane;
    private Parent currentPage;

    public void addTab(Parent content, String title)
    {
        Tab tab = new Tab(title);
        tab.setContent(content);
        tab.setId(title.replaceAll("\\s+",""));

        tabPane.getTabs().add(tab);
    }

    @Override
    public void reset() {
        HBox root = new HBox();

        navBar = new Navbar().getComponent();
        tabPane = new TabPane();
        tabPane.setId("parent-tab-pane");

        root.getChildren().addAll(navBar, tabPane);
        root.getStyleClass().add("page");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/page.css");
        helper.load(root);
        setRoot(root);
    }

    @Override
    public Pane getAndResetComponent()
    {
        return super.getAndResetComponent();
    }

}
