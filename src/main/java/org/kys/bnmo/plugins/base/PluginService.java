package org.kys.bnmo.plugins.base;

import javafx.scene.Parent;
import org.kys.bnmo.plugins.adapters.ControllerAdapter;
import org.kys.bnmo.plugins.interfaces.ControllerAdapterInterface;
import org.kys.bnmo.plugins.interfaces.PageAdapterInterface;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

public class PluginService implements PluginServiceInterface {

    private PageAdapterInterface pageBuilder;

    public PluginService(PageAdapterInterface pageBuilder)
    {
        this.pageBuilder = pageBuilder;
    }
    @Override
    public void addTab(Parent content, String title) {
        pageBuilder.addTab(content, title);
        pageBuilder.addFactoryButton(title);
    }

    @Override
    public ControllerAdapterInterface getController() {
        return new ControllerAdapter();
    }
}
