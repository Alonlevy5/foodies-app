package com.comas.foodies;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class AddRecipeFragment extends Fragment {


    ImageView avatarImv;
    EditText nameEt;
    EditText idEt;
    EditText descEt;
    TextView locationEt;
    FloatingActionButton locationBtn;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button saveBtn;
    Button cancelBtn;
    ProgressBar progressBar;
    int REQUEST_CODE = 111;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipeFragment newInstance(String param1, String param2) {

        AddRecipeFragment fragment = new AddRecipeFragment();
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

        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        avatarImv = view.findViewById(R.id.addRecipe_avatar_img);
        nameEt = view.findViewById(R.id.addRecipe_name_et);
        descEt = view.findViewById(R.id.addRecipe_desc_et);
        idEt = view.findViewById(R.id.addRecipe_id_et);
        locationEt = view.findViewById(R.id.addRecipe_location);
        //buttons handling
        saveBtn = view.findViewById(R.id.addRecipe_save_btn);
        cancelBtn = view.findViewById(R.id.addRecipe_cancel_btn);
        locationBtn = view.findViewById(R.id.addRecipe_locationBtn);
        progressBar = view.findViewById(R.id.main_progressbar);
        progressBar.setVisibility(View.GONE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        saveBtn.setOnClickListener(v -> save());
        cancelBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("some" , "some-data");
                startActivity(intent);
            }
        });

        return view;
    }


    private void save() {

        progressBar.setVisibility(View.VISIBLE);
        saveBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        String desc = descEt.getText().toString();
        String name = nameEt.getText().toString();
        String id = idEt.getText().toString();
//        String img = avatarImv.getText().toString();

        Log.d("TAG", "saved name:" + name + " id:" + id);

        Recipe recipe = new Recipe(name, id, desc);

        //add the recipe and then return to list page

        Model.instance.addRecipe(recipe, () -> {
           Navigation.findNavController(nameEt).navigateUp();
        });



    }

}