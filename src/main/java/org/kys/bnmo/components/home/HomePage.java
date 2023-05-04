package org.kys.bnmo.components.home;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentFactory;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class HomePage implements ComponentFactory {

    private Pane root;

    public HomePage() {
    }

    public Pane getComponent() {
        // Initialize root
        Pane root = new Pane();


        Label programTitle = new Label("CashMate.");
        programTitle.getStyleClass().add("program-title");
        programTitle.relocate(650,62);

        // Create Welcome
        Label title = new Label(" Welcome Back!!");
        title.getStyleClass().add("add-member-title");
        title.relocate(580,110);

        //create anggota kelompok

        Label title1 = new Label(" Noel Christoffel Simbolon - 13521096");
        title1.getStyleClass().add("add-member-title1");
        title1.relocate(605,180);

        Label title2 = new Label(" Farhan Nabil Suryono - 13521114");
        title2.getStyleClass().add("add-member-title2");
        title2.relocate(605,220);

        Label title3 = new Label(" Rinaldy Adin - 13521134");
        title3.getStyleClass().add("add-member-title3");
        title3.relocate(605,260);

        Label title4 = new Label(" Enrique Alifio Ditya - 13521142");
        title4.getStyleClass().add("add-member-title4");
        title4.relocate(605,300);

        Label title5 = new Label(" Johanes Lee - 13521148");
        title5.getStyleClass().add("add-member-title5");
        title5.relocate(605,340);

        Label title6 = new Label(" Irgiansyah Mondo - 13521167");
        title6.getStyleClass().add("add-member-title6");
        title6.relocate(605,380);


        // Add components to the root
        root.getChildren().add(title);
        root.getChildren().add(title1);
        root.getChildren().add(title2);
        root.getChildren().add(title3);
        root.getChildren().add(title4);
        root.getChildren().add(title5);
        root.getChildren().add(title6);
        root.getChildren().add(programTitle);

        // Style the root
        root.getStyleClass().add("add-member-container");


        StyleLoadHelper helper = new StyleLoadHelper("/home/homePage.css");

        helper.load(root);

        return root;
    }






}
