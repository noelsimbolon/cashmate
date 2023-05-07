package org.kys.bnmo;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kys.bnmo.components.SingleComponent;
import org.kys.bnmo.components.documents.BillDocument;
import org.kys.bnmo.components.interfaces.ComponentFactory;
import org.kys.bnmo.components.tabs.*;
import org.kys.bnmo.controllers.DataStore;
import org.kys.bnmo.events.NavigationHandler;
import org.kys.bnmo.helpers.plugins.PluginLoader;
import org.kys.bnmo.helpers.views.DocumentPrinter;
import org.kys.bnmo.helpers.views.IconButtonHelper;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;
import org.kys.bnmo.plugins.adapters.PageAdapter;
import org.kys.bnmo.plugins.base.PluginService;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;
import org.kys.bnmo.views.Page;

import java.lang.reflect.Method;
import java.util.*;

public class BnmoApplication extends Application {
    private class DefaultTab {
        public ComponentFactory factory;
        public Tab tab;
        public DefaultTab(ComponentFactory factory, Tab tab)
        {
            this.factory = factory;
            this.tab = tab;
        }
    }

    private List<DefaultTab> defaultTabs = new ArrayList<>();
    private final static IconButtonHelper navbarHelper = new IconButtonHelper();
    private Pane root;
    private List<Button> navbarButtons;
    private TabPane tabPane;

    private class NavbarButtonHandler implements EventHandler<ActionEvent> {
        private Button button;

        public NavbarButtonHandler(Button button) {
            this.button = button;
        }
        @Override
        public void handle(ActionEvent event) {
            List<Tab> tabs = tabPane.getTabs();

            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

            if (selectedTab != null
                    && Objects.equals(button.getId(), tabPane.getSelectionModel().getSelectedItem().getId())) return;

            for (Tab tab: tabs)
            {
                if (Objects.equals(tab.getId(), button.getId()))
                {
                    tabPane.getSelectionModel().select(tab);
                    return;
                }
            }

            for (DefaultTab defaultTab: defaultTabs)
            {
                defaultTab.tab = Page.getTemplateTab(
                        defaultTab.factory.getComponent(),
                        defaultTab.tab.getId()
                );

                if (Objects.equals(defaultTab.tab.getId(), button.getId()))
                {
                    tabPane.getTabs().add(defaultTab.tab);
                    tabPane.getSelectionModel().select(defaultTab.tab);
                    return;
                }
            }
        }
    }

    private class TabChangeListener implements ChangeListener<Tab> {

