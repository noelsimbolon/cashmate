package org.kys.bnmo.components.documents;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.ComponentBuilder;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;
import org.kys.bnmo.model.Order;
import org.kys.bnmo.model.Transaction;

import java.util.ArrayList;

public class ReportPage extends ComponentBuilder {

    private static final DocumentPageBuilder documentPageBuilder = new DocumentPageBuilder();

    @NotNull
    private DocumentPageBuilder.ColumnProperty[] getColumnProperties() {
        DocumentPageBuilder.ColumnProperty property1 = new DocumentPageBuilder.ColumnProperty(
                "Date",
                18,
                HPos.LEFT
        );

        DocumentPageBuilder.ColumnProperty property2 = new DocumentPageBuilder.ColumnProperty(
                "Transaction ID",
                19,
                HPos.LEFT
        );

        DocumentPageBuilder.ColumnProperty property3 = new DocumentPageBuilder.ColumnProperty(
                "Customer",
                19,
                HPos.LEFT
        );

        DocumentPageBuilder.ColumnProperty property4 = new DocumentPageBuilder.ColumnProperty(
                "Item(s) Sold",
                25,
                HPos.LEFT
        );

        DocumentPageBuilder.ColumnProperty property5 = new DocumentPageBuilder.ColumnProperty(
                "Subtotal",
                19,
                HPos.RIGHT
        );

        return new DocumentPageBuilder.ColumnProperty[]{
                property1,
                property2,
                property3,
                property4,
                property5
        };
    }

    public void addRows(@NotNull ArrayList<Transaction> chunk) {
        for (Transaction transaction : chunk) {
            for (Order order : transaction.getOrders()) {
                VBox columnContent1 = new VBox();
                Label row1 = new Label(transaction.getDate().toString());
                columnContent1.getChildren().add(row1);
                columnContent1.getStyleClass().addAll("small-text", "light");

                Label columnContent2 = new Label(order.getOrderID().toString());

                VBox columnContent3 = new VBox();
                row1 = new Label(transaction.getCustomer().getCustomerID().toString());
                row1.getStyleClass().addAll("small-text", "dark");
                columnContent3.getChildren().add(row1);

                VBox columnContent4 = new VBox();
                row1 = new Label(order.getItem().getItemName());
                var row2 = new Label(order.getItem().getItemID().toString());
                row1.getStyleClass().addAll("small-text", "dark");
                row2.getStyleClass().addAll("very-small-text", "dark");
                columnContent4.getChildren().addAll(row1, row2);

                Label columnContent5 = new Label(Double.toString(order.getPurchasePrice()));

                documentPageBuilder.addTableRow(
                        columnContent1,
                        columnContent2,
                        columnContent3,
                        columnContent4,
                        columnContent5
                );
            }
        }
    }

    @Override
    public void reset() {
        documentPageBuilder.addTitle("Transaction Report");
        documentPageBuilder.setTable(getColumnProperties());

        Pane root = documentPageBuilder.getAndResetComponent();
        StyleLoadHelper helper = new StyleLoadHelper("/styles/report.css");
        helper.load(root);

        setRoot(root);
    }
}
