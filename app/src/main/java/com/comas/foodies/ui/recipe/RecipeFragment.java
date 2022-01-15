package com.comas.foodies.ui.recipe;

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

import com.comas.foodies.R;
import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.comas.foodies.RecipeViewModel;

public class RecipeFragment extends Fragment {

    private RecipeViewModel mViewModel;

    protected ImageView mRecipeImageView;
    protected ImageButton mEditImageButton;
    protected EditText mRecipeNameEditText;
    protected EditText mRecipeDescEditText;
    protected Button mSaveButton;
    protected Button mEditButton;
    protected Button mDeleteButton;

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, container, false);
        initializeViewElements(view);
        initializeViewHandlers();
        return view;
    }

    private void initializeViewElements(View view) {
        mRecipeImageView = view.findViewById(R.id.recipe_frag_image);
        mEditImageButton = view.findViewById(R.id.recipe_frag_edit_image);
        mRecipeNameEditText = view.findViewById(R.id.recipe_frag_name_text);
        mRecipeDescEditText = view.findViewById(R.id.recipe_frag_description_text);
        mSaveButton = view.findViewById(R.id.recipe_frag_save_button);
        mEditButton = view.findViewById(R.id.recipe_frag_edit_button);
        mDeleteButton = view.findViewById(R.id.recipe_frag_delete_button);
    }

    private void initializeViewHandlers() {
        mSaveButton.setOnClickListener(this::handleSave);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        // TODO: Use the ViewModel
    }
    private void handleSave(View view) {
        Model.instance.addRecipe(new Recipe("2",
                mRecipeNameEditText.getText().toString(),
                mRecipeDescEditText.getText().toString(),
                null));
        Navigation.findNavController(view).popBackStack();
    }
}