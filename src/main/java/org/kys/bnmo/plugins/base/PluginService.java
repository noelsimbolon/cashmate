package org.kys.bnmo.plugins.base;

import org.kys.bnmo.plugins.interfaces.PageAdapterInterface;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

public class PluginService implements PluginServiceInterface {
    private PageAdapterInterface pageBuilder;
    public PluginService(PageAdapterInterface pageBuilder)
    {
        this.pageBuilder = pageBuilder;
    }
    @Override
    public PageAdapterInterface getPageBuilder() {
        return pageBuilder;
    }
}
