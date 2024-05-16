package com.cabdespatch.driverapp.beta.payment;

import android.app.Activity;
import android.content.Intent;
/*
import com.payleven.payment.api.PaylevenApi;
import com.payleven.payment.api.TransactionRequest;
import com.payleven.payment.api.TransactionRequestBuilder;
*/

/**
 * Created by Pleng on 13/11/2016.
 */

public class PaylevenHandler extends CreditCardHandler
{
    @Override
    public Boolean isReady() {
        return false;
    }

    public PaylevenHandler(OnCreditCardPaymentResultListener _listener)
    {
        super(_listener);
    }

    @Override
    protected void startPayment_Process(Activity _a) {

    }

    @Override
    protected void processActivityResult_Process(Intent data) {

    }
/*
    public PaylevenHandler(OnCreditCardPaymentResultListener _listener)
    {
        super(_listener);
    }

    @Override
    protected void startPayment_Process(Activity _a)
    {
        PaylevenApi.configure("yourApiKey");

        TransactionRequest request = new TransactionRequestBuilder(120, Currency.getInstance(""))
        .setDescription("description")
                .createTransactionRequest();
            //.setBitmap(image);

        //TransactionRequest request = builder.createTransactionRequest()

        PaylevenApi.initiatePayment(_a, "orderId", request);
    }

    @Override
    public void processActivityResult_Process(Intent data)
    {

    }*/
}
