package org.kys.bnmo.plugins.adapters;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public interface SettingAdapterInterface {
    public void addDropdown(
            String label,
            String placeholder,
            String[] items,
            String defaultValue,
            Property<String> selectedValue);
}
