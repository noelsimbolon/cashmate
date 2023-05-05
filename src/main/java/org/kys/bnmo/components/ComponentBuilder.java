package org.kys.bnmo.components;

import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class ComponentBuilder {

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Pane root;

    public ComponentBuilder() {
        reset();
    }

    public Pane getAndResetComponent() {
        Pane rootResult = root;
        reset();
        return rootResult;
    }

    abstract public void reset();
}
