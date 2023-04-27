package org.kys.bnmo.components;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.kys.bnmo.helpers.StyleLoadHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NavBarFactory implements ComponentFactory {
    @Override
    public Parent getComponent() {
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

            // Set Button Action if Clicked
            button.setOnAction(event -> {
                // Change Style to selected-nav-button
                // Also Change the Button Graphic
                for (int i = 0; i < buttonList.size(); i++) {
                    if (buttonList.get(i) == event.getSource()) {
                        buttonList.get(i).getStyleClass().clear();
                        buttonList.get(i).getStyleClass().add("selected-nav-button");
                        setButtonGraphic(buttonList.get(i), "/icon/" + buttonNames.get(i) + "SelectedImage.png");
                    } else {
                        buttonList.get(i).getStyleClass().clear();
                        buttonList.get(i).getStyleClass().add("unselected-nav-button");
                        setButtonGraphic(buttonList.get(i), "/icon/" + buttonNames.get(i) + "Image.png");
                    }
                }
            });

            // Set Default Button Style and Graphic
            button.getStyleClass().add("unselected-nav-button");
            setButtonGraphic(button, "/icon/" + buttonName + "Image.png");
            buttonList.add(button);
        }
        
        // Add Components to the root
        root.getChildren().add(programTitle);
        root.getChildren().addAll(buttonList);
        
        // Load CSS File
        StyleLoadHelper helper = new StyleLoadHelper("/styles/navBar.css");
        helper.load(root);

        return root;
    }

    private void setButtonGraphic(Button button, String path) {
        // Set Button Graphic to Image that Located in the Path
        Image image = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
    }
}
