package com.comas.foodies;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class MobileNavigationDirections {
  private MobileNavigationDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return new ActionOnlyNavDirections(R.id.action_global_recipeFragment);
  }
}
