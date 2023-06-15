package de.openknowledge.workshop.serverless.cs.lambda;

import de.openknowledge.workshop.serverless.cs.model.MenuEntry;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MenuItemsResponse {

    List<MenuEntry> menuEntryList;

    public MenuItemsResponse() {
        // empty by default
    }

    public MenuItemsResponse(List<MenuEntry> menuEntryList) {
        this.menuEntryList = menuEntryList;
    }

    public MenuItemsResponse(String menuEntryList) {
        this.menuEntryList = Collections.EMPTY_LIST;
    }

    public List<MenuEntry> getMenuEntryList() {
        return menuEntryList;
    }

    public void setMenuEntryList(List<MenuEntry> menuEntryList) {
        this.menuEntryList = menuEntryList;
    }
}
