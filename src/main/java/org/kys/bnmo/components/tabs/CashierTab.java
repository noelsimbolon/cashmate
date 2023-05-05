package org.kys.bnmo.components.tabs;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.bases.CheckoutPanel;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.components.bases.TableBuilder;

public class CashierTab extends TabContainer {

    private static final CheckoutPanel checkoutPanelFactory = new CheckoutPanel();

    private static final TableBuilder tableBuilder = new TableBuilder();

    @Override
    protected Pane getContent() {

        HBox root = new HBox();

        // table
//        tableBuilder.addSearchBar(2);

        Pane itemTable = tableBuilder.getAndResetComponent();
        Pane checkoutPanel = checkoutPanelFactory.getComponent();

        itemTable.getStyleClass().add("tab-cashier-table");
        checkoutPanel.getStyleClass().add("tab-checkout-panel");

        HBox.setHgrow(itemTable, Priority.ALWAYS);

        root.getChildren().addAll(
                itemTable,
                checkoutPanel
        );

        root.setPrefWidth(Double.MAX_VALUE);
        VBox.setVgrow(root, Priority.ALWAYS);
        root.getStyleClass().add("tab-row");
        return root;

    }

    @Override
    protected void additionalAction()
    {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Cashier");
    }
}
