package com.comas.foodies.model;

public class Recipe {
    private String id;
    private String name;
    private String desc;
    private String image;

    public Recipe(String id, String name, String desc, String image) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
