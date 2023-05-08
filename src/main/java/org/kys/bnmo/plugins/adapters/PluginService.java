package org.kys.bnmo.plugins.adapters;

import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import org.kys.bnmo.plugins.adapters.CheckoutPanelAdapter;
import org.kys.bnmo.plugins.adapters.ControllerAdapter;
import org.kys.bnmo.plugins.adapters.SettingAdapterInterface;
import org.kys.bnmo.plugins.interfaces.ControllerAdapterInterface;
import org.kys.bnmo.plugins.adapters.PageAdapterInterface;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;
import java.util.ArrayList;
import java.util.List;

public class PluginService implements PluginServiceInterface {

    private PageAdapterInterface pageBuilder;
    private SettingAdapterInterface settingBuilder;
    private CheckoutPanelAdapter checkoutPanel;
    private Modifiable modifiable;

    @Getter
    private List<Pane> pageContainers;

    @Getter
    private List<EventHandler<ActionEvent>> settingSaveActions;

    public PluginService(
            @Nullable PageAdapterInterface pageBuilder,
            @Nullable SettingAdapterInterface settingBuilder,
            @Nullable CheckoutPanelAdapter checkoutPanel,
            @Nullable Modifiable modifiable
            )
    {
        this.pageBuilder = pageBuilder;
        this.settingBuilder = settingBuilder;
        this.checkoutPanel = checkoutPanel;
        this.modifiable = modifiable;

        pageContainers = new ArrayList<>();
        settingSaveActions = new ArrayList<>();
    }

    @Override
    public void addTab(Pane content, String title) {

        if (pageBuilder != null)
        {
            pageBuilder.addTab(content, title);
            pageBuilder.addFactoryButton(title);
            pageContainers.add(content);
        }
    }

    @Override
    public void addTab(Pane content, String title, Timeline ... timelines) {
        if (pageBuilder != null)
        {
            addTab(content, title);
            for (Timeline timeline : timelines) {
                timeline.play();
            }
        }
    }
    @Override
    public ControllerAdapterInterface getController() {
        return new ControllerAdapter();
    }

    @Override
    @Nullable
    public Modifiable getModifiable() {
        return modifiable;
    }

    @Override
    public void addDropdownSetting(
            String label,
            String placeholder,
            String[] items,
            String defaultValue,
            Property<String> selectedValue) {


        if (settingBuilder != null)
        {
            settingBuilder.addDropdown(label, placeholder, items, defaultValue, selectedValue);
        }

    }

    @Override
    public void addSettingSaveAction(EventHandler<ActionEvent> handler) {
        settingSaveActions.add(handler);
    }

    @Override
    public void addTextBoxSetting(
            String label,
            String placeholder,
            String defaultValue,
            StringProperty property
    ) {
        if (settingBuilder != null) {
            settingBuilder.addTextBox(label, placeholder, defaultValue, property);
        }
    }

    @Override
    public void addStaticFieldCashier(String label, DoubleProperty property, String unit) {
        if (checkoutPanel != null) {
            checkoutPanel.addStaticField(label, property, unit);
        }
    }

    @Override
    public void addDynamicFieldCashier(String label, TextField field, String unit) {
        if (checkoutPanel != null) {
            checkoutPanel.addDynamicField(label, field, unit);
        }
    }
}
