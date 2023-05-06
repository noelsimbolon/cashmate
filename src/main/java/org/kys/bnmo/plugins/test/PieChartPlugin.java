package org.kys.bnmo.plugins.test;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.tabs.ReportTab;
import org.kys.bnmo.plugins.BasePlugin;
import org.kys.bnmo.plugins.PluginInterface;
import org.kys.bnmo.plugins.adapters.PageAdapter;

public class PieChartPlugin extends BasePlugin implements PluginInterface {

    public PieChartPlugin(PageAdapter pageAdapter)
    {
        super(pageAdapter);
    }

    private Pane getContent()
    {
        return new ReportTab().getComponent();
    }
    @Override
    public void onLoad() {
        getPageBuilder().addTab(getContent(), "Plugin broo");
    }
}
