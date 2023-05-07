package org.kys.bnmo.plugins.interfaces;

public abstract class BasePlugin implements PluginInterface{
    private PluginServiceInterface service;

    protected PluginServiceInterface getService()
    {
        return service;
    }

    public BasePlugin(PluginServiceInterface service)
    {
        this.service = service;
    }
}
