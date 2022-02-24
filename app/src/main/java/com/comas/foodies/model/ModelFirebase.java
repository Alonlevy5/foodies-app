package com.comas.foodies.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //TODO 24/02/22 need to add delete update and getById to fireBase
    public void getAllRecipes(Model.GetAllRecipesListener listener) {
        db.collection(Recipe.collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Recipe> list = new LinkedList<Recipe>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Recipe recipe = Recipe.create(doc.getData());
                                if (recipe != null) {
                                    recipe.setId(doc.getId());
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
                .document(recipe.getName())
                .set(json)
                //.add(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void getRecipeByName(String recipeName, Model.GetRecipesByName listener) {
        db.collection(Recipe.collectionName)
                .document(recipeName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Recipe recipe = null;
                        if (task.isSuccessful() & task.getResult()!= null){
                            recipe = Recipe.create(task.getResult().getData());
                        }
                        listener.onComplete(recipe);
                    }
                });
    }
}
