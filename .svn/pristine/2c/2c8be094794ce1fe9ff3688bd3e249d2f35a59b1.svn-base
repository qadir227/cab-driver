package com.cabdespatch.driverapp.beta.activities2017;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cabdespatch.driverapp.beta.AndroidBug5497Workaround;
import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.CabDespatchNetworkOldSty;
import com.cabdespatch.driverapp.beta.DEBUGMANAGER;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.NoLeadingZerosTextWatcher;
import com.cabdespatch.driverapp.beta.PauseAndRun;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.SOUNDMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.activities.Launcher;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;
import com.cabdespatch.driverapp.beta.fragments.StatusBar;
import com.cabdespatch.driverapp.beta.services.DataService;
import com.cabdespatch.libcabapiandroid.Apis.CabApi.CabApiRequest;
import com.cabdespatch.libcabapiandroid.Apis.CabApi.DriverPayAndGoBalanceApi;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

public class LoginActivity extends MenuActivity implements CabApiRequest.AnyApiListener
{

    @Override
    protected String getDebugTag()
    {
        return "LoginActivity.java";
    }

	private ImageButton oBtnLogin;
	private View oProgressLogin;
	private EditText oTxtDriverNo;
	private EditText oTxtVehicleNo;
	private EditText oTxtPinNo;
	private boolean oUsePinLogin;
	private int oTouchyCount = 0;
	//private LoginStatusChecker oLoginStatusChecker;
	private final int oTimeoutMins = 5;
    private TimeOut oTimeOut;
	private Boolean oDebugTimeout = false;
	private StatusBar oStatusBar;

	private int API_TAG_GET_DRIVER_BALANCE = 100;

	public Runnable SetViewVisibility(final View _v, final int _visibility, final int _preDelay)
	{
		return new Runnable()
		{

			@Override
			public void run() 
			{
                if(_preDelay > 0)
                {
                    class giveItTime extends PauseAndRun
                    {
                        @Override
                        protected void onPostExecute(Void _void)
                        {
                            _v.setVisibility(_visibility);
                        }
                    }

                    new giveItTime().Start(_preDelay);
                }
                else
                {
                    _v.setVisibility(_visibility);
                }
			}
			
		};
	}


    @Override
    protected void showDriverMessage()
    {
        Intent i = new Intent(this, DriverMessage.class);
        startActivity(i);
    }

    @Override
    protected void onNetworkStateChange(Boolean _connected)
    {

    }

    @Override
    protected void onBroadcastReceived(Context _context, Intent _intent)
    {
		if(!(this.oStatusBar==null))
		{
			this.oStatusBar.onPreBroadcastReceived(_context, _intent);
		}
    }

    @Override
	public void onPause()
	{
		super.onPause();
        this.oTimeOut.finish();
/*
		if(!(this.oLoginStatusChecker==null))
		{
			this.oLoginStatusChecker.stopRunning();	
		}

        try
        {
            this.unregisterReceiver(this.oMessageReceiver);
        } catch (Exception ex) { /*never mind }
    */
		
	}

	@Override
	public void onLogEvent(String _data)
	{
		//do nothing
	}


	private ViewGroup aMenuBackground;

	@Override
	protected void onTick(long _millis)
	{
		super.onTick(_millis);

		/* CLAY
		if(!(oBtnLogin==null))
		{
			if(CabDespatchNetwork.getConnectionStatus()== CabDespatchNetwork.CONNECTION_STATUS.CONNECTED)
			{
				oBtnLogin.setVisibility(View.VISIBLE);
			}
			else
			{
				oBtnLogin.setVisibility(View.INVISIBLE);
			}
		}*/

		if (!(oStatusBar == null))
		{
			oStatusBar.onTick();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Globals.registerBugHandler(this);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login_compat);

		oStatusBar = (StatusBar) getSupportFragmentManager().findFragmentById(R.id.status_bar_login);

        this.aMenuBackground = (ViewGroup) this.findViewById(R.id.menuBackground);

		this.setBackground();
		AndroidBug5497Workaround.assistActivity(this);

		DoStuffThatWasPrviouslyInOnResume();


	}

