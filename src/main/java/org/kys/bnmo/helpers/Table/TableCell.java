package org.kys.bnmo.helpers.Table;

import javafx.scene.image.Image;

public class TableCell {
    private Image image;
    private String text;

    public TableCell(String str) {
        this.text = str;
        this.image = null;
    }

    public TableCell(String str, Image image) {
        this.text = str;
        this.image = image;
    }

    public TableCell(Image image) {
        this.text = null;
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
