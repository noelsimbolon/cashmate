package org.kys.bnmo.userPlugins.test2;

import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.model.Transaction;
import org.kys.bnmo.plugins.interfaces.BasePlugin;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class LineBarChartPlugin extends BasePlugin {

    public LineBarChartPlugin(PluginServiceInterface service)
    {
        super(service);
    }

    private Chart getLineChart()
    {
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Month");
        xAxis.lookup(".axis-label").setStyle("-fx-text-fill: #ffffff;");

        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Transaction Count");
        yAxis.lookup(".axis-label").setStyle("-fx-text-fill: #ffffff;");

        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Transaction Count by Months");
        lineChart.lookup(".chart-title").setStyle("-fx-text-fill: #ffffff;");

        // Defining a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Transaction Count");

        // Mapping Month Number to Month Name
        String[] monthNames = new String[] {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        // Get Transaction Count
        int[] transactionCount = new int[12];
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) getService().getController().getTransactions();

        for (Transaction transaction: transactions) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(transaction.getDate());
            transactionCount[cal.get(Calendar.MONTH)]++;
        }

        // Populating the series with data
        for (int i = 0; i < 12; i++) {
            series.getData().add(new XYChart.Data<>(monthNames[i], transactionCount[i]));
        }
        lineChart.getData().add(series);

        return lineChart;
    }

    private Chart getBarChart()
    {
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Country");
        xAxis.lookup(".axis-label").setStyle("-fx-text-fill: #ffffff;");

        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");
        yAxis.lookup(".axis-label").setStyle("-fx-text-fill: #ffffff;");

        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle("Transaction by Membership Level for The Last 5 Years");
        bc.lookup(".chart-title").setStyle("-fx-text-fill: #ffffff;");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Customer");
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Member");
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("VIP");

        int[][] transactionCount = new int[5][3];
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) getService().getController().getTransactions();

        for (Transaction transaction: transactions) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(transaction.getDate());
            int year = cal.get(Calendar.YEAR);
//            System.out.println(year);

            int yearIndex = year - LocalDate.now().getYear() + 4;
            if (yearIndex < 0 || yearIndex >= 5) continue;

            String memberLevel = transaction.getCustomer().getMemberLevel();
            if (memberLevel.equals("Customer")) {
                transactionCount[yearIndex][0]++;
            } else if (memberLevel.equals("Member")) {
                transactionCount[yearIndex][1]++;
            } else {
                transactionCount[yearIndex][2]++;
            }
        }

        // Add the data to the chart
        for (int i = 0; i < 5; i++) {
            series1.getData().add(new XYChart.Data<>(Integer.toString(LocalDate.now().getYear() - 4 + i), transactionCount[i][0]));
            series2.getData().add(new XYChart.Data<>(Integer.toString(LocalDate.now().getYear() - 4 + i), transactionCount[i][1]));
            series3.getData().add(new XYChart.Data<>(Integer.toString(LocalDate.now().getYear() - 4 + i), transactionCount[i][2]));
        }

        bc.getData().add(series1);
        bc.getData().add(series2);
        bc.getData().add(series3);
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

        Label headerTitle = new Label("Line Bar Chart");
        headerTitle.getStyleClass().add("add-member-title");

        header.getChildren().add(headerTitle);

        VBox root = new VBox();
        root.getChildren().addAll(header, getContent());
        root.getStyleClass().addAll("tab-content", "center-tab-content");

        return root;
    }
    @Override
    public void onLoad() {
        getService().addTab(getTabContainer(), "Line Bar Chart");
    }
}
