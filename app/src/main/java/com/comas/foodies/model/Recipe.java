package com.comas.foodies.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Recipe {

    final public static String collectionName = "recipes";

    @PrimaryKey
    @NonNull
    String id;
    String name;
    String desc;
    String imageUrl;
    Long updateDate = 0L;
//    Boolean isDeleted = false;

    public Recipe() {

    }

    public Recipe(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Recipe(String name, String id, String desc) {
        this.name = name;
        this.id = id;
        this.desc = desc;
    }


    public Recipe(String id, String name, String desc, String imageUrl, Long updateDate) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.updateDate = updateDate;

    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public Map<String, Object> toJson() {

        Map<String, Object> json = new HashMap<String, Object>();

        json.put("id", id);
        json.put("name", name);
        json.put("desc", desc);
        json.put("updateDate", FieldValue.serverTimestamp());
        json.put("imageUrl", imageUrl);

        return json;
    }

    //factory pattern
    public static Recipe create(Map<String, Object> json) {

        String id = (String) json.get("id");
        String name = (String) json.get("name");
        String desc = (String) json.get("desc");
        String imageUrl = (String) json.get("imageUrl");

        Timestamp ts = (Timestamp) json.get("updateDate");
        Long updateDate = null;
        if (ts != null) {
            updateDate = ts.getSeconds();
        }


        return new Recipe(id, name, desc, imageUrl, updateDate);
    }


}
