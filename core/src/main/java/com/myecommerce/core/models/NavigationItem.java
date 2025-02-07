package com.myecommerce.core.models;

import java.util.List;

public class NavigationItem {
    private String title;
    private String path;
    private boolean active;
    private List<NavigationItem> children;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<NavigationItem> getChildren() {
        return children;
    }

    public void setChildren(List<NavigationItem> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }
}
