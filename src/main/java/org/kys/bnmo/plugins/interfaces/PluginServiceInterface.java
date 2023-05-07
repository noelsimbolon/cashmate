package org.kys.bnmo.plugins.interfaces;

import javafx.animation.Timeline;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.kys.bnmo.model.Modifiable;

public interface PluginServiceInterface {

    public void addTab(Pane content, String title);
    public void addTab(Pane content, String title, Timeline timeline);
    public ControllerAdapterInterface getController();
    public Modifiable getModifiable();
    public void addDropdownSetting(
            String label,
            String placeholder,
            String[] items,
            String defaultValue,
            Property<String> selectedValue);

    public void addSettingSaveAction(EventHandler<ActionEvent> handler);

    public void addTextBoxSetting(
            String label,
            String placeholder,
            String defaultValue,
            StringProperty property
    );
}
