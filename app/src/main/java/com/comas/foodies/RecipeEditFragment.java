package com.comas.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeEditFragment extends Fragment {


    TextView nameTv;
    //TextView idTv;
    TextView descEt;
    Button saveBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecipeEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeEditFragment newInstance(String param1, String param2) {
        RecipeEditFragment fragment = new RecipeEditFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_edit, container, false);

        String recipeId = RecipeDetailsFragmentArgs.fromBundle(getArguments()).getRecipeId();

        Model.instance.getRecipeById(recipeId, new Model.GetRecipesById() {
            @Override
            public void onComplete(Recipe recipe) {
                nameTv.setText(recipe.getName());

                descEt.setText(recipe.getDesc());
            }
        });

        nameTv = view.findViewById(R.id.edit_recipe_namET);
        descEt = view.findViewById(R.id.edit_recipe_descET);
        saveBtn = view.findViewById(R.id.edit_recipe_saveBtn);

        //send the field we want to update and then return to detail page
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.updateRecipeById(recipeId, nameTv.getText().toString(), descEt.getText().toString(), new Model.UpdateRecipeById() {
                    @Override
                    public void onComplete(Recipe recipe) {
                        nameTv.setText(recipe.getName());
                        descEt.setText(recipe.getDesc());
                        Navigation.findNavController(nameTv).navigateUp();
                    }
                });
            }

        });


        return view;
    }
}