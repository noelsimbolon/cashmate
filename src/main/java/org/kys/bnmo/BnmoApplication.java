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
import org.kys.bnmo.components.AddMemberScene;

public class BnmoApplication extends Application {

    @Override
    public void start(Stage stage) {
        AddMemberScene addMember = new AddMemberScene();

        VBox root = new VBox();
        root.getChildren().add(addMember.getComponent());

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

        Scene scene = new Scene(root, root.getWidth(), root.getHeight());
        stage.setTitle("My JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
