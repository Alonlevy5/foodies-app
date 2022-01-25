package com.comas.foodies.ui.gallery;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.comas.foodies.MobileNavigationDirections;

public class GalleryFragmentDirections {
  private GalleryFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return MobileNavigationDirections.actionGlobalRecipeFragment();
  }
}
