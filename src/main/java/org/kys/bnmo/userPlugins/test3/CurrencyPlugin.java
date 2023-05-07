package org.kys.bnmo.userPlugins.test3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Order;
import org.kys.bnmo.model.Transaction;
import org.kys.bnmo.plugins.interfaces.BasePlugin;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyPlugin extends BasePlugin {

    private static final String defaultCurrency = "IDR";
    private static final String configFileName = "currencyPlugin";
    private static final Map<String, Double> currencyMap = new HashMap<>();
    private StringProperty value;
    private class SaveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

            try {

                ArrayList<String> contents = new ArrayList<>();
                contents.add(value.getValue());

                getService()
                        .getController()
                        .getDatastore()
                        .writeConfigAPI(configFileName, contents);
            }

            catch (Exception exception)
            {
                System.out.println(exception.getMessage());
            }
        }
    }
    public CurrencyPlugin(PluginServiceInterface service)
    {
        super(service);

        currencyMap.put("IDR", 1.);
        currencyMap.put("USD", 0.000068);
        currencyMap.put("SGD", 0.000090);
        currencyMap.put("MYR", 0.00030);

        value = new SimpleStringProperty(defaultCurrency);
    }

    private void addCurrencySetting()
    {
        try {
            List<String> res = (getService()
                            .getController()
                            .getDatastore()
                            .loadConfigAPI(configFileName)
            );

            if (res.size() == 0
                    || !currencyMap.containsKey(res.get(0)))
            {
                throw new FileNotFoundException("Content invalid!");
            }

            value.setValue(res.get(0));
        }

        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());

            ArrayList<String> contents = new ArrayList<>();
            contents.add(defaultCurrency);

            try {
                getService()
                        .getController()
                        .getDatastore()
                        .writeConfigAPI(configFileName, contents);
            }

            catch (Exception exception)
            {
                System.out.println(exception.getMessage());
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        getService().addDropdownSetting(
                "Currency",
                "Select Currency",
                currencyMap.keySet().toArray(new String[0]),
                value.getValue(),
                value
        );
    }

    private void processCurrency()
    {
        Modifiable modifiable = getService().getModifiable();
        List<Transaction> transactions = modifiable.transactions;
        List<InventoryItem> inventoryItems = modifiable.inventoryItems;
        List<Order> orders = modifiable.orders;

        double factor = currencyMap.get(value.getValue());

        if (!modifiable.get) factor = 1 / factor;

        if (transactions != null)
        {
            for (Transaction transaction: transactions)
            {
                transaction.setTotalPrice(transaction.getTotalPrice() * factor);
            }
        }

        if (inventoryItems != null)
        {
            for (InventoryItem item: inventoryItems)
            {
                item.setPrice(item.getPrice() * factor);
                item.setPurchasePrice(item.getPurchasePrice() * factor);
            }
        }

        if (orders != null)
        {
            for (Order order: orders)
            {
                order.setPurchasePrice(order.getPurchasePrice() * factor);
            }
        }
    }
    @Override
    public void onLoad() {
        addCurrencySetting();
        getService().addSettingSaveAction(new SaveHandler());
        processCurrency();
    }
}
