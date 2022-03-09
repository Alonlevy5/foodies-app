// Generated by view binder compiler. Do not edit!
package com.comas.foodies.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.comas.foodies.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ProgressBar HomeFragProgressBar;

  @NonNull
  public final Button button2;

  @NonNull
  public final RecyclerView fragmentHomeRv;

  @NonNull
  public final FloatingActionButton homeAddBtn;

  private FragmentHomeBinding(@NonNull ConstraintLayout rootView,
      @NonNull ProgressBar HomeFragProgressBar, @NonNull Button button2,
      @NonNull RecyclerView fragmentHomeRv, @NonNull FloatingActionButton homeAddBtn) {
    this.rootView = rootView;
    this.HomeFragProgressBar = HomeFragProgressBar;
    this.button2 = button2;
    this.fragmentHomeRv = fragmentHomeRv;
    this.homeAddBtn = homeAddBtn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Home_frag_progressBar;
      ProgressBar HomeFragProgressBar = ViewBindings.findChildViewById(rootView, id);
      if (HomeFragProgressBar == null) {
        break missingId;
      }

      id = R.id.button2;
      Button button2 = ViewBindings.findChildViewById(rootView, id);
      if (button2 == null) {
        break missingId;
      }

      id = R.id.fragment_home_Rv;
      RecyclerView fragmentHomeRv = ViewBindings.findChildViewById(rootView, id);
      if (fragmentHomeRv == null) {
        break missingId;
      }

      id = R.id.home_addBtn;
      FloatingActionButton homeAddBtn = ViewBindings.findChildViewById(rootView, id);
      if (homeAddBtn == null) {
        break missingId;
      }

      return new FragmentHomeBinding((ConstraintLayout) rootView, HomeFragProgressBar, button2,
          fragmentHomeRv, homeAddBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
