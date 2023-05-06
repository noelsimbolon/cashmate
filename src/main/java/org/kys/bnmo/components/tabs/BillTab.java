package org.kys.bnmo.components.tabs;
import javafx.scene.layout.Pane;
import org.kys.bnmo.components.documents.BillDocument;

public class BillTab extends TabContainer{

    private BillDocument billDocumentFactory;
    private int customerID;
    public BillTab(int customerID)
    {
        this.customerID = customerID;
        this.billDocumentFactory =  new BillDocument(customerID);
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
        this.billDocumentFactory.setCustomerID(customerID);
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
