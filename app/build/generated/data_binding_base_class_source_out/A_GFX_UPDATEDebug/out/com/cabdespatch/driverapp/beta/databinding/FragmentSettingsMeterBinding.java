// Generated by view binder compiler. Do not edit!
package com.cabdespatch.driverapp.beta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

public final class FragmentSettingsMeterBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CheckBox chkSettingsMeterEnabled;

  @NonNull
  public final TextView settingstitle;

  private FragmentSettingsMeterBinding(@NonNull LinearLayout rootView,
      @NonNull CheckBox chkSettingsMeterEnabled, @NonNull TextView settingstitle) {
    this.rootView = rootView;
    this.chkSettingsMeterEnabled = chkSettingsMeterEnabled;
    this.settingstitle = settingstitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSettingsMeterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSettingsMeterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_settings_meter, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSettingsMeterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.chkSettingsMeterEnabled;
      CheckBox chkSettingsMeterEnabled = ViewBindings.findChildViewById(rootView, id);
      if (chkSettingsMeterEnabled == null) {
        break missingId;
      }

      id = R.id.settingstitle;
      TextView settingstitle = ViewBindings.findChildViewById(rootView, id);
      if (settingstitle == null) {
        break missingId;
      }

      return new FragmentSettingsMeterBinding((LinearLayout) rootView, chkSettingsMeterEnabled,
          settingstitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
