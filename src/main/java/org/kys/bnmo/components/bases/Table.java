package org.kys.bnmo.components.bases;

import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import org.kys.bnmo.helpers.IconButtonHelper;
import org.kys.bnmo.helpers.Table.TableCell;
import org.kys.bnmo.helpers.Table.TableData;
import org.kys.bnmo.helpers.Table.TableEntry;

import java.util.List;

public class Table extends TabPane {
    private final TableData tableData;
    private int showNEntries;
    private int nPages;
    private List<Integer> filterIndices;

    public Table(TableData tableData, List<Integer> filterIndices, int showNEntries) {
        super();
        this.getStyleClass().add("table-tab-pane");
        this.showNEntries = showNEntries;
        this.filterIndices = filterIndices;
        this.tableData = tableData;

        displayPages(tableData.getEntries());
    }

    private void displayPages(List<TableEntry> entries) {
        this.nPages = (entries.size() + showNEntries - 1) / showNEntries;
        this.getTabs().clear();
        int currentEntryIdx = 1;
        int currentEntryRow = 1;
        GridPane currentTablePage = new GridPane();
        while (currentEntryIdx < entries.size()) {
            if (currentEntryRow % showNEntries == 1) {
                currentTablePage = new GridPane();
                currentTablePage.getStyleClass().add("table-grid");
                this.getTabs().add(new Tab(Integer.toString(currentEntryIdx), currentTablePage));
                currentEntryRow = 1;
            }

            for (int j = 0; j < entries.get(currentEntryIdx).getColumns().size(); j++) {
                HBox cell = new HBox();
                cell.setAlignment(Pos.CENTER_LEFT);
                cell.setSpacing(8);
                TableCell cellData = entries.get(currentEntryIdx).getColumns().get(j);
                cell.getStyleClass().add("cell");

                if (cellData.getImage() != null) {
                    ImageView imageView = new ImageView(cellData.getImage());
                    cell.getChildren().add(imageView);
                }

                if (cellData.getText() != null) {
                    Text text = new Text(cellData.getText());
                    text.getStyleClass().add("text");
                    cell.getChildren().add(text);
                }

                if (currentEntryRow % 2 == 0)
                    cell.getStyleClass().add("even-row-cell");
                currentTablePage.add(cell, j, currentEntryRow, 1, 1);
            }

            if (entries.get(currentEntryIdx).getAddHandler() != null) {
                HBox cell = new HBox();
                cell.setAlignment(Pos.CENTER);
                cell.getStyleClass().add("cell");
                Button addButton = new Button();
                addButton.getStyleClass().add("entry-action-button");
                addButton.setOnMouseClicked(entries.get(currentEntryIdx).getAddHandler());
                IconButtonHelper iconButtonHelper = new IconButtonHelper();
                iconButtonHelper.setButtonGraphic(addButton, "/icon/PlusIconLight.png");
                if (currentEntryRow % 2 == 0)
                    cell.getStyleClass().add("even-row-cell");
                cell.getChildren().add(addButton);
                currentTablePage.add(cell, entries.get(currentEntryIdx).getColumns().size(), currentEntryRow, 1, 1);
            }

            if (entries.get(currentEntryIdx).getContextMenu() != null) {
                HBox cell = new HBox();
                cell.setAlignment(Pos.CENTER);
                cell.getStyleClass().add("cell");
                Button addButton = new Button();
                addButton.getStyleClass().add("entry-action-button");
                int currentIdx = currentEntryIdx;
                addButton.setOnMouseClicked(e -> {
                    entries.get(currentIdx).getContextMenu().show(addButton, Side.BOTTOM, 0, 0);
                    e.consume();
                });
                IconButtonHelper iconButtonHelper = new IconButtonHelper();
                iconButtonHelper.setButtonGraphic(addButton, "/icon/MoreIcon.png");
                if (currentEntryRow % 2 == 0)
                    cell.getStyleClass().add("even-row-cell");
                cell.getChildren().add(addButton);
                currentTablePage.add(cell, entries.get(currentEntryIdx).getColumns().size(), currentEntryRow, 1, 1);
            }

            currentEntryIdx++;
            currentEntryRow++;
        }

        while (currentEntryRow % showNEntries == 0) {
            for (int j = 0; j < tableData.getHeading().getColumns().size(); j++) {
                HBox cell = new HBox();
                cell.getStyleClass().add("cell");
                Text text = new Text(tableData.getHeading().getColumns().get(j).getText());
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
                for (int j = 0; j < tableData.getHeading().getColumns().size(); j++) {
                    HBox cell = new HBox();
                    if ((entries.get(0).getAddHandler() != null || entries.get(0).getContextMenu() != null) && j == tableData.getHeading().getColumns().size() - 1)
                        cell.setAlignment(Pos.CENTER);
                    if ((entries.get(0).getAddHandler() != null && entries.get(0).getContextMenu() != null) && j == tableData.getHeading().getColumns().size() - 2)
                        cell.setAlignment(Pos.CENTER);
                    cell.getStyleClass().add("cell");
                    Text text = new Text(tableData.getHeading().getColumns().get(j).getText());
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
                for (int i = 0; i < tableData.getHeading().getColumns().size(); i++) {
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

    public void setShowNEntries(int showNEntries) {
        this.showNEntries = showNEntries;
        displayPages(this.tableData.getEntries());
    }

    public void setCurrentPageIndex(int page) {
        this.getSelectionModel().select(page);
    }

    public void applyFilter(String filter) {
        List<TableEntry> filteredData = this.tableData.getEntries()
                .stream()
                .filter(row -> {
                    for (int index : filterIndices) {
                        if (row.getColumns().get(index).getText().contains(filter)) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();

        displayPages(filteredData);
    }

    public void setFilterIndices(List<Integer> filterIndices) {
        this.filterIndices = filterIndices;
    }

    public void setColumnAlignment(int columnIdx, Pos pos) {
        for (Tab pageTab : this.getTabs()) {
            GridPane tableGrid = (GridPane) pageTab.getContent();

            for (var node : tableGrid.getChildren())
                if (GridPane.getColumnIndex(node) == columnIdx) {
                    HBox cell = (HBox) node;
                    cell.setAlignment(pos);
                }
        }
    }
}
