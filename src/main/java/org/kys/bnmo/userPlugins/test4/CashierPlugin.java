package org.kys.bnmo.userPlugins.test4;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.plugins.interfaces.BasePlugin;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class CashierPlugin extends BasePlugin {

    private static final String defaultTax = "0";
    private static final String defaultServiceCharge = "0";
    private static final String configFileName = "cashierPlugin";
    private static final String defaultDiscount = "0";
    private StringProperty taxValue;
    private StringProperty serviceChargeValue;
    private DoubleProperty taxNumberValue;
    private DoubleProperty serviceChargeNumberValue;
    private DoubleProperty otherDiscountNumberValue;
    private TextField otherDiscountInput;
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

                synchronizeNumbers();
            }

            catch (Exception exception)
            {
                taxValue.set(taxNumberValue.getValue().toString());
                serviceChargeValue.set(serviceChargeNumberValue.getValue().toString());
                System.out.println(exception.getMessage());
            }
        }
    }

    private void synchronizeNumbers()
    {
        taxNumberValue.setValue(Double.parseDouble(taxValue.getValue()));
        serviceChargeNumberValue.setValue(
                Double.parseDouble(serviceChargeValue.getValue()));
    }
    public CashierPlugin(PluginServiceInterface service)
    {
        super(service);
        taxValue = new SimpleStringProperty(defaultTax);
        serviceChargeValue = new SimpleStringProperty(defaultServiceCharge);

        taxNumberValue = new SimpleDoubleProperty(
                Double.parseDouble(defaultTax));
        serviceChargeNumberValue = new SimpleDoubleProperty(
                Double.parseDouble(defaultServiceCharge));
        otherDiscountNumberValue = new SimpleDoubleProperty(
                Double.parseDouble(defaultDiscount));

        synchronizeNumbers();
    }

    private boolean isConfigValid(ArrayList<String> configs)
    {
        for (String config: configs)
        {
            try {
                double val = Double.parseDouble(config);

                if (val < 0 || val > 100) throw new Exception("Invalid value range!");
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

            synchronizeNumbers();
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
        getService().addStaticFieldCashier("Tax", taxNumberValue, "%");
        getService().addStaticFieldCashier("Service Charge", serviceChargeNumberValue, "%");

        otherDiscountInput = new TextField();;
        otherDiscountInput.textProperty().setValue(otherDiscountNumberValue.getValue().toString());
        otherDiscountInput.focusedProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (!newValue) {
                    try {
                        double val = Double.parseDouble(
                                otherDiscountInput.textProperty().getValue());

                        if (val < 0 || val > 100) throw new Exception("Invalid value range!");

                        otherDiscountNumberValue.setValue(val);
                    }

                    catch (Exception e)
                    {
                        otherDiscountInput.textProperty()
                                .setValue(otherDiscountNumberValue.getValue().toString());
                        System.out.println(e.getMessage());
                    }
                }
            });
        otherDiscountInput.setStyle("-fx-pref-width: 50px");
        getService().addDynamicFieldCashier("Other discount", otherDiscountInput, "%");
    }

    private void processPrice()
    {
        Modifiable.TotalPrice totalPrice = getService().getModifiable().totalPrice;

        if (totalPrice.prioritize)
        {
            totalPrice.amount += totalPrice.amount * serviceChargeNumberValue.getValue() / 100;
            totalPrice.amount += totalPrice.amount * taxNumberValue.getValue() / 100;
        }

        else
        {
            totalPrice.amount -= totalPrice.amount * otherDiscountNumberValue.getValue() / 100;
        }
    }

    @Override
    public void onLoad() {
        addSettings();
        addCashierMenu();
        getService().addSettingSaveAction(new SaveHandler());
        processPrice();
    }
}
