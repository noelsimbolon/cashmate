package org.kys.bnmo.userPlugins.test;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.Member;
import org.kys.bnmo.plugins.interfaces.BasePlugin;
import org.kys.bnmo.plugins.interfaces.ControllerAdapterInterface;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

import java.util.List;

public class PieChartPlugin extends BasePlugin {

    public PieChartPlugin(PluginServiceInterface service) {
        super(service);
    }

    @NotNull
    private Chart getChart() {
        ControllerAdapterInterface controller = getService().getController();

        List<Customer> customers = controller.getCustomers();
        List<Member> members = controller.getMembers();

        IntegerProperty customerCount = new SimpleIntegerProperty(customers.size());

        IntegerProperty memberCount = new SimpleIntegerProperty(
                (int) members.stream().filter(m -> m.getMemberLevel().equals("Member")).count()
        );

        IntegerProperty vipCount = new SimpleIntegerProperty(
                (int) members.stream().filter(m -> m.getMemberLevel().equals("VIP")).count()
        );

        PieChart.Data data1 = new PieChart.Data("Customers", customerCount.getValue());
        PieChart.Data data2 = new PieChart.Data("Members", memberCount.getValue());
        PieChart.Data data3 = new PieChart.Data("VIPs", vipCount.getValue());

        data1.pieValueProperty().bind(customerCount);
        data2.pieValueProperty().bind(memberCount);
        data3.pieValueProperty().bind(vipCount);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        data1,
                        data2,
                        data3
                );

        final PieChart chart = new PieChart(pieChartData);

        chart.setTitle("Customer Distribution");

        // Color the chart title
        chart.lookup(".chart-title").setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 25;"
        );

        // Set the labels to be invisible
        chart.setLabelsVisible(false);

        // Set up a Timeline to update the chart data every 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            List<Customer> customersUpdated = controller.getCustomers();
            List<Member> membersUpdated = controller.getMembers();

            int customerCountUpdated = customersUpdated.size();
            int memberCountUpdated = (int) membersUpdated.stream().filter(m -> m.getMemberLevel().equals("Member")).count();
            int vipCountUpdated = (int) membersUpdated.stream().filter(m -> m.getMemberLevel().equals("VIP")).count();

            // Update the pie chart data
            ObservableList<PieChart.Data> pieChartDataUpdated = FXCollections.observableArrayList(
                    new PieChart.Data("Customers", customerCountUpdated),
                    new PieChart.Data("Members", memberCountUpdated),
                    new PieChart.Data("VIPs", vipCountUpdated)
            );
            chart.setData(pieChartDataUpdated);
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return chart;
    }

    @NotNull
    private Pane getContent() {
        VBox root = new VBox(getChart());
        root.setStyle("-fx-alignment: center");
        return root;
    }

    @NotNull
    private Pane getTabContainer() {
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
        getService().addTab(getTabContainer(), "Pie Chart");
    }
}
