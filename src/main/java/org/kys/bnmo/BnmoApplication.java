package org.kys.bnmo;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kys.bnmo.helpers.StyleLoadHelper;
import org.kys.bnmo.views.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BnmoApplication extends Application {

    private void loadFonts(Parent root, String family, String ext)
    {
        List<String> fontStyles = new ArrayList(Arrays.asList(
                "Bold",
                "Italic",
                "Medium",
                "Regular",
                "SemiBold"
        ));

        for (String fontStyle : fontStyles) {

            try {
                String path = "/fonts/" + family + "-" + fontStyle;
                String italicPath = path + "Italic" + "." + ext;
                path += "." + ext;

                Font.loadFont(getClass().getResource(path).toExternalForm(), 30);

                if (!(fontStyle.equals("Regular") || fontStyle.equals("Italic")))
                    Font.loadFont(getClass().getResource(italicPath).toExternalForm(), 30);
            }

            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    @Override
    public void start(Stage stage) {
        Page page = new Page();
        Parent root = page.getComponent();
        StyleLoadHelper helper = new StyleLoadHelper("/styles/global.css");
        helper.load(root);

        loadFonts(root, "Poppins", "ttf");

        Scene scene = new Scene(root, 1290, 650);
        stage.setTitle("My JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
