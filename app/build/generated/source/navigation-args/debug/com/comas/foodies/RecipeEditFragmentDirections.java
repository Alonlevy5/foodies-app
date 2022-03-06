package com.comas.foodies;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

public class RecipeEditFragmentDirections {
  private RecipeEditFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return MobileNavigationDirections.actionGlobalRecipeFragment();
  }
}
