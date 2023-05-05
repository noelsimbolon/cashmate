package org.kys.bnmo.helpers;

import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DocumentBuilderHelper {

    public void addGridRow(GridPane gridpane, int row, @NotNull List<Parent> elements)
    {
        for (int i = 0; i < elements.size(); i++)
        {
            GridPane.setConstraints(elements.get(i), i, row);
            gridpane.getChildren().add(elements.get(i));
        }
    }

    public void addGridRow(GridPane gridpane, int row, List<Parent> elements, RowConstraints constraint)
    {
        addGridRow(gridpane, row, elements);
        gridpane.getRowConstraints().add(constraint);
    }

    public GridPane getSpaceBetweenColumnTemplate()
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
}