	private void DoStuffThatWasPrviouslyInOnResume()
	{
		super.onResume();

		this.oTimeOut = new TimeOut();
		this.oTimeOut.start();

		StartPayAndGoBalanceRequest();

		if (STATUSMANAGER.getAppState(this).equals(STATUSMANAGER.APP_STATE.LAUNCHER))
		{
			this.finish();
		}
		else
		{
            /*
	    	this.oLoginStatusChecker = new LoginStatusChecker();
		    this.oLoginStatusChecker.start();
		    */

			ImageView imgDemoMode = (ImageView) this.findViewById(R.id.frmStart_demoOverlay);
			Boolean demoMode = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.IS_DEMO_MODE.getValue(this));

			if(demoMode)
			{
				imgDemoMode.setVisibility(View.VISIBLE);
				SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.putValue(this, "199");
			}
			else
			{
				imgDemoMode.setVisibility(View.INVISIBLE);
			}

			ImageView imgLogo = (ImageView) this.findViewById(R.id.frmStart_Logo);
			imgLogo.setOnClickListener(new View.OnClickListener()
			{

				class foo extends Thread
				{
					@Override
					public void run()
					{
						try
						{
							Thread.sleep(2000);
							LoginActivity.this.oTouchyCount = 0;
						}
						catch (Exception ex)
						{
							//CLAY
						}
					}
				}

				@Override
				public void onClick(View v)
				{
					Boolean hasCake = STATUSMANAGER.getBoolean(LoginActivity.this, STATUSMANAGER.STATUSES.HAS_DEBUG_CONNECTOR);
					if (hasCake)
					{
						if(LoginActivity.this.oTouchyCount++==5)
						{
							SOUNDMANAGER.speakThankyou(LoginActivity.this);
							SETTINGSMANAGER.SETTINGS.THANKYOU_MODE.putValue(LoginActivity.this, String.valueOf(true));
							LoginActivity.this.oTouchyCount = 0;
						}
						else
						{
							new foo().start();
						}
					}
				}
			});

			this.oUsePinLogin = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_PIN_LOGIN.getValue(this));

			this.oTxtDriverNo = (EditText) this.findViewById(R.id.frmStart_txtDriverNo);
			this.oTxtDriverNo.addTextChangedListener(new NoLeadingZerosTextWatcher(this.oTxtDriverNo));

			this.oTxtVehicleNo = (EditText) this.findViewById(R.id.frmStart_txtVehicleNo);
			this.oTxtPinNo = (EditText) this.findViewById(R.id.frmStart_txtPinNo);

			this.oProgressLogin = findViewById(R.id.frmStart_prgLogin);
			this.oProgressLogin.setVisibility(View.GONE);

