package com.comas.foodies;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

public class RecipeDetailsFragmentDirections {
  private RecipeDetailsFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return MobileNavigationDirections.actionGlobalRecipeFragment();
  }
}
