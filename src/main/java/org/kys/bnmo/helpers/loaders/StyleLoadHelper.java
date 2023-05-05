package org.kys.bnmo.helpers.loaders;

import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

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
    public void setResourcePath(@NotNull String ... paths) {
        for (String path: paths)
        {
            this.paths.add(path);
        }
    }
}
