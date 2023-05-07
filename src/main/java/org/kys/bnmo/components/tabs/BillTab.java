package org.kys.bnmo.components.tabs;
import javafx.scene.layout.Pane;
import org.kys.bnmo.components.documents.BillDocument;

import java.util.UUID;

public class BillTab extends TabContainer{

    private BillDocument billDocumentFactory;
    private UUID transactionId;
    public BillTab(UUID transactionId)
    {
        this.transactionId = transactionId;
        this.billDocumentFactory =  new BillDocument(transactionId);
    }

    @Override
    protected Pane getContent() {
        Pane root =  billDocumentFactory.getComponent();
        return root;
    }

    @Override
    protected void additionalAction()
    {
        getRoot().getStyleClass().add("center-tab-content");
    }
}
