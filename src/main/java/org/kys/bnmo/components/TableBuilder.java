package org.kys.bnmo.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.kys.bnmo.helpers.StyleLoadHelper;

import java.util.ArrayList;
import java.util.List;

public class TableBuilder implements ComponentBuilder {
    private VBox root;
    private HBox rightTopBox;
    private HBox bottomBox;
    private HBox paginationNumberBox;
    private ComboBox<String> entriesComboBox;
    private int showNEntries;
    private GridPane table;
    private List<List<String>> tableData;
    private int currentPage;
    private Button nextButton;

    public TableBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.root = new VBox();
        this.root.setFillWidth(true);
        this.root.getStyleClass().add("table-container");

        HBox topBox = new HBox();
        topBox.getStyleClass().add("table-topbox");
        topBox.setAlignment(Pos.CENTER_LEFT);
        this.root.getChildren().add(topBox);

        HBox showEntriesBox = new HBox(12);
        showEntriesBox.setAlignment(Pos.CENTER_LEFT);
        showEntriesBox.getStyleClass().add("table-show-entries-box");
        topBox.getChildren().add(showEntriesBox);

        Text showText = new Text("Show");
        showText.getStyleClass().add("text");
        showEntriesBox.getChildren().add(showText);

        this.entriesComboBox = new ComboBox<>();
        this.entriesComboBox.getItems().addAll("5", "10", "20", "50");
        this.entriesComboBox.setValue("10");
        this.entriesComboBox.setOnAction(e -> {
            int newValue = Integer.parseInt(this.entriesComboBox.getValue());
            this.currentPage = (this.currentPage - 1) * this.showNEntries / newValue + 1;
            this.showNEntries = newValue;
            updateTable();
        });
        this.showNEntries = 10;
        showEntriesBox.getChildren().add(this.entriesComboBox);

        Text entriesText = new Text("Entries");
        entriesText.getStyleClass().add("text");
        showEntriesBox.getChildren().add(entriesText);

        Pane topBoxStretch = new Pane();
        HBox.setHgrow(topBoxStretch, Priority.ALWAYS);
        topBox.getChildren().add(topBoxStretch);

        this.rightTopBox = new HBox();
        topBox.getChildren().add(this.rightTopBox);

        this.table = new GridPane();
        this.table.getStyleClass().add("table-grid");
        this.tableData = new ArrayList<>();
        this.root.getChildren().add(this.table);

        this.bottomBox = new HBox();
        this.bottomBox.setSpacing(12);
        this.bottomBox.getStyleClass().add("table-bottombox");
        this.bottomBox.setAlignment(Pos.CENTER_RIGHT);
        this.root.getChildren().add(this.bottomBox);

        Button previousButton = new Button("Previous");
        previousButton.getStyleClass().add("text-like-button");
        previousButton.setOnMouseClicked(e -> {
            if (this.currentPage > 1) {
                this.currentPage--;
                updateTable();
            }
        });
        this.currentPage = 1;
        this.bottomBox.getChildren().add(previousButton);

        this.paginationNumberBox = new HBox();
        this.paginationNumberBox.setSpacing(12);
        this.bottomBox.getChildren().add(this.paginationNumberBox);

        this.nextButton = new Button("Next");
        this.nextButton.getStyleClass().add("text-like-button");
        this.bottomBox.getChildren().add(this.nextButton);

