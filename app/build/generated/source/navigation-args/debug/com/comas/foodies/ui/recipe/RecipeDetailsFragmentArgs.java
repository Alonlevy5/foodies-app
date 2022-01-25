package com.comas.foodies.ui.recipe;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class RecipeDetailsFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private RecipeDetailsFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private RecipeDetailsFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static RecipeDetailsFragmentArgs fromBundle(@NonNull Bundle bundle) {
    RecipeDetailsFragmentArgs __result = new RecipeDetailsFragmentArgs();
    bundle.setClassLoader(RecipeDetailsFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("recipe_id")) {
      String recipeId;
      recipeId = bundle.getString("recipe_id");
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipe_id\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("recipe_id", recipeId);
    } else {
      throw new IllegalArgumentException("Required argument \"recipe_id\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getRecipeId() {
    return (String) arguments.get("recipe_id");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("recipe_id")) {
      String recipeId = (String) arguments.get("recipe_id");
      __result.putString("recipe_id", recipeId);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    RecipeDetailsFragmentArgs that = (RecipeDetailsFragmentArgs) object;
    if (arguments.containsKey("recipe_id") != that.arguments.containsKey("recipe_id")) {
      return false;
    }
    if (getRecipeId() != null ? !getRecipeId().equals(that.getRecipeId()) : that.getRecipeId() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getRecipeId() != null ? getRecipeId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RecipeDetailsFragmentArgs{"
        + "recipeId=" + getRecipeId()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(RecipeDetailsFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipe_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipe_id", recipeId);
    }

    @NonNull
    public RecipeDetailsFragmentArgs build() {
      RecipeDetailsFragmentArgs result = new RecipeDetailsFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setRecipeId(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipe_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipe_id", recipeId);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getRecipeId() {
      return (String) arguments.get("recipe_id");
    }
  }
}
