package org.kys.bnmo.plugins.adapters;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.TextField;
import org.kys.bnmo.components.bases.CheckoutPanel;

public class CheckoutPanelAdapter implements CheckoutPanelAdapterInterface {

    private CheckoutPanel checkoutPanel;
    public CheckoutPanelAdapter(CheckoutPanel checkoutPanel) {
        this.checkoutPanel = checkoutPanel;
    }

    @Override
    public void addStaticField(String label, DoubleProperty property, String unit)
    {
        checkoutPanel.addStaticField(label, property, unit);
    }

    @Override
    public void addDynamicField(String label, TextField field, String unit)
    {
        checkoutPanel.addDynamicField(label, field, unit);
    }
}
