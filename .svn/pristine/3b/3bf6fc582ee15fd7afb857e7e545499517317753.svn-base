package com.cabdespatch.driverapp.beta.activities2017;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.payment.CreditCardHandler;


	public class TestActivity extends AnyActivity implements CreditCardHandler.OnCreditCardPaymentResultListener
	{

		CreditCardHandler payment;

		@Override
		public void onCreditCardPaymentResult(int _result, Double _amount, String _debugData)
		{

		}

		@Override
		public void onCreate(Bundle _savedState)
		{
			super.onCreate(_savedState);
			setContentView(R.layout.activity_test);

			TextView lbl = findViewById(R.id.frmTest_lblTest);
			EditText t1 = findViewById(R.id.frmTest_txtTest1);
			EditText t2 = findViewById(R.id.frmTest_txtTest2);
			EditText t3 = findViewById(R.id.frmTest_txtTest3);
			EditText t4 = findViewById(R.id.frmTest_txtTest4);

			/*
			lbl.setText(androidId);
			t1.setText(IMEI);
			t2.setText(String.valueOf(IMEI.length()));

			t3.setText(SIMNo);
			t4.setText(String.valueOf(SIMNo.length()));
			*/

			/*
			SumUpState.init(this);

			setContentView(R.layout.activity_test);

			cabdespatchJob j = new cabdespatchJob(3.40, false);
			j.setFromAddress("Pickup address");
			j.setToAddress("Destination address");
			STATUSMANAGER.setCurrentJob(this, j);

			payment = new SumUpHandler(this);
			payment.startPayment(this);*/
		}

/*
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data)
		{
			if (requestCode == CreditCardHandler.REQUEST_SUMUP_LOGIN)
			{
				if (payment.isReady())
				{

					payment.startPayment(this);
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
		}*/
	}
