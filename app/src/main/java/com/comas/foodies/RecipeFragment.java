package com.comas.foodies;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipeFragment extends Fragment {

    //TODO need to fix problem with recipe Details and Crate xml design not appearing and cannot change

    private RecipeViewModel mViewModel;

    protected ImageView mRecipeImageView;
    protected ImageButton mEditImageButton;
    protected EditText mRecipeNameEditText;
    protected EditText mRecipeDescEditText;
    protected EditText mLocationEditText;
    FloatingActionButton mLocationBtn;
    protected Button mEditButton;
    protected Button mDeleteButton;
    ProgressBar progressBar;

//    public static RecipeFragment newInstance() {
//        return new RecipeFragment();
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        mRecipeImageView = view.findViewById(R.id.details_recipe_img);
        mEditImageButton = view.findViewById(R.id.recipe_frag_edit_image);
        mRecipeNameEditText = view.findViewById(R.id.details_recipe_name_TV);
        mRecipeDescEditText = view.findViewById(R.id.details_recipe_Id_TV);
        mEditButton = view.findViewById(R.id.details_recipe_edit_btn);
        mDeleteButton = view.findViewById(R.id.details_recipe_delete_btn);
        mLocationEditText = view.findViewById(R.id.addRecipe_location);
        mLocationBtn = view.findViewById(R.id.addRecipe_locationBtn);
        progressBar = view.findViewById(R.id.details_recipe_progressBar);
        progressBar.setVisibility(View.GONE);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        // TODO: Use the ViewModel
    }

    private void handleSave(View view) {

        progressBar.setVisibility(View.VISIBLE);
        mDeleteButton.setEnabled(false);
        mEditButton.setEnabled(false);

        Model.instance.addRecipe(new Recipe(
                mRecipeNameEditText.getText().toString(),
                mRecipeDescEditText.getText().toString()
        ), () -> {
            Navigation.findNavController(mRecipeNameEditText).navigateUp();

        });

    }
}