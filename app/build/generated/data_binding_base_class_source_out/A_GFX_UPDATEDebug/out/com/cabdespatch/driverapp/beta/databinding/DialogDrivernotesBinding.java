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

public final class DialogDrivernotesBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout actualcontent;

  @NonNull
  public final ImageButton dlgDriverNotesBtnBack;

  @NonNull
  public final ImageButton dlgDriverNotesBtnGo;

  @NonNull
  public final EditText dlgDriverNotesTxtNotes;

  @NonNull
  public final ImageButton dlgPriceBtnPanic;

  @NonNull
  public final TextView dlgPriceLblTitle;

  private DialogDrivernotesBinding(@NonNull RelativeLayout rootView,
      @NonNull LinearLayout actualcontent, @NonNull ImageButton dlgDriverNotesBtnBack,
      @NonNull ImageButton dlgDriverNotesBtnGo, @NonNull EditText dlgDriverNotesTxtNotes,
      @NonNull ImageButton dlgPriceBtnPanic, @NonNull TextView dlgPriceLblTitle) {
    this.rootView = rootView;
    this.actualcontent = actualcontent;
    this.dlgDriverNotesBtnBack = dlgDriverNotesBtnBack;
    this.dlgDriverNotesBtnGo = dlgDriverNotesBtnGo;
    this.dlgDriverNotesTxtNotes = dlgDriverNotesTxtNotes;
    this.dlgPriceBtnPanic = dlgPriceBtnPanic;
    this.dlgPriceLblTitle = dlgPriceLblTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogDrivernotesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogDrivernotesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_drivernotes, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogDrivernotesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.actualcontent;
      LinearLayout actualcontent = ViewBindings.findChildViewById(rootView, id);
      if (actualcontent == null) {
        break missingId;
      }

      id = R.id.dlgDriverNotes_btnBack;
      ImageButton dlgDriverNotesBtnBack = ViewBindings.findChildViewById(rootView, id);
      if (dlgDriverNotesBtnBack == null) {
        break missingId;
      }

      id = R.id.dlgDriverNotes_btnGo;
      ImageButton dlgDriverNotesBtnGo = ViewBindings.findChildViewById(rootView, id);
      if (dlgDriverNotesBtnGo == null) {
        break missingId;
      }

      id = R.id.dlgDriverNotes_txtNotes;
      EditText dlgDriverNotesTxtNotes = ViewBindings.findChildViewById(rootView, id);
      if (dlgDriverNotesTxtNotes == null) {
        break missingId;
      }

      id = R.id.dlgPrice_btnPanic;
      ImageButton dlgPriceBtnPanic = ViewBindings.findChildViewById(rootView, id);
      if (dlgPriceBtnPanic == null) {
        break missingId;
      }

      id = R.id.dlgPrice_lblTitle;
      TextView dlgPriceLblTitle = ViewBindings.findChildViewById(rootView, id);
      if (dlgPriceLblTitle == null) {
        break missingId;
      }

      return new DialogDrivernotesBinding((RelativeLayout) rootView, actualcontent,
          dlgDriverNotesBtnBack, dlgDriverNotesBtnGo, dlgDriverNotesTxtNotes, dlgPriceBtnPanic,
          dlgPriceLblTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
