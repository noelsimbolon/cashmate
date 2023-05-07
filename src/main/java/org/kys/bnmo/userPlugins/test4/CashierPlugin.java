package org.kys.bnmo.userPlugins.test4;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.model.Order;
import org.kys.bnmo.model.Transaction;
import org.kys.bnmo.plugins.interfaces.BasePlugin;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashierPlugin extends BasePlugin {

    private static final String defaultTax = "0";
    private static final String defaultServiceCharge = "0";
    private static final String configFileName = "cashierPlugin";
    private static final String defaultDiscount = "0";
    private StringProperty taxValue;
    private StringProperty serviceChargeValue;
    private StringProperty discountValue;
    private class SaveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                ArrayList<String> contents = new ArrayList<>();

                contents.add(taxValue.getValue());
                contents.add(serviceChargeValue.getValue());

                if (!isConfigValid(contents)) throw new Exception("Invalid Data");

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
    public CashierPlugin(PluginServiceInterface service)
    {
        super(service);
        taxValue = new SimpleStringProperty(defaultTax);
        serviceChargeValue = new SimpleStringProperty(defaultServiceCharge);
        discountValue = new SimpleStringProperty(defaultDiscount);
    }

    private boolean isConfigValid(ArrayList<String> configs)
    {
        for (String config: configs)
        {
            try {
                Double.parseDouble(config);
            }

            catch (Exception e)
            {
                return false;
            }
        }

        return true;
    }

    private void addSettings()
    {
        try {
            ArrayList<String> res = (getService()
                    .getController()
                    .getDatastore()
                    .loadConfigAPI(configFileName)
            );

            if (res.size() != 2 || !isConfigValid(res))
            {
                throw new FileNotFoundException("Content invalid!");
            }

            taxValue.setValue(res.get(0));
            serviceChargeValue.setValue(res.get(1));
        }

        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());

            ArrayList<String> contents = new ArrayList<>();
            contents.add(defaultTax);
            contents.add(defaultServiceCharge);

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

        getService().addTextBoxSetting(
                "Tax Percentage",
                "Insert Tax Percentage",
                taxValue.getValue(),
                taxValue
        );

        getService().addTextBoxSetting(
                "Service Charge",
                "Insert Service Charge Percentage",
                serviceChargeValue.getValue(),
                serviceChargeValue
        );
    }

    private void addCashierMenu()
    {

    }

    private void processPrice()
    {

    }

    @Override
    public void onLoad() {
        addSettings();
        addCashierMenu();
        getService().addSettingSaveAction(new SaveHandler());
    }
}
