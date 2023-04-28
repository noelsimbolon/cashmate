package org.kys.bnmo.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.kys.bnmo.helpers.DocumentBuilderHelper;

import java.util.Arrays;
import java.util.List;

public class DocumentPageBuilder extends ComponentBuilder {
    private static final DocumentBuilderHelper documentBuilderHelper = new DocumentBuilderHelper();
    VBox root;
    VBox header;
    VBox footer;
    StringProperty titleProperty;
    GridPane footerHeader;
    GridPane footerContent;
    public DocumentPageBuilder()
    {
        reset();
    }

    private VBox getHeader()
    {
        Label title = new Label();
        title.textProperty().bind(this.titleProperty);

        title.getStyleClass().addAll("large-text", "dark");
        Label appTitle = new Label("CashMate.");
        appTitle.getStyleClass().add("app-title");

        GridPane titleRow = documentBuilderHelper.getSpaceBetweenColumnTemplate();
        documentBuilderHelper.addGridRow(titleRow, 0, Arrays.asList(title, appTitle));
        titleRow.getStyleClass().add("title-row");


        VBox root = new VBox();
        root.getChildren().addAll(titleRow);

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

        documentBuilderHelper.addGridRow(header, 0, Arrays.asList(
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

            documentBuilderHelper.addGridRow(content, i, Arrays.asList(
                    firstColumnContent, columnLabel2, columnLabel3, columnLabel4)
            );
        }

        content.getStyleClass().add("content");

        VBox root = new VBox();
        root.getChildren().addAll(header, content);

        return root;
    }

    public void addTitle(String titleText) {
        titleProperty.set(titleText);
    }

    public void addHeaderRow(Parent row)
    {
        header.getChildren().add(row);
    }

    public void addFooterRow(Parent row)
    {
        footer.getChildren().add(row);
    }

    private VBox getFooter()
    {
        VBox root = new VBox();
        root.getStyleClass().add("footer");
        return root;
    }

    @Override
    public void reset() {
        root = new VBox();
        titleProperty = new SimpleStringProperty(this, "");

        header = getHeader();
        VBox mainComponent = new VBox();
        mainComponent.getChildren().addAll(header, getContent());
        mainComponent.getStyleClass().add("main-component");

        footer = getFooter();
        root.getChildren().addAll(mainComponent, footer);
        root.getStyleClass().add("page");
    }

    @Override
    public Pane getAndResetComponent() {
        Pane rootResult = root;
        reset();
        return rootResult;
    }
}
