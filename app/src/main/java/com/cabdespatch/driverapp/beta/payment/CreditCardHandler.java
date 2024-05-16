package com.cabdespatch.driverapp.beta.payment;

import android.app.Activity;
import android.content.Intent;

import com.cabdespatch.driverapp.beta.STATUSMANAGER;

/**
 * Created by Pleng on 13/11/2016.
 */

public abstract class CreditCardHandler
{

    public static final Integer REQUEST_SUMUP_LOGIN = 196;
    public static final Integer REQUEST_SUMUP_CARD_PAYMENT = 195;

    //see PaylevenApi.Class - public static void initiatePayment(Activity activity, String orderId, TransactionRequest request)
    //public static final Integer REQUEST_PAYLEVEN_CARD_PAYMENT = 133;

    public static final int RESULT_FAIL = -100;
    public static final int RESULT_CANX = 0;
    public static final int RESULT_PASS = 100;
    protected OnCreditCardPaymentResultListener oListener;

    public abstract Boolean isReady();

    private static Boolean PAYMENT_IN_PROGRESS = false;

    public static Boolean isProcessingPayment()
    {
        return PAYMENT_IN_PROGRESS;
    }

    public CreditCardHandler(OnCreditCardPaymentResultListener _listener)
    {
        this.oListener = _listener;
    }

    public interface OnCreditCardPaymentResultListener
    {
        public void onCreditCardPaymentResult(int _result, Double _amount, String _debugData);
    }

    public final void startPayment(Activity _a)
    {
        STATUSMANAGER.aquireLock();
        PAYMENT_IN_PROGRESS = true;
        startPayment_Process(_a);
    }


    public void processActivityResult(Intent data, int resultCode, String _debugData)
    {
        PAYMENT_IN_PROGRESS = false;
        // Make sure the request was successful
        if (resultCode == Activity.RESULT_OK)
        {
            processActivityResult_Process(data);
        }
        else
        {
            oListener.onCreditCardPaymentResult(CreditCardHandler.RESULT_CANX, 0.0, _debugData);
        }
    }

    protected abstract void startPayment_Process(Activity _a);
    protected abstract void processActivityResult_Process(Intent data);




}
