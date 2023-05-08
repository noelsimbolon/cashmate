package org.kys.bnmo.plugins.adapters;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.TextField;

public interface CheckoutPanelAdapterInterface {
    public void addStaticField(String label, DoubleProperty property, String unit);

    public void addDynamicField(String label, TextField field, String unit);
}
