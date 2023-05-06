package org.kys.bnmo.components.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.components.ComponentBuilder;
import org.kys.bnmo.components.ComponentFactory;

public abstract class TabContainer implements ComponentFactory {

    private StringProperty headerProperty;

    private Pane content;

    private Label headerTitle;

    @Getter(AccessLevel.PROTECTED)
    private HBox header;

    @Getter(AccessLevel.PROTECTED)
    private Pane root;

    protected abstract Pane getContent();

    protected void addHeaderTitle(String title)
    {
        headerProperty.set(title);
        header.getChildren().add(headerTitle);
    }

    protected void additionalAction() {}

    @Override
    @NotNull
    public Pane getComponent() {
        header = new HBox();
        header.getStyleClass().add("tab-header");
        header.setPrefWidth(Double.MAX_VALUE);

        headerTitle = new Label();
        headerTitle.getStyleClass().add("add-member-title");

        headerProperty = new SimpleStringProperty("");
        headerTitle.textProperty().bind(headerProperty);
        content = getContent();

        root = new VBox();
        root.getChildren().addAll(header, content);
        root.getStyleClass().add("tab-content");

        additionalAction();

        return root;
    }
}
