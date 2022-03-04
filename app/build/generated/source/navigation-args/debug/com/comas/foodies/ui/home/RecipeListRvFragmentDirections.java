package com.comas.foodies.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.comas.foodies.MobileNavigationDirections;
import com.comas.foodies.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class RecipeListRvFragmentDirections {
  private RecipeListRvFragmentDirections() {
  }

  @NonNull
  public static ActionRecipeListRvToRecipeDetails actionRecipeListRvToRecipeDetails(
      @NonNull String recipeId) {
    return new ActionRecipeListRvToRecipeDetails(recipeId);
  }

  @NonNull
  public static NavDirections actionRecipeListRvToRecipeAdd() {
    return new ActionOnlyNavDirections(R.id.action_recipeListRv_to_recipeAdd);
  }

  @NonNull
  public static NavDirections actionGlobalRecipeFragment() {
    return MobileNavigationDirections.actionGlobalRecipeFragment();
  }

  public static class ActionRecipeListRvToRecipeDetails implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionRecipeListRvToRecipeDetails(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipe_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipe_id", recipeId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionRecipeListRvToRecipeDetails setRecipeId(@NonNull String recipeId) {
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
      return R.id.action_recipeListRv_to_recipeDetails;
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
      ActionRecipeListRvToRecipeDetails that = (ActionRecipeListRvToRecipeDetails) object;
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
      return "ActionRecipeListRvToRecipeDetails(actionId=" + getActionId() + "){"
          + "recipeId=" + getRecipeId()
          + "}";
    }
  }
}
