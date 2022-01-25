package com.comas.foodies.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
   FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getAllRecipes(Model.GetAllRecipesListener listener) {
        db.collection(Recipe.collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Recipe> list=new LinkedList<Recipe>();
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot doc:task.getResult() ){
                                Recipe recipe=Recipe.create(doc.getData());
                                if(recipe !=null){
                                    list.add(recipe);
                                }

                            }
                        }
                        listener.onComplete(list);

                    }

                });
    }

    public void addRecipe(Recipe recipe, Model.AddRecipeListener listener) {
        Map<String, Object> json = recipe.toJson();

        db.collection(Recipe.collectionName)
                .document(recipe.getId())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }

    public void getRecipeById(String recipeId) {
    }
}
