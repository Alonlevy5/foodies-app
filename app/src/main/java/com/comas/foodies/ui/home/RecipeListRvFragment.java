package com.comas.foodies.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.comas.foodies.R;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class RecipeListRvFragment extends Fragment {


    RecipeListRvViewModel viewModel;
    MyAdapter mAdapter;
    SwipeRefreshLayout swipeRefresh;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(RecipeListRvViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        swipeRefresh = view.findViewById(R.id.home_swiperefresh);
        swipeRefresh.setOnRefreshListener(() -> Model.instance.refreshRecipeList());


        RecyclerView list = view.findViewById(R.id.fragment_home_Rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MyAdapter();
        list.setAdapter(mAdapter);

        //gets the right recipe from the list, by click
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String recipeId = viewModel.getData().getValue().get(position).getId();
                Navigation.findNavController(v).navigate(RecipeListRvFragmentDirections.actionRecipeListRvToRecipeDetails(recipeId));

            }
        });

        setHasOptionsMenu(true);

        viewModel.getData().observe(getViewLifecycleOwner(), recipes -> refresh());

        swipeRefresh.setRefreshing(Model.instance.getRecipeListLoadingState().getValue() == Model.RecipeListLoadingState.loading);

        Model.instance.getRecipeListLoadingState().observe(getViewLifecycleOwner(), new Observer<Model.RecipeListLoadingState>() {
            @Override
            public void onChanged(Model.RecipeListLoadingState recipeListLoadingState) {
                if (recipeListLoadingState == Model.RecipeListLoadingState.loading)
                    swipeRefresh.setRefreshing(true);
                else
                    swipeRefresh.setRefreshing(false);
            }
        });
        // to Add a new Recipe
        FloatingActionButton fab = view.findViewById(R.id.home_addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(RecipeListRvFragmentDirections.actionRecipeListRvToRecipeAdd());
            }
        });

        return view;
    }

    // gets the recipeList from FireBase
    private void refresh() {

        mAdapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;
        ImageView imgView;
        TextView idTv; //???
        //desc

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.recipelistRow_item_text);
            imgView = itemView.findViewById(R.id.recipelistRow_item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v, pos);
                }
            });
        }

        public void bind(Recipe recipe){
            nameTv.setText(recipe.getName());
            imgView.setImageResource(R.drawable.ic_menu_gallery);
            if (recipe.getImageUrl() != null) {
                Picasso.get()
                        .load(recipe.getImageUrl())
                        .into(imgView);
            }
        }
    }

    interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.recipelist_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view, listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Recipe recipe = viewModel.getData().getValue().get(position);

            holder.bind(recipe);


        }

        @Override
        public int getItemCount() {
            if (viewModel.getData().getValue() == null) {
                return 0;
            }
            return viewModel.getData().getValue().size();
        }
    }


}