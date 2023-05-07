package org.kys.bnmo.components;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.interfaces.ComponentFactory;

public class SingleComponent implements ComponentFactory {

    private Pane root;
    public SingleComponent (Pane root)
    {
        this.root = root;
    }
    @Override
    public @NotNull Pane getComponent() {
        return root;
    }
}
