// Generated by view binder compiler. Do not edit!
package com.cabdespatch.driverapp.beta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cabdespatch.driverapp.beta.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RowCarsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView rowCarCount;

  @NonNull
  public final TextView rowCarPlot;

  private RowCarsBinding(@NonNull LinearLayout rootView, @NonNull TextView rowCarCount,
      @NonNull TextView rowCarPlot) {
    this.rootView = rootView;
    this.rowCarCount = rowCarCount;
    this.rowCarPlot = rowCarPlot;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RowCarsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RowCarsBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.row_cars, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RowCarsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.rowCar_count;
      TextView rowCarCount = ViewBindings.findChildViewById(rootView, id);
      if (rowCarCount == null) {
        break missingId;
      }

      id = R.id.rowCar_Plot;
      TextView rowCarPlot = ViewBindings.findChildViewById(rootView, id);
      if (rowCarPlot == null) {
        break missingId;
      }

      return new RowCarsBinding((LinearLayout) rootView, rowCarCount, rowCarPlot);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}