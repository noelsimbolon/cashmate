package org.kys.bnmo.components;
import javafx.scene.layout.Pane;

public abstract class ComponentBuilder {

    protected Pane root;
    ComponentBuilder()
    {
        reset();
    }
    abstract public void reset();
    public Pane getAndResetComponent()
    {
        Pane rootResult = root;
        reset();
        return rootResult;
    }
}
