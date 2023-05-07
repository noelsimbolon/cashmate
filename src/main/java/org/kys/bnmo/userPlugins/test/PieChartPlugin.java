package org.kys.bnmo.userPlugins.test;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.plugins.interfaces.ControllerAdapterInterface;
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
        ControllerAdapterInterface controller = service.getController();

        IntegerProperty customersCount = new SimpleIntegerProperty();
        IntegerProperty membersCount = new SimpleIntegerProperty();
        IntegerProperty vipsCount = new SimpleIntegerProperty();

        PieChart.Data data1 = new PieChart.Data("Customers", customersCount.getValue());
        PieChart.Data data2 = new PieChart.Data("Members", membersCount.getValue());
        PieChart.Data data3 = new PieChart.Data("VIPs", vipsCount.getValue());

        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                data1,
                data2,
                data3
            );

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
