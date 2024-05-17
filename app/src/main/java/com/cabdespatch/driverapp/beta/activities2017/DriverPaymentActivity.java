package com.cabdespatch.driverapp.beta.activities2017;

import androidx.activity.ComponentActivity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.PauseAndRun;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.gpay.CheckoutActivity;
import com.cabdespatch.libcabapiandroid.Apis.AnyApiRequest;
import com.cabdespatch.libcabapiandroid.Apis.CabApi.CabApiRequest;
import com.cabdespatch.libcabapiandroid.Apis.CabApi.DriverPayAndGoBalanceApi;
import com.cabdespatch.libcabapiandroid.Apis.CabApi.GetPaymentIntentApi;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.payments.paymentlauncher.PaymentLauncher;
import com.stripe.android.payments.paymentlauncher.PaymentResult;
import com.stripe.android.view.CardMultilineWidget;

public class DriverPaymentActivity extends AnyActivity implements CabApiRequest.AnyApiListener {

    ///NOT USED... the Api request is made from loginactivity
    //and the paymyment is launced directly from the label click in paymentactivity

    ViewGroup LayProgress;
    Button BtnPay;

    private RadioGroup paymentRadioGroup;


    //private String paymentIntentClientSecret;
    private PaymentLauncher paymentLauncher;

