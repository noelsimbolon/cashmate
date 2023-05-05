package org.kys.bnmo.components.bases;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentBuilder;
import org.kys.bnmo.helpers.IconButtonHelper;
import org.kys.bnmo.helpers.loaders.StyleLoadHelper;

public class Navbar extends ComponentBuilder {

    private static final IconButtonHelper helper = new IconButtonHelper();
    private Pane buttons;

    public void addButton(String name)
    {
        Button button = new Button(name);
        button.setId(name.replaceAll("\\s+",""));

        // Set Default Button Style and Graphic
        button.getStyleClass().add("unselected-nav-button");
        helper.setButtonGraphic(button, "/icon/" + name + "Image.png");
        buttons.getChildren().add(button);

    }
    @Override
    public void reset() {
        // Main Vertical Box
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);

        // Create The Components
        // Program Title
        Label programTitle = new Label("CashMate.");
        programTitle.getStyleClass().add("program-title");
        programTitle.setAlignment(Pos.CENTER);

        // Add Components to the root
        root.getChildren().add(programTitle);
        ScrollPane buttonScrollPane = new ScrollPane();
        buttons = new VBox();
        buttons.getStyleClass().add("buttons-box");
        buttonScrollPane.setContent(buttons);
        buttonScrollPane.setId("navbar-scroll-panel");
        buttonScrollPane.getStyleClass().add("navbar-scroll-panel");
        root.getChildren().add(buttonScrollPane);

        // Load CSS File
        StyleLoadHelper helper = new StyleLoadHelper("/styles/navBar.css");
        helper.load(root);

        root.getStyleClass().add("navbar");
        setRoot(root);
    }
}
