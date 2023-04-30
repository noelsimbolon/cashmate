package org.kys.bnmo.components.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentBuilder;

public abstract class TabContainer extends ComponentBuilder {

    private StringProperty headerProperty;
    private Pane content;
    private Label headerTitle;
    private HBox header;

    protected abstract Pane getContent();
    protected void setHeaderProperty(String title)
    {
        headerProperty.set(title);
    }

    protected abstract void setHeader();
    public void setBackButton()
    {
    }

    @Override
    public void reset() {

        headerTitle = new Label();
        headerProperty = new SimpleStringProperty("");
        headerTitle.textProperty().bind(headerProperty);

        header = new HBox();
        header.getChildren().add(headerTitle);

        content = getContent();

        VBox root = new VBox();
        root.getChildren().addAll(header, content);
        root.getStyleClass().add("tab-content");
        setHeader();

        setRoot(root);
    }

}
