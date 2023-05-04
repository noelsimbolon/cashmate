package org.kys.bnmo.helpers.loaders;

import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;

public abstract class LoadHelper {
    protected List<String> paths;

    protected LoadHelper()
    {
        paths = new ArrayList<>();
    }

    protected LoadHelper(String ... paths)
    {
        this.paths = new ArrayList<>();
        for (String path: paths) this.paths.add(path);
    }
    public abstract void load(Parent root);
    public abstract void setResourcePath(String ... paths);
}
