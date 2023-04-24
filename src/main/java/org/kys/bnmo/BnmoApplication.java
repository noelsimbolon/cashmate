package org.kys.bnmo;

import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kys.bnmo.components.MemberList;

public class BnmoApplication extends Application {

    @Override
    public void start(Stage stage) {
        MemberList memberList = new MemberList(new SimpleListProperty<>());
        VBox root = new VBox();
        root.getChildren().add(memberList.getComponent());
//        Font.loadFont(getClass().getResource("/fonts/FontAwesome.otf").toExternalForm(), 10);

        try {

            String css = this.getClass()
                    .getResource("/styles/global.css")
                    .toExternalForm();

            root.getStylesheets().add(css);
        }

        catch (NullPointerException e)
        {
            System.out.println("Failed to load css for global component!");
        }
        Scene scene = new Scene(root, 1290, 650);
        stage.setTitle("My JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
