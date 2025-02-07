
// FilterOption.java
package com.myecommerce.core.models;

public class FilterOption {
    private String name;
    private String value;
    private int count;
    private String filterUrl;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public String getFilterUrl() { return filterUrl; }
    public void setFilterUrl(String filterUrl) { this.filterUrl = filterUrl; }
}