			this.oBtnLogin = (ImageButton) this.findViewById(R.id.frmStart_btnLogin);
			this.oBtnLogin.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v)
				{
					LoginActivity.this.onInteraction();
					if(SETTINGSMANAGER.RESET_PENDING)
					{
						new Dialog_MsgBox(LoginActivity.this, "You need to reset your device before logging in").show();
					}
					else
					{
						LoginActivity.this.HideLoginButton(true);

						if(LoginActivity.this.oUsePinLogin)
						{


							String drvNo = LoginActivity.this.oTxtDriverNo.getText().toString();
							String vNo = LoginActivity.this.oTxtVehicleNo.getText().toString();
							String pinNo = LoginActivity.this.oTxtPinNo.getText().toString();

							if(drvNo.equals(""))
							{
								//pressed login with no driver number
								//reshow the view and ignore the press
								v.setVisibility(View.VISIBLE);
							}
							else
							{
								STATUSMANAGER.configPinLogin(LoginActivity.this, drvNo, vNo, pinNo);
								BROADCASTERS.Login(LoginActivity.this, true);
							}
						}
						else
						{
							BROADCASTERS.Login(LoginActivity.this, false);
						}

					}
				}
			});

			ImageButton btnMenu = (ImageButton) this.findViewById(R.id.frmStart_btnMenu);
			btnMenu.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					LoginActivity.this.onInteraction();
					LoginActivity.this.showMenu();
				}
			});


			if (STATUSMANAGER.getBoolean(this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION))
			{
				this.oBtnLogin.setVisibility(View.VISIBLE);
			}
			else
			{
				this.oBtnLogin.setVisibility(View.INVISIBLE);
			}

			//CLAY
			this.oBtnLogin.setVisibility(View.VISIBLE);

			TextView lblInfo = (TextView) this.findViewById(R.id.frmStart_lblInfo);

			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

			String IMEI = "", phoneNumber = "", networkName = "", simNo = "", description = "";
			try
			{
				//IMEI = telephonyManager.getDeviceId();
				phoneNumber = telephonyManager.getLine1Number();
				networkName = telephonyManager.getNetworkOperatorName();
				//simNo = telephonyManager.getSimSerialNumber();
				description = Build.MANUFACTURER + " " + Build.MODEL;
			}
			catch (SecurityException ex)
			{

			}


			String appVersion = "Unknown";

			appVersion = STATUSMANAGER.getExquisiteAppVersion(this);

			String appInfo = ("App Version: " + appVersion);

			if(SETTINGSMANAGER.SETTINGS.DATA_MODE.getValue(this).equals(CabDespatchNetworkOldSty._CONNECTION_TYPE.CLASSIC))
			{
				appInfo += "*";
			}

			if(!(this.oUsePinLogin)) appInfo += "\nDriver Number: " + SETTINGSMANAGER.get(this, SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN);

			appInfo+=("\n\n" + description +
					"\nDevice ID: " + IMEI +
					"\n\n" + SETTINGSMANAGER.get(this, SETTINGSMANAGER.SETTINGS.COMPANY_NAME));

			String signalRHost = SETTINGSMANAGER.getSignalRHost(this);

			Boolean fireData = SETTINGSMANAGER.SETTINGS.FIRE_DATA.parseBoolean(this);
			if(Globals.isDebug(this))
			{
				//appInfo += "\n\n***DEBUG BUILD***\n";
				if(signalRHost.equals(Settable.NOT_SET))
				{
					appInfo += (fireData?"FIREDATA - ":"IPDATA - ") + SETTINGSMANAGER.SETTINGS.IP_ADDRESS.getValue(this) + ":" + SETTINGSMANAGER.SETTINGS.PORT_NO.getValue(this);
				}
				else
				{
					appInfo += signalRHost;
				}

			}
			else
			{
				if(signalRHost.equals(Settable.NOT_SET))
				{
					appInfo += "\nTCP Data";
				}
				else
				{
					if(fireData)
					{
						appInfo += "\nFire Data";
					}
					else
					{
						appInfo += "\nProxy Data";
					}

				}
			}

			lblInfo.setText(appInfo);


			View layPinLogin = this.findViewById(R.id.frmStart_layoutPinLogin);

			if(this.oUsePinLogin)
			{
				layPinLogin.setVisibility(View.VISIBLE);
			}
			else
			{
				layPinLogin.setVisibility(View.GONE);
			}
			DataService.checkOutstandingDriverMessages(this);

		}
	}

    @Override
    public void onItemSelected(String _tag)
    {
        super.onItemSelected(_tag);
        if(_tag.equals(Globals.MENU_TAGS.EXIT))
        {
            Intent i = new Intent(this, Launcher.class);
            this.startActivity(i);
            finish();
        }
    }

	private void StartPayAndGoBalanceRequest()
	{
		DriverPayAndGoBalanceApi.DriverPayAndGoBalanceRequestArgs args = new DriverPayAndGoBalanceApi.DriverPayAndGoBalanceRequestArgs();
		args.auth.companyId = SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(this);
		args.driverCallSign = SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.getValue(this);

		DriverPayAndGoBalanceApi api = new DriverPayAndGoBalanceApi(API_TAG_GET_DRIVER_BALANCE, args,  this, isCabApiDebug());
		api.Go();
	}

    @Override
	public void onResume()
	{
	    super.onResume();
		ShowLoginButton();
	}

	private void ShowLoginButton()
	{
		oBtnLogin.setVisibility(View.VISIBLE);
		oProgressLogin.setVisibility(View.GONE);
	}

	private void HideLoginButton(boolean reshow)
	{
		oBtnLogin.setVisibility(View.GONE);
		oProgressLogin.setVisibility(View.VISIBLE);

		if(reshow)
		{
			class reshow extends PauseAndRun
			{

				@Override
				protected void onPostExecute(Void _void)
				{
					try
					{
						LoginActivity.this.ShowLoginButton();
					}
					catch (Exception ex)
					{
						//do nothing
					}
				}
			}
			new reshow().Start(1500 * 10);
		}
	}

	public void showMenu()
	{
		showMenu(this.aMenuBackground, false);
    }




	
	private void onInteraction()
	{
		this.oTimeOut.Reset();
	}

	@Override
	public void OnApiRequestProgress(int tag, double progress)
	{

	}

	@Override
	public void OnApiRequestComplete(int tag, CabApiRequest.AnyApiResult response)
	{
		ProcessGetBalanceApiRequest(tag, response);
	}

	private void ProcessGetBalanceApiRequest(final int tag, final CabApiRequest.AnyApiResult response)
	{
		class runny implements Runnable
		{

			@Override
			public void run()
			{
				//LayProgress.setVisibility(View.GONE);

				if(tag == API_TAG_GET_DRIVER_BALANCE)
				{
					HandleGetDriverBalanceResponse(response);
					return;
				}
			}
		}

		runOnUiThread(new runny());
	}

	private void SetAllTextViewsColor(ViewGroup root, int colourResourceId)
	{
		for (int i = 0; i < root.getChildCount(); i++) {
			View childView = root.getChildAt(i);
			if (childView instanceof TextView) {
				TextView textView = (TextView) childView;
				textView.setTextColor(ContextCompat.getColor(this, colourResourceId));
			}
		}
	}


	private void HandleGetDriverBalanceResponse(CabApiRequest.AnyApiResult response)
	{
		if(!( response.getResponseCode() == CabApiRequest.AnyApiResult.ResponseCodes.OK))
		{
			ErrorOutPayAndGo();
			return;
		}

		DriverPayAndGoBalanceApi.DriverPayAndGoBalanceResult result = (DriverPayAndGoBalanceApi.DriverPayAndGoBalanceResult) response.getResult();

		if(result.amountInLowestDenomination < 0)
		{
			//ErrorOutPayAndGo();
			//return;
		}

		int amount = result.amountInLowestDenomination;

		STATUSMANAGER.STATUSES.PAY_AND_GO_BALANCE.putValue(this, amount);
		//STATUSMANAGER.STATUSES.PAY_AND_GO_PAYMENT_INTENT_CLIENT_SECRET.putValue(this, result.clientSecret);
		//STATUSMANAGER.STATUSES.PAY_AND_GO_PAYMENT_INTENT_PUBLISHABLE_KEY.putValue(this, result.publishableKey);

		double pounds = amount / 100;

		TextView lbl = findViewById(R.id.lblCircuitBalance);

		Boolean animate = false;
		Boolean addClick = false;

		if(pounds > 0)
		{
			lbl.setText("£" + HandyTools.Strings.formatPrice(pounds));
			animate = true;
			addClick = true;
		}

		if(pounds == 0)
		{
			lbl.setText("No Outstanding");
		}

		if (pounds < 0)
		{
			lbl.setText("No Outstanding");
			if(isDebug())
			{
				addClick = true;
			}
		}

		final ViewGroup layout = findViewById(R.id.layPaymentPrompt);
		SetAllTextViewsColor(layout, R.color.colorAccent);

		if(addClick)
		{
			SetAllTextViewsColor(layout, R.color.White);
			layout.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					if(!(LoginActivity.this == null))
					{
						DriverPaymentActivity.StartPayAndGoPayment(LoginActivity.this);
					}
				}
			});
		}



		if(animate)
		{
			if(layout.getVisibility() == View.VISIBLE)
			{
				//do not slide in if already visible
				return;
			}

			YoYo.with(Techniques.SlideInLeft)
					.withListener(new Animator.AnimatorListener()
					{
						@Override
						public void onAnimationStart(Animator animation)
						{
							layout.setVisibility(View.VISIBLE);
						}

						@Override
						public void onAnimationEnd(Animator animation)
						{

						}

						@Override
						public void onAnimationCancel(Animator animation)
						{

						}

						@Override
						public void onAnimationRepeat(Animator animation)
						{

						}
					})
					.duration(1000)
					.playOn(layout);
			return;
		}

		if(layout.getVisibility() == View.VISIBLE)
		{
			//do not fade in if already visible
			return;
		}

		YoYo.with(Techniques.FadeIn)
				.withListener(new Animator.AnimatorListener()
				{
					@Override
					public void onAnimationStart(Animator animation)
					{
						layout.setVisibility(View.VISIBLE);
					}

					@Override
					public void onAnimationEnd(Animator animation)
					{

					}

					@Override
					public void onAnimationCancel(Animator animation)
					{

					}

					@Override
					public void onAnimationRepeat(Animator animation)
					{

					}
				})
				.duration(300)
				.playOn(layout);

		layout.setVisibility(View.VISIBLE);


	}



	private void ErrorOutPayAndGo()
	{
		DebugToast("Error getting circuit balance");
	}

	private void DebugToast(String message)
	{
		cdToast.showShort(this, message);
	}


	private class TimeOut extends Thread
    {
        final int oTimeoutMinutes = 5;
        Boolean cancelled = false;
        int oTimeRemaining;

        public TimeOut()
        {
            this.Reset();
        }

        @Override
        public void run()
        {
            Boolean active = true;

            while(active)
            {
                try
                {
                    Thread.sleep(1000);
                    this.oTimeRemaining -= 1000;

                    if (this.oTimeRemaining <= 0)
                    {
                        active = false;
                        if(!(this.cancelled))
                        {
                            if (!(STATUSMANAGER.isLoggedIn(LoginActivity.this)))
                            {
                                BROADCASTERS.Quit(LoginActivity.this);
                            }
                        }
                    }
                    else if (LoginActivity.this.oDebugTimeout)
                    {
                        STATUSMANAGER.setStatusBarText(LoginActivity.this, String.valueOf(this.oTimeRemaining));
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        public void finish()
        {
            this.cancelled = true;
            this.oTimeRemaining = 0;
        }

        public void Reset()
        {
            this.oTimeRemaining = (this.oTimeoutMinutes * 60 * 1000);
        }
    }
	
//	private Runnable timeOut = new Runnable() {
//		    public void run()
//		    {
//		    	if(!(STATUSMANAGER.isLoggedIn(LoginActivity.this)))
//		    	{
//		    		BROADCASTERS.Quit(LoginActivity.this);
//		    	}
//
//		    }
//		};

}
