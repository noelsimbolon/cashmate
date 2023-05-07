package org.kys.bnmo.plugins.interfaces;

import javafx.beans.property.Property;
import javafx.scene.Parent;
import org.kys.bnmo.model.Modifiable;

public interface PluginServiceInterface {

    public void addTab(Parent content, String title);
    public ControllerAdapterInterface getController();
    public Modifiable getModifiable();
    public void addDropdownSetting(
            String label,
            String placeholder,
            String[] items,
            Property<String> selectedValue);
}
