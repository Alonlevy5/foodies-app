package com.comas.foodies.ui.recipe;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.comas.foodies.MobileNavigationDirections;

public class RecipeCreateFragmentDirections {
  private RecipeCreateFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return MobileNavigationDirections.actionGlobalRecipeFragment();
  }
}
