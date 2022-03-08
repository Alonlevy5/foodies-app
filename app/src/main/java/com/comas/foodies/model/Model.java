package com.comas.foodies.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {

    public final static Model instance = new Model();

    Executor executor = Executors.newFixedThreadPool(1);

    Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    ModelFirebase modelFirebase = new ModelFirebase();

    public enum RecipeListLoadingState {
        loading,
        loaded
    }

    MutableLiveData<RecipeListLoadingState> recipeListLoadingState = new MutableLiveData<>();

    public LiveData<RecipeListLoadingState> getRecipeListLoadingState() {
        return recipeListLoadingState;
    }


    private Model() {
        recipeListLoadingState.setValue(RecipeListLoadingState.loaded);
    }

    MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();

    public LiveData<List<Recipe>> getAll() {

        if (recipeList.getValue() == null)
            refreshRecipeList();

        return recipeList;
    }

    public void refreshRecipeList() {
        recipeListLoadingState.setValue(RecipeListLoadingState.loading);

        modelFirebase.getAllRecipes(new ModelFirebase.GetAllRecipesListener() {
            @Override
            public void onComplete(List<Recipe> list) {
                recipeList.setValue(list);
                recipeListLoadingState.setValue(RecipeListLoadingState.loaded);
            }
        });
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
        modelFirebase.deleteRecipeById(recipeId, listener);
    }

    public interface UpdateRecipeById {
        void onComplete(Recipe recipe);
    }

    public void updateRecipeById(String recipeID, String name, String desc, UpdateRecipeById listener) {
        modelFirebase.updateRecipeById(recipeID, name, desc, listener);
    }

}