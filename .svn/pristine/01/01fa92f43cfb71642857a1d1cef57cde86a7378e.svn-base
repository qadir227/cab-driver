package com.cabdespatch.driverapp.beta.activities;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BroadcastHandler;
import com.cabdespatch.driverapp.beta.DEBUGMANAGER;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.PauseAndRun;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.activities2017.DataMonitorActivity;
import com.cabdespatch.driverapp.beta.activities2017.DriverMessage;
import com.cabdespatch.driverapp.beta.activities2017.DriverPaymentActivity;
import com.cabdespatch.driverapp.beta.activities2017.LoginActivity;
import com.cabdespatch.driverapp.beta.activities2017.PermissionActivity;
import com.cabdespatch.driverapp.beta.activities2017.ViewMessages;
import com.cabdespatch.driverapp.beta.cabdespatchServiceDetectors;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_Busy;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_DataWarning;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_InitialSetup;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_InitialSetup.OnGoPressedListener;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_InstallCabLock;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._BUTTON;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._SHOWBUTTONS;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox_New;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_Password;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_Startup;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_Startup._STARTUPDIALOGRESULT;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_Update;
import com.cabdespatch.driverapp.beta.services.DataService;
import com.cabdespatch.driverapp.beta.services.FirebaseMessagingService;
import com.cabdespatch.libcabapiandroid.Apis.Activation.Activation;
import com.cabdespatch.libcabapiandroid.Apis.Activation.Checkin;
import com.cabdespatch.libcabapiandroid.Apis.AnyApiRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.joda.time.DateTime;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
import com.sumup.merchant.api.SumUpAPI;
import com.sumup.merchant.api.SumUpPayment;
import com.sumup.merchant.api.SumUpState;
*/

public class Launcher extends AnyActivity implements Dialog_Update.OnUpdatePackageDownloadListener, Dialog_DataWarning.OnDataWarningConfirmCompleteListener, Dialog_MsgBox_New.OnMessageBoxButtonListener, OnFailureListener, Dialog_Startup.StartupDialogResultListener, AnyApiRequest.AnyApiListener
{

        private int statusCheckRetryCount = 0;
        private boolean isDefaultLauncher = false;

        private static final int REQUEST_CODE_DO_PERMISSIONS = 91;
        private static final int REQUEST_OVERLAY_PERMISSIONS = 92;

    @Override
    public void OnConfirmComplete()
    {
        //don't launch 'reallyStartLoginActivity()'... run the whole check again...
        StartLoginActivity();
    }

    @Override
    public void MessageBoxOKPressed()
    {
        SETTINGSMANAGER.SETTINGS.ERROR_MESSAGE.reset(this);
    }

    @Override
    public void CountdownFinished()
    {
        //no nothing
    }

//    //firebase instance id...
//    @Override
//    public void onSuccess(InstanceIdResult instanceIdResult)
//    {
//        String token = instanceIdResult.getToken();
//        FirebaseMessagingService.updateToken(this, token);
//    }

    @Override
    public void onFailure(Exception e)
    {
        //cdToast.showShort(this, e.toString());
    }

    @Override
    public void OnStartupDialogResult(final _STARTUPDIALOGRESULT result, final String message, final Exception ex)
    {
        class ThradSafteyIsStupid implements Runnable
        {

            @Override
            public void run()
            {
                Launcher.this.OnStartupDialogResultAction(result, message, ex);
            }
        }

        runOnUiThread(new ThradSafteyIsStupid());
    }

