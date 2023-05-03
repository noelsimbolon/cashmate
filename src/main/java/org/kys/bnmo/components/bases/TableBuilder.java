package org.kys.bnmo.components.bases;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.kys.bnmo.components.ComponentBuilder;
import org.kys.bnmo.helpers.IconButtonHelper;
import org.kys.bnmo.helpers.StyleLoadHelper;
import org.kys.bnmo.helpers.Table.TableData;

import java.util.List;
import java.util.Objects;

public class TableBuilder extends ComponentBuilder {
    Table currentTable;

    public TableBuilder() {
        super();
    }

    @Override
    public void reset() {
        this.currentTable = null;

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

        ComboBox<String> entriesComboBox = new ComboBox<>();
        entriesComboBox.getStyleClass().add("entries-combo-box");
        entriesComboBox.getItems().addAll("5", "10", "20", "50");
        entriesComboBox.setValue("10");
        showEntriesBox.getChildren().add(entriesComboBox);

        Text entriesText = new Text("Entries");
        entriesText.getStyleClass().add("text");
        showEntriesBox.getChildren().add(entriesText);

        Pane topBoxStretch = new Pane();
        HBox.setHgrow(topBoxStretch, Priority.ALWAYS);
        topBox.getChildren().add(topBoxStretch);

        HBox rightTopBox = new HBox();
        rightTopBox.getStyleClass().add("right-top-box");
        rightTopBox.setAlignment(Pos.CENTER_LEFT);
        rightTopBox.setSpacing(24);
        topBox.getChildren().add(rightTopBox);

        VBox tableWrapper = new VBox();
        tableWrapper.getStyleClass().add("table-table-wrapper");
        root.getChildren().add(tableWrapper);

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

    private void updatePageNumbering(Parent root, Table table, int currentPage) {
        HBox paginationNumberBox = (HBox) root.lookup(".pagination-number-box");
        paginationNumberBox.getChildren().clear();

        for (int i = 1; i <= table.getNPages(); i++) {
            Button button = new Button(Integer.toString(i));
            button.getStyleClass().add("pagination-button");
            if (currentPage == i)
                button.getStyleClass().add("pagination-button-active");
            button.setOnMouseClicked(e -> {
                if (currentPage != Integer.parseInt(button.getText())) {
                    updatePageNumbering(root, table, Integer.parseInt(button.getText()));
                }
            });
            paginationNumberBox.getChildren().add(button);
        }

        Button previous = (Button) root.lookup(".previous-button");
        previous.setOnMouseClicked(e -> {
            if (currentPage != 1)
                updatePageNumbering(root, table, currentPage - 1);
        });

        Button next = (Button) root.lookup(".next-button");
        next.setOnMouseClicked(e -> {
            if (currentPage != table.getNPages())
                updatePageNumbering(root, table, currentPage + 1);
        });

        table.setCurrentPageIndex(currentPage - 1);
    }

    public void setTableData(TableData data, int  filterIndex) {
        VBox root = (VBox) getRoot();
        ComboBox<String> entriesComboBox = (ComboBox<String>) getRoot().lookup(".entries-combo-box");
        Table table = new Table(data, filterIndex, Integer.parseInt(entriesComboBox.getValue()));
        this.currentTable = table;
        entriesComboBox.valueProperty().addListener((ov, oldValue, newValue) -> {
            table.setShowNEntries(Integer.parseInt(newValue));
            int currentPage = table.getSelectionModel().getSelectedIndex() + 1;
            updatePageNumbering(root, table, (currentPage - 1) * table.getShowNEntries() / Integer.parseInt(newValue) + 1);
        });
        root.getChildren().add(1, table);
        updatePageNumbering(root, table, 1);
    }

    public void addSearchBar() {
        VBox root = (VBox) getRoot();
        HBox rightTopBox = (HBox) root.lookup(".right-top-box");
        Table table = currentTable;

        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("searchbox");
        searchBox.setSpacing(8);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        rightTopBox.getChildren().add(searchBox);

        Image searchImage = new Image(Objects.requireNonNull(getClass().getResource("/icon/SearchIcon.png")).toExternalForm());
        ImageView searchImageView = new ImageView(searchImage);
        searchBox.getChildren().add(searchImageView);

        TextField searchBarInput = new TextField();
        searchBarInput.getStyleClass().add("search-input");
        searchBarInput.textProperty().addListener((observable, oldValue, newValue) -> {
            table.applyFilter(newValue);
        });
        searchBarInput.setPromptText("Search...");
        searchBox.getChildren().add(searchBarInput);
    }

    public void addAddItemButton(String buttonText, EventHandler<? super MouseEvent> eventHandler) {
        Button button = new Button(buttonText);
        button.getStyleClass().add("table-add-item-button");
        button.setOnMouseClicked(eventHandler);
        IconButtonHelper iconButtonHelper = new IconButtonHelper();
        iconButtonHelper.setButtonGraphic(button, "/icon/PlusIconDark.png");
        HBox rightTopBox = (HBox) getRoot().lookup(".right-top-box");
        rightTopBox.getChildren().add(button);
    }

    public void setColumnAlignment(int columnIdx, Pos pos) {
        if (currentTable != null) {
            Table table = currentTable;
            table.setColumnAlignment(columnIdx, pos);
        }
    }
}
