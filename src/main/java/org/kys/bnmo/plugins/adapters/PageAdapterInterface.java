package org.kys.bnmo.plugins.adapters;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public interface PageAdapterInterface {

    public void addFactoryButton(String name);

    public void addTab(Pane content, String title);
}
