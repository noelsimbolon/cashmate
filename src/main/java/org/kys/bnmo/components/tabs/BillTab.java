package org.kys.bnmo.components.tabs;

import javafx.scene.layout.Pane;
import org.kys.bnmo.components.documents.BillDocument;

public class BillTab extends TabContainer{
    private static final BillDocument billDocumentFactory = new BillDocument();
    @Override
    protected Pane getContent() {
        return billDocumentFactory.getComponent();
    }

    @Override
    protected void setHeader() {
        setHeaderProperty("");
    }

    public void setContent() {

    }
}
