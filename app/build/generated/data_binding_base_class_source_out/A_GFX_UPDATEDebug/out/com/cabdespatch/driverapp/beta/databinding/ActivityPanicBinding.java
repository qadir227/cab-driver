// Generated by view binder compiler. Do not edit!
package com.cabdespatch.driverapp.beta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cabdespatch.driverapp.beta.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPanicBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout frmPanicBtn999;

  @NonNull
  public final ImageButton frmPanicBtnBack;

  @NonNull
  public final LinearLayout frmPanicBtnSendCars;

  private ActivityPanicBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout frmPanicBtn999,
      @NonNull ImageButton frmPanicBtnBack, @NonNull LinearLayout frmPanicBtnSendCars) {
    this.rootView = rootView;
    this.frmPanicBtn999 = frmPanicBtn999;
    this.frmPanicBtnBack = frmPanicBtnBack;
    this.frmPanicBtnSendCars = frmPanicBtnSendCars;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPanicBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPanicBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_panic, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPanicBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.frmPanic_btn999;
      LinearLayout frmPanicBtn999 = ViewBindings.findChildViewById(rootView, id);
      if (frmPanicBtn999 == null) {
        break missingId;
      }

      id = R.id.frmPanic_btnBack;
      ImageButton frmPanicBtnBack = ViewBindings.findChildViewById(rootView, id);
      if (frmPanicBtnBack == null) {
        break missingId;
      }

      id = R.id.frmPanic_btnSendCars;
      LinearLayout frmPanicBtnSendCars = ViewBindings.findChildViewById(rootView, id);
      if (frmPanicBtnSendCars == null) {
        break missingId;
      }

      return new ActivityPanicBinding((LinearLayout) rootView, frmPanicBtn999, frmPanicBtnBack,
          frmPanicBtnSendCars);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
