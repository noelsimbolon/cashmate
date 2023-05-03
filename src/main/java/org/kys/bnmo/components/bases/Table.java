package org.kys.bnmo.components.bases;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Table extends TabPane {
    final private List<List<String>> tableData;
    private int showNEntries;
    private int nPages;
    private int filterIndex;

    public Table(List<List<String>> tableData, int filterIndex, int showNEntries) {
        super();
        this.getStyleClass().add("table-tab-pane");
        this.showNEntries = showNEntries;
        this.filterIndex = filterIndex;

        this.tableData = new ArrayList<>();
        for (var row : tableData) {
            this.tableData.add(new ArrayList<>(row));
        }

        displayPages(tableData);
    }

    private void displayPages(List<List<String>> tableData) {
        this.nPages = (tableData.size() + showNEntries - 1) / showNEntries;
        this.getTabs().clear();
        int currentEntryIdx = 1;
        int currentEntryRow = 1;
        GridPane currentTablePage = new GridPane();
        while (currentEntryIdx < tableData.size()) {
            if (currentEntryRow % showNEntries == 1) {
                currentTablePage = new GridPane();
                currentTablePage.getStyleClass().add("table-grid");
                this.getTabs().add(new Tab(Integer.toString(currentEntryIdx), currentTablePage));
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

        for (Tab pageTab : this.getTabs())
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
    }

    public int getNPages() {
        return this.nPages;
    }

    public int getShowNEntries() {
        return showNEntries;
    }

    public void setCurrentPageIndex(int page) {
        this.getSelectionModel().select(page);
    }

    public void setShowNEntries(int showNEntries) {
        this.showNEntries = showNEntries;
        displayPages(this.tableData);
    }

    public void applyFilter(String filter) {
        List<List<String>> filteredData = this.tableData.stream()
                                                        .filter(row -> row.get(this.filterIndex)
                                                        .contains(filter))
                                                        .toList();
        displayPages(filteredData);
    }

    public void setFilterIndex(int filterIndex) {
        this.filterIndex = filterIndex;
    }
}
