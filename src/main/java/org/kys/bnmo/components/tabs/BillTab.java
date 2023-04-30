package org.kys.bnmo.components.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.documents.BillDocument;

public class BillTab extends TabContainer{
    private static final BillDocument billDocumentFactory = new BillDocument();
    @Override
    protected Pane getContent() {
        Pane root = billDocumentFactory.getComponent();
        return root;
    }

    public void setContent() {

    }

    @Override
    public void reset() {

        super.reset();
        getRoot().getStyleClass().add("center-tab-content");
    }
}
