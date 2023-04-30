package org.kys.bnmo;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kys.bnmo.components.tabs.AddMemberTab;
import org.kys.bnmo.components.tabs.BillTab;
import org.kys.bnmo.components.tabs.ReportTab;
import org.kys.bnmo.components.tabs.SettingTab;
import org.kys.bnmo.helpers.StyleLoadHelper;
import org.kys.bnmo.views.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BnmoApplication extends Application {

    private Pane root;

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

    private List<Tab> getTabs()
    {
        return ((TabPane)root.lookup("parent-tab-pane")).getTabs();
    }

    private Optional<Tab> getTab(String id)
    {
        List<Tab> tabs = ((TabPane)root.lookup("parent-tab-pane")).getTabs();

        return tabs.stream().filter(tab -> tab.getId() == id).findFirst();
    }

    @Override
    public void start(Stage stage) {
        Page page = new Page();
        page.addTab(new AddMemberTab().getAndResetComponent(), "Membership");
        page.addTab(new SettingTab(stage).getAndResetComponent(), "Settings");
        page.addTab(new BillTab().getAndResetComponent(), "Bill[id]");
        page.addTab(new ReportTab().getAndResetComponent(), "Report[id]");

        root = page.getAndResetComponent();

        StyleLoadHelper helper = new StyleLoadHelper("/styles/global.css");
        helper.load(root);

        loadFonts(root, "Poppins", "ttf");

        Scene scene = new Scene(root, 1290, 650);
        stage.setTitle("My JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

// Checkout panel testing
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // Create a new instance of CheckoutPanel
//        var checkoutPanel = new CheckoutPanel();
//
//        // Create a new scene with the root node of CheckoutPanel
//        var scene = new Scene(checkoutPanel.getComponent(), 250, 326);
//
//        // Set the scene and show the stage
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
