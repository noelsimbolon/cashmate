package org.kys.bnmo.components.bases;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kys.bnmo.components.ComponentBuilder;
import org.kys.bnmo.helpers.views.loaders.StyleLoadHelper;

import java.io.File;

public class FormBuilder extends ComponentBuilder {
    private VBox inputFields;

    @Override
    public void reset() {
        VBox root = new VBox();
        this.inputFields = new VBox();
        root.getStyleClass().add("form-container");
        inputFields.getStyleClass().add("input-field-container");
        root.getChildren().add(inputFields);
        StyleLoadHelper helper = new StyleLoadHelper("/styles/form.css");
        helper.load(root);

        setRoot(root);
    }

    public void addTitle(String title) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("form-title");
        getRoot().getChildren().add(0, titleLabel);
    }

    // Text fields
    public void addTextBox(String label, String placeholder) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // Text field
        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        textField.getStyleClass().add("form-text-field");
        HBox.setHgrow(textField, Priority.ALWAYS);

        // Add components to root
        row.getChildren().addAll(labelComponent, textField);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    public void addTextBox(String label, String placeholder, String defaultValue) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // Text field
        TextField textField = new TextField(defaultValue);
        textField.setPromptText(placeholder);
        textField.getStyleClass().add("form-text-field");
        HBox.setHgrow(textField, Priority.ALWAYS);

        // Add components to root
        row.getChildren().addAll(labelComponent, textField);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    // Text field with string property binding
    public void addTextBox(String label, String placeholder, StringProperty property) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // Text field
        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        textField.getStyleClass().add("form-text-field");
        HBox.setHgrow(textField, Priority.ALWAYS);

        // Bind text property to parameter
        textField.textProperty().bindBidirectional(property);

        // Add components to root
        row.getChildren().addAll(labelComponent, textField);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    public void addTextBox(String label, String placeholder, String defaultValue, StringProperty property) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // Text field
        TextField textField = new TextField(defaultValue);
        textField.setPromptText(placeholder);
        textField.getStyleClass().add("form-text-field");
        HBox.setHgrow(textField, Priority.ALWAYS);

        // Bind text property to parameter
        textField.textProperty().bindBidirectional(property);

        // Add components to root
        row.getChildren().addAll(labelComponent, textField);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    // Dropdowns
    public void addDropdown(String label, String placeholder, String[] items) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // Dropdown
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(placeholder);
        comboBox.getStyleClass().add("form-dropdown");
        comboBox.getItems().addAll(items);

        // Bind the preferred width of the dropdown to the available space
        DoubleBinding comboBoxWidth = Bindings.createDoubleBinding(() ->
                        row.getWidth() - labelComponent.getWidth() - row.getSpacing(),
                row.widthProperty(), labelComponent.widthProperty());

        comboBox.prefWidthProperty().bind(comboBoxWidth);

        // Add components to root
        row.getChildren().addAll(labelComponent, comboBox);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    public void addDropdown(String label, String placeholder, String[] items, String defaultValue) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // Dropdown
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(placeholder);
        comboBox.getStyleClass().add("form-dropdown");
        comboBox.getItems().addAll(items);
        comboBox.setValue(defaultValue);

        // Bind the preferred width of the dropdown to the available space
        DoubleBinding comboBoxWidth = Bindings.createDoubleBinding(() ->
                        row.getWidth() - labelComponent.getWidth() - row.getSpacing(),
                row.widthProperty(), labelComponent.widthProperty());

        comboBox.prefWidthProperty().bind(comboBoxWidth);

        // Add components to root
        row.getChildren().addAll(labelComponent, comboBox);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    // Dropdown with string property binding
    public void addDropdown(String label, String placeholder, String[] items, String defaultValue, Property<String> selectedValue) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // Dropdown
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(placeholder);
        comboBox.getStyleClass().add("form-dropdown");
        comboBox.getItems().addAll(items);
        comboBox.setValue(defaultValue);

        // Bind the preferred width of the dropdown to the available space
        DoubleBinding comboBoxWidth = Bindings.createDoubleBinding(() ->
                        row.getWidth() - labelComponent.getWidth() - row.getSpacing(),
                row.widthProperty(), labelComponent.widthProperty());

        comboBox.prefWidthProperty().bind(comboBoxWidth);

        // Bind the value property of the ComboBox to the selectedValue property
        comboBox.valueProperty().bindBidirectional(selectedValue);

        // Add components to root
        row.getChildren().addAll(labelComponent, comboBox);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    public void addDropdown(String label, String placeholder, String[] items, Property<String> selectedValue) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // Dropdown
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(placeholder);
        comboBox.getStyleClass().add("form-dropdown");
        comboBox.getItems().addAll(items);

        // Bind the preferred width of the dropdown to the available space
        DoubleBinding comboBoxWidth = Bindings.createDoubleBinding(() ->
                        row.getWidth() - labelComponent.getWidth() - row.getSpacing(),
                row.widthProperty(), labelComponent.widthProperty());

        comboBox.prefWidthProperty().bind(comboBoxWidth);

        // Bind the value property of the ComboBox to the selectedValue property
        comboBox.valueProperty().bindBidirectional(selectedValue);

        // Add components to root
        row.getChildren().addAll(labelComponent, comboBox);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    public void addFilePicker(String label, Stage stage) {

        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // create a Button
        Button button = new Button(label);
        button.getStyleClass().add("file-picker-button");

        // create a File chooser
        FileChooser chooser = new FileChooser();

        // create an Event Handler
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        // get the file selected
                        File file = chooser.showOpenDialog(stage);
                        if (file != null) {
                        }
                    }
                };

        button.setOnAction(event);

        // Bind the preferred width of the dropdown to the available space
        DoubleBinding comboBoxWidth = Bindings.createDoubleBinding(() ->
                        row.getWidth() - labelComponent.getWidth() - row.getSpacing(),
                row.widthProperty(), labelComponent.widthProperty());

        button.prefWidthProperty().bind(comboBoxWidth);

        // Add components to root
        row.getChildren().addAll(labelComponent, button);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    public void addFolderPicker(String label, Stage stage, StringProperty path) {

        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        labelComponent.getStyleClass().add("form-label");

        // create a Button
        Button button = new Button(label);
        button.getStyleClass().add("file-picker-button");

        // create a Directory chooser
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a folder");
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        // create an Event Handler
        EventHandler<ActionEvent> event =
                e -> {
                    // get the folder selected
                    File folder = chooser.showDialog(stage);
                    if (folder != null) {
                        button.setText(folder.getAbsolutePath());
                    }
                };

        button.setOnAction(event);

        // Bind the preferred width of the dropdown to the available space
        DoubleBinding comboBoxWidth = Bindings.createDoubleBinding(() ->
                        row.getWidth() - labelComponent.getWidth() - row.getSpacing(),
                row.widthProperty(), labelComponent.widthProperty());

        button.prefWidthProperty().bind(comboBoxWidth);

        // Bind the text property of the button to the path property
        button.textProperty().bindBidirectional(path);

        // Add components to root
        row.getChildren().addAll(labelComponent, button);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    public void addButton(String label, EventHandler<ActionEvent> ...eventHandler) {
        // Create button
        Button button = new Button(label);
        button.getStyleClass().add("form-button");
        HBox.setHgrow(button, Priority.ALWAYS);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add event handler
        for (EventHandler<ActionEvent> handler : eventHandler)
        {
            button.addEventHandler(ActionEvent.ACTION, handler);
        }

        // Add components to root
        HBox row = new HBox(spacer, button);
        row.getStyleClass().add("form-input");
        row.setAlignment(Pos.CENTER_RIGHT);
        getRoot().getChildren().add(row);
    }
}
