package com.cabdespatch.driverapp.beta.activities2017;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.PauseAndRun;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.cdToast;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.payments.paymentlauncher.PaymentLauncher;
import com.stripe.android.payments.paymentlauncher.PaymentResult;
import com.stripe.android.view.CardMultilineWidget;

public class StripePaymentActivity extends AnyActivity
{

    public static final String EXTRA_PUBLISHABLE_KEY = "EXTRA_PUBLISHABLE_KEY";
    public static final String EXTRA_CLIENT_SECRET = "EXTRA_CLIENT_SECRET";
    public static final String EXTRA_AMOUNT_TO_PAY = "EXTRA_AMOUNT_TO_PAY";

    private PaymentLauncher paymentLauncher;
    private  View LayProgress;
    private TextView LblPayment;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);

        final String client_secret = getIntent().getStringExtra(EXTRA_CLIENT_SECRET);
        final String publishable_key = getIntent().getStringExtra(EXTRA_PUBLISHABLE_KEY);
        final double amount_to_pay = getIntent().getDoubleExtra(EXTRA_AMOUNT_TO_PAY, -1);

        PaymentConfiguration.init(this, publishable_key);

        LayProgress = findViewById(R.id.layProgress);
        LblPayment = findViewById(R.id.lblBalance);

        LblPayment.setText("Make Payment of £" + HandyTools.Strings.formatPrice(amount_to_pay));

        final PaymentConfiguration paymentConfiguration
                = PaymentConfiguration.getInstance(getApplicationContext());
        paymentLauncher = PaymentLauncher.Companion.create(
                (ComponentActivity) this,
                paymentConfiguration.getPublishableKey(),
                paymentConfiguration.getStripeAccountId(),
                this::onPaymentResult);

        View btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                StripePaymentActivity.this.btnBack_Click(view);
            }
        });

        Button btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StripePaymentActivity.this.ConfigureCheckout(amount_to_pay, client_secret);
            }
        });

        btnPay.setText("Pay £" + HandyTools.Strings.formatPrice(amount_to_pay));

    }

    private void btnBack_Click(View v)
    {
        v.setVisibility(View.GONE);
        end(true);
    }

    private void ConfigureCheckout(double amountToPay, String clientSecret)
    {

        LayProgress.setVisibility(View.VISIBLE);

        final CardMultilineWidget cardInputWidget = this.findViewById(R.id.cardMultiLineWidget);
        final PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
        if (params != null)
        {
            final ConfirmPaymentIntentParams confirmParams =
                    ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
                            params,
                            clientSecret);
            paymentLauncher.confirm(confirmParams);
        }
    }

    private void onPaymentResult(PaymentResult paymentResult) {
        String message = "Unknown Error";
        if (paymentResult instanceof PaymentResult.Completed)
        {
            message = "Your payment is being processed";
        } else if (paymentResult instanceof PaymentResult.Canceled) {
            message = "The request was cancelled";
        } else if (paymentResult instanceof PaymentResult.Failed) {
            // This string comes from the PaymentIntent's error message.
            // See here: https://stripe.com/docs/api/payment_intents/object#payment_intent_object-last_payment_error-message
            message = "The request failed"
                    + ((PaymentResult.Failed) paymentResult).getThrowable().getMessage();
        }

        cdToast.showLong(this, message);
        end(true);

    }

    private void end(final Boolean launchLogin)
    {
        class MakeSureToastDisplays extends PauseAndRun
        {
            @Override
            protected void onPostExecute(Void _void)
            {
                if(launchLogin)
                {
                    Intent i = new Intent(StripePaymentActivity.this, LoginActivity.class);
                    StripePaymentActivity.this.startActivity(i);
                }
                StripePaymentActivity.this.finish();
            }
        }

        new MakeSureToastDisplays().Start(1000);

    }



}