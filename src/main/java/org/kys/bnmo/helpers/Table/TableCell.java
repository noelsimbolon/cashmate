package org.kys.bnmo.helpers.Table;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

public class TableCell {

    @Getter
    @Setter
    private Image image;

    @Getter
    @Setter
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
}
