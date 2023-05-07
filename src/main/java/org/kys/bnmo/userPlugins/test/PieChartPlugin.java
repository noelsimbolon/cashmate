package org.kys.bnmo.userPlugins.test;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.plugins.interfaces.PluginInterface;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

public class PieChartPlugin implements PluginInterface {
    PluginServiceInterface service;

    public PieChartPlugin(PluginServiceInterface service)
    {
        this.service = service;
    }

    private Chart getChart()
    {
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30));

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");

        return chart;
    }

    private Pane getContent()
    {
        VBox root = new VBox(getChart());
        root.setStyle("-fx-alignment: center");
        return root;
    }

    private Pane getTabContainer()
    {
        HBox header = new HBox();
        header.getStyleClass().add("tab-header");
        header.setPrefWidth(Double.MAX_VALUE);

        Label headerTitle = new Label("Pie Chart");
        headerTitle.getStyleClass().add("add-member-title");

        header.getChildren().add(headerTitle);

        VBox root = new VBox();
        root.getChildren().addAll(header, getContent());
        root.getStyleClass().addAll("tab-content", "center-tab-content");

        return root;
    }
    @Override
    public void onLoad() {
        service.addTab(getTabContainer(), "Pie Chart");
    }
}
