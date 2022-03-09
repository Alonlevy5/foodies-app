// Generated by view binder compiler. Do not edit!
package com.comas.foodies.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.comas.foodies.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RecipelistItemBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView recipelistItemImage;

  @NonNull
  public final TextView recipelistItemText;

  private RecipelistItemBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView recipelistItemImage, @NonNull TextView recipelistItemText) {
    this.rootView = rootView;
    this.recipelistItemImage = recipelistItemImage;
    this.recipelistItemText = recipelistItemText;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RecipelistItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RecipelistItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.recipelist_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RecipelistItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.recipelist_item_image;
      ImageView recipelistItemImage = ViewBindings.findChildViewById(rootView, id);
      if (recipelistItemImage == null) {
        break missingId;
      }

      id = R.id.recipelist_item_text;
      TextView recipelistItemText = ViewBindings.findChildViewById(rootView, id);
      if (recipelistItemText == null) {
        break missingId;
      }

      return new RecipelistItemBinding((ConstraintLayout) rootView, recipelistItemImage,
          recipelistItemText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
