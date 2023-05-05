package org.kys.bnmo.components.tabs;

import javafx.scene.layout.Pane;
import org.kys.bnmo.components.documents.BillDocument;
import org.kys.bnmo.components.documents.ReportDocument;

public class ReportTab extends TabContainer {

    private static final ReportDocument reportDocumentFactory = new ReportDocument();

    @Override
    protected Pane getContent() {
        Pane root = reportDocumentFactory.getComponent();
        root.getStyleClass().add("center-tab-content");
        return root;
    }
    public void setContent() {

    }

    @Override
    protected void additionalAction()
    {
        getRoot().getStyleClass().add("center-tab-content");
    }
}
