package org.kys.bnmo.components;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public interface ComponentFactory {

    @NotNull
    Pane getComponent();
}
