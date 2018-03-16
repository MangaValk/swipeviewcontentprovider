package com.example.konata.swipeviewcontentprovider;

/**
 * Created by marmm on 15/03/2018.
 */

public class Recipe {
    private Integer mId;
    private String name, description;
    private Integer image;

    public Recipe(Integer mId, String name, String description, Integer image) {
        this.mId = mId;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}