package com.comas.foodies.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public ModelFirebase() {
        // see the same list of recipes in two devices
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }


    public interface GetAllRecipesListener {
        void onComplete(List<Recipe> list);
    }

    public void getAllRecipes(GetAllRecipesListener listener) {

        db.collection(Recipe.collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Recipe> list = new LinkedList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Recipe recipe = Recipe.create(doc.getData());
                                if (recipe != null) {
                                    //recipe.setId(doc.getId());
                                    Log.d("TAG", "id is " + recipe.getId());
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

        //add(object) is for adding and generate random id in fireBase
        //document(string) and set(object) is for document() give the name he get to the doc
        //set() add it to fireBase
        db.collection(Recipe.collectionName)
                .document(recipe.getId())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void getRecipeById(String recipeId, Model.GetRecipesById listener) {

        db.collection(Recipe.collectionName)
                .document(recipeId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Recipe recipe = null;
                        if (task.isSuccessful() & task.getResult() != null) {
                            recipe = Recipe.create(task.getResult().getData());
                        }
                        listener.onComplete(recipe);
                    }
                });
    }


    public void updateRecipeById(String recipeId, String name, String desc, Model.UpdateRecipeById listener) {
        Map<String, Object> updateRecipe = new HashMap<>();
        //crate the filed we want to update and give it to firebase
        updateRecipe.put("name", name);
        updateRecipe.put("desc", desc);


        db.collection(Recipe.collectionName)
                .document(recipeId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful() & task.getResult() != null) {

                            db.collection(Recipe.collectionName)
                                    .document(recipeId)
                                    .update(updateRecipe)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Recipe recipe = null;
                                            recipe = Recipe.create(task.getResult().getData());
                                            listener.onComplete(recipe);
                                        }
                                    });
                        }
                    }
                });

    }

    public void deleteRecipeById(String recipeId, Model.DeleteRecipeById listener) {

        //delete by id we get
        db.collection(Recipe.collectionName)
                .document(recipeId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });

    }


}
