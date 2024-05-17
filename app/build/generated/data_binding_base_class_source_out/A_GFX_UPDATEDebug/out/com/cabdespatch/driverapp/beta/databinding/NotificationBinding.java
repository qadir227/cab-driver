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

public final class NotificationBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView notificationContent;

  @NonNull
  public final TextView notificationTitle;

  private NotificationBinding(@NonNull LinearLayout rootView, @NonNull TextView notificationContent,
      @NonNull TextView notificationTitle) {
    this.rootView = rootView;
    this.notificationContent = notificationContent;
    this.notificationTitle = notificationTitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NotificationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.notification, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NotificationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.notification_content;
      TextView notificationContent = ViewBindings.findChildViewById(rootView, id);
      if (notificationContent == null) {
        break missingId;
      }

      id = R.id.notification_title;
      TextView notificationTitle = ViewBindings.findChildViewById(rootView, id);
      if (notificationTitle == null) {
        break missingId;
      }

      return new NotificationBinding((LinearLayout) rootView, notificationContent,
          notificationTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
