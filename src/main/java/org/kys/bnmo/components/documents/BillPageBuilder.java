package org.kys.bnmo.components.documents;

import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.ComponentBuilder;
import org.kys.bnmo.helpers.views.DocumentBuilderHelper;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;

import java.util.Arrays;

public class BillPageBuilder extends ComponentBuilder {

    private static final DocumentPageBuilder documentPageBuilder = new DocumentPageBuilder();
    private static final DocumentBuilderHelper documentBuilderHelper = new DocumentBuilderHelper();
    private GridPane footerHeader;
    private GridPane footerContent;

    private @NotNull Parent getDateIdSegment() {
        Label dateTitle = new Label("Date:");
        Label dateValue = new Label("June 3, 2020");
        dateTitle.getStyleClass().addAll("small-text", "dark");
        dateValue.getStyleClass().addAll("small-text", "light");

        HBox dateRow = new HBox();
        dateRow.getChildren().addAll(dateTitle, dateValue);
        dateRow.getStyleClass().add("label-row");

        Label idTitle = new Label("Invoice:");
        Label idValue = new Label("#0317");
        idTitle.getStyleClass().addAll("small-text", "dark");
        idValue.getStyleClass().addAll("small-text", "light");

        HBox idRow = new HBox();
        idRow.getChildren().addAll(idTitle, idValue);
        idRow.getStyleClass().add("label-row");

        VBox dateIdSegment = new VBox();
        dateIdSegment.getChildren().addAll(dateRow, idRow);
        dateIdSegment.getStyleClass().add("date-id");

        return dateIdSegment;
    }

    private @NotNull Parent getCustomerSegment() {
        Label customerTitle = new Label("BILL TO");
        Label customerName = new Label("Client Name");
        customerTitle.getStyleClass().addAll("very-small-text", "light");
        customerName.getStyleClass().addAll("small-text", "dark");
        VBox customerSegment = new VBox();
        customerSegment.getChildren().addAll(customerTitle, customerName);

        return customerSegment;
    }

    private void addInformationRow(GridPane root, int row, String label, String value) {
        Label column1 = new Label(label);
        Label column2 = new Label(": " + value);

        column1.getStyleClass().addAll("small-text", "dark");
        column2.getStyleClass().addAll("small-text");

        documentBuilderHelper.addGridRow(root, row, Arrays.asList(column1, column2));
    }

    private @NotNull GridPane getFooterInformation() {
        GridPane root = new GridPane();
        addInformationRow(root, 0, "Costumer ID", "0000-000");
        addInformationRow(root, 1, "Membership", "VIP");
        addInformationRow(root, 2, "Status", "Active");
        addInformationRow(root, 3, "Phone Number", "+00 000 000 0000");
        addInformationRow(root, 4, "Point", "1000");

        root.getStyleClass().add("footer-information");
        return root;
    }

    private GridPane getFooterContent() {
        footerContent = documentBuilderHelper.getSpaceBetweenColumnTemplate();
        GridPane contentColumn1 = getFooterInformation();
        documentBuilderHelper.addGridRow(footerContent, 0, Arrays.asList(contentColumn1));
        footerContent.getStyleClass().add("bottom-border");

        return footerContent;
    }

    private GridPane getFooterHeader() {
        footerHeader = documentBuilderHelper.getSpaceBetweenColumnTemplate();
        Label headerColumn1 = new Label("Additional Information");

        documentBuilderHelper.addGridRow(footerHeader, 0, Arrays.asList(headerColumn1));
        footerHeader.getStyleClass().addAll("bottom-border", "small-text", "dark");

        return footerHeader;
    }

    private @NotNull GridPane getFooterEnd() {
        GridPane footerEnd = documentBuilderHelper.getSpaceBetweenColumnTemplate();
        Label endColumn1 = new Label("Thank you! — yourename@gmail.com");
        Label endColumn2 = new Label("$CAD");

        footerEnd.getStyleClass().addAll("small-text");
        endColumn2.getStyleClass().addAll("dark");

        documentBuilderHelper.addGridRow(footerEnd, 0, Arrays.asList(endColumn1, endColumn2));

        return footerEnd;
    }

