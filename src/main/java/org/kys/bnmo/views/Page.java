package org.kys.bnmo.views;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.*;
import org.kys.bnmo.components.bases.Navbar;
import org.kys.bnmo.components.home.HomeDisplay;
import org.kys.bnmo.helpers.IconButtonHelper;
import org.kys.bnmo.helpers.loaders.StyleLoadHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Page extends ComponentBuilder {
    private static final Navbar navbarBuilder = new Navbar();
    private static final IconButtonHelper buttonHelper = new IconButtonHelper();

    private static final HomeDisplay homeDisplayFactory = new HomeDisplay();
    private ArrayList<String> buttonNames = new ArrayList<>(
            Arrays.asList("Membership", "Cashier", "Catalogue", "Settings"));
    private TabPane tabPane;

    public static final String getTemplateId(String title)
    {
        return title.replaceAll("\\s+","");
    }
    public static final Tab getTemplateTab(Parent content, String title)
    {
        Tab tab = new Tab(title);
        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        tab.setContent(sp);
        tab.setId(title.replaceAll("\\s+",""));

        return tab;
    }
    private Pane getNavbar()
    {
        // Create Button List

        for (String name: buttonNames)
        {
            navbarBuilder.addButton(name);
        }

        return navbarBuilder.getAndResetComponent();
    }

    private List<Node> getButtonList(Pane navbar)
    {
        return ((Pane)((ScrollPane)navbar.lookup("#navbar-scroll-panel")).getContent())
                .getChildren();
    }
    private void setNavbarAction(Pane navbar)
    {
        // Set Button Action if Clicked
        List<Node> buttonList = getButtonList(navbar);
        for (Node node: buttonList) {
            Button button = (Button) node;

            button.setOnAction(event -> {
                // Change Style to selected-nav-button
                // Also Change the Button Graphic
                for (int i = 0; i < buttonList.size(); i++) {
                    if (buttonList.get(i) == event.getSource()) {
                        buttonHelper.selectButton((Button) buttonList.get(i));
                    } else {
                        buttonHelper.unselectButton((Button) buttonList.get(i));
                    }
                }
            });
        }
    }

    public void addFactoryButton(String name)
    {
        if (!buttonNames.contains(name)) buttonNames.add(name);
    }

    public void removeFactoryButton(String name)
    {
        buttonNames.remove(name);
    }
    public void addTab(Parent content, String title)
    {
        tabPane.getTabs().add(getTemplateTab(content, title));
    }

    @Override
    public void reset() {
        HBox root = new HBox();

        tabPane = new TabPane();
        tabPane.setId("parent-tab-pane");

        Pane homeDisplay = new HomeDisplay().getComponent();
        BooleanBinding bb = Bindings.isEmpty(tabPane.getTabs());

        homeDisplay.visibleProperty().bind(bb);
        homeDisplay.managedProperty().bind(bb);

        root.getChildren().addAll(new VBox(tabPane, homeDisplay));
        root.getStyleClass().add("page");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/page.css");
        helper.load(root);
        setRoot(root);
    }

    @Override
    public Pane getAndResetComponent()
    {
        Pane navbar = getNavbar();
        setNavbarAction(navbar);
        getRoot().getChildren().add(0, navbar);

        return super.getAndResetComponent();
    }

}
