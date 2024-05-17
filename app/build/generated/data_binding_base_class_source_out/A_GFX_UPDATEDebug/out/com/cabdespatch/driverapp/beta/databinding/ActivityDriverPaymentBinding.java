// Generated by view binder compiler. Do not edit!
package com.cabdespatch.driverapp.beta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cabdespatch.driverapp.beta.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDriverPaymentBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton btnBack;

  @NonNull
  public final Button btnPay;

  @NonNull
  public final Button btnPaymentOption0;

  @NonNull
  public final Button btnPaymentOption1;

  @NonNull
  public final Button btnPaymentOption2;

  @NonNull
  public final Button btnPaymentOption3;

  @NonNull
  public final Button btnPaymentOption4;

  @NonNull
  public final LinearLayoutCompat layDisplayPayment;

  @NonNull
  public final LinearLayoutCompat layProgress;

  @NonNull
  public final TextView lblBalance;

  @NonNull
  public final RadioButton radioButtonCreditCard;

  @NonNull
  public final RadioButton radioButtonGPay;

  @NonNull
  public final RadioButton radioButtonPayPal;

  @NonNull
  public final RadioGroup radioGroupPaymentMethods;

  @NonNull
  public final TextView textViewSelectPayment;

  private ActivityDriverPaymentBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageButton btnBack, @NonNull Button btnPay, @NonNull Button btnPaymentOption0,
      @NonNull Button btnPaymentOption1, @NonNull Button btnPaymentOption2,
      @NonNull Button btnPaymentOption3, @NonNull Button btnPaymentOption4,
      @NonNull LinearLayoutCompat layDisplayPayment, @NonNull LinearLayoutCompat layProgress,
      @NonNull TextView lblBalance, @NonNull RadioButton radioButtonCreditCard,
      @NonNull RadioButton radioButtonGPay, @NonNull RadioButton radioButtonPayPal,
      @NonNull RadioGroup radioGroupPaymentMethods, @NonNull TextView textViewSelectPayment) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.btnPay = btnPay;
    this.btnPaymentOption0 = btnPaymentOption0;
    this.btnPaymentOption1 = btnPaymentOption1;
    this.btnPaymentOption2 = btnPaymentOption2;
    this.btnPaymentOption3 = btnPaymentOption3;
    this.btnPaymentOption4 = btnPaymentOption4;
    this.layDisplayPayment = layDisplayPayment;
    this.layProgress = layProgress;
    this.lblBalance = lblBalance;
    this.radioButtonCreditCard = radioButtonCreditCard;
    this.radioButtonGPay = radioButtonGPay;
    this.radioButtonPayPal = radioButtonPayPal;
    this.radioGroupPaymentMethods = radioGroupPaymentMethods;
    this.textViewSelectPayment = textViewSelectPayment;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDriverPaymentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDriverPaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_driver_payment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDriverPaymentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnBack;
      ImageButton btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.btnPay;
      Button btnPay = ViewBindings.findChildViewById(rootView, id);
      if (btnPay == null) {
        break missingId;
      }

      id = R.id.btnPaymentOption0;
      Button btnPaymentOption0 = ViewBindings.findChildViewById(rootView, id);
      if (btnPaymentOption0 == null) {
        break missingId;
      }

      id = R.id.btnPaymentOption1;
      Button btnPaymentOption1 = ViewBindings.findChildViewById(rootView, id);
      if (btnPaymentOption1 == null) {
        break missingId;
      }

      id = R.id.btnPaymentOption2;
      Button btnPaymentOption2 = ViewBindings.findChildViewById(rootView, id);
      if (btnPaymentOption2 == null) {
        break missingId;
      }

      id = R.id.btnPaymentOption3;
      Button btnPaymentOption3 = ViewBindings.findChildViewById(rootView, id);
      if (btnPaymentOption3 == null) {
        break missingId;
      }

      id = R.id.btnPaymentOption4;
      Button btnPaymentOption4 = ViewBindings.findChildViewById(rootView, id);
      if (btnPaymentOption4 == null) {
        break missingId;
      }

      id = R.id.layDisplayPayment;
      LinearLayoutCompat layDisplayPayment = ViewBindings.findChildViewById(rootView, id);
      if (layDisplayPayment == null) {
        break missingId;
      }

      id = R.id.layProgress;
      LinearLayoutCompat layProgress = ViewBindings.findChildViewById(rootView, id);
      if (layProgress == null) {
        break missingId;
      }

      id = R.id.lblBalance;
      TextView lblBalance = ViewBindings.findChildViewById(rootView, id);
      if (lblBalance == null) {
        break missingId;
      }

      id = R.id.radioButtonCreditCard;
      RadioButton radioButtonCreditCard = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonCreditCard == null) {
        break missingId;
      }

      id = R.id.radioButtonGPay;
      RadioButton radioButtonGPay = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonGPay == null) {
        break missingId;
      }

      id = R.id.radioButtonPayPal;
      RadioButton radioButtonPayPal = ViewBindings.findChildViewById(rootView, id);
      if (radioButtonPayPal == null) {
        break missingId;
      }

      id = R.id.radioGroupPaymentMethods;
      RadioGroup radioGroupPaymentMethods = ViewBindings.findChildViewById(rootView, id);
      if (radioGroupPaymentMethods == null) {
        break missingId;
      }

      id = R.id.textViewSelectPayment;
      TextView textViewSelectPayment = ViewBindings.findChildViewById(rootView, id);
      if (textViewSelectPayment == null) {
        break missingId;
      }

      return new ActivityDriverPaymentBinding((RelativeLayout) rootView, btnBack, btnPay,
          btnPaymentOption0, btnPaymentOption1, btnPaymentOption2, btnPaymentOption3,
          btnPaymentOption4, layDisplayPayment, layProgress, lblBalance, radioButtonCreditCard,
          radioButtonGPay, radioButtonPayPal, radioGroupPaymentMethods, textViewSelectPayment);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
