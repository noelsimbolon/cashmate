package org.kys.bnmo.components.tabs;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.bases.TableBuilder;

public class CatalogueTab extends TabContainer {
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
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Catalogue");
    }
}
