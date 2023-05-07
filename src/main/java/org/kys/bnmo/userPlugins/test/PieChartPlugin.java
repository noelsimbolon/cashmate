package org.kys.bnmo.userPlugins.test;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.plugins.interfaces.PluginInterface;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

public class PieChartPlugin implements PluginInterface {

    PluginServiceInterface service;

    public PieChartPlugin(PluginServiceInterface service)
    {
        this.service = service;
    }

    private Pane getContent()
    {
        return new VBox();
    }
    @Override
    public void onLoad() {
        service.getPageBuilder().addTab(getContent(), "Plugin broo");
    }
}
