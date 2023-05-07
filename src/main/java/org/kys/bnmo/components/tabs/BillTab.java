package org.kys.bnmo.components.tabs;
import javafx.scene.layout.Pane;
import org.kys.bnmo.components.documents.BillDocument;

public class BillTab extends TabContainer{

    private BillDocument billDocumentFactory;
    private int transactionId;
    public BillTab(int transactionId)
    {
        this.transactionId = transactionId;
        this.billDocumentFactory =  new BillDocument(transactionId);
    }

    public void setCustomerID(int transactionId) {
        this.transactionId = transactionId;
        this.billDocumentFactory.setTransactionID(transactionId);
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
