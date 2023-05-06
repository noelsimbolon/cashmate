package org.kys.bnmo.plugins.adapters;

import javafx.scene.Parent;
import org.kys.bnmo.views.Page;

public class PageAdapter {

    private Page pageBuilder;
    public PageAdapter()
    {}

    public PageAdapter(Page pageBuilder)
    {
        this.pageBuilder = pageBuilder;
    }

    public void addFactoryButton(String name)
    {
        pageBuilder.addFactoryButton(name);
    }

    public void addTab(Parent content, String title)
    {
        pageBuilder.addTab(content, title);
    }
}
