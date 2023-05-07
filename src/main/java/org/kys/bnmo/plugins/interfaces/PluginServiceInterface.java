package org.kys.bnmo.plugins.interfaces;

import javafx.scene.Parent;

public interface PluginServiceInterface {

    public void addTab(Parent content, String title);
    public ControllerAdapterInterface getController();

}
