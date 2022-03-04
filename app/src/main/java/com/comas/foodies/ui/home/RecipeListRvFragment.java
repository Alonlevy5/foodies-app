package com.comas.foodies.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comas.foodies.R;

import com.comas.foodies.model.Model;
import com.comas.foodies.model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecipeListRvFragment extends Fragment {


    ProgressBar progressBar;
    private List<Recipe> mRecipeList;
    MyAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        progressBar = view.findViewById(R.id.Home_frag_progressBar);
        progressBar.setVisibility(View.GONE);

        RecyclerView list = view.findViewById(R.id.fragment_home_Rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new MyAdapter();
        list.setAdapter(mAdapter);

        //gets the right recipe from the list, by click
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String recipeId = mRecipeList.get(position).getId();
                Navigation.findNavController(v).navigate(RecipeListRvFragmentDirections.actionRecipeListRvToRecipeDetails(recipeId));

            }
        });

        setHasOptionsMenu(true);
        refresh();

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
        progressBar.setVisibility(View.VISIBLE);
        Model.instance.getAllRecipes((list) -> {
            mRecipeList = list;
            mAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);


        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;
        ImageView imgView;
        TextView idTv; //???
        //desc

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.recipelist_item_text);
            imgView = itemView.findViewById(R.id.recipelist_item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v, pos);
                }
            });
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
            Recipe recipe = mRecipeList.get(position);
            holder.nameTv.setText(recipe.getName());


        }

        @Override
        public int getItemCount() {
            if (mRecipeList == null) {
                return 0;
            }
            return mRecipeList.size();
        }
    }


}