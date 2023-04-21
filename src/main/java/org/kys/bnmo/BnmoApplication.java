package org.kys.bnmo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BnmoApplication extends Application {

    @Override
    public void start(Stage stage) {
        Button button = new Button("Click me!");
        button.setOnAction(event -> System.out.println("Hello, JavaFX!"));

        VBox root = new VBox();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 300, 250);

        stage.setTitle("My JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
