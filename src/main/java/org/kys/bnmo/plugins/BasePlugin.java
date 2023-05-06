package org.kys.bnmo.plugins;

import org.kys.bnmo.plugins.adapters.PageAdapter;
import org.kys.bnmo.views.Page;

public class BasePlugin {
    private PageAdapter pageBuilder;
    public BasePlugin(PageAdapter pageBuilder)
    {
        this.pageBuilder = pageBuilder;
    }

    protected PageAdapter getPageBuilder() {
        return pageBuilder;
    }
}
