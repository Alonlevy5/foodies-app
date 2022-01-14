package com.comas.foodies;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipeFragment extends Fragment {

    private RecipeViewModel mViewModel;

    private ImageView mRecipeImageView;
    private ImageButton mEditImageButton;
    private EditText mRecipeNameEditText;
    private EditText mRecipeDescEditText;
    private Button mSaveButton;
    private Button mEditButton;
    private Button mDeleteButton;

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, container, false);
        initializeViewVariables(view);
        String recipeId = RecipeFragmentArgs.fromBundle(getArguments()).getRecipeId();
        mRecipeNameEditText.setText(recipeId);
        return view;
    }

    private void initializeViewVariables(View view) {
        mRecipeImageView = view.findViewById(R.id.recipe_frag_image);
        mEditImageButton = view.findViewById(R.id.recipe_frag_edit_image);
        mRecipeNameEditText = view.findViewById(R.id.recipe_frag_name_text);
        mRecipeDescEditText = view.findViewById(R.id.recipe_frag_description_text);
        mSaveButton = view.findViewById(R.id.recipe_frag_save_button);
        mEditButton = view.findViewById(R.id.recipe_frag_edit_button);
        mDeleteButton = view.findViewById(R.id.recipe_frag_delete_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        // TODO: Use the ViewModel
    }

}