    private void addSummaryRow(GridPane root, int row, String label, String value, boolean isBold) {
        Label column1 = new Label(label);
        Label column2 = new Label(":");
        Label column3 = new Label(value);

        column1.getStyleClass().addAll("medium-text");
        column2.getStyleClass().addAll("medium-text");
        column3.getStyleClass().addAll("medium-text");

        if (isBold) {
            column1.getStyleClass().addAll("dark");
            column2.getStyleClass().addAll("dark");
            column3.getStyleClass().addAll("dark");
        }
        documentBuilderHelper.addGridRow(root, row, Arrays.asList(column1, column2, column3));
    }

    public void addSummary() {

        Label headerColumn2 = new Label("Totals");
        GridPane.setConstraints(headerColumn2, 1, 0);
        footerHeader.getChildren().add(headerColumn2);

        GridPane summary = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        column1.setHalignment(HPos.LEFT);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(5);
        column2.setHalignment(HPos.LEFT);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(75);
        column3.setHalignment(HPos.RIGHT);
        summary.getColumnConstraints().addAll(column1, column2, column3);

        addSummaryRow(summary, 0, "Subtotal", "$12,345.00", false);
        addSummaryRow(summary, 1, "Discount", "$0.00", false);
        addSummaryRow(summary, 2, "Tax", "$0.00", false);
        addSummaryRow(summary, 3, "Total", "$12,345.00", true);

        GridPane.setConstraints(summary, 1, 0);
        footerContent.getChildren().add(summary);
    }

    private @NotNull DocumentPageBuilder.ColumnProperty[] getColumnProperties() {
        DocumentPageBuilder.ColumnProperty property1 = new DocumentPageBuilder.ColumnProperty(
                "Description",
                50,
                HPos.LEFT
        );

        DocumentPageBuilder.ColumnProperty property2 = new DocumentPageBuilder.ColumnProperty(
                "Rate",
                16,
                HPos.RIGHT
        );

        DocumentPageBuilder.ColumnProperty property3 = new DocumentPageBuilder.ColumnProperty(
                "Quantity",
                16,
                HPos.CENTER
        );

        DocumentPageBuilder.ColumnProperty property4 = new DocumentPageBuilder.ColumnProperty(
                "Subtotal",
                18,
                HPos.RIGHT
        );

        return new DocumentPageBuilder.ColumnProperty[]{
                property1,
                property2,
                property3,
                property4
        };
    }

    public void addRows() {
        for (int i = 0; i < 10; i++) {
            VBox firstColumnContent = new VBox();
            Label row1 = new Label("Line Item Title");
            Label row2 = new Label("Description");
            row1.getStyleClass().addAll("small-text", "dark");
            row2.getStyleClass().addAll("very-small-text", "dark");
            firstColumnContent.getChildren().addAll(row1, row2);

            Label columnLabel2 = new Label("$ 12,345.00");
            Label columnLabel3 = new Label("1");
            Label columnLabel4 = new Label("$ 12,345.00");

            documentPageBuilder.addTableRow(
                    firstColumnContent,
                    columnLabel2,
                    columnLabel3,
                    columnLabel4);
        }
    }

    @Override
    public void reset() {
        documentPageBuilder.addTitle("Invoice");
        documentPageBuilder.addHeaderRow(getDateIdSegment());
        documentPageBuilder.addHeaderRow(getCustomerSegment());
        documentPageBuilder.addFooterRow(getFooterHeader());
        documentPageBuilder.addFooterRow(getFooterContent());
        documentPageBuilder.addFooterRow(getFooterEnd());
        documentPageBuilder.setTable(getColumnProperties());

        Pane root = documentPageBuilder.getAndResetComponent();
        StyleLoadHelper helper = new StyleLoadHelper("/styles/bill.css");
        helper.load(root);
        setRoot(root);
    }
}
