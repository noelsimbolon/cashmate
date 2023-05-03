package org.kys.bnmo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kys.bnmo.components.tabs.*;
import org.kys.bnmo.helpers.IconButtonHelper;
import org.kys.bnmo.helpers.StyleLoadHelper;
import org.kys.bnmo.views.Page;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BnmoApplication extends Application {

    private final static IconButtonHelper navbarHelper = new IconButtonHelper();
    private List<Tab> defaultTabs = new ArrayList();
    private Pane root;
    private List<Button> navbarButtons;
    private TabPane tabPane;

    private class NavbarButtonHandler implements EventHandler<ActionEvent> {
        private Button button;
        private TabPane tabPane;

        public NavbarButtonHandler(Button button, TabPane tabPane) {
            this.button = button;
            this.tabPane = tabPane;
        }
        @Override
        public void handle(ActionEvent event) {
            List<Tab> tabs = tabPane.getTabs();

            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

            if (selectedTab != null
                    && button.getId() == tabPane.getSelectionModel().getSelectedItem().getId()) return;

            for (Tab tab: tabs)
            {
                if (tab.getId() == button.getId())
                {
                    tabPane.getSelectionModel().select(tab);
                    return;
                }
            }

            for (Tab tab: defaultTabs)
            {
                if (tab.getId() == button.getId())
                {
                    tabPane.getTabs().add(tab);
                    tabPane.getSelectionModel().select(tab);
                    return;
                }
            }
        }
    }

    private class TabChangeListener implements ChangeListener<Tab> {
        private List<Button> buttons;
        public TabChangeListener(List<Button> buttons) {
            this.buttons = buttons;
        }
        @Override
        public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
            if (newTab != null) {
                for (Button button: buttons)
                {
                    if (button.getId() == newTab.getId())
                    {
                        button.fire();
                        return;
                    }

                    else
                    {
                        navbarHelper.unselectButton(button);
                    }
                }
            }

            else if (oldTab != null)
            {
                for (Button button: buttons)
                {
                    if (button.getId() == oldTab.getId())
                    {
                        navbarHelper.unselectButton(button);
                        return;
                    }
                }
            }
        }
    }

    private TabPane getTabPane() {return ((TabPane)root.lookup("#parent-tab-pane"));}

    private List<Button> getNavbarButtons()
    {
        return ((Pane)((ScrollPane)root.lookup("#navbar-scroll-panel")).getContent())
                .getChildren()
                .stream()
                .map(button -> (Button) button)
                .toList();
    }

    @Override
    public void start(Stage stage) {

        // initialize page
        Page page = new Page();
        page.addTab(new MembershipTab().getComponent(), "Membership");
        page.addTab(new CashierTab().getComponent(), "Cashier");
        page.addTab(new CatalogueTab().getComponent(), "Catalogue");
        page.addTab(new SettingTab(stage).getComponent(), "Settings");

        root = page.getAndResetComponent();

        // copy and clear tabs
        tabPane = getTabPane();
        defaultTabs.addAll(tabPane.getTabs());
        tabPane.getTabs().clear();

        // navbar handler
        navbarButtons = getNavbarButtons();
        navbarButtons.forEach(button -> {
            NavbarButtonHandler handler = new NavbarButtonHandler(button, tabPane);
            button.addEventHandler(ActionEvent.ACTION, handler);
        });

        tabPane.getSelectionModel()
                .selectedItemProperty()
                .addListener(new TabChangeListener(navbarButtons));

        // styles
        StyleLoadHelper helper = new StyleLoadHelper("/styles/global.css");
        helper.load(root);

        Scene scene = new Scene(root, 1290, 650);
        stage.setTitle("My JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

// Checkout panel testing
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // Create a new instance of CheckoutPanel
//        var checkoutPanel = new CheckoutPanel();
//
//        // Create a new scene with the root node of CheckoutPanel
//        var scene = new Scene(checkoutPanel.getComponent(), 250, 326);
//
//        // Set the scene and show the stage
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
