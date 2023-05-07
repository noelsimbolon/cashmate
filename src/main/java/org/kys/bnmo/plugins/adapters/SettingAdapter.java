package org.kys.bnmo.plugins.adapters;

import javafx.beans.property.Property;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.views.Page;

public class SettingAdapter implements SettingAdapterInterface{

    private FormBuilder settingBuilder;

    public SettingAdapter (FormBuilder settingBuilder)
    {
        this.settingBuilder = settingBuilder;
    }

    @Override
    public void addDropdown(
            String label,
            String placeholder,
            String[] items,
            Property<String> selectedValue) {

        settingBuilder.addDropdown(
                label,
                placeholder,
                items,
                selectedValue
        );
    }
}
