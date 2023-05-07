package org.kys.bnmo.userPlugins.test3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Transaction;
import org.kys.bnmo.plugins.interfaces.BasePlugin;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyPlugin extends BasePlugin {

    private static final Map<String, Double> currencyMap = new HashMap<>();
    private static final StringProperty value = new SimpleStringProperty("IDR");
    public CurrencyPlugin(PluginServiceInterface service)
    {
        super(service);

        currencyMap.put("IDR", 1.);
        currencyMap.put("USD", 0.000068);
        currencyMap.put("SGD", 0.000090);
        currencyMap.put("MYR", 0.00030);
    }

    private void addCurrencySetting()
    {
        StringProperty value = new SimpleStringProperty();

        getService().addDropdownSetting(
                "Currency",
                "Select Currency",
                currencyMap.keySet().toArray(new String[0]),
                value

        );
    }

    private void processCurrency()
    {
        Modifiable modifiable = getService().getModifiable();
        List<Transaction> transactions = modifiable.transactions;
        List<InventoryItem> inventoryItems = modifiable.inventoryItems;

        if (transactions != null)
        {
            for (Transaction transaction: transactions)
            {

            }
        }

        if (inventoryItems != null)
        {
            for (InventoryItem item: inventoryItems)
            {
                if (modifiable.get)
                {
                    item.setPrice(item.getPrice() * currencyMap.get(value.getValue()));
                    item.setPurchasePrice(item.getPurchasePrice() * currencyMap.get(value.getValue()));

                }
                else
                {
                    item.setPrice(item.getPrice() / currencyMap.get(value.getValue()));
                    item.setPurchasePrice(item.getPurchasePrice() / currencyMap.get(value.getValue()));
                }
            }
        }
    }
    @Override
    public void onLoad() {
        addCurrencySetting();
        processCurrency();
    }
}
