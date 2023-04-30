package org.kys.bnmo.helpers;

import javafx.scene.Parent;

public class StyleLoadHelper extends LoadHelper {
    private String path;

    public StyleLoadHelper() {}
    public StyleLoadHelper(String ... paths)
    {
        super(paths);
    }

    @Override
    public void load(Parent root) {

        for (String path: paths) {
            try {
                String css = this.getClass()
                        .getResource(path)
                        .toExternalForm();

                root.getStylesheets().add(css);
            } catch (NullPointerException e) {
                System.out.println("Failed to load css with path: " + path);
            }
        }
    }
    @Override
    public void setResourcePath(String ... paths) {
        for (String path: paths)
        {
            this.paths.add(path);
        }
    }
}
