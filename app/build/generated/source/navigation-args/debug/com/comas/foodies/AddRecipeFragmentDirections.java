package com.comas.foodies;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

public class AddRecipeFragmentDirections {
  private AddRecipeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return MobileNavigationDirections.actionGlobalRecipeFragment();
  }
}