    private int API_TAG_GET_INTENT = 100;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_payment);

        LayProgress = findViewById(R.id.layProgress);
        BtnPay = findViewById(R.id.btnPay);
        paymentRadioGroup = findViewById(R.id.radioGroupPaymentMethods);

        BtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = paymentRadioGroup.getCheckedRadioButtonId();

                switch (selectedId) {

                    case R.id.radioButtonCreditCard:
                        Log.e("Click", "Credit Card");
                        payWithStripe(v);
                        break;

                    case R.id.radioButtonGPay:

                        Log.e("Click", "Gpay");
                        Toast.makeText(DriverPaymentActivity.this,
                                "G Pay", Toast.LENGTH_SHORT).show();

                        Intent mIntent = new Intent(DriverPaymentActivity.this, CheckoutActivity.class);
                        Log.e("amountDue", AmountDue + "");
                        mIntent.putExtra("amountDue", AmountDue);

                        startActivity(mIntent);

                        break;
                    case R.id.radioButtonPayPal:
                        Log.e("Click", "PayPal");
                        Toast.makeText(DriverPaymentActivity.this,
                                "PayPal", Toast.LENGTH_SHORT).show();
                        break;


                }
            }
        });

        View btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DriverPaymentActivity.this.btnBack_Click(view);
            }
        });

        double api_amount = STATUSMANAGER.STATUSES.PAY_AND_GO_BALANCE.parseDouble(this);
        api_amount /= 100;

        TextView txtPrompt = findViewById(R.id.lblBalance);
        txtPrompt.setText("You are in credit by £" + HandyTools.Strings.formatPrice(Math.abs(api_amount)));

        if (api_amount > 0) {
            txtPrompt.setText("You have an outstanding balance of £" + HandyTools.Strings.formatPrice(api_amount));
            txtPrompt.setTextColor(getResources().getColor(R.color.error_text));
        }

        setupPaymentButtons(api_amount);

    }


    private double AmountDue;

    private void setupPaymentButtons(double amount) {
        double first_option = 5;
        final double min_balance_required = 5; //5 pounds
        final double increment = 10; //10 pounds

        Button btn0 = findViewById(R.id.btnPaymentOption0);
        btn0.setVisibility((View.GONE));

        if (amount > 0) {
            btn0.setVisibility(View.VISIBLE);
            btn0.setTag(amount);
            btn0.setText("Outstanding Only (£" + HandyTools.Strings.formatPrice(amount) + ")");
            btn0.setOnClickListener(btnAmountClick());

            while (first_option < (amount + min_balance_required)) {
                first_option += 5;
            }
        }

        double next_amount = first_option;

        Button btn1 = findViewById(R.id.btnPaymentOption1);
        btn1.setTag(next_amount);
        btn1.setText("£" + HandyTools.Strings.formatPrice(next_amount));
        btn1.setOnClickListener(btnAmountClick());

        next_amount += increment;

        Button btn2 = findViewById(R.id.btnPaymentOption2);
        btn2.setTag(next_amount);
        btn2.setText("£" + HandyTools.Strings.formatPrice(next_amount));
        btn2.setOnClickListener(btnAmountClick());

        next_amount += increment;

        Button btn3 = findViewById(R.id.btnPaymentOption3);
        btn3.setTag(next_amount);
        btn3.setText("£" + HandyTools.Strings.formatPrice(next_amount));
        btn3.setOnClickListener(btnAmountClick());

        next_amount += increment;

        Button btn4 = findViewById(R.id.btnPaymentOption4);
        btn4.setTag(next_amount);
        btn4.setText("£" + HandyTools.Strings.formatPrice(next_amount));
        btn4.setOnClickListener(btnAmountClick());

        btnAmount_Click(btn1);

    }

    private View.OnClickListener btnAmountClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverPaymentActivity.this.btnAmount_Click(v);
            }
        };
    }

    private void btnAmount_Click(View v) {
        Button btn1 = findViewById(R.id.btnPaymentOption1);
        btn1.setSelected(false);

        Button btn2 = findViewById(R.id.btnPaymentOption2);
        btn2.setSelected(false);

        Button btn3 = findViewById(R.id.btnPaymentOption3);
        btn3.setSelected(false);

        Button btn4 = findViewById(R.id.btnPaymentOption4);
        btn4.setSelected(false);


        AmountDue = Double.valueOf(v.getTag().toString());
        BtnPay.setText("Pay £" + HandyTools.Strings.formatPrice(AmountDue));

        v.setSelected(true);
    }

    private void btnBack_Click(View v) {
        v.setVisibility(View.GONE);
        end(true);
    }

    private void payWithStripe(View v) {
        LayProgress.setVisibility(View.VISIBLE);
        GetPaymentIntentApi.GetPaymentIntentRequestArgs args = new GetPaymentIntentApi.GetPaymentIntentRequestArgs();
        //auth not used in api currently, but it's added here
        //for forward-compatibility
        args.auth.companyId = SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(this);

        args.companyId = SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(this);
        args.driverCallSign = SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.getValue(this);
        args.amountInSmallestDenomination = ((int) AmountDue) * 100;

        GetPaymentIntentApi api = new GetPaymentIntentApi(API_TAG_GET_INTENT, args, (AnyApiRequest.AnyApiListener) this, isCabApiDebug());
        api.Go();
    }


    private void ConfigureCheckout(String clientSecret, String publishableKey) {

        Intent i = new Intent(this, StripePaymentActivity.class);
        i.putExtra(StripePaymentActivity.EXTRA_AMOUNT_TO_PAY, AmountDue);
        i.putExtra(StripePaymentActivity.EXTRA_CLIENT_SECRET, clientSecret);
        i.putExtra(StripePaymentActivity.EXTRA_PUBLISHABLE_KEY, publishableKey);

        this.startActivity(i);
        end(false);

        //this.startActivityForResult(i, 1);
    }

    @Override
    public void OnApiRequestProgress(int tag, double progress) {

    }

    @Override
    public void OnApiRequestComplete(int tag, CabApiRequest.AnyApiResult response) {
        ProcessGetBalanceApiRequest(tag, response);
    }

    private void ProcessGetBalanceApiRequest(final int tag, final CabApiRequest.AnyApiResult response) {
        class runny implements Runnable {

            @Override
            public void run() {
                //LayProgress.setVisibility(View.GONE);

                if (tag == API_TAG_GET_INTENT) {
                    HandlePaymentIntentRequest(response);
                    return;
                }
            }
        }

        runOnUiThread(new runny());
    }

    private void HandlePaymentIntentRequest(AnyApiRequest.AnyApiResult response) {
        if (!(response.getResponseCode() == CabApiRequest.AnyApiResult.ResponseCodes.OK)) {
            GetIntentError();
            end(true);
            return;
        }

        GetPaymentIntentApi.GetPaymentIntentResult result = (GetPaymentIntentApi.GetPaymentIntentResult) response.getResult();
        ConfigureCheckout(result.clientSecret, result.publishableKey);

    }

    private void end(final Boolean launchLogin) {
        class MakeSureToastDisplays extends PauseAndRun {
            @Override
            protected void onPostExecute(Void _void) {
                if (launchLogin) {
                    Intent i = new Intent(DriverPaymentActivity.this, LoginActivity.class);
                    DriverPaymentActivity.this.startActivity(i);
                }
                DriverPaymentActivity.this.finish();
            }
        }

        new MakeSureToastDisplays().Start(1000);

    }

    private void GetIntentError() {
        cdToast.showLong(this, "There was an error communicating with the server. Your card has not been charged.");
    }


    public static void StartPayAndGoPayment(Context context) {
        Intent i = new Intent(context, DriverPaymentActivity.class);
        context.startActivity(i);
    }


}