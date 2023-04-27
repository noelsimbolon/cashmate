package org.kys.bnmo.components;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class SingleBillPage implements ComponentBuilder {
    VBox root;
    public SingleBillPage()
    {
        reset();
    }

    private VBox getHeaderInfo()
    {
        Label title = new Label("Invoice");

        Label dateTitle = new Label("Date:");
        Label dateValue = new Label("June 3, 2020");
        HBox dateRow = new HBox();
        dateRow.getChildren().addAll(dateTitle, dateValue);

        Label idTitle = new Label("Invoice:");
        Label idValue = new Label("#0317");
        HBox idRow = new HBox();
        idRow.getChildren().addAll(idTitle, idValue);

        VBox dateIdSegment = new VBox();
        dateIdSegment.getChildren().addAll(dateRow, idRow);

        Label customerTitle = new Label("BILL TO");
        Label customerName = new Label("Client Name");
        VBox customerSegment = new VBox();
        customerSegment.getChildren().addAll(customerTitle, customerName);

        VBox root = new VBox();
        root.getChildren().addAll(title, dateIdSegment, customerSegment);
        root.getStyleClass().add("header-info");
        return root;
    }
    private HBox getHeader()
    {
        Label appTitle = new Label("CashMate.");
        appTitle.getStyleClass().add("app-title");

        HBox root = new HBox();
        root.getChildren().addAll(getHeaderInfo(), appTitle);
        root.getStyleClass().add("header");
        StyleLoadHelper helper = new StyleLoadHelper("/styles/bill.css");
        helper.load(root);

        return root;
    }
    private VBox getContent()
    {
        GridPane header = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(16);
        ColumnConstraints column3 = new ColumnConstraints();
        column2.setPercentWidth(16);
        ColumnConstraints column4 = new ColumnConstraints();
        column2.setPercentWidth(16);
        header.getColumnConstraints().addAll(column1, column2, column3, column4);

        Label columnLabel1 = new Label("Description");
        Label columnLabel2 = new Label("Rate");
        Label columnLabel3 = new Label("Quantity");
        Label columnLabel4 = new Label("Subtotal");

        GridPane.setConstraints(columnLabel1, 0, 0);
        GridPane.setConstraints(columnLabel2, 1, 0);
        GridPane.setConstraints(columnLabel3, 2, 0);
        GridPane.setConstraints(columnLabel4, 3, 0);

        header.getChildren().addAll(columnLabel1, columnLabel2, columnLabel3, columnLabel4);

        VBox root = new VBox();
        root.getChildren().addAll(header);
        return root;
    }
    private VBox getFooter()
    {
        VBox root = new VBox();
        return root;
    }
    @Override
    public void reset() {
        root = new VBox();

        root.getChildren().addAll(getHeader(), getContent(), getFooter());
        StyleLoadHelper helper = new StyleLoadHelper("/styles/bill.css");
        helper.load(root);
    }

    public void addSummary() {

    }

    @Override
    public Parent getAndResetComponent() {
        Parent rootResult = root;
        reset();
        return rootResult;
    }
}
