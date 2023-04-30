package org.kys.bnmo.components.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.ComponentBuilder;

public abstract class TabContainer extends ComponentBuilder {

    private StringProperty headerProperty;
    private Pane content;
    private Label headerTitle;
    private HBox header;

    protected abstract Pane getContent();
    protected void addHeaderTitle(String title)
    {
        headerProperty.set(title);
        header.getChildren().add(headerTitle);
    }
    public void setBackButton()
    {
    }

    @Override
    public void reset() {

        header = new HBox();
        header.getStyleClass().add("tab-header");
        header.setPrefWidth(Double.MAX_VALUE);

        headerTitle = new Label();
        headerTitle.getStyleClass().add("add-member-title");

        headerProperty = new SimpleStringProperty("");
        headerTitle.textProperty().bind(headerProperty);

        content = getContent();

        VBox root = new VBox();
        root.getChildren().addAll(header, content);
        root.getStyleClass().add("tab-content");

        setRoot(root);
    }

}
