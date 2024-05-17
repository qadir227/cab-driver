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

public final class DialogPriceBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout actualcontent;

  @NonNull
  public final ImageButton dlgPriceBtnBack;

  @NonNull
  public final ImageButton dlgPriceBtnGo;

  @NonNull
  public final ImageButton dlgPriceBtnPanic;

  @NonNull
  public final TextView dlgPriceLblSurchargePrompt;

  @NonNull
  public final TextView dlgPriceLblTitle;

  @NonNull
  public final TextView dlgPriceLblTotalFare;

  @NonNull
  public final EditText dlgPriceTxtFare;

  private DialogPriceBinding(@NonNull RelativeLayout rootView, @NonNull LinearLayout actualcontent,
      @NonNull ImageButton dlgPriceBtnBack, @NonNull ImageButton dlgPriceBtnGo,
      @NonNull ImageButton dlgPriceBtnPanic, @NonNull TextView dlgPriceLblSurchargePrompt,
      @NonNull TextView dlgPriceLblTitle, @NonNull TextView dlgPriceLblTotalFare,
      @NonNull EditText dlgPriceTxtFare) {
    this.rootView = rootView;
    this.actualcontent = actualcontent;
    this.dlgPriceBtnBack = dlgPriceBtnBack;
    this.dlgPriceBtnGo = dlgPriceBtnGo;
    this.dlgPriceBtnPanic = dlgPriceBtnPanic;
    this.dlgPriceLblSurchargePrompt = dlgPriceLblSurchargePrompt;
    this.dlgPriceLblTitle = dlgPriceLblTitle;
    this.dlgPriceLblTotalFare = dlgPriceLblTotalFare;
    this.dlgPriceTxtFare = dlgPriceTxtFare;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogPriceBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogPriceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_price, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogPriceBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.actualcontent;
      LinearLayout actualcontent = ViewBindings.findChildViewById(rootView, id);
      if (actualcontent == null) {
        break missingId;
      }

      id = R.id.dlgPrice_btnBack;
      ImageButton dlgPriceBtnBack = ViewBindings.findChildViewById(rootView, id);
      if (dlgPriceBtnBack == null) {
        break missingId;
      }

      id = R.id.dlgPrice_btnGo;
      ImageButton dlgPriceBtnGo = ViewBindings.findChildViewById(rootView, id);
      if (dlgPriceBtnGo == null) {
        break missingId;
      }

      id = R.id.dlgPrice_btnPanic;
      ImageButton dlgPriceBtnPanic = ViewBindings.findChildViewById(rootView, id);
      if (dlgPriceBtnPanic == null) {
        break missingId;
      }

      id = R.id.dlgPrice_lblSurchargePrompt;
      TextView dlgPriceLblSurchargePrompt = ViewBindings.findChildViewById(rootView, id);
      if (dlgPriceLblSurchargePrompt == null) {
        break missingId;
      }

      id = R.id.dlgPrice_lblTitle;
      TextView dlgPriceLblTitle = ViewBindings.findChildViewById(rootView, id);
      if (dlgPriceLblTitle == null) {
        break missingId;
      }

      id = R.id.dlgPrice_lblTotalFare;
      TextView dlgPriceLblTotalFare = ViewBindings.findChildViewById(rootView, id);
      if (dlgPriceLblTotalFare == null) {
        break missingId;
      }

      id = R.id.dlgPrice_txtFare;
      EditText dlgPriceTxtFare = ViewBindings.findChildViewById(rootView, id);
      if (dlgPriceTxtFare == null) {
        break missingId;
      }

      return new DialogPriceBinding((RelativeLayout) rootView, actualcontent, dlgPriceBtnBack,
          dlgPriceBtnGo, dlgPriceBtnPanic, dlgPriceLblSurchargePrompt, dlgPriceLblTitle,
          dlgPriceLblTotalFare, dlgPriceTxtFare);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}