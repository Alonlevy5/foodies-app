package com.comas.foodies;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

public class RecipeFragmentDirections {
  private RecipeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return MobileNavigationDirections.actionGlobalRecipeFragment();
  }
}
