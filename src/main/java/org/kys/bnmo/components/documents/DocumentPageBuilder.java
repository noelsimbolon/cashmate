package org.kys.bnmo.components.documents;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.kys.bnmo.components.ComponentBuilder;
import org.kys.bnmo.helpers.DocumentBuilderHelper;
import org.kys.bnmo.helpers.loaders.StyleLoadHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DocumentPageBuilder extends ComponentBuilder {
    private static final DocumentBuilderHelper documentBuilderHelper = new DocumentBuilderHelper();
    private VBox header;
    private VBox footer;
    private StringProperty titleProperty;
    private VBox contentParent;
    private GridPane contentTable;
    private int currentRow;

    public static class ColumnProperty {
        public ColumnProperty (String title, int widthPercentage, HPos position) {
            this.title = title;
            this.position = position;
            this.widthPercentage = widthPercentage;
        }

        public String title;
        public int widthPercentage;
        public HPos position;
    }
    public DocumentPageBuilder()
    {
        reset();
    }

    private VBox getHeader()
    {
        Label title = new Label();
        title.textProperty().bind(this.titleProperty);
        title.getStyleClass().add("title-label");

        title.getStyleClass().addAll("large-text", "dark");
        Label appTitle = new Label("CashMate.");
        appTitle.getStyleClass().add("app-title");

        RowConstraints rowConstraint = new RowConstraints();
        rowConstraint.setValignment(VPos.TOP);

        GridPane titleRow = documentBuilderHelper.getSpaceBetweenColumnTemplate();
        documentBuilderHelper.addGridRow(titleRow, 0, Arrays.asList(title, appTitle), rowConstraint);
        titleRow.getStyleClass().add("title-row");


        VBox root = new VBox();
        root.getChildren().addAll(titleRow);

        root.getStyleClass().add("header");

        return root;
    }


    private GridPane getListColumnTemplate(ColumnProperty ... properties)
    {
        GridPane header = new GridPane();

        for (ColumnProperty property: properties)
        {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(property.widthPercentage);
            column.setHalignment(property.position);
            header.getColumnConstraints().add(column);
        }

        return header;
    }

    private String[] getTitles(ColumnProperty ... properties)
    {
        return Arrays.stream(properties)
                .map(property -> property.title)
                .toArray(size -> new String[size]);
    }

    private GridPane getContentHeader(ColumnProperty ... properties)
    {
        GridPane header = getListColumnTemplate(properties);

        List<Parent> titleLabels = new ArrayList<>();

        for (String title: getTitles(properties))
        {
            Label columnLabel = new Label(title);
            titleLabels.add(columnLabel);
        }

        documentBuilderHelper.addGridRow(header, 0, titleLabels);

        header.getStyleClass().add("content-header");

        return header;
    }
    private VBox getContent(ColumnProperty ... properties)
    {
        contentParent = new VBox();
        return contentParent;
    }

    private VBox getFooter()
    {
        VBox root = new VBox();
        root.getStyleClass().add("footer");
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
        footer.setStyle("-fx-padding: 20px 40px 10px 40px;");
        footer.getChildren().add(row);
    }

    public void setTable(ColumnProperty ... properties) {
        GridPane header = getContentHeader(properties);
        contentTable = getListColumnTemplate(properties);
        contentTable.getStyleClass().add("content");
        contentParent.getChildren().clear();
        contentParent.getChildren().addAll(header, contentTable);
    }

    public void addTableRow(Parent ... elements)
    {
        RowConstraints rowConstraint = new RowConstraints();
        rowConstraint.setValignment(VPos.TOP);
        documentBuilderHelper.addGridRow(
                contentTable,
                currentRow,
                Arrays.stream(elements).toList(),
                rowConstraint);

        currentRow++;
    }

    @Override
    public void reset() {

        currentRow = 0;

        VBox root = new VBox();
        titleProperty = new SimpleStringProperty(this, "");

        header = getHeader();
        VBox mainComponent = new VBox();
        mainComponent.getChildren().addAll(header, getContent());
        mainComponent.getStyleClass().add("main-component");

        footer = getFooter();
        root.getChildren().addAll(mainComponent, footer);
        root.getStyleClass().add("page");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/documentPage.css");
        helper.load(root);

        setRoot(root);
    }

}
