package org.kys.bnmo.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.kys.bnmo.components.tabs.TabContainer;

public interface NavigationHandler {
    public EventHandler<ActionEvent> getEventHandler(TabContainer factory, String title);
}
