package com.cabdespatch.driverapp.beta.activities2017;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.DEBUGMANAGER;
import com.cabdespatch.driverapp.beta.PauseAndRun;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.payment.CreditCardHandler;
import com.cabdespatch.driverapp.beta.payment.SumUpHandler;
import com.sumup.merchant.reader.api.SumUpState;

public class PaymentActivity extends AnyActivity implements CreditCardHandler.OnCreditCardPaymentResultListener, View.OnClickListener {

    private static CreditCardHandler payment;
    private static Double pre_fee_fare = -1d;

    private TextView text;
    private ProgressBar progress;
    private View btnBack;

    @Override
    protected void onCreated()
    {
        super.onCreated();
        SumUpState.init(this);

        setContentView(R.layout.activity_payment);

        text = findViewById(R.id.text);
        progress = findViewById(R.id.progress);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(payment==null)
        {
            payment = new SumUpHandler(this);
            new doStartPayment().Start(1500);
        }

    }


    private class doStartPayment extends PauseAndRun
    {

        @Override
        protected void onPostExecute(Void _void)
        {
            payment.startPayment(PaymentActivity.this);
        }
    }

    private class showResult extends PauseAndRun
    {
        public String DisplayMessage;

        public  showResult(String _message)
        {
            DisplayMessage = _message;
        }


        @Override
        protected void onPostExecute(Void _void)
        {
            progress.setVisibility(View.GONE);
            text.setText(DisplayMessage);
            text.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onCreditCardPaymentResult(int _result, Double _amount, String _debugData)
    {
        /*
        String logData = "there was an unexpected response type from the payment class";
        String display_message = "An unexpected error occurred";

        switch(_result)
        {
            case CreditCardHandler.RESULT_CANX:
                logData = "Driver's request for credit card transaction was cancelled";
                display_message = "The payment request was cancelled";
                cabdespatchJob jc = STATUSMANAGER.getCurrentJob(this);
                jc.revertPriceTo(pre_fee_fare);
                STATUSMANAGER.setCurrentJob(this, jc);
                break;
            case CreditCardHandler.RESULT_FAIL:
                logData = "Driver's request for credit card transaction failed";
                display_message = "The payment request failed";
                cabdespatchJob jf = STATUSMANAGER.getCurrentJob(this);
                jf.revertPriceTo(pre_fee_fare);
                STATUSMANAGER.setCurrentJob(this, jf);
                break;
            case CreditCardHandler.RESULT_PASS:
                display_message = "The payment was a success";
                logData = "Driver received card payment of £" + String.valueOf(_amount);
                break;
        }

        BROADCASTERS.HistoryStringMessage(this, logData);
        new showResult(display_message).Start(1500);
        */

        String logData = "Payment ended with the following response:  " + _debugData;
        BROADCASTERS.HistoryStringMessage(this, logData);

        class delayfinish extends PauseAndRun
        {

            @Override
            protected void onPostExecute(Void _void)
            {
                try
                {
                    PaymentActivity.this.finish();
                }
                catch (Exception _ex)
                {
                    DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR, "P/MNT", _ex.toString());
                }
            }
        }

        //this is required as SumUP API seems to try and remove things from host activity
        //if we finish the activity before SumUp do their thing then we get ourselves an
        //anr
        new delayfinish().Start(3000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CreditCardHandler.REQUEST_SUMUP_LOGIN)
        {
            if (payment.isReady())
            {

                new doStartPayment().Start(1500);
                cdToast.setTempBottomGravity();
                if (isDebug()){cdToast.showLong(this, "Starting payment...");}
            }
            else
            {
                cdToast.setTempBottomGravity();
                cdToast.showLong(this, "Could not start payment", "Login Error");
            }
        }
        else if (requestCode == CreditCardHandler.REQUEST_SUMUP_CARD_PAYMENT)
        {
            payment.processActivityResult(data, resultCode, "");
        }
    }


    public static void Launch(Context _context)
    {

        cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
        pre_fee_fare = Double.valueOf(j.getPrice());

        Intent i = new Intent(_context, PaymentActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    @Override
    protected void onDestroying()
    {
        super.onDestroying();
        reset();
    }

    private void reset()
    {
        pre_fee_fare = -1d;
        payment = null;
    }


    @Override
    public void onClick(View v)
    {
        //only one object so far - btnBack
        this.onBackPressed();
    }

}
