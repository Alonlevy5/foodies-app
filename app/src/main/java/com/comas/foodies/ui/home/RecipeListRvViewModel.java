package com.comas.foodies.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;

import java.util.List;

public class RecipeListRvViewModel extends ViewModel {


    LiveData<List<Recipe>> data;


    public RecipeListRvViewModel() {
        this.data = Model.instance.getAll();
    }

    public LiveData<List<Recipe>> getData() {
        return data;
    }


}