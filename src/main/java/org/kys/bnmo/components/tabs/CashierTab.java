package org.kys.bnmo.components.tabs;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.bases.CheckoutPanel;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.components.bases.TableBuilder;
import org.kys.bnmo.helpers.Table.TableData;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CashierTab extends TabContainer {

    private static final TableBuilder tableBuilder = new TableBuilder();

    @Override
    protected Pane getContent() {
        List<Member> members = new ArrayList<>();
        members.add(new Member("Fio", "+62-812-3456-891"));
        members.add(new Member("Jojo", "+62-812-3456-891"));
        members.add(new Member("Agus", "+62-812-3456-891"));
        CheckoutPanel checkoutPanel = new CheckoutPanel(members);

        List<String> headers = new ArrayList<>(Arrays.asList("Item ID", "Name", "Price", "Actions"));
        List<List<String>> content = new ArrayList<>();
        int item_id = 2000;
        List<String> foodNames = Arrays.asList("Burger", "Pizza", "Hotdog", "Taco", "Sushi");

        Random random = new Random();
        List<InventoryItem> items = new ArrayList<>();
        List<EventHandler<MouseEvent>> handlers = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            int randomIndex = random.nextInt(foodNames.size());
            String randomFoodName = foodNames.get(randomIndex);
            content.add(new ArrayList<>(Arrays.asList(Integer.toString(item_id), randomFoodName, "Rp12.000")));
            items.add(new InventoryItem(10, randomFoodName, 12000, "Fast Food", null));
            int idx = i;
            handlers.add(e -> {
                checkoutPanel.addItem(items.get(idx));
            });
            item_id++;
        }
        TableData tableData = new TableData(headers, content, handlers, null);
        tableBuilder.setTableData(tableData, new ArrayList<>(Arrays.asList(0, 1)));
        tableBuilder.setColumnAlignment(0, Pos.CENTER);
        tableBuilder.addSearchBar();

        HBox root = new HBox();

        Pane itemTable = tableBuilder.getAndResetComponent();

        itemTable.getStyleClass().add("tab-cashier-table");
        checkoutPanel.getStyleClass().add("tab-checkout-panel");

        HBox.setHgrow(itemTable, Priority.ALWAYS);

        root.getChildren().addAll(
                itemTable,
                checkoutPanel
        );

        root.setPrefWidth(Double.MAX_VALUE);
        VBox.setVgrow(root, Priority.ALWAYS);
        root.getStyleClass().add("tab-row");
        return root;

    }

    @Override
    protected void additionalAction()
    {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Cashier");
    }
}
