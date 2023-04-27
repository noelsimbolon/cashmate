package org.kys.bnmo.components;

import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class BillDisplay implements ComponentFactory {
    private ScrollPane root;
    @Override
    public Parent getComponent() {

        root = new ScrollPane();
        VBox pages = new VBox();
        SingleBillPage singleBillPageFactory = new SingleBillPage();

        for (int i = 1; i < 3; i++)
        {
            pages.getChildren().add(singleBillPageFactory.getAndResetComponent());
        }
        root.setContent(pages);

        StyleLoadHelper helper = new StyleLoadHelper("/styles/bill.css");
        helper.load(root);

        return root;
    }
}
