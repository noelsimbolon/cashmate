package org.kys.bnmo.userPlugins.test2;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.plugins.interfaces.PluginInterface;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

public class LineBarChartPlugin implements PluginInterface {
    PluginServiceInterface service;

    public LineBarChartPlugin(PluginServiceInterface service)
    {
        this.service = service;
    }

    private Chart getLineChart()
    {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");

        final LineChart<Number,Number> lineChart =
                new LineChart(xAxis, yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

        lineChart.getData().add(series);

        return lineChart;
    }

    private Chart getBarChart()
    {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart(xAxis,yAxis);
        bc.setTitle("Country Summary");
        xAxis.setLabel("Country");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data("austria", 25601.34));
        series1.getData().add(new XYChart.Data("brazil", 20148.82));
        series1.getData().add(new XYChart.Data("france", 10000));
        series1.getData().add(new XYChart.Data("italy", 35407.15));
        series1.getData().add(new XYChart.Data("usa", 12000));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data("austria", 57401.85));
        series2.getData().add(new XYChart.Data("brazil", 41941.19));
        series2.getData().add(new XYChart.Data("france", 45263.37));
        series2.getData().add(new XYChart.Data("italy", 117320.16));
        series2.getData().add(new XYChart.Data("usa", 14845.27));

        bc.getData().addAll(series1, series2);

        bc.lookup(".chart-legend").setStyle("-fx-background-color: white; -fx-pref-height: 50px; -fx-pref-width: 100px");

        return bc;
    }

    private Pane getContent()
    {
        HBox root = new HBox(new VBox(getLineChart()), new VBox(getBarChart()));

        root.setStyle("-fx-spacing: 40; -fx-alignment: center");
        root.setPrefWidth(Double.MAX_VALUE);
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
        service.addTab(getTabContainer(), "Line Bar Chart");
    }
}
