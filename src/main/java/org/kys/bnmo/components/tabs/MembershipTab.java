package org.kys.bnmo.components.tabs;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.bases.TableBuilder;
import org.kys.bnmo.helpers.IconButtonHelper;

public class MembershipTab extends TabContainer {

    private static final TableBuilder tableBuilder = new TableBuilder();
    @Override
    protected Pane getContent() {

        // table
        tableBuilder.addSearchBar(2);

        Pane root = tableBuilder.getAndResetComponent();

        VBox.setVgrow(root, Priority.ALWAYS);
        return root;
    }

    @Override
    protected void additionalAction()
    {
//        Button backButton = new Button();
//        new IconButtonHelper().setButtonGraphic(backButton, "/icon/BackArrow.png", 20, 20);
//        backButton.getStyleClass().add("back-button");
//        getHeader().getChildren().add(0, backButton);

        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Member List");
    }
}