        StyleLoadHelper helper = new StyleLoadHelper("/styles/table.css");
        helper.load(this.root);
    }

    @Override
    public Parent getAndResetComponent() {
        reset();

        return this.root;
    }

    private void updateTable() {
        updatePaginationNumbering();

        this.table.getChildren().clear();

        for (int j = 0; j < this.tableData.get(0).size(); j++) {
            HBox cell = new HBox();
            cell.getStyleClass().add("cell");
            Text text = new Text(this.tableData.get(0).get(j));
            text.getStyleClass().add("text");
            cell.getChildren().add(text);
            cell.getStyleClass().add("header-cell");
            cell.getStyleClass().add("even-row-cell");
            this.table.add(cell, j, 0, 1, 1);
        }

        for (int i = this.showNEntries * (this.currentPage - 1) + 1; i < this.tableData.size() && i < this.showNEntries * this.currentPage + 1; i++) {
            for (int j = 0; j < this.tableData.get(i).size(); j++) {
                HBox cell = new HBox();
                cell.getStyleClass().add("cell");
                Text text = new Text(this.tableData.get(i).get(j));
                text.getStyleClass().add("text");
                cell.getChildren().add(text);
                if (i % 2 == 0)
                    cell.getStyleClass().add("even-row-cell");
                this.table.add(cell, j, i, 1, 1);
            }
        }

        if (this.currentPage != 1)
            for (int i = this.tableData.size(); i < this.showNEntries * this.currentPage + 1; i++) {
                for (int j = 0; j < this.tableData.get(0).size(); j++) {
                    HBox cell = new HBox();
                    cell.getStyleClass().add("cell");
                    Text text = new Text(this.tableData.get(this.tableData.size() - 1).get(j));
                    text.getStyleClass().add("text");
                    cell.getChildren().add(text);
                    cell.setVisible(false);
                    this.table.add(cell, j, i, 1, 1);
                }
            }

        this.table.getColumnConstraints().clear();
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setFillWidth(true);
        colConstraints.setHgrow(Priority.ALWAYS);
        for (int i = 0; i < this.tableData.get(0).size(); i++) {
            this.table.getColumnConstraints().add(colConstraints);
        }
    }

    private void updatePaginationNumbering() {
        this.paginationNumberBox.getChildren().clear();
        for (int i = 1; i <= (this.tableData.size() - 1 + this.showNEntries - 1) / this.showNEntries; i++) {
            Button button = new Button(Integer.toString(i));
            button.getStyleClass().add("pagination-button");
            if (this.currentPage == i)
                button.getStyleClass().add("pagination-button-active");
            button.setOnMouseClicked(e -> {
                if (this.currentPage != Integer.parseInt(button.getText())) {
                    this.currentPage = Integer.parseInt(button.getText());
                    updateTable();
                }
            });
            this.paginationNumberBox.getChildren().add(button);
        }

        this.nextButton.setOnMouseClicked(e -> {
            if (this.tableData.size() > this.showNEntries && this.currentPage + 1 <= (this.tableData.size() - 1 + this.showNEntries - 1) / this.showNEntries) {
                this.currentPage++;
                updateTable();
            }
        });
    }

    public void setTableData(List<List<String>> data) {
        this.tableData.clear();
        for (List<String> row : data)
            this.tableData.add(new ArrayList<>(row));
        updateTable();
    }

    public void addTableData(List<String> row) {
        this.tableData.add(row);
        updateTable();
    }

    public void addSearchBar(int columnIndex) {
        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("searchbox");
        this.rightTopBox.getChildren().add(searchBox);

        TextField searchBarInput = new TextField();
        searchBarInput.getStyleClass().add("search-input");
        searchBarInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                this.bottomBox.setVisible(true);
                updateTable();
            } else {
                this.table.getChildren().clear();

                for (int j = 0; j < this.tableData.get(0).size(); j++) {
                    HBox cell = new HBox();
                    cell.getStyleClass().add("cell");
                    Text text = new Text(this.tableData.get(0).get(j));
                    text.getStyleClass().add("text");
                    cell.getChildren().add(text);
                    cell.getStyleClass().add("header-cell");
                    cell.getStyleClass().add("even-row-cell");
                    this.table.add(cell, j, 0, 1, 1);
                }

                for (int i = 1; i < this.tableData.size(); i++) {
                    if (this.tableData.get(i).get(columnIndex).contains(newValue))
                        for (int j = 0; j < this.tableData.get(i).size(); j++) {
                            HBox cell = new HBox();
                            cell.getStyleClass().add("cell");
                            Text text = new Text(this.tableData.get(i).get(j));
                            text.getStyleClass().add("text");
                            cell.getChildren().add(text);
                            if (i % 2 == 0)
                                cell.getStyleClass().add("even-row-cell");
                            this.table.add(cell, j, i, 1, 1);
                        }
                }

                this.bottomBox.setVisible(false);
            }
        });
        searchBox.getChildren().add(searchBarInput);
    }

    public void addAddItemButton(String buttonText, EventHandler<? super MouseEvent> eventHandler) {
        Button button = new Button(buttonText);
        button.getStyleClass().add("table-add-item-button");
        button.setOnMouseClicked(eventHandler);
        this.rightTopBox.getChildren().add(button);
    }
}
