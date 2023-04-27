package org.kys.bnmo.components;

import javafx.scene.Parent;

public interface ComponentBuilder {
    public void reset();
    public Parent getAndResetComponent();
}
