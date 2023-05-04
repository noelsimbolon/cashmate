package org.kys.bnmo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kys.bnmo.components.bases.TableBuilder;
import org.kys.bnmo.helpers.loaders.StyleLoadHelper;
import org.kys.bnmo.helpers.Table.TableData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TableSandbox extends Application {

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
        TableBuilder table = new TableBuilder();

        Image image = new Image(Objects.requireNonNull(getClass().getResource("/icon/MembershipImage.png")).toExternalForm());
        List<String> heading = new ArrayList<>(Arrays.asList("index", "hi", "hello", "actions"));
        List<Image> images = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        List<EventHandler<MouseEvent>> handlers = new ArrayList<>();
        List<ContextMenu> contextMenus = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            List<String> row = Arrays.asList(Integer.toString(i), "hello", "welcome");
            List<String> copied = new ArrayList<>(row);
            data.add(copied);
            images.add(image);
            handlers.add(e -> {
                System.out.println("hi");
            });

            MenuItem item1 = new MenuItem("menu 1");
            MenuItem item2 = new MenuItem("menu 2");

            ContextMenu cm = new ContextMenu(item1, item2);
            contextMenus.add(cm);
        }

        TableData tableData1 = new TableData(heading, data, images, 1, handlers, null);

        table.setTableData(tableData1, 1);

        table.addSearchBar();
        table.addAddItemButton("Add Item", e -> {
            System.out.println("hello");
        });
        table.setColumnAlignment(0, Pos.CENTER);
        Parent table1 = table.getAndResetComponent();

        TableData tableData2 = new TableData(heading, data, images, 1, null, contextMenus);
        table.setTableData(tableData2, 1);
        table.addSearchBar();
        table.addAddItemButton("Add Item", e -> {
            System.out.println("hello");
        });
        table.setColumnAlignment(0, Pos.CENTER);
        Parent table2 = table.getAndResetComponent();

        HBox root = new HBox(table1, table2);

        StyleLoadHelper helper = new StyleLoadHelper("/styles/global.css");
        helper.load(root);

        loadFonts(root, "Poppins", "ttf");

        Scene scene = new Scene(root);
        stage.setTitle("My JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
