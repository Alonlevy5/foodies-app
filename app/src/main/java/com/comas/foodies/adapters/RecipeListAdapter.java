package com.comas.foodies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.comas.foodies.R;
import com.comas.foodies.model.Recipe;
import com.comas.foodies.ui.home.RecipeListRvFragmentDirections;

import java.util.List;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private final List<Recipe> mRecipeList;
    private final LayoutInflater mInflater;

    OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecipeListAdapter(Context context, List<Recipe> recipeList) {
        mInflater = LayoutInflater.from(context);
        this.mRecipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recipelist_item, parent, false);
        return new RecipeViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.RecipeViewHolder holder, int position) {

        Recipe mCurrent = mRecipeList.get(position);
        holder.recipeItemText.setText(mCurrent.getName());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (mRecipeList == null)
            return 0;
        return mRecipeList.size();
    }


    interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView recipeItemText;
        public final ImageView recipeItemImage;
        final RecipeListAdapter mAdapter;

        public RecipeViewHolder(@NonNull View itemView, RecipeListAdapter adapter, OnItemClickListener listener) {
            super(itemView);
            recipeItemText = itemView.findViewById(R.id.recipelist_item_text);
            recipeItemImage = itemView.findViewById(R.id.recipelist_item_image);
            this.mAdapter = adapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v, pos);
                }
            });
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            Log.d("POS", "ROW Item: " + mPosition);

            Navigation.findNavController(view)
                    .navigate(RecipeListRvFragmentDirections.actionRecipeListRvToRecipeDetails(String.valueOf(mPosition)));

        }
    }
}
