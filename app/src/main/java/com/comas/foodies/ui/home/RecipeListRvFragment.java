package com.comas.foodies.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comas.foodies.R;
import com.comas.foodies.adapters.RecipeListAdapter;
import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecipeListRvFragment extends Fragment {

    private RecipeListRvViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;
    ProgressBar progressBar;

    private List<Recipe> mRecipeList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(RecipeListRvViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        progressBar = view.findViewById(R.id.Home_frag_progressBar);
        progressBar.setVisibility(View.GONE);

        refresh();

        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.fragment_home_Rv);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new RecipeListAdapter(view.getContext(), mRecipeList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FloatingActionButton fab = view.findViewById(R.id.home_addBtn);
        fab.setOnClickListener(v -> Navigation.findNavController(v).navigate(RecipeListRvFragmentDirections.actionRecipeListRvToRecipeAdd()));

        mAdapter.set

        return view;
    }

    private void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        Model.instance.getAllRecipes((list) -> {
            mRecipeList = list;
            mAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);


        });

    }
}