// Generated by view binder compiler. Do not edit!
package com.cabdespatch.driverapp.beta.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.legacy.widget.Space;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cabdespatch.driverapp.beta.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityJobofferBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TableLayout actualcontent;

  @NonNull
  public final ImageButton frmJobOfferBtnAccept;

  @NonNull
  public final ImageButton frmJobOfferBtnReject;

  @NonNull
  public final LinearLayout frmJobOfferLayPleaseWait;

  @NonNull
  public final TextView frmJobOfferLblGPSPlot;

  @NonNull
  public final TextView frmJobOfferLblJobDistance;

  @NonNull
  public final TextView frmJobOfferLblJobTime;

  @NonNull
  public final TextView frmJobOfferLblMode;

  @NonNull
  public final TextView frmJobOfferLblPickupPlot;

  @NonNull
  public final TextView frmJobOfferLblTitle;

  @NonNull
  public final TextView frmJobOfferLblVehicleType;

  @NonNull
  public final ProgressBar frmJobOfferPrgTimeout;

  @NonNull
  public final LinearLayout layDestinationPlot;

  @NonNull
  public final LinearLayout layFare;

  @NonNull
  public final Space laySpacer;

  @NonNull
  public final TextView lblDestinationPlot;

  @NonNull
  public final TextView lblFare;

  private ActivityJobofferBinding(@NonNull RelativeLayout rootView,
      @NonNull TableLayout actualcontent, @NonNull ImageButton frmJobOfferBtnAccept,
      @NonNull ImageButton frmJobOfferBtnReject, @NonNull LinearLayout frmJobOfferLayPleaseWait,
      @NonNull TextView frmJobOfferLblGPSPlot, @NonNull TextView frmJobOfferLblJobDistance,
      @NonNull TextView frmJobOfferLblJobTime, @NonNull TextView frmJobOfferLblMode,
      @NonNull TextView frmJobOfferLblPickupPlot, @NonNull TextView frmJobOfferLblTitle,
      @NonNull TextView frmJobOfferLblVehicleType, @NonNull ProgressBar frmJobOfferPrgTimeout,
      @NonNull LinearLayout layDestinationPlot, @NonNull LinearLayout layFare,
      @NonNull Space laySpacer, @NonNull TextView lblDestinationPlot, @NonNull TextView lblFare) {
    this.rootView = rootView;
    this.actualcontent = actualcontent;
    this.frmJobOfferBtnAccept = frmJobOfferBtnAccept;
    this.frmJobOfferBtnReject = frmJobOfferBtnReject;
    this.frmJobOfferLayPleaseWait = frmJobOfferLayPleaseWait;
    this.frmJobOfferLblGPSPlot = frmJobOfferLblGPSPlot;
    this.frmJobOfferLblJobDistance = frmJobOfferLblJobDistance;
    this.frmJobOfferLblJobTime = frmJobOfferLblJobTime;
    this.frmJobOfferLblMode = frmJobOfferLblMode;
    this.frmJobOfferLblPickupPlot = frmJobOfferLblPickupPlot;
    this.frmJobOfferLblTitle = frmJobOfferLblTitle;
    this.frmJobOfferLblVehicleType = frmJobOfferLblVehicleType;
    this.frmJobOfferPrgTimeout = frmJobOfferPrgTimeout;
    this.layDestinationPlot = layDestinationPlot;
    this.layFare = layFare;
    this.laySpacer = laySpacer;
    this.lblDestinationPlot = lblDestinationPlot;
    this.lblFare = lblFare;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityJobofferBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityJobofferBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_joboffer, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityJobofferBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.actualcontent;
      TableLayout actualcontent = ViewBindings.findChildViewById(rootView, id);
      if (actualcontent == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_btnAccept;
      ImageButton frmJobOfferBtnAccept = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferBtnAccept == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_btnReject;
      ImageButton frmJobOfferBtnReject = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferBtnReject == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_layPleaseWait;
      LinearLayout frmJobOfferLayPleaseWait = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferLayPleaseWait == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_lblGPSPlot;
      TextView frmJobOfferLblGPSPlot = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferLblGPSPlot == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_lblJobDistance;
      TextView frmJobOfferLblJobDistance = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferLblJobDistance == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_lblJobTime;
      TextView frmJobOfferLblJobTime = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferLblJobTime == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_lblMode;
      TextView frmJobOfferLblMode = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferLblMode == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_lblPickupPlot;
      TextView frmJobOfferLblPickupPlot = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferLblPickupPlot == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_lblTitle;
      TextView frmJobOfferLblTitle = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferLblTitle == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_lblVehicleType;
      TextView frmJobOfferLblVehicleType = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferLblVehicleType == null) {
        break missingId;
      }

      id = R.id.frmJobOffer_prgTimeout;
      ProgressBar frmJobOfferPrgTimeout = ViewBindings.findChildViewById(rootView, id);
      if (frmJobOfferPrgTimeout == null) {
        break missingId;
      }

      id = R.id.layDestinationPlot;
      LinearLayout layDestinationPlot = ViewBindings.findChildViewById(rootView, id);
      if (layDestinationPlot == null) {
        break missingId;
      }

      id = R.id.layFare;
      LinearLayout layFare = ViewBindings.findChildViewById(rootView, id);
      if (layFare == null) {
        break missingId;
      }

      id = R.id.laySpacer;
      Space laySpacer = ViewBindings.findChildViewById(rootView, id);
      if (laySpacer == null) {
        break missingId;
      }

      id = R.id.lblDestinationPlot;
      TextView lblDestinationPlot = ViewBindings.findChildViewById(rootView, id);
      if (lblDestinationPlot == null) {
        break missingId;
      }

      id = R.id.lblFare;
      TextView lblFare = ViewBindings.findChildViewById(rootView, id);
      if (lblFare == null) {
        break missingId;
      }

      return new ActivityJobofferBinding((RelativeLayout) rootView, actualcontent,
          frmJobOfferBtnAccept, frmJobOfferBtnReject, frmJobOfferLayPleaseWait,
          frmJobOfferLblGPSPlot, frmJobOfferLblJobDistance, frmJobOfferLblJobTime,
          frmJobOfferLblMode, frmJobOfferLblPickupPlot, frmJobOfferLblTitle,
          frmJobOfferLblVehicleType, frmJobOfferPrgTimeout, layDestinationPlot, layFare, laySpacer,
          lblDestinationPlot, lblFare);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
