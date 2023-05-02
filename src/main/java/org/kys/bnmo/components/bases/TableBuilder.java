package org.kys.bnmo.components.bases;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.kys.bnmo.components.ComponentBuilder;
import org.kys.bnmo.helpers.StyleLoadHelper;
import java.util.ArrayList;
import java.util.List;

public class TableBuilder extends ComponentBuilder {
    @Override
    public void reset() {
        VBox root = new VBox();
        setRoot(root);
        root.setFillWidth(true);
        root.getStyleClass().add("table-container");

        HBox topBox = new HBox();
        topBox.getStyleClass().add("table-topbox");
        topBox.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().add(topBox);

        HBox showEntriesBox = new HBox(12);
        showEntriesBox.setAlignment(Pos.CENTER_LEFT);
        showEntriesBox.getStyleClass().add("table-show-entries-box");
        topBox.getChildren().add(showEntriesBox);

        Text showText = new Text("Show");
        showText.getStyleClass().add("text");
        showEntriesBox.getChildren().add(showText);

//        ComboBox<String> entriesComboBox = new ComboBox<>();
//        entriesComboBox.getStyleClass().add("entries-combo-box");
//        entriesComboBox.getItems().addAll("5", "10", "20", "50");
//        entriesComboBox.setValue("10");
//        showEntriesBox.getChildren().add(entriesComboBox);

        Text entriesText = new Text("Entries");
        entriesText.getStyleClass().add("text");
        showEntriesBox.getChildren().add(entriesText);

        Pane topBoxStretch = new Pane();
        HBox.setHgrow(topBoxStretch, Priority.ALWAYS);
        topBox.getChildren().add(topBoxStretch);

        HBox rightTopBox = new HBox();
        topBox.getChildren().add(rightTopBox);

        TabPane table = new TabPane();
        table.getStyleClass().add("table-tab-pane");
        root.getChildren().add(table);

        HBox bottomBox = new HBox();
        bottomBox.setSpacing(12);
        bottomBox.getStyleClass().add("table-bottombox");
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        root.getChildren().add(bottomBox);

        Button previousButton = new Button("Previous");
        previousButton.getStyleClass().add("text-like-button");
        previousButton.getStyleClass().add("previous-button");
        bottomBox.getChildren().add(previousButton);

        HBox paginationNumberBox = new HBox();
        paginationNumberBox.getStyleClass().add("pagination-number-box");
        paginationNumberBox.setSpacing(12);
        bottomBox.getChildren().add(paginationNumberBox);

        Button nextButton = new Button("Next");
        nextButton.getStyleClass().add("text-like-button");
        nextButton.getStyleClass().add("next-button");
        bottomBox.getChildren().add(nextButton);

        StyleLoadHelper helper = new StyleLoadHelper("/styles/table.css");
        helper.load(root);
    }

    private void updateTable(Parent root, List<List<String>> tableData) {
        TabPane table = (TabPane) root.lookup(".table-tab-pane");
        table.getTabs().clear();
//        ComboBox<String> entriesComboBox = (ComboBox<String>)getRoot().lookup(".entries-combo-box");
        int showNEntries = 10;

        int currentEntryIdx = 1;
        int currentEntryRow = 1;
        GridPane currentTablePage = new GridPane();
        while (currentEntryIdx < tableData.size()) {
            if (currentEntryRow % showNEntries == 1) {
                currentTablePage = new GridPane();
                var styles =currentTablePage.getStylesheets();
                styles.add("table-grid");
                table.getTabs().add(new Tab(Integer.toString(currentEntryIdx), currentTablePage));
                currentEntryRow = 1;
            }

            for (int j = 0; j < tableData.get(currentEntryIdx).size(); j++) {
                HBox cell = new HBox();
                cell.getStyleClass().add("cell");
                Text text = new Text(tableData.get(currentEntryIdx).get(j));
                text.getStyleClass().add("text");
                cell.getChildren().add(text);
                if (currentEntryRow % 2 == 0)
                    cell.getStyleClass().add("even-row-cell");
                currentTablePage.add(cell, j, currentEntryRow, 1, 1);
            }
            currentEntryIdx++;
            currentEntryRow++;
        }

        while (currentEntryRow % showNEntries == 0) {
            for (int j = 0; j < tableData.get(0).size(); j++) {
                HBox cell = new HBox();
                cell.getStyleClass().add("cell");
                Text text = new Text(tableData.get(tableData.size() - 1).get(j));
                text.getStyleClass().add("text");
                cell.getChildren().add(text);
                cell.setVisible(false);
                currentTablePage.add(cell, j, currentEntryRow, 1, 1);
            }
            currentEntryRow++;
        }

        for (Tab pageTab : table.getTabs())
            if (pageTab.getContent() instanceof GridPane) {
                currentTablePage = (GridPane) pageTab.getContent();
                for (int j = 0; j < tableData.get(0).size(); j++) {
                    HBox cell = new HBox();
                    cell.getStyleClass().add("cell");
                    Text text = new Text(tableData.get(0).get(j));
                    text.getStyleClass().add("text");
                    cell.getChildren().add(text);
                    cell.getStyleClass().add("header-cell");
                    cell.getStyleClass().add("even-row-cell");
                    currentTablePage.add(cell, j, 0, 1, 1);
                }

                currentTablePage.getColumnConstraints().clear();
                ColumnConstraints colConstraints = new ColumnConstraints();
                colConstraints.setFillWidth(true);
                colConstraints.setHgrow(Priority.ALWAYS);
                for (int i = 0; i < tableData.get(0).size(); i++) {
                    currentTablePage.getColumnConstraints().add(colConstraints);
                }
            }

        updatePaginationNumbering(root, 1, tableData.size());
    }

    private void updatePaginationNumbering(Parent root, int currentPage, int tableSize) {
        HBox paginationNumberBox = (HBox) root.lookup(".pagination-number-box");
        paginationNumberBox.getChildren().clear();
//        ComboBox<String> entriesComboBox = (ComboBox<String>)getRoot().lookup(".entries-combo-box");
        int showNEntries = 10;
        int nPages = (tableSize + showNEntries - 1) / showNEntries;

        for (int i = 1; i <= nPages; i++) {
            Button button = new Button(Integer.toString(i));
            button.getStyleClass().add("pagination-button");
            if (currentPage == i)
                button.getStyleClass().add("pagination-button-active");
            button.setOnMouseClicked(e -> {
                if (currentPage != Integer.parseInt(button.getText())) {
                    updatePaginationNumbering(root, Integer.parseInt(button.getText()), tableSize);
                }
            });
            paginationNumberBox.getChildren().add(button);
        }

        Button previous = (Button) root.lookup(".previous-button");
        previous.setOnMouseClicked(e -> {
            if (currentPage != 1)
                updatePaginationNumbering(root, currentPage - 1, tableSize);
        });

        Button next = (Button) root.lookup(".next-button");
        next.setOnMouseClicked(e -> {
            if (currentPage != nPages) {
                updatePaginationNumbering(root, currentPage + 1, tableSize);
            }
        });

        TabPane table = (TabPane) root.lookup(".table-tab-pane");
        table.getSelectionModel().select(currentPage - 1);
    }

    public void setTableData(List<List<String>> data) {
        updateTable(getRoot(), data);
    }

//    public void addSearchBar(int columnIndex) {
//        HBox searchBox = new HBox();
//        searchBox.getStyleClass().add("searchbox");
//        this.rightTopBox.getChildren().add(searchBox);
//
//        TextField searchBarInput = new TextField();
//        searchBarInput.getStyleClass().add("search-input");
//        searchBarInput.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.equals("")) {
//                this.bottomBox.setVisible(true);
//                updateTable();
//            } else {
//                this.table.getChildren().clear();
//
//                for (int j = 0; j < this.tableData.get(0).size(); j++) {
//                    HBox cell = new HBox();
//                    cell.getStyleClass().add("cell");
//                    Text text = new Text(this.tableData.get(0).get(j));
//                    text.getStyleClass().add("text");
//                    cell.getChildren().add(text);
//                    cell.getStyleClass().add("header-cell");
//                    cell.getStyleClass().add("even-row-cell");
//                    this.table.add(cell, j, 0, 1, 1);
//                }
//
//                for (int i = 1; i < this.tableData.size(); i++) {
//                    if (this.tableData.get(i).get(columnIndex).contains(newValue))
//                        for (int j = 0; j < this.tableData.get(i).size(); j++) {
//                            HBox cell = new HBox();
//                            cell.getStyleClass().add("cell");
//                            Text text = new Text(this.tableData.get(i).get(j));
//                            text.getStyleClass().add("text");
//                            cell.getChildren().add(text);
//                            if (i % 2 == 0)
//                                cell.getStyleClass().add("even-row-cell");
//                            this.table.add(cell, j, i, 1, 1);
//                        }
//                }
//
//                this.bottomBox.setVisible(false);
//            }
//        });
//        searchBox.getChildren().add(searchBarInput);
//    }
//
//    public void addAddItemButton(String buttonText, EventHandler<? super MouseEvent> eventHandler) {
//        Button button = new Button(buttonText);
//        button.getStyleClass().add("table-add-item-button");
//        button.setOnMouseClicked(eventHandler);
//        this.rightTopBox.getChildren().add(button);
//    }
}
