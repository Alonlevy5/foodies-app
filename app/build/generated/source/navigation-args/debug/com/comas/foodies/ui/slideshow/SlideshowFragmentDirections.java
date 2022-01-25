package com.comas.foodies.ui.slideshow;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.comas.foodies.MobileNavigationDirections;

public class SlideshowFragmentDirections {
  private SlideshowFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return MobileNavigationDirections.actionGlobalRecipeFragment();
  }
}
