// Generated by view binder compiler. Do not edit!
package com.cabdespatch.driverapp.beta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cabdespatch.driverapp.beta.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogInitialSetupBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout actualcontent;

  @NonNull
  public final ImageButton dlgInitialSetupBtnCancel;

  @NonNull
  public final ImageButton dlgInitialSetupBtnGo;

  @NonNull
  public final TextView dlgInitialSetupLblTitle;

  @NonNull
  public final EditText dlgInitialSetupTxtCompanyID;

  @NonNull
  public final EditText dlgInitialSetupTxtDriverNo;

  private DialogInitialSetupBinding(@NonNull RelativeLayout rootView,
      @NonNull LinearLayout actualcontent, @NonNull ImageButton dlgInitialSetupBtnCancel,
      @NonNull ImageButton dlgInitialSetupBtnGo, @NonNull TextView dlgInitialSetupLblTitle,
      @NonNull EditText dlgInitialSetupTxtCompanyID, @NonNull EditText dlgInitialSetupTxtDriverNo) {
    this.rootView = rootView;
    this.actualcontent = actualcontent;
    this.dlgInitialSetupBtnCancel = dlgInitialSetupBtnCancel;
    this.dlgInitialSetupBtnGo = dlgInitialSetupBtnGo;
    this.dlgInitialSetupLblTitle = dlgInitialSetupLblTitle;
    this.dlgInitialSetupTxtCompanyID = dlgInitialSetupTxtCompanyID;
    this.dlgInitialSetupTxtDriverNo = dlgInitialSetupTxtDriverNo;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogInitialSetupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogInitialSetupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_initial_setup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogInitialSetupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.actualcontent;
      LinearLayout actualcontent = ViewBindings.findChildViewById(rootView, id);
      if (actualcontent == null) {
        break missingId;
      }

      id = R.id.dlgInitialSetup_btnCancel;
      ImageButton dlgInitialSetupBtnCancel = ViewBindings.findChildViewById(rootView, id);
      if (dlgInitialSetupBtnCancel == null) {
        break missingId;
      }

      id = R.id.dlgInitialSetup_btnGo;
      ImageButton dlgInitialSetupBtnGo = ViewBindings.findChildViewById(rootView, id);
      if (dlgInitialSetupBtnGo == null) {
        break missingId;
      }

      id = R.id.dlgInitialSetup_lblTitle;
      TextView dlgInitialSetupLblTitle = ViewBindings.findChildViewById(rootView, id);
      if (dlgInitialSetupLblTitle == null) {
        break missingId;
      }

      id = R.id.dlgInitialSetup_txtCompanyID;
      EditText dlgInitialSetupTxtCompanyID = ViewBindings.findChildViewById(rootView, id);
      if (dlgInitialSetupTxtCompanyID == null) {
        break missingId;
      }

      id = R.id.dlgInitialSetup_txtDriverNo;
      EditText dlgInitialSetupTxtDriverNo = ViewBindings.findChildViewById(rootView, id);
      if (dlgInitialSetupTxtDriverNo == null) {
        break missingId;
      }

      return new DialogInitialSetupBinding((RelativeLayout) rootView, actualcontent,
          dlgInitialSetupBtnCancel, dlgInitialSetupBtnGo, dlgInitialSetupLblTitle,
          dlgInitialSetupTxtCompanyID, dlgInitialSetupTxtDriverNo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}