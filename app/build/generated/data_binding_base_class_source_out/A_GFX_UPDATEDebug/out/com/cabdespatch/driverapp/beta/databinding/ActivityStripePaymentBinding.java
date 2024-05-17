// Generated by view binder compiler. Do not edit!
package com.cabdespatch.driverapp.beta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cabdespatch.driverapp.beta.R;
import com.stripe.android.view.CardMultilineWidget;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityStripePaymentBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton btnBack;

  @NonNull
  public final Button btnPay;

  @NonNull
  public final CardMultilineWidget cardMultiLineWidget;

  @NonNull
  public final LinearLayoutCompat layDisplayPayment;

  @NonNull
  public final LinearLayoutCompat layProgress;

  @NonNull
  public final TextView lblBalance;

  private ActivityStripePaymentBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton btnBack, @NonNull Button btnPay,
      @NonNull CardMultilineWidget cardMultiLineWidget,
      @NonNull LinearLayoutCompat layDisplayPayment, @NonNull LinearLayoutCompat layProgress,
      @NonNull TextView lblBalance) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.btnPay = btnPay;
    this.cardMultiLineWidget = cardMultiLineWidget;
    this.layDisplayPayment = layDisplayPayment;
    this.layProgress = layProgress;
    this.lblBalance = lblBalance;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityStripePaymentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityStripePaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_stripe_payment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityStripePaymentBinding bind(@NonNull View rootView) {
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

      id = R.id.cardMultiLineWidget;
      CardMultilineWidget cardMultiLineWidget = ViewBindings.findChildViewById(rootView, id);
      if (cardMultiLineWidget == null) {
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

      return new ActivityStripePaymentBinding((ConstraintLayout) rootView, btnBack, btnPay,
          cardMultiLineWidget, layDisplayPayment, layProgress, lblBalance);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}