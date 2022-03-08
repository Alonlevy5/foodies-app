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

import org.jetbrains.annotations.NotNull;

public class RecipeDetailsFragment extends Fragment {

    TextView nameTv;
    TextView idTv;
    TextView descEt;
    ImageView avatarImv;
    Button deleteBtn;
    Button editBtn;
    ProgressBar progressBar;

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


        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        String recipeId = RecipeDetailsFragmentArgs.fromBundle(getArguments()).getRecipeId();

        progressBar = view.findViewById(R.id.details_recipe_progressBar);


        Model.instance.getRecipeById(recipeId, new Model.GetRecipesById() {
            @Override
            public void onComplete(Recipe recipe) {
                progressBar.setVisibility(View.VISIBLE);
                nameTv.setText(recipe.getName());
                idTv.setText(recipe.getId());
                descEt.setText(recipe.getDesc());
                progressBar.setVisibility(View.GONE);
            }
        });

        nameTv = view.findViewById(R.id.details_recipe_name_ET);
        idTv = view.findViewById(R.id.details_recipe_Id_ET);
        descEt = view.findViewById(R.id.details_recipe_desc_ET);

        deleteBtn = view.findViewById(R.id.details_recipe_delete_btn);
        editBtn = view.findViewById(R.id.details_recipe_edit_btn);

        //delete recipe by id and return to home list
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.deleteRecipeById(recipeId, new Model.DeleteRecipeById() {
                    @Override
                    public void onComplete() {
                        Navigation.findNavController(nameTv).navigateUp();
                    }
                });
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToRecipeEditFragment(recipeId));
            }
        });

//        Button backBtn = view.findViewById(R.id.deta);
//        backBtn.setOnClickListener((v) -> {
//            Navigation.findNavController(v).navigateUp();
//        });
        return view;


//        mRecipe = Model.instance.getRecipeById(recipeId);
//        Model.instance.getRecipeByName(recipeId,(n)->{
//
//        });

        // setContainerData();
    }

}