package org.kys.bnmo.helpers;

import javafx.scene.Parent;

public class StyleLoadHelper implements LoadHelper {
    private String path;
    public StyleLoadHelper(String path)
    {
        this.path = path;
    }

    @Override
    public void load(Parent root) {
        try {
            String css = this.getClass()
                    .getResource(path)
                    .toExternalForm();

            root.getStylesheets().add(css);
        }

        catch (NullPointerException e)
        {
            System.out.println("Failed to load css for component!");
        }
    }
    @Override
    public void setResourcePath(String path) {
        this.path = path;
    }
}
