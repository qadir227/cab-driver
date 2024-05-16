package com.cabdespatch.driverapp.beta.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.sumup.merchant.reader.api.SumUpAPI;
import com.sumup.merchant.reader.api.SumUpLogin;
import com.sumup.merchant.reader.api.SumUpPayment;
import com.sumup.merchant.reader.models.TransactionInfo;

import java.math.BigDecimal;

/**
 * Created by Pleng on 13/11/2016.
 */

public class SumUpHandler extends CreditCardHandler
{

    @Override
    public Boolean isReady()
    {
        try
        {
            return SumUpAPI.isLoggedIn();
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public SumUpHandler(OnCreditCardPaymentResultListener _listener)
    {
        super(_listener);
    }

    private String oAffiliateKey = "0b650bac-627d-4057-a975-b27be3e91f68";

    SumUpPayment payment;

    @Override
    protected void startPayment_Process(Activity _a)
    {
        cabdespatchJob j = STATUSMANAGER.getCurrentJob(_a);

        if (isReady())
        {
            SumUpPayment payment = SumUpPayment.builder()
                    // mandatory parameters
                    .total(BigDecimal.valueOf(Double.valueOf(j.getPrice()))) // minimum 1.00
                    .currency(SumUpPayment.Currency.GBP)
                    .title("Taxi Ride")
                    // optional: Add metadata
                    .addAdditionalInfo("From", j.getFromAddress())
                    .addAdditionalInfo("To", j.getToAddress())
                    // optional: foreign transaction ID, must be unique!
                    //.foreignTransactionId(UUID.randomUUID().toString())  // can not exceed 128 chars
                    // optional: skip the success screen
                    ///.skipSuccessScreen()
                    .build();

            SumUpAPI.checkout(_a, payment, CreditCardHandler.REQUEST_SUMUP_CARD_PAYMENT);
        }
        else
        {
            SumUpLogin sumupLogin = SumUpLogin.builder(oAffiliateKey).build();
            SumUpAPI.openLoginActivity(_a, sumupLogin, CreditCardHandler.REQUEST_SUMUP_LOGIN);
        }

        /* old api version
        payment = SumUpPayment.builder()
                //mandatory parameters
                // Please go to https://me.sumup.com/developers to get your Affiliate Key by entering the application ID of your app. (e.g. com.sumup.sdksampleapp)
                .affiliateKey("0b650bac-627d-4057-a975-b27be3e91f68")
                .productAmount(Double.valueOf(j.getPrice()))
                .currency(SumUpPayment.Currency.GBP)
                .productTitle("Taxi Ride")
                .addAdditionalInfo("From", j.getFromAddress())
                .addAdditionalInfo("To", j.getToAddress())
                .build();

        SumUpAPI.openPaymentActivity(_a, payment, CreditCardHandler.REQUEST_SUMUP_CARD_PAYMENT);
        */


    }

    @Override
    public void processActivityResult_Process(Intent data)
    {
        Bundle extra = data.getExtras();
        int paymentResultCode =  extra.getInt(SumUpAPI.Response.RESULT_CODE);
        if(paymentResultCode == SumUpAPI.Response.ResultCode.SUCCESSFUL)
        {
            TransactionInfo paymentTansactionInfo = extra.getParcelable(SumUpAPI.Response.TX_INFO);
            if(paymentTansactionInfo.getStatus().equals("SUCCESSFUL"))
            {
                //logData += "(Card ending with: " + ;
                oListener.onCreditCardPaymentResult(CreditCardHandler.RESULT_PASS, paymentTansactionInfo.getAmount(), "PTI-" + paymentTansactionInfo.getStatus());
            }
            else
            {
                oListener.onCreditCardPaymentResult(CreditCardHandler.RESULT_FAIL, 0.0, "PTI-" + paymentTansactionInfo.getStatus());
            }
        }
        else
        {
            oListener.onCreditCardPaymentResult(CreditCardHandler.RESULT_FAIL, 0.0, "PRC-" + paymentResultCode);
        }

    }
}
