package org.kys.bnmo.plugins.base;

import javafx.beans.property.Property;
import javafx.scene.Parent;
import org.jetbrains.annotations.Nullable;
import org.kys.bnmo.plugins.adapters.ControllerAdapter;
import org.kys.bnmo.plugins.adapters.SettingAdapterInterface;
import org.kys.bnmo.plugins.interfaces.ControllerAdapterInterface;
import org.kys.bnmo.plugins.adapters.PageAdapterInterface;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

public class PluginService implements PluginServiceInterface {

    private PageAdapterInterface pageBuilder;
    private SettingAdapterInterface settingBuilder;
    private Modifiable modifiable;

    public PluginService(
            @Nullable PageAdapterInterface pageBuilder,
            @Nullable SettingAdapterInterface settingBuilder,
            @Nullable Modifiable modifiable
            )
    {
        this.pageBuilder = pageBuilder;
        this.settingBuilder = settingBuilder;
        this.modifiable = modifiable;
    }
    @Override
    public void addTab(Parent content, String title) {

        if (pageBuilder != null)
        {
            pageBuilder.addTab(content, title);
            pageBuilder.addFactoryButton(title);
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
    public void addDropdownSetting(String label, String placeholder, String[] items, Property<String> selectedValue) {

        if (settingBuilder != null)
        {
            settingBuilder.addDropdown(label, placeholder, items, selectedValue);
        }

    }
}
