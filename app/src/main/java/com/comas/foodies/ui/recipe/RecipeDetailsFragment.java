package com.comas.foodies.ui.recipe;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;

import org.jetbrains.annotations.NotNull;

public class RecipeDetailsFragment extends RecipeFragment {

    private Recipe mRecipe;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailsFragment newInstance(String param1, String param2) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setContainerVisibility();

        String recipeId = RecipeDetailsFragmentArgs.fromBundle(getArguments()).getRecipeId();
        mRecipe = Model.instance.getRecipeById(recipeId);

       // setContainerData();
        return view;
    }

    public void setContainerVisibility() {
        mEditImageButton.setVisibility(View.GONE);
        mDeleteButton.setVisibility(View.GONE);
        mEditButton.setVisibility(View.GONE);
        mSaveButton.setVisibility(View.GONE);

        mRecipeNameEditText.setKeyListener(null);
        mRecipeNameEditText.setBackgroundResource(android.R.color.transparent);
        mRecipeDescEditText.setKeyListener(null);
        mRecipeDescEditText.setBackgroundResource(android.R.color.transparent);

    }

    private void setContainerData() {
            mRecipeNameEditText.setText(mRecipe.getName());
            mRecipeDescEditText.setText(mRecipe.getDesc());
        }
    }
