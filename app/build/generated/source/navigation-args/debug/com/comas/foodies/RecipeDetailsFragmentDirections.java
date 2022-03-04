package com.comas.foodies;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class RecipeDetailsFragmentDirections {
  private RecipeDetailsFragmentDirections() {
  }

  @NonNull
  public static ActionRecipeDetailsFragmentToRecipeEditFragment actionRecipeDetailsFragmentToRecipeEditFragment(
      @NonNull String recipeId) {
    return new ActionRecipeDetailsFragmentToRecipeEditFragment(recipeId);
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return MobileNavigationDirections.actionGlobalRecipeFragment();
  }

  public static class ActionRecipeDetailsFragmentToRecipeEditFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionRecipeDetailsFragmentToRecipeEditFragment(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipe_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipe_id", recipeId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionRecipeDetailsFragmentToRecipeEditFragment setRecipeId(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipe_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipe_id", recipeId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("recipe_id")) {
        String recipeId = (String) arguments.get("recipe_id");
        __result.putString("recipe_id", recipeId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_recipeDetailsFragment_to_recipeEditFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getRecipeId() {
      return (String) arguments.get("recipe_id");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionRecipeDetailsFragmentToRecipeEditFragment that = (ActionRecipeDetailsFragmentToRecipeEditFragment) object;
      if (arguments.containsKey("recipe_id") != that.arguments.containsKey("recipe_id")) {
        return false;
      }
      if (getRecipeId() != null ? !getRecipeId().equals(that.getRecipeId()) : that.getRecipeId() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getRecipeId() != null ? getRecipeId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionRecipeDetailsFragmentToRecipeEditFragment(actionId=" + getActionId() + "){"
          + "recipeId=" + getRecipeId()
          + "}";
    }
  }
}
