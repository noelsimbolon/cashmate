package org.kys.bnmo.helpers;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class IconButtonHelper {

    public void setButtonGraphic(Button button, String path) {
        // Set Button Graphic to Image that Located in the Path
        Image image = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
    }

    public void setButtonGraphic(Button button, String path, int width, int height) {
        // Set Button Graphic to Image that Located in the Path
        Image image = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        button.setGraphic(imageView);
    }

    public void selectButton(Button button)
    {
        button.getStyleClass().clear();
        button.getStyleClass().add("selected-nav-button");
        setButtonGraphic(button, "/icon/" + button.getId() + "SelectedImage.png");
    }
    public void unselectButton(Button button)
    {
        button.getStyleClass().clear();
        button.getStyleClass().add("unselected-nav-button");
        setButtonGraphic(button,
                "/icon/" + button.getId() + "Image.png");
    }
}
