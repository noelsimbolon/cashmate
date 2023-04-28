package org.kys.bnmo.components;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kys.bnmo.helpers.DocumentBuilderHelper;

import java.util.Arrays;

public class BillPageBuilder extends ComponentBuilder {

    private static final DocumentPageBuilder documentPageBuilder = new DocumentPageBuilder();
    private static final DocumentBuilderHelper documentBuilderHelper = new DocumentBuilderHelper();
    GridPane footerHeader;
    GridPane footerContent;

    private Parent getDateIdSegment()
    {
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

    private Parent getCustomerSegment()
    {
        Label customerTitle = new Label("BILL TO");
        Label customerName = new Label("Client Name");
        customerTitle.getStyleClass().addAll("very-small-text", "light");
        customerName.getStyleClass().addAll("small-text", "dark");
        VBox customerSegment = new VBox();
        customerSegment.getChildren().addAll(customerTitle, customerName);

        return customerSegment;
    }

    private void addInformationRow(GridPane root, int row, String label, String value)
    {
        Label column1 = new Label(label);
        Label column2 = new Label(": " + value);

        column1.getStyleClass().addAll("small-text", "dark");
        column2.getStyleClass().addAll("small-text");

        documentBuilderHelper.addGridRow(root, row, Arrays.asList(column1, column2));
    }
    private GridPane getFooterInformation()
    {
        GridPane root = new GridPane();
        addInformationRow(root, 0, "Costumer ID", "0000-000");
        addInformationRow(root, 1, "Membership", "VIP");
        addInformationRow(root, 2, "Status", "Active");
        addInformationRow(root, 3, "Phone Number", "+00 000 000 0000");
        addInformationRow(root, 4, "Point", "1000");

        root.getStyleClass().add("footer-information");
        return root;
    }
    private GridPane getFooterContent()
    {
        footerContent = documentBuilderHelper.getSpaceBetweenColumnTemplate();
        GridPane contentColumn1 = getFooterInformation();
        documentBuilderHelper.addGridRow(footerContent, 0, Arrays.asList(contentColumn1));
        footerContent.getStyleClass().add("bottom-border");

        return footerContent;
    }

    private GridPane getFooterHeader()
    {
        footerHeader = documentBuilderHelper.getSpaceBetweenColumnTemplate();
        Label headerColumn1 = new Label("Additional Information");

        documentBuilderHelper.addGridRow(footerHeader, 0, Arrays.asList(headerColumn1));
        footerHeader.getStyleClass().addAll("bottom-border", "small-text", "dark");

        return footerHeader;
    }

    private GridPane getFooterEnd()
    {
        GridPane footerEnd = documentBuilderHelper.getSpaceBetweenColumnTemplate();
        Label endColumn1 = new Label("Thank you! — yourename@gmail.com");
        Label endColumn2 = new Label("$CAD");

        footerEnd.getStyleClass().addAll("small-text");
        endColumn2.getStyleClass().addAll("dark");

        documentBuilderHelper.addGridRow(footerEnd, 0, Arrays.asList(endColumn1, endColumn2));

        return footerEnd;
    }

    private void addSummaryRow(GridPane root, int row, String label, String value, boolean isBold)
    {
        Label column1 = new Label(label);
        Label column2 = new Label(":");
        Label column3 = new Label(value);

        column1.getStyleClass().addAll("medium-text");
        column2.getStyleClass().addAll("medium-text");
        column3.getStyleClass().addAll("medium-text");

        if (isBold)
        {
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
    @Override
    public void reset() {
        documentPageBuilder.addTitle("Invoice");
        documentPageBuilder.addHeaderRow(getDateIdSegment());
        documentPageBuilder.addHeaderRow(getCustomerSegment());
        documentPageBuilder.addFooterRow(getFooterHeader());
        documentPageBuilder.addFooterRow(getFooterContent());
        documentPageBuilder.addFooterRow(getFooterEnd());
        root = documentPageBuilder.getAndResetComponent();
        addSummary();
    }
}
