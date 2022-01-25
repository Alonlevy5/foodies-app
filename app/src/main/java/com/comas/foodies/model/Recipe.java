package com.comas.foodies.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Recipe {
    final  public static String collectionName="recipes";
    @PrimaryKey
    @NonNull
   // private String id;
    private String name;
    private String desc;
    private String image;

    public Recipe(){

    }

    public Recipe( String name, String desc, String image) {
      //  this.id = id;
        this.name = name;
        this.desc = desc;
        this.image = image;
    }




    public String getId() {
        return name;
    }

    //public void setId(String id) {
     //   this.id = id;
    //}

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

    public  Map<String, Object> toJson() {
        Map<String,Object> json=new HashMap<String,Object>();
       // json.put("id",id);
        json.put("name",name);
        json.put("desc",desc);

        return json;
    }
    public static Recipe create(Map<String, Object> json) {
       // String id=(String)json.get("id");
        String name=(String)json.get("name");
        String desc=(String)json.get("desc");
        Recipe recipe=new Recipe(name,desc,null);
        return recipe;
    }

}