    private void OnStartupDialogResultAction(_STARTUPDIALOGRESULT result, String message, Exception ex)
    {
        DEBUGMANAGER.Log(Launcher.this, "DIALOG RESULT", result.toString());
        switch (result)
        {

            case STORAGE_NOT_READABLE:
            case STORAGE_NOT_WRITABLE:
                new Dialog_MsgBox(Launcher.this, "Device storage issue", getString(R.string.error_storage_issue), _SHOWBUTTONS.OK).show();
                break;
            case COULD_NOT_CREATE_FOLDER:
            case COULD_NOT_CREATE_SOUND_FOLDERS:
            case COULD_NOT_GET_MESSAGES:
            case COULD_NOT_GET_SETTINGS_FILE:
            case COULD_NOT_GET_ZONE_FILE:
            case COULD_NOT_WRITE_MESSAGES:
            case COULD_NOT_WRITE_SETTINGS_FILE:
            case COULD_NOT_WRITE_ZONE_FILE:
                new Dialog_MsgBox(Launcher.this, "Could not start Cab Despatch", "Could not download vital files requires to start Cab Despatch Android. Please check that you have internet connectivity, and that your storage is not mounted to a computer or otherwise busy", _SHOWBUTTONS.OK).show();
                break;
            case COULD_NOT_CREATE_ZONE_SOUNDS_FOLDER:
            case COULD_NOT_EXTRACT_ZONE_SOUNDS:
            case COULD_NOT_GET_SOME_SOUND_FILES:
            case COULD_NOT_GET_ZONE_SOUNDS:
				/*
				 * No longer needed - using text-to-speach engine
				 *
				 * Dialog_MsgBox dlgdatamissing;
					dlgdatamissing = new Dialog_MsgBox(Launcher.this, "Some data missing", "Certian data which can enhance the performace of Cab Despatch Android could not be downloaded. Cab Despatch Android will attempt to download them next time it is launched.", Launcher.this.oGlobals.getCurrentTheme(), _SHOWBUTTONS.OK);
					dlgdatamissing.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
					{

						@Override
						public void ButtonPressed(com.cabdespatch.driverapp.dialogs.Dialog_MsgBox._BUTTON _button)
						{
							Launcher.this.StartLoginActivity();

						}
					});
					// dlgdatamissing.show();*/

                Launcher.this.StartLoginActivity();
                break;
            case UPDATE_REQUIRED:
                if (!(SETTINGSMANAGER.RESET_PENDING))
                {
                    Dialog_MsgBox dlgupdatereq;
                    if (Launcher.this.oLockdown)
                    {
                        dlgupdatereq = new Dialog_MsgBox(Launcher.this, "An update is required before you can launch Cab Despatch Android. Please see the office.");
                    }
                    else
                    {
                        dlgupdatereq = new Dialog_MsgBox(Launcher.this, "Update Required", "This version of Cab Despatch Android can no longer be used. Would you like to update to the latest version? (choosing 'no' will return you to the launcher)", _SHOWBUTTONS.YESNO);
                        dlgupdatereq.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                        {

                            @Override
                            public void ButtonPressed(_BUTTON _button)
                            {
                                if (_button == _BUTTON.YES)
                                {
                                    Globals.CrossFunctions.UpdateApp(Launcher.this);
                                }

                            }
                        });
                    }

                    dlgupdatereq.show();
                }
                break;
            case UPDTAE_AVAILABLE:
                if (!(SETTINGSMANAGER.RESET_PENDING))
                {
                    Dialog_MsgBox dlgupdateavail;
                    if (Launcher.this.oLockdown)
                    {
                        //let Socket Server deal with optional updates if devices are locked down...
                        //
                        dlgupdateavail = new Dialog_MsgBox(Launcher.this, "There is an update available. Please contact the office if you would like to update.");
                        dlgupdateavail.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                        {
                            @Override
                            public void ButtonPressed(_BUTTON _button)
                            {
                                Launcher.this.StartLoginActivity();
                            }
                        });
                    }
                    else
                    {
                        dlgupdateavail = new Dialog_MsgBox(Launcher.this, "Update Available", "There is an update available. Would you like to download it now?", _SHOWBUTTONS.YESNO);
                        dlgupdateavail.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                        {

                            @Override
                            public void ButtonPressed(_BUTTON _button)
                            {
                                if (_button == _BUTTON.YES)
                                {
                                    Globals.CrossFunctions.UpdateApp(Launcher.this);
                                }
                                else
                                {
                                    Launcher.this.StartLoginActivity();
                                }

                            }
                        });
                    }
                    dlgupdateavail.show();

                }
                break;
            case BILLING_VERIFICATION_REQUIRED:
            case RE_REGISTRATION_REQUIRED:
                Dialog_MsgBox dlgverify;
                String verifymessage;
                if (result == _STARTUPDIALOGRESULT.BILLING_VERIFICATION_REQUIRED)
                {
                    verifymessage = "This device has not yet been registered with Cab Despatch. Would you like to register it now? Registering this device will result in its use being added to your monthly bill.";
                }
                else
                {
                    verifymessage = "This device has previously been registered but is not currently active, so is not being billed for. Would you like to re-activate this device and add it to your monthly bill?";
                }
                dlgverify = new Dialog_MsgBox(Launcher.this, "Register Device For Billing", verifymessage, _SHOWBUTTONS.YESNO);
                dlgverify.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
                {

                    @Override
                    public void ButtonPressed(_BUTTON _button)
                    {
                        if (_button == _BUTTON.YES)
                        {

                            Dialog_Password dlgPassword = new Dialog_Password(Launcher.this, SETTINGSMANAGER.get(Launcher.this, SETTINGSMANAGER.SETTINGS.COMPANY_ID), "Activate Device for Billing");
                            dlgPassword.setOnGoPressedListener(new Dialog_Password.OnGoPressedListener()
                            {

                                @Override
                                public void goPressed(String _companyID, String _password)
                                {
                                    Launcher.this.RegisterForBilling(_companyID, SETTINGSMANAGER.get(Launcher.this, SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN), _password);
                                }
                            });
                            dlgPassword.show();
                        }
                    }
                });
                dlgverify.show();
                break;
            case COULD_NOT_VERIFY_DEVICE:
                if (Launcher.this.statusCheckRetryCount++ > 5)
                {
                    Launcher.this.statusCheckRetryCount = 0;
                    new Dialog_MsgBox(Launcher.this, "Could Not Verify Device", "We were unable to verify your device. Please check that you have internet connectivity and try again", _SHOWBUTTONS.YESNO).show();
                }
                else
                {
                    String[] cmpIdAndDriver = message.split("~");
                    new Dialog_Startup(Launcher.this, 3000, cmpIdAndDriver[0], cmpIdAndDriver[1], Launcher.this).show();
                }

                break;
            case PASS:
                Launcher.this.StartLoginActivity();
                break;

        }
    }

    @Override
    public void OnApiRequestProgress(int tag, double progress)
    {

    }

    @Override
    public void OnApiRequestComplete(int tag, AnyApiRequest.AnyApiResult response)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                HandleApiResponse(tag, response);
            }
        });
    }

    public void HandleApiResponse(int tag, AnyApiRequest.AnyApiResult response)
    {
        oDlgBusy.hide();

        if(response.Failed())
        {
            new Dialog_MsgBox(Launcher.this, "Could Not Verify Device", "We were unable to verify your device. Please check that you have internet connectivity and try again", _SHOWBUTTONS.OK).show();
            return;
        }

        if(tag==TAG_ACTIVATION)
        {
            Activation.PdaActivationResponse item = (Activation.PdaActivationResponse) response.getResult();
            if(item.activationStatus == Activation.PdaActivationResponse.STATUS_ACTIVE)
            {
                STATUSMANAGER.STATUSES.ACTIVATION_KEY.putValue(this, item.deviceKey);
                new Dialog_MsgBox(Launcher.this, "Device Activated", "Your device has been activated. You may now start Cab Despatch", _SHOWBUTTONS.OK).show();
                return;
            }

            new Dialog_MsgBox(Launcher.this, "Could Not Verify Device", "We were unable to verify your device. Please check that you have internet connectivity and try again", _SHOWBUTTONS.OK).show();
            return;

        }

    }

    private class StatusChecker extends BroadcastHandler
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Launcher.this.updateStatusIconsAndText();
        }
    }

    private StatusChecker statuschecker;

    Boolean oStatusCheckerRegistered = false;
    private void registerStatusChecker()
    {
        IntentFilter inf = new IntentFilter();
        inf.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        inf.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        this.statuschecker = new StatusChecker();
        this.registerReceiver(this.statuschecker, inf);
        oStatusCheckerRegistered = true;
    }
    private void unregisterStatusChecker()
    {
        if(oStatusCheckerRegistered)
        {
            this.unregisterReceiver(this.statuschecker);
            oStatusCheckerRegistered = false;
        }
    }

    private Dialog_Busy oDlgBusy;
    //private Globals oGlobals;
    Boolean oGPSEnabled;
    Boolean oIsDemo;
    //Boolean oBackLock = true;
    Boolean oLockdown = false;
    int oTouchyCount;

    private TextView iLblGPS_State;
    private ImageView iImgGPSState;
    private TextView iLblNetworkState;
    private ImageView iImgNetworkState;

    private LinearLayout iBtnLaunch;

    private String oIntentID;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.statusCheckRetryCount = 0;
        if(SETTINGSMANAGER.getLockDownMode(this).equals(SETTINGSMANAGER.LOCK_DOWN_MODES.KIT_KAT))
        {
            /*Intent i = new Intent(this, UiBlockerService.class);
            this.startService(i);*/
        }

        //ErrorActivity.handleError(this, new ErrorActivity.ERRORS.TEST_ERROR("tag1", "some detail here"));
        //Debug.waitForDebugger();

    }



    private boolean isMyLauncherDefault() {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);

        List<IntentFilter> filters = new ArrayList<IntentFilter>();
        filters.add(filter);

        final String myPackageName = getPackageName();
        List<ComponentName> activities = new ArrayList<ComponentName>();
        final PackageManager packageManager = (PackageManager) getPackageManager();

        // You can use name of your package here as third argument
        packageManager.getPreferredActivities(filters, activities, null);

        for (ComponentName activity : activities) {
            if (myPackageName.equals(activity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        String firebaseID = STATUSMANAGER.getFirebaseInstanceId(this);

        if(firebaseID.equals(STATUSMANAGER.getEmptyFirebaseId()))
        {
            //FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this).addOnFailureListener(this);
        }

        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        this.isDefaultLauncher = isMyLauncherDefault();

        this.oLockdown = (!(SETTINGSMANAGER.getLockDownMode(this).equals(SETTINGSMANAGER.LOCK_DOWN_MODES.NONE)));
        if(this.oLockdown)
        {
            STATUSMANAGER.putBoolean(this, STATUSMANAGER.STATUSES.ALLOW_LOCAL_SMS, true);
        }
        else
        {
            STATUSMANAGER.putBoolean(this, STATUSMANAGER.STATUSES.ALLOW_LOCAL_SMS, false);
        }

        Boolean proceedToPermissionCheck = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!Settings.canDrawOverlays(this))
            {
                cdToast.setTempBottomGravity();
                cdToast.showLong(this, R.string.prompt_enable_screen_draw);
                proceedToPermissionCheck = false;
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSIONS);
            }
        }

        if(proceedToPermissionCheck)
        {
            if(PermissionActivity.beginPermissionsCheck(this, REQUEST_CODE_DO_PERMISSIONS))
            {
                //we're doing some permission checks...
            }
            else
            {
                if(passLockdownCheck())
                {
                    doResume();
                }
            }
        }

    }

    public void doResume()
    {

        STATUSMANAGER.setAppState(this, STATUSMANAGER.APP_STATE.LAUNCHER);
        //DataService.Initialize(this);

        this.reset();
        //this.oBackLock = true;

        registerStatusChecker();

        Boolean flightModeHack = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.FLIGHT_MODE_HACK.getValue(this));
        if(flightModeHack)
        {
            //turn on the radio
            //not really needed here, but it's a good way to force
            //the SU Allow prompt on launch after an update
            STATUSMANAGER.setFlightMode(this, false);
        }



        this.oIntentID = "";
        this.oIsDemo = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.IS_DEMO_MODE.getValue(this));

        Intent i = this.getIntent();

        if(!(i.getAction()==null))
        {
            if(i.getAction().equals(Intent.ACTION_VIEW))
            {
                if(this.oIsDemo)
                {
                    try
                    {
                        String[] data = HandyTools.Strings.fromUri(i.getData()).split(" ");
                        this.oIntentID = data[0].trim();
                        STATUSMANAGER.putString(this, STATUSMANAGER.STATUSES.STORED_PASSWORD, data[1].trim());
                    }
                    catch (Exception ex)
                    {
                        cdToast.showLong(this, "There was an error processing the file");
                    }
                }
                else
                {
                    cdToast.showLong(this, "Error");
                }

            }
        }

        this.oDlgBusy = new Dialog_Busy(this);

        this.setContentView(R.layout.activity_launchermain);
        ConfigureButtonTest(DataService.NETWORK_DEBUG);
        this.setBackground();

        String _errorMessage = SETTINGSMANAGER.SETTINGS.ERROR_MESSAGE.getValue(this);
        if(!(_errorMessage.equals(Settable.NOT_SET)))
        {
            new Dialog_MsgBox_New(this, R.drawable.big_icon, (ViewGroup) this.findViewById(R.id.dlg_container), "Error Recovery", _errorMessage, this).show();
        }

        //CLAY u are an f-in idiot for
        //previously putting this BEFORE setting the content view


        if(isDeveloperEdition())
        {
            //this is developer edition
            String iCompanyID = SETTINGSMANAGER.get(this, SETTINGSMANAGER.SETTINGS.COMPANY_ID);
            if(iCompanyID.equals(SETTINGSMANAGER.SETTINGS.COMPANY_ID.getDefaultValue()))
            {
                //do nothing
            }
            else
            {
                //we've already been set up for one company
                //show the "change company" button
                ViewGroup layChangeCompnay = (ViewGroup) this.findViewById(R.id.launcher_btnSetCompanyId);
                layChangeCompnay.setVisibility(View.VISIBLE);

                TextView lblCompanyID = (TextView) this.findViewById(R.id.lblCurrentCompanyId);
                lblCompanyID.setText(SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(this));
            }
        }


        this.iBtnLaunch = (LinearLayout) this.findViewById(R.id.launcher_btnLaunchCabDespatch);
        this.disableMenuItem(this.iBtnLaunch, 100, true);

        iLblGPS_State = (TextView) findViewById(R.id.launcher_lblGPS);
        iImgGPSState = (ImageView) this.findViewById(R.id.launcher_icoGPSState);

        iImgGPSState.setOnClickListener(new View.OnClickListener()
        {

            class foo extends Thread
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(2000);
                        Launcher.this.oTouchyCount = 0;
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
                if (Launcher.this.oTouchyCount++ == 3)
                {
                    Launcher.this.showOverlayMenu();
                    Launcher.this.oTouchyCount = 0;
                }
                else
                {
                    new foo().start();
                }

            }
        });


        iLblNetworkState = (TextView) findViewById(R.id.launcher_lblNetwork);
        iImgNetworkState = (ImageView) this.findViewById(R.id.launcher_icoNetState);


        this.updateStatusIconsAndText();


        ImageButton iBtnNetworkState = (ImageButton) findViewById(R.id.launcher_btnWiFi_Staus);
        ImageButton iBtnGPS = (ImageButton) findViewById(R.id.launcher_btnGPS_Staus);

        if(this.oLockdown)
        {
            iBtnNetworkState.setVisibility(View.GONE);
            iBtnGPS.setVisibility(View.GONE);

        }
        else
        {

            LinearLayout l = (LinearLayout) findViewById(R.id.launcher_btnManageApps);
            l.setVisibility(View.VISIBLE);
            l = (LinearLayout) findViewById(R.id.launcher_btnDialPhone);
            l.setVisibility(View.VISIBLE);

        }




        if (!(SETTINGSMANAGER.navigationAvailable(this)))
        {
            LinearLayout btnNavigate = (LinearLayout) this.findViewById(R.id.launcher_btnNavigate);
            btnNavigate.setVisibility(View.GONE);
        }


        STATUSMANAGER.setStatusBarText(this, "");

        if(!(this.oIntentID.equals("")))
        {
            new Dialog_InitialSetup(this, this.oIntentID,"",DialogInitialSetup_GO_Pressed()).show();
        }

        TextView lblVersionNumber = (TextView) this.findViewById(R.id.launcher_lblVersionNumber);
        String appVersion = "Unknown";
        String installDate = "UNKNOWN";

        appVersion = STATUSMANAGER.getExquisiteAppVersion(this);
        installDate = STATUSMANAGER.getInstallDate(this);


        String appInfo = ("App Version: " + appVersion + "\n" + "Installed: " + installDate);
        //appInfo = appVersion + ": " + getString(R.string.broadcast_activity_switch);
        lblVersionNumber.setText(appInfo);
        hideOverlayMenu();
    }


    @Override
    public void onPause()
    {
        //FareMeterService.finish();

        super.onPause();
        unregisterReceiver(onDownloadComplete);

        this.setIntent(new Intent(this, Launcher.class));
        unregisterStatusChecker();
    }

    @Override
    public final void onBackPressed()
    {
        if(this.overlayMenuShowing)
        {
            hideOverlayMenu();
        }
        else
        {
            if(this.oLockdown)
            {
                //do nothing
            }
            else
            {
                if(isDefaultLauncher)
                {
                    //do nothing
                }
                else
                {
                    this.finish();
                }
                /*
                if(this.oBackLock)
                {
                    cdToast.showShort(this, "Application has not finished loading. Please wait a few seconds and try again", "Quit Disabled");
                }
                else
                {
                    this.finish();
                }*/
            }
        }

    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
            showOverlayMenu();
            return true;
        }
        else
        {
            return super.onKeyUp(keyCode, event);
        }

    }

    private Boolean overlayMenuShowing = false;
    private void showOverlayMenu()
    {
        overlayMenuShowing = true;
        View v = findViewById(R.id.layMenu);
        v.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Launcher.this.hideOverlayMenu();
            }
        });
        v.setVisibility(View.VISIBLE);
        v.setBackgroundResource(R.color.TransparentBlack);
    }

    private void hideOverlayMenu()
    {
        overlayMenuShowing = false;
        View v = findViewById(R.id.layMenu);
        v.setVisibility(View.INVISIBLE);
    }

    public void overlayMenuItemSelected(View _v)
    {

        hideOverlayMenu();

        Boolean needPassword = true;
        Boolean hasDebugConnector = STATUSMANAGER.getBoolean(this, STATUSMANAGER.STATUSES.HAS_DEBUG_CONNECTOR);

        if(hasDebugConnector)
        {
            DateTime d = new DateTime(STATUSMANAGER.getString(this, STATUSMANAGER.STATUSES.LAST_ENGINEER_SESSION)).withTimeAtStartOfDay();
            if (d.equals(new DateTime().withTimeAtStartOfDay()))
            {
                //we've looged in today already
                needPassword = false;
            }
        }
        else
        {
            needPassword = true;
        }

        //CLAY
        //needPassword = true;

        String launchactivity = "";
        switch(_v.getId())
        {
            case R.id.menu_option_engineer_mode:
                launchactivity = Password.REASON_ENGINEER_MODE;
                break;
            case R.id.menu_option_manager_mode:
                launchactivity = Password.REASON_MANAGER_MODE;
                if(hasDebugConnector) { needPassword=false; }
                break;
        }

        Intent i;

        if(needPassword)
        {
            i = new Intent(this, Password.class);
            i.putExtra(Password.EXTRA_LAUNCH_REASON, launchactivity);
        }
        else
        {
            if(launchactivity.equals(Password.REASON_MANAGER_MODE))
            {
                i = new Intent(this, ManagerModeMenu.class);
            }
            else
            {
                i = new Intent(this, EngineerModeMenu.class);
            }
        }

        Globals.StartActivity(this, i);
    }

    private void disableMenuItem(final LinearLayout _item, Integer _delay, final Boolean _onlyReenableIfInternetConnectionAvailable)
    {
        configMenuItem(_item, false);

        new PauseAndRun()
        {
            @Override
            protected void onPostExecute(Void _void)
            {
                Boolean reEnable = true;
                if(_onlyReenableIfInternetConnectionAvailable)
                {
                    switch(cabdespatchServiceDetectors.getNetworkConnectionType(Launcher.this))
                    {
                        case GPRS3G:
                        case WIFI:
                            reEnable = true;
                            break;
                        case NONE:
                        case NULL:
                            reEnable = false;
                            break;
                    }
                }
                if(reEnable)
                {
                    configMenuItem(_item, true);
                }

            }
        }.Start(_delay);
    }

    private void configMenuItem(LinearLayout _item, Boolean _clickable)
    {

        _item.setClickable(_clickable);
        if(_clickable)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            {
                _item.setAlpha(1);
            }
            else
            {
               _item.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            {
                _item.setAlpha(0.6f);
            }
            else
            {
                _item.setVisibility(View.VISIBLE);
            }
        }

    }

    public void startLaunch()
    {
        disableMenuItem(this.iBtnLaunch, 10000, true);
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            if(STATUSMANAGER.hasConflictingPackages(this))
            {
                Dialog_MsgBox d = new Dialog_MsgBox(this, "You still have a beta version of this application installed. This must be uninstalled before you continue. If you are unsure how to do this then please contact your Cab Despatch representative");
                d.show();
            }
            else
            {
                String iCompanyID = SETTINGSMANAGER.get(this, SETTINGSMANAGER.SETTINGS.COMPANY_ID);
                if(iCompanyID.equals(SETTINGSMANAGER.SETTINGS.COMPANY_ID.getDefaultValue()))
                {
                    new Dialog_InitialSetup(this, "", "", DialogInitialSetup_GO_Pressed()).show();
                }
                else
                {
                    String iDriverNo = SETTINGSMANAGER.get(this, SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN);
                    if(iDriverNo.startsWith("*"))
                    {
                        iDriverNo = iDriverNo.replace("*", "");
                        new Dialog_InitialSetup(this, iCompanyID, iDriverNo, DialogInitialSetup_GO_Pressed()).show();
                    }
                    else
                    {
                        new Dialog_Startup(this, 0, iCompanyID, iDriverNo, this).show();
                    }
                }
            }
        }
        else
        {
            Dialog_MsgBox d = new Dialog_MsgBox(this, getString(R.string.error_storage_issue));
            d.show();
        }
    }

    public void btnLaunch_Click(View v)
    {
        startLaunch();
    }


    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);;
        if(data==null)
        {
            //do nothing
        }
        else
        {
            if(requestCode==REQUEST_OVERLAY_PERMISSIONS)
            {
                if (Settings.canDrawOverlays(this))
                {
                    //recreate();
                }
                else
                {
                    cdToast.showLong(this, getString(R.string.permission_failed_quit_message_screen_draw));
                    finish();
                }

            }
            if(requestCode==REQUEST_CODE_DO_PERMISSIONS)
            {
                String errorMessage = data.getStringExtra(PermissionActivity.PERMISSION_REQUEST_ERROR_MESSAGE);
                if(errorMessage.equals(PermissionActivity.ERROR_MESSAGE_NO_ERROR))
                {
                    //do nothing...
                }
                else
                {
                    cdToast.showLong(this, errorMessage);
                    finish();
                }

            }
        }

    }

        public void btnNavigate_Click(View v)
    {
        Globals.CrossFunctions.startNavigate(this);
    }

    public void btnManageApps_Click(View v)
    {
        Intent intentSettings = new Intent();
        intentSettings.setAction(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
        intentSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentSettings);
    }

    public void btnPhoneDialler_Click(View v)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void btnSetCompany_Click(View _v)
    {
        //new Dialog_InitialSetup(Launcher.this, "", "", onGoPressed()).show();
        SETTINGSMANAGER.reset(Launcher.this, true);
        Launcher.this.startLaunch();
    }

    public void btnWifi_Click(View v)
    {
        //DriverDocumentsApi.DriverDocumentRequestArgs args = new DriverDocumentsApi.DriverDocumentRequestArgs();
    }

    public void btnGPS_Click(View v)
    {
        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void cablockFatalError()
    {
        cdToast.showLong(this, R.string.cablock_fatal_error);
        finish();
    }

    public boolean passLockdownCheck()
    {
        if(SETTINGSMANAGER.getLockDownMode(this).equals(SETTINGSMANAGER.LOCK_DOWN_MODES.KIT_KAT))
        {
            boolean passValue = false;

            int lockerPresentState = 0; // 0 = not installed

            Cursor c = getContentResolver().query(Uri.parse("content://cabdespatch.com.cablocker.ConfigProvider/anytype"), null, null, null, null);
            if(c==null)
            {
                //do nothing
            }
            else
            {
                HashMap<String, String> allValues = new HashMap<>();
                while (c.moveToNext())
                {
                    allValues.put(c.getString(0), c.getString(1));
                }

                if(allValues.containsKey("enabled"))
                {
                    Boolean enabled = Boolean.valueOf(allValues.get("enabled"));
                    lockerPresentState = (enabled?20:10); //20 = fully configred, 10 == installed but not configured
                }
                else
                {
                    lockerPresentState = -1; //error
                }
            }

            String debugMessage = "";
            if(lockerPresentState<0)
            {
                debugMessage = "SQL Error";
                cablockFatalError();
            }
            else if(lockerPresentState==0)
            {
                installCabLock();
                debugMessage = "Locker Not Installed";
            }
            else if(lockerPresentState==10)
            {
                debugMessage = "Further Configuration Required";
                try
                {
                   startActivity(getPackageManager().getLaunchIntentForPackage("cabdespatch.com.cablocker"));
                }
                catch (Exception ex)
                {
                    cablockFatalError();
                }

            }
            else if(lockerPresentState==20)
            {
                debugMessage = "Locker Fully Configured";
                Intent intent = new Intent();
                intent.setClassName("cabdespatch.com.cablocker", "cabdespatch.com.cablocker.UiLocker");

                intent.putExtra("com.cabdespatch.blockercolour", getResources().getColor(R.color.colorPrimaryDark));

                startService(intent);

                passValue = true;
            }

            //Toast.makeText(this, debugMessage, Toast.LENGTH_LONG).show();


            return passValue;
        }
        else
        {
            return true;
        }


    }

    public void btnTest_Click(View v)
    {

        Intent i = new Intent(this, ViewMessages.class);
        startActivity(i);

    }

    private void ConfigureButtonTest(Boolean doShow)
    {

        ViewGroup button = findViewById(R.id.launcher_btnTest);
        button.setVisibility(View.GONE);

        if(doShow)
        {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Launcher.this.btnTest_Click(view);
                }
            });
        }
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (id == Dialog_InstallCabLock.DOWNLOAD_ID)
            {
                Dialog_InstallCabLock.showDownloads(context);
            }
        }
    };

    private void installCabLock()
    {
        FrameLayout f = new FrameLayout(this);
        setContentView(f);
        new Dialog_InstallCabLock(this, f).show();
    }


    private OnGoPressedListener DialogInitialSetup_GO_Pressed()
    {
        return new OnGoPressedListener()
        {

            @Override
            public void goPressed(String _driverNo, String _companyID)
            {
                SETTINGSMANAGER.reset(Launcher.this, false);
                new Dialog_Startup(Launcher.this, 0, _companyID, _driverNo, Launcher.this).show();

            }

        };

    }
    private static final int TAG_ACTIVATION = 109;

    private void RegisterForBilling(String _companyId, String _driverNo, String _password)
    {
//public static Activation Obtain(int tag, String companyId, String deviceIdentifier, String deviceDescription, String webPass, double appVersion,  AnyApiRequest.AnyApiListener listener)
        //return Globals.activateDevice(Launcher.this, i_companyId, i_driverNo, i_password);
        oDlgBusy.show();
        String deviceIdentifier =  Globals.CrossFunctions.getDeviceIdentifier_wasIMEI(this);
        String deviceDescription = Build.MANUFACTURER + " " + Build.MODEL;
        Activation activation = Activation.Obtain(TAG_ACTIVATION, _companyId, deviceIdentifier, deviceDescription, _password, Double.valueOf(STATUSMANAGER.getAppVersionNumber(this)), this);
        activation.Go();

//        class doActivate extends AsyncTask<String, Integer, Boolean>
//        {
//
//
//            @Override
//            protected void onPreExecute()
//            {
//
//                Launcher.this.oDlgBusy.show();
//            }
//
//            @Override
//            protected Boolean doInBackground(String... arg)
//            {
//                String i_companyId = arg[0];
//                String i_driverNo = arg[1];
//                String i_password = arg[2];
//
//                return Globals.activateDevice(Launcher.this, i_companyId, i_driverNo, i_password);
//            }
//
//            @Override
//            protected void onPostExecute(Boolean success)
//            {
//                Launcher.this.oDlgBusy.cancel();
//
//                if(success)
//                {
//                    Dialog_MsgBox dlgmsg = new Dialog_MsgBox(Launcher.this, "Device Activated","Your device has been activated. You may now launch Cab Despatch.",  _SHOWBUTTONS.OK);
//                    dlgmsg.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
//                    {
//
//                        @Override
//                        public void ButtonPressed(_BUTTON _button)
//                        {
//                            //Launcher.this.StartLoginActivity();
//                        }
//                    });
//                    dlgmsg.show();
//                }
//                else
//                {
//                    Dialog_MsgBox dlgmsg = new Dialog_MsgBox(Launcher.this, "Device Not Activated","Your device could not be activated. If this message persists please contact support",  _SHOWBUTTONS.OK);
//                    dlgmsg.show();
//                }
//            }
//
//        }
//
//        new doActivate().execute(_companyId, _driverNo, _password);

    }



    private void StartLoginActivity()
    {
        DEBUGMANAGER.Log(this, "SLI", "Start Login Activity");

        if(cabdespatchServiceDetectors.areMockLocationsEnabled(this))
        {
            Dialog_MsgBox mock = new Dialog_MsgBox(this, "Mock Locations Enabled", "You have mock locations enabled. You must disable this setting to run CabDespatch",  _SHOWBUTTONS.OK);
            mock.show();
        }
        else if((this.oGPSEnabled)||(this.oIsDemo))
        {
            //this.reallyStartLoginActivity();
            this.checkDataWarningVersion();
        }
        else
        {
            Boolean gpsRequired = SETTINGSMANAGER.SETTINGS.REQUIRE_GPS.parseBoolean(this);
            if(gpsRequired)
            {
                Dialog_MsgBox gps = new Dialog_MsgBox(this, "GPS Not Enabled", "You must have GPS enabled to launch CabDespatch",  _SHOWBUTTONS.OK);
                gps.show();
            }
            else
            {
                this.checkDataWarningVersion();
            }

        }

    }

    private void checkDataWarningVersion()
    {
        DEBUGMANAGER.Log(this, "SLI", "Check Data Warning Version");
        Integer currentWarningAcceptVersion = SETTINGSMANAGER.SETTINGS.DATA_WARNING_ACCEPT_VERSION.parseInteger(this);
        if(currentWarningAcceptVersion > SETTINGSMANAGER.CURRENT_DATA_WARNING_VERSION)
        {
            //we've messed about in testing most likely, reset to current version
            SETTINGSMANAGER.SETTINGS.DATA_WARNING_ACCEPT_VERSION.putValue(this, SETTINGSMANAGER.CURRENT_DATA_WARNING_VERSION);
        }

        if(currentWarningAcceptVersion.equals(SETTINGSMANAGER.CURRENT_DATA_WARNING_VERSION))
        {
           reallyStartLoginActivity();
        }
        else
        {
            //they've either never accepted, or have only accepted a previous version of
            //the data warning
            Dialog_DataWarning dlg = new Dialog_DataWarning(this, this);
            dlg.show();
        }
    }

    private void reallyStartLoginActivity()
    {
        DEBUGMANAGER.Log(this, "SLI", "Really Start Login Activity");
        STATUSMANAGER.Reset(this);
        STATUSMANAGER.setAppState(this, STATUSMANAGER.APP_STATE.LOGGED_OFF);

        this.statusCheckRetryCount = 0;

        //DataService.Quit = false; //the app is running and the user has not requested an exit
        /*DataService.StartDataService(this);

        if (STATUSMANAGER.getAppState(this).equals(STATUSMANAGER.APP_STATE.LOGGED_ON))
        {
            DEBUGMANAGER.Log(this, "SLI", "Show Driver Screen");
            BROADCASTERS.SwitchActivity(this,_ACTIVITIES.DRIVER_SCREEN);
        }
        else
        {
            DEBUGMANAGER.Log(this, "SLI", "Show Login Screen");
            BROADCASTERS.SwitchActivity(this,_ACTIVITIES.LOGIN_SCREEN);
        }*/

        if(isDeveloperEdition() && false)
        {
             Intent u = new Intent(Launcher.this, DataMonitorActivity.class);
             this.startActivity(u);
        }
        else
        {
            Intent i = new Intent(Launcher.this, LoginActivity.class);
            this.startActivity(i);
        }

    }


    private Globals.OnGlobalErrorHandler OnGlobalsError()
    {
        return new Globals.OnGlobalErrorHandler()
        {

            @Override
            public void OnError(String _error, Exception _ex)
            {
                DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR,"GLOBALS ERROR", _error);

                if(_ex==null)
                {
                    DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR, "GLOBALS ERROR", "***No Exception Availabe***");
                    DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR,"GLOBALS ERROR", "***************************");
                }
                else
                {
                    DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR,"GLOBALS ERROR", "***Exception Follows***");
                    DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR,"GLOBALS ERROR", _ex.toString());
                    DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_ERROR,"GLOBALS ERROR", "***********************");
                }

            }
        };
    }

    private void reset()
    {
        STATUSMANAGER.Reset(this);

    }

    @Override
    public void onUpdatePackageDownloadComplete()
    {
        cdToast.showShort(this, "Update Downloaded");
    }

    @Override
    public void onUpdatePackageDownloadFailed(FAIL_REASON _reason)
    {
        cdToast.showShort(this, "Update Failed");
    }
    @Override
    public void onUpdatePackageDownloadCancelled()
    {
        cdToast.showShort(this, "Update Cancelled");
    }

    private void updateStatusIconsAndText()
    {
        this.oGPSEnabled = cabdespatchServiceDetectors.isGPSEnabled(this);

        if (this.oGPSEnabled)
        {
            iLblGPS_State.setText("GPS is enabled");
            iImgGPSState.setImageResource(R.drawable.n_icogps);
        }
        else
        {
            iLblGPS_State.setText("GPS is not enabled");
            iImgGPSState.setImageResource(R.drawable.n_iconogps);
        }


        iLblNetworkState.setTextColor(iLblGPS_State.getCurrentTextColor());

        switch (cabdespatchServiceDetectors.getNetworkConnectionType(this))
        {
            case GPRS3G:
                iLblNetworkState.setText("You have an active mobile data connection");
                configMenuItem(this.iBtnLaunch, true);
                iImgNetworkState.setImageResource(R.drawable.n_ico3g);
                break;
            case NONE:
                iLblNetworkState.setText("You do not appear to have a network connection - Cab Despatch Android may not function as expected");
                iImgNetworkState.setImageResource(R.drawable.n_icononet);
                configMenuItem(this.iBtnLaunch, false);
                break;
            case NULL:
                iLblNetworkState.setText("You may not have a network connection");
                iLblNetworkState.setTextColor(getResources().getColor(R.color.Red));
                iImgNetworkState.setImageResource(R.drawable.n_icononet);
                configMenuItem(this.iBtnLaunch, false);
                break;
            case WIFI:
                iLblNetworkState.setText("You have an active WiFi connection");
                iImgNetworkState.setImageResource(R.drawable.n_icowifi);
                configMenuItem(this.iBtnLaunch, true);
                break;

        }

    }

}
