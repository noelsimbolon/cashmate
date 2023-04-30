package org.kys.bnmo.components.documents;

import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentBuilder;

public class ReportPage extends ComponentBuilder {
    private static final DocumentPageBuilder documentPageBuilder = new DocumentPageBuilder();

    private Parent getPeriodSegment() {
        VBox root = new VBox();

        Label periodTitle = new Label("Period");
        periodTitle.getStyleClass().addAll("dark", "small-text");
        Label periodValue = new Label("June 3, 2020 - March 21, 2021");
        periodValue.getStyleClass().addAll("light", "small-text");

        root.getChildren().addAll(periodTitle, periodValue);
        return root;
    }

    private Parent getTotalSegment() {
        VBox root = new VBox();

        Label periodTitle = new Label("Total Amount");
        periodTitle.getStyleClass().addAll("dark", "small-text");
        Label periodValue = new Label("$169,420.00");
        periodValue.getStyleClass().addAll("amount-label");

        root.getChildren().addAll(periodTitle, periodValue);
        return root;
    }
    private DocumentPageBuilder.ColumnProperty[] getColumnProperties()
    {
        DocumentPageBuilder.ColumnProperty property1 = new DocumentPageBuilder.ColumnProperty(
                "Date",
                18,
                HPos.LEFT
        );

        DocumentPageBuilder.ColumnProperty property2 = new DocumentPageBuilder.ColumnProperty(
                "Order no.",
                19,
                HPos.LEFT
        );

        DocumentPageBuilder.ColumnProperty property3 = new DocumentPageBuilder.ColumnProperty(
                "Service to",
                19,
                HPos.LEFT
        );

        DocumentPageBuilder.ColumnProperty property4 = new DocumentPageBuilder.ColumnProperty(
                "Item Sold",
                25,
                HPos.LEFT
        );

        DocumentPageBuilder.ColumnProperty property5 = new DocumentPageBuilder.ColumnProperty(
                "Subtotal",
                19,
                HPos.RIGHT
        );

        return new DocumentPageBuilder.ColumnProperty[] {
                property1,
                property2,
                property3,
                property4,
                property5
        };
    }

    public void addRows()
    {
        for (int i = 0; i < 10; i++)
        {
            VBox columnContent1 = new VBox();
            Label row1 = new Label("27/04/2023");
            Label row2 = new Label("01:46:56 PM");
            columnContent1.getChildren().addAll(row1, row2);
            columnContent1.getStyleClass().addAll("small-text", "light");

            Label columnContent2 = new Label("F-123456789");

            VBox columnContent3 = new VBox();
            row1 = new Label("Customer Name");
            row2 = new Label("#CustomerID");
            row1.getStyleClass().addAll("small-text", "dark");
            row2.getStyleClass().addAll("very-small-text", "dark");
            columnContent3.getChildren().addAll(row1, row2);

            VBox columnContent4 = new VBox();
            row1 = new Label("Line Item");
            row2 = new Label("#ItemID");
            row1.getStyleClass().addAll("small-text", "dark");
            row2.getStyleClass().addAll("very-small-text", "dark");
            columnContent4.getChildren().addAll(row1, row2);

            Label columnContent5 = new Label("$ 12,345.00");

            documentPageBuilder.addTableRow(
                    columnContent1,
                    columnContent2,
                    columnContent3,
                    columnContent4,
                    columnContent5
            );
        }
    }

    @Override
    public void reset() {
        documentPageBuilder.addTitle("Transaction Report");
        documentPageBuilder.addHeaderRow(getPeriodSegment());
        documentPageBuilder.addHeaderRow(getTotalSegment());
        documentPageBuilder.setTable(getColumnProperties());
        setRoot(documentPageBuilder.getAndResetComponent());
    }
}
