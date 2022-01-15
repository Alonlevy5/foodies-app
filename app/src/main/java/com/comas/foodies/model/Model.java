package com.comas.foodies.model;

import java.util.LinkedList;
import java.util.List;

public class Model {

    public final static Model instance = new Model();

    private List<Recipe> recipes = new LinkedList<>();

    private Model() {
        recipes.add(new Recipe("0", "MARAK OF", "Put one sasi gez inside", null));
        recipes.add(new Recipe("1", "HAMIN", "Dont eat to much", null));

    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public Recipe getRecipeById(String id) {
        return recipes.get(Integer.parseInt(id));
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
}