        @Override
        public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
            if (newTab != null) {
                for (Button button: navbarButtons)
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
                for (Button button: navbarButtons)
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

    private class TabNavigationHandler implements NavigationHandler {

        private String title;

        TabNavigationHandler(String title)
        {
            this.title = title;
        }
        private class ReplaceTabAction implements EventHandler<ActionEvent> {
            private TabContainer defaultFactory;
            private String title;
            public ReplaceTabAction(TabContainer defaultFactory, String title) {

                this.defaultFactory = defaultFactory;
                this.title = title;
            }
            @Override
            public void handle(ActionEvent event) {

                Tab newTab = Page.getTemplateTab(defaultFactory.getComponent(), title);

                List<Tab> tabs = tabPane.getTabs();

                boolean found = false;

                for (int i = 0; i < tabs.size(); i++)
                {
                    if (tabs.get(i).getId() == newTab.getId())
                    {
                        tabPane.getTabs().remove(i);
                        tabPane.getTabs().add(i, newTab);
                        found = true;
                        break;
                    }
                }

                if (found)
                {
                    for (Button button: navbarButtons)
                    {
                        if (button.getId() == newTab.getId()) button.fire();
                    }
                }

                else
                {
                    tabPane.getTabs().add(newTab);
                    tabPane.getSelectionModel().select(newTab);
                }

            }
        }

        @Override
        public EventHandler<ActionEvent> getEventHandler(TabContainer factory) {
            return new ReplaceTabAction(factory, title);
        }

        @Override
        public EventHandler<ActionEvent> getEventHandler(TabContainer factory, String title) {
            return new ReplaceTabAction(factory, title);
        }
    }

    private class BackTabAction implements EventHandler<ActionEvent> {
        private String title;
        public BackTabAction(String title) {
            this.title = title;
        }
        @Override
        public void handle(ActionEvent event) {

            List<Tab> tabs = tabPane.getTabs();

            for (int i = 0; i < tabs.size(); i++)
            {
                if (tabs.get(i).getId() == Page.getTemplateId(title))
                {
                    tabPane.getTabs().remove(i);
                    break;
                }
            }

            for (Button button: navbarButtons)
            {
                if (button.getId() == Page.getTemplateId(title)) button.fire();
            }
        }
    }

    private TabPane getTabPane() {
        return ((TabPane)root.lookup("#parent-tab-pane"));
    }

    private List<Button> getNavbarButtons()
    {
        return ((Pane)((ScrollPane)root.lookup("#navbar-scroll-panel")).getContent())
                .getChildren()
                .stream()
                .map(button -> (Button) button)
                .toList();
    }

    private void setDefaultTabs(DefaultTab ...defaultTabs)
    {
        this.defaultTabs.addAll(Arrays.stream(defaultTabs).toList());
    }

    private void initiateRoot(Stage stage)
    {

        // initialize factory
        MembershipTab membershipTabFactory = new MembershipTab(
                new TabNavigationHandler("Membership"),
                new BackTabAction("Membership")
        );

        CashierTab cashierTabFactory = new CashierTab();

        CatalogueTab catalogueTabFactory = new CatalogueTab(
                new TabNavigationHandler("Catalogue"),
                new BackTabAction("Catalogue")
        );

        SettingTab settingTabFactory = new SettingTab(stage);

        // initialize page
        Page page = new Page();
        page.addTab(membershipTabFactory.getComponent(), "Membership");
        page.addTab(cashierTabFactory.getComponent(), "Cashier");
        page.addTab(catalogueTabFactory.getComponent(), "Catalogue");
        page.addTab(settingTabFactory.getComponent(), "Settings");
        PluginLoader pluginLoader = new PluginLoader();

        PluginService pluginService = new PluginService(new PageAdapter(page), null, null);
        pluginLoader.runClasses(pluginService);

        root = page.getAndResetComponent();
        tabPane = getTabPane();

        List<DefaultTab> defaultTabs = new ArrayList<>();

        defaultTabs.add(new DefaultTab(membershipTabFactory, tabPane.getTabs().get(0)));
        defaultTabs.add(new DefaultTab(cashierTabFactory, tabPane.getTabs().get(1)));
        defaultTabs.add(new DefaultTab(catalogueTabFactory, tabPane.getTabs().get(2)));
        defaultTabs.add(new DefaultTab(settingTabFactory, tabPane.getTabs().get(3)));

        for (int i = 0; i < pluginService.getPageContainers().size(); i++)
        {
            defaultTabs.add(new DefaultTab(
                            new SingleComponent(pluginService.getPageContainers().get(i)),
                            tabPane.getTabs().get(i+4)));
        }

        setDefaultTabs(defaultTabs.toArray(new DefaultTab[0]));


        tabPane.getTabs().clear();

    }

    private void handleNavigation() {
        // navbar handler
        navbarButtons = getNavbarButtons();
        navbarButtons.forEach(button -> {
            NavbarButtonHandler handler = new NavbarButtonHandler(button);
            button.addEventHandler(ActionEvent.ACTION, handler);
        });

        tabPane.getSelectionModel()
                .selectedItemProperty()
                .addListener(new TabChangeListener());
    }

    @Override
    public void start(Stage stage) {

        initiateRoot(stage);
        handleNavigation();

        // styles
        StyleLoadHelper helper = new StyleLoadHelper("/styles/global.css");
        helper.load(root);

        Scene scene = new Scene(root, 1290, 650);
        stage.setTitle("Cashmate.");
        stage.setScene(scene);

        stage.show();

//        Tab a = new Tab("Transaction history for Customer [ID]");
//        a.setContent(new BillTab(1).getComponent());
//        tabPane.getTabs().add(a);
//
//        BillDocument billDocumentFactory = new BillDocument(1);
//        Pane bill = billDocumentFactory.getComponent();
//        DocumentPrinter printer = new DocumentPrinter(stage);
//        try {
//            printer.printElement(bill, "bill-123");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
        (new DataStore()).loadConfig();
        launch(args);
    }
}