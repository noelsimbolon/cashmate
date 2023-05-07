package org.kys.bnmo.plugins.adapters;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.kys.bnmo.views.Page;

public class PageAdapter implements PageAdapterInterface {

    private Page pageBuilder;

    public PageAdapter(Page pageBuilder)
    {
        this.pageBuilder = pageBuilder;
    }

    @Override
    public void addFactoryButton(String name)
    {
        pageBuilder.addFactoryButton(name);
    }
    @Override
    public void addTab(Pane content, String title)
    {
        pageBuilder.addTab(content, title);
    }
}
