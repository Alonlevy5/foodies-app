package com.comas.foodies;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class RecipeDetailsFragment extends Fragment {

    TextView nameTv;
    TextView idTv;
    TextView descEt;
    ImageView avatarImv;
    Button deleteBtn;
    Button editBtn;
    ProgressBar progressBar;




    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        String recipeId = RecipeDetailsFragmentArgs.fromBundle(getArguments()).getRecipeId();

        progressBar = view.findViewById(R.id.details_recipe_progressBar);

        nameTv = view.findViewById(R.id.details_recipe_name_TV);
        idTv = view.findViewById(R.id.details_recipe_Id_TV);
        descEt = view.findViewById(R.id.details_recipe_desc_TV);

        avatarImv = view.findViewById(R.id.details_recipe_img);

        Model.instance.getRecipeById(recipeId, new Model.GetRecipesById() {
            @Override
            public void onComplete(Recipe recipe) {
                progressBar.setVisibility(View.VISIBLE);
                nameTv.setText(recipe.getName());
                idTv.setText(recipe.getId());
                descEt.setText(recipe.getDesc());

                // displays image on this fragment
                if (recipe.getImageUrl() != null) {
                    Picasso.get()
                            .load(recipe.getImageUrl())
                            .into(avatarImv);
                }

                progressBar.setVisibility(View.GONE);
            }
        });

        nameTv = view.findViewById(R.id.details_recipe_name_TV);
        idTv = view.findViewById(R.id.details_recipe_Id_TV);
        descEt = view.findViewById(R.id.details_recipe_desc_TV);

        deleteBtn = view.findViewById(R.id.details_recipe_delete_btn);
        editBtn = view.findViewById(R.id.details_recipe_edit_btn);

        //delete recipe by id and return to home list
        deleteBtn.setOnClickListener(v -> Model.instance.deleteRecipeById(recipeId, () -> Navigation.findNavController(nameTv).navigateUp()));

        editBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToRecipeEditFragment(recipeId)));

        return view;


    }

}