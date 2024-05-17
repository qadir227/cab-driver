// Generated by view binder compiler. Do not edit!
package com.cabdespatch.driverapp.beta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cabdespatch.driverapp.beta.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageButton frmlistBtnBack;

  @NonNull
  public final LinearLayout frmlistBusy;

  @NonNull
  public final ListView frmlistList;

  @NonNull
  public final TextView frmlistTitle;

  private ActivityListBinding(@NonNull LinearLayout rootView, @NonNull ImageButton frmlistBtnBack,
      @NonNull LinearLayout frmlistBusy, @NonNull ListView frmlistList,
      @NonNull TextView frmlistTitle) {
    this.rootView = rootView;
    this.frmlistBtnBack = frmlistBtnBack;
    this.frmlistBusy = frmlistBusy;
    this.frmlistList = frmlistList;
    this.frmlistTitle = frmlistTitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.frmlist_btnBack;
      ImageButton frmlistBtnBack = ViewBindings.findChildViewById(rootView, id);
      if (frmlistBtnBack == null) {
        break missingId;
      }

      id = R.id.frmlist_busy;
      LinearLayout frmlistBusy = ViewBindings.findChildViewById(rootView, id);
      if (frmlistBusy == null) {
        break missingId;
      }

      id = R.id.frmlist_list;
      ListView frmlistList = ViewBindings.findChildViewById(rootView, id);
      if (frmlistList == null) {
        break missingId;
      }

      id = R.id.frmlist_title;
      TextView frmlistTitle = ViewBindings.findChildViewById(rootView, id);
      if (frmlistTitle == null) {
        break missingId;
      }

      return new ActivityListBinding((LinearLayout) rootView, frmlistBtnBack, frmlistBusy,
          frmlistList, frmlistTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
