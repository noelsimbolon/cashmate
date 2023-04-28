package org.kys.bnmo.components;

import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class SingleBillPage implements ComponentBuilder {
    VBox root;
    GridPane footerHeader;
    GridPane footerContent;
    public SingleBillPage()
    {
        reset();
    }

    private void addRow(GridPane gridpane, int row, List<Parent> elements)
    {
        for (int i = 0; i < elements.size(); i++)
        {
            GridPane.setConstraints(elements.get(i), i, row);
            gridpane.getChildren().add(elements.get(i));
        }
    }
    private GridPane getSpaceBetweenColumnTemplate()
    {
        GridPane gridpane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHalignment(HPos.LEFT);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        column2.setHalignment(HPos.RIGHT);
        gridpane.getColumnConstraints().addAll(column1, column2);
        gridpane.getStyleClass().add("space-between");

        return gridpane;
    }
    private VBox getHeader()
    {
        Label title = new Label("Invoice");
        title.getStyleClass().addAll("large-text", "dark");
        Label appTitle = new Label("CashMate.");
        appTitle.getStyleClass().add("app-title");

        GridPane titleRow = getSpaceBetweenColumnTemplate();
        addRow(titleRow, 0, Arrays.asList(title, appTitle));
        titleRow.getStyleClass().add("title-row");


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

        Label customerTitle = new Label("BILL TO");
        Label customerName = new Label("Client Name");
        customerTitle.getStyleClass().addAll("very-small-text", "light");
        customerName.getStyleClass().addAll("small-text", "dark");
        VBox customerSegment = new VBox();
        customerSegment.getChildren().addAll(customerTitle, customerName);

        VBox root = new VBox();
        root.getChildren().addAll(titleRow, dateIdSegment, customerSegment);

        root.getStyleClass().add("header");

        return root;
    }


    private GridPane getListColumnTemplate()
    {
        GridPane header = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHalignment(HPos.LEFT);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(16);
        column2.setHalignment(HPos.RIGHT);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(16);
        column3.setHalignment(HPos.CENTER);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(18);
        column4.setHalignment(HPos.RIGHT);
        header.getColumnConstraints().addAll(column1, column2, column3, column4);

        return header;
    }
    private GridPane getContentHeader()
    {
        GridPane header = getListColumnTemplate();

        Label columnLabel1 = new Label("Description");
        Label columnLabel2 = new Label("Rate");
        Label columnLabel3 = new Label("Quantity");
        Label columnLabel4 = new Label("Subtotal");

        addRow(header, 0, Arrays.asList(
                columnLabel1, columnLabel2, columnLabel3, columnLabel4)
        );

        header.getStyleClass().add("content-header");

        return header;
    }
    private VBox getContent()
    {
        GridPane header = getContentHeader();
        GridPane content = getListColumnTemplate();

        for (int i = 0; i < 10; i++)
        {
            VBox firstColumnContent = new VBox();
            Label row1 = new Label("Line Item Title");
            Label row2 = new Label("Description");
            row1.getStyleClass().addAll("small-text", "dark");
            row2.getStyleClass().addAll("very-small-text", "dark");
            firstColumnContent.getChildren().addAll(row1, row2);

            Label columnLabel2 = new Label("$ 12,345.00");
            Label columnLabel3 = new Label("1");
            Label columnLabel4 = new Label("$ 12,345.00");

            addRow(content, i, Arrays.asList(
                    firstColumnContent, columnLabel2, columnLabel3, columnLabel4)
            );
        }

        content.getStyleClass().add("content");

        VBox root = new VBox();
        root.getChildren().addAll(header, content);

        return root;
    }

    private void addInformationRow(GridPane root, int row, String label, String value)
    {
        Label column1 = new Label(label);
        Label column2 = new Label(": " + value);

        column1.getStyleClass().addAll("small-text", "dark");
        column2.getStyleClass().addAll("small-text");

        addRow(root, row, Arrays.asList(column1, column2));
    }
    private GridPane getFooterInformation()
    {
        GridPane root = new GridPane();

        addInformationRow(root, 0, "Costumer ID", "0000-000");
        addInformationRow(root, 1, "Membership", "VIP");
        addInformationRow(root, 2, "Status", "Active");
        addInformationRow(root, 3, "Phone Number", "+00 000 000 0000");
        addInformationRow(root, 4, "Point", "1000");

        return root;
    }
    private GridPane getFooterContent()
    {
        footerContent = getSpaceBetweenColumnTemplate();
        GridPane contentColumn1 = getFooterInformation();
        addRow(footerContent, 0, Arrays.asList(contentColumn1));
        footerContent.getStyleClass().add("bottom-border");

        return footerContent;
    }

    private VBox getFooter()
    {
        footerHeader = getSpaceBetweenColumnTemplate();
        Label headerColumn1 = new Label("Additional Information");

        addRow(footerHeader, 0, Arrays.asList(headerColumn1));
        footerHeader.getStyleClass().addAll("bottom-border", "small-text", "dark");

        GridPane footerEnd = getSpaceBetweenColumnTemplate();
        Label endColumn1 = new Label("Thank you! — yourename@gmail.com");
        Label endColumn2 = new Label("$CAD");

        footerEnd.getStyleClass().addAll("small-text");
        endColumn2.getStyleClass().addAll("dark");

        addRow(footerEnd, 0, Arrays.asList(endColumn1, endColumn2));
        VBox root = new VBox();
        root.getChildren().addAll(footerHeader, getFooterContent(), footerEnd);
        root.getStyleClass().add("footer");
        return root;
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
        addRow(root, row, Arrays.asList(column1, column2, column3));
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
        root = new VBox();

        VBox mainComponent = new VBox();
        mainComponent.getChildren().addAll(getHeader(), getContent());
        mainComponent.getStyleClass().add("main-component");

        root.getChildren().addAll(mainComponent, getFooter());
        root.getStyleClass().add("page");

        addSummary();
    }

    @Override
    public Parent getAndResetComponent() {
        Parent rootResult = root;
        reset();
        return rootResult;
    }
}
