package org.kys.bnmo.components;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class TabContainer extends ComponentBuilder {

    public TabContainer(Parent content)
    {
        root = new VBox();
    }

    @Override
    public void reset() {

    }
}
