package org.kys.bnmo.components.home;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.interfaces.ComponentFactory;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HomeDisplay implements ComponentFactory {

    private Pane root;

    public HomeDisplay() {}

    @NotNull
    public Pane getComponent() {
        // Initialize root
        root = new VBox();

        // Welcome
        Label title = new Label(" Welcome Back!");
        title.getStyleClass().add("add-member-title");

        // Members
        Label title1 = new Label(" Noel Christoffel Simbolon - 13521096");
        title1.getStyleClass().add("member-title");

        Label title2 = new Label(" Farhan Nabil Suryono - 13521114");
        title2.getStyleClass().add("member-title");

        Label title3 = new Label(" Rinaldy Adin - 13521134");
        title3.getStyleClass().add("member-title");

        Label title4 = new Label(" Enrique Alifio Ditya - 13521142");
        title4.getStyleClass().add("member-title");

        Label title5 = new Label(" Johanes Lee - 13521148");
        title5.getStyleClass().add("member-title");

        Label title6 = new Label(" Irgiansyah Mondo - 13521167");
        title6.getStyleClass().add("member-title");

        VBox members = new VBox();
        members.getChildren().addAll(
                title1,
                title2,
                title3,
                title4,
                title5,
                title6
        );

        members.getStyleClass().add("project-members");

        Label dateTimeLabel1 = new Label();
        dateTimeLabel1.getStyleClass().add("date-time-label");

        Label dateTimeLabel2 = new Label();
        dateTimeLabel2.getStyleClass().add("date-time-label-2");

        // Create animation timer to update the date and time label
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Get the current date and time
                LocalDateTime currentDateTime = LocalDateTime.now();

                // Format the current date and time
                String formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                String formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"));

                // Update the date and time label
                dateTimeLabel1.setText(formattedTime);
                dateTimeLabel2.setText(formattedDate);

            }
        };

        // Start the animation timer
        timer.start();

        // Add components to the root
        root.getChildren().addAll(
                title,
                dateTimeLabel1,
                dateTimeLabel2,
                members
        );

        // Style the root
        root.getStyleClass().add("add-member-container");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/homePage.css");

        helper.load(root);

        return root;
    }
}
