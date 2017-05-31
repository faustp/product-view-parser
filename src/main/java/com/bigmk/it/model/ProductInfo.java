package com.bigmk.it.model;

/**
 * Created by faust on 12/6/16.
 */
public class ProductInfo {

    private String name;
    private int views;

    public ProductInfo(String name, int views) {
        this.name = name;
        this.views = views;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "name='" + name + '\'' +
                ", views=" + views +
                '}';
    }
}
