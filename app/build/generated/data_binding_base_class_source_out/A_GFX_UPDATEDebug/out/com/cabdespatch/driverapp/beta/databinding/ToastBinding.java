// Generated by view binder compiler. Do not edit!
package com.cabdespatch.driverapp.beta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public final class ToastBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView toastFooter;

  @NonNull
  public final ImageView toastImage;

  @NonNull
  public final ImageView toastImageSmall;

  @NonNull
  public final LinearLayout toastLayoutRoot;

  @NonNull
  public final TextView toastText;

  @NonNull
  public final TextView toastTitle;

  @NonNull
  public final LinearLayout toastTitleLayout;

  private ToastBinding(@NonNull LinearLayout rootView, @NonNull TextView toastFooter,
      @NonNull ImageView toastImage, @NonNull ImageView toastImageSmall,
      @NonNull LinearLayout toastLayoutRoot, @NonNull TextView toastText,
      @NonNull TextView toastTitle, @NonNull LinearLayout toastTitleLayout) {
    this.rootView = rootView;
    this.toastFooter = toastFooter;
    this.toastImage = toastImage;
    this.toastImageSmall = toastImageSmall;
    this.toastLayoutRoot = toastLayoutRoot;
    this.toastText = toastText;
    this.toastTitle = toastTitle;
    this.toastTitleLayout = toastTitleLayout;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ToastBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ToastBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.toast, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ToastBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.toast_footer;
      TextView toastFooter = ViewBindings.findChildViewById(rootView, id);
      if (toastFooter == null) {
        break missingId;
      }

      id = R.id.toast_image;
      ImageView toastImage = ViewBindings.findChildViewById(rootView, id);
      if (toastImage == null) {
        break missingId;
      }

      id = R.id.toast_image_small;
      ImageView toastImageSmall = ViewBindings.findChildViewById(rootView, id);
      if (toastImageSmall == null) {
        break missingId;
      }

      LinearLayout toastLayoutRoot = (LinearLayout) rootView;

      id = R.id.toast_text;
      TextView toastText = ViewBindings.findChildViewById(rootView, id);
      if (toastText == null) {
        break missingId;
      }

      id = R.id.toast_title;
      TextView toastTitle = ViewBindings.findChildViewById(rootView, id);
      if (toastTitle == null) {
        break missingId;
      }

      id = R.id.toast_title_layout;
      LinearLayout toastTitleLayout = ViewBindings.findChildViewById(rootView, id);
      if (toastTitleLayout == null) {
        break missingId;
      }

      return new ToastBinding((LinearLayout) rootView, toastFooter, toastImage, toastImageSmall,
          toastLayoutRoot, toastText, toastTitle, toastTitleLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}