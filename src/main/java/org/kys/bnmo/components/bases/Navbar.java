package org.kys.bnmo.components.bases;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentFactory;
import org.kys.bnmo.helpers.IconButtonHelper;
import org.kys.bnmo.helpers.StyleLoadHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class Navbar implements ComponentFactory {

    private static final IconButtonHelper helper = new IconButtonHelper();
    @Override
    public Pane getComponent() {
        // Main Vertical Box
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);

        // Create The Components
        // Program Title
        Label programTitle = new Label("CashMate.");
        programTitle.getStyleClass().add("program-title");
        programTitle.setAlignment(Pos.CENTER);

        // Create Button List
        ArrayList<String> buttonNames = new ArrayList<>(Arrays.asList("Membership", "Cashier", "Catalogue", "Settings"));
        ArrayList<Button> buttonList = new ArrayList<>();

        // Create Buttons Based On buttonNames and Add to the List
        for (String buttonName : buttonNames) {
            Button button = new Button(buttonName);
            button.setId(buttonName.replaceAll("\\s+",""));

            // Set Button Action if Clicked
            button.setOnAction(event -> {
                // Change Style to selected-nav-button
                // Also Change the Button Graphic
                for (int i = 0; i < buttonList.size(); i++) {
                    if (buttonList.get(i) == event.getSource()) {
                        helper.selectButton(buttonList.get(i));
                    } else {
                        helper.unselectButton(buttonList.get(i));
                    }
                }
            });

            // Set Default Button Style and Graphic
            button.getStyleClass().add("unselected-nav-button");
            helper.setButtonGraphic(button, "/icon/" + buttonName + "Image.png");
            buttonList.add(button);
        }
        
        // Add Components to the root
        root.getChildren().add(programTitle);
        VBox buttons = new VBox();
        buttons.getChildren().addAll(buttonList);
        buttons.setId("buttons-box");
        buttons.getStyleClass().add("buttons-box");
        root.getChildren().add(buttons);
        
        // Load CSS File
        StyleLoadHelper helper = new StyleLoadHelper("/styles/navBar.css");
        helper.load(root);

        root.getStyleClass().add("navbar");

        return root;
    }
}
