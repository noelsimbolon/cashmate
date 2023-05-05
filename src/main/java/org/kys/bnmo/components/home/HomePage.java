package org.kys.bnmo.components.home;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.ComponentFactory;
import org.kys.bnmo.helpers.loaders.StyleLoadHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HomePage implements ComponentFactory {

    private Pane root;

    public HomePage() {
    }

    @NotNull
    public Pane getComponent() {
        // Initialize root
        root = new Pane();


        Label programTitle = new Label("CashMate.");
        programTitle.getStyleClass().add("program-title");
        programTitle.relocate(650, 62);

        // Create Welcome
        Label title = new Label(" Welcome Back!!");
        title.getStyleClass().add("add-member-title");
        title.relocate(580, 110);

        //create anggota kelompok

        Label title1 = new Label(" Noel Christoffel Simbolon - 13521096");
        title1.getStyleClass().add("add-member-title1");
        title1.relocate(605, 180);

        Label title2 = new Label(" Farhan Nabil Suryono - 13521114");
        title2.getStyleClass().add("add-member-title2");
        title2.relocate(605, 220);

        Label title3 = new Label(" Rinaldy Adin - 13521134");
        title3.getStyleClass().add("add-member-title3");
        title3.relocate(605, 260);

        Label title4 = new Label(" Enrique Alifio Ditya - 13521142");
        title4.getStyleClass().add("add-member-title4");
        title4.relocate(605, 300);

        Label title5 = new Label(" Johanes Lee - 13521148");
        title5.getStyleClass().add("add-member-title5");
        title5.relocate(605, 340);

        Label title6 = new Label(" Irgiansyah Mondo - 13521167");
        title6.getStyleClass().add("add-member-title6");
        title6.relocate(605, 380);

        Label dateTimeLabel1 = new Label();
        dateTimeLabel1.getStyleClass().add("date-time-label");
        dateTimeLabel1.relocate(1080, 700);

        Label dateTimeLabel2 = new Label();
        dateTimeLabel2.getStyleClass().add("date-time-label");
        dateTimeLabel2.relocate(1000, 740);

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
        root.getChildren().add(title);
        root.getChildren().add(title1);
        root.getChildren().add(title2);
        root.getChildren().add(title3);
        root.getChildren().add(title4);
        root.getChildren().add(title5);
        root.getChildren().add(title6);
        root.getChildren().add(dateTimeLabel1);
        root.getChildren().add(dateTimeLabel2);
        root.getChildren().add(programTitle);

        // Style the root
        root.getStyleClass().add("add-member-container");


        StyleLoadHelper helper = new StyleLoadHelper("/home/homePage.css");

        helper.load(root);

        return root;
    }
}
