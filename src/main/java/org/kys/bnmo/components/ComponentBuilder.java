package org.kys.bnmo.components;
import javafx.scene.layout.Pane;

public abstract class ComponentBuilder {

    private Pane root;

    protected void setRoot(Pane root)
    {
        this.root = root;
    }

    protected Pane getRoot()
    {
        return this.root;
    }

    public ComponentBuilder()
    {
        reset();
    }
    public Pane getAndResetComponent()
    {
        Pane rootResult = root;
        reset();
        return rootResult;
    }

    abstract public void reset();
}
