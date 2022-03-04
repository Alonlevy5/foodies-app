package com.comas.foodies.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {

    public final static Model instance = new Model();

    Executor executor = Executors.newFixedThreadPool(1);

    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    ModelFirebase modelFirebase = new ModelFirebase();

    private Model() {

    }

    public interface GetAllRecipesListener {
        void onComplete(List<Recipe> list);
    }

    public void getAllRecipes(GetAllRecipesListener listener) {
        modelFirebase.getAllRecipes(listener);
    }


    public interface GetRecipesById {
        void onComplete(Recipe recipe);
    }

    public void getRecipeById(String recipeId, GetRecipesById listener) {
        modelFirebase.getRecipeById(recipeId, listener);
    }

    public interface AddRecipeListener {
        void onComplete();
    }

    public void addRecipe(Recipe recipe, AddRecipeListener listener) {
        modelFirebase.addRecipe(recipe, listener);
    }

    public interface DeleteRecipeById {
        void onComplete();
    }

    public void deleteRecipeById(String recipeId, DeleteRecipeById listener) {
        modelFirebase.deleteRecipeById(recipeId,listener);
    }

    public interface UpdateRecipeById{
        void onComplete(Recipe recipe);
    }

    public void updateRecipeById(String recipeID, String name, String desc, UpdateRecipeById listener){
        modelFirebase.updateRecipeById(recipeID,name,desc,listener);
    }

}