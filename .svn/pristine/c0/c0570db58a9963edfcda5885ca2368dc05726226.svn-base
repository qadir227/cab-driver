package com.cabdespatch.driverapp.beta.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.activities2017.ComposeMessage;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;

public class ManagerModeMenu extends AnyActivity
{

    private Boolean isResuming = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_mode_menu);
        this.setBackground();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_manager_mode_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.isResuming = true;

        CheckBox chkFairMeter = (CheckBox) this.findViewById(R.id.managerMode_chkFairMeter);
        /*
        chkFairMeter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(!(ManagerModeMenu.this.isResuming))
                {
                    SETTINGSMANAGER.SETTINGS.METER_TYPE.putValue(buttonView.getContext(), (isChecked?SETTINGSMANAGER.METER_TYPE.FAIR_METER:SETTINGSMANAGER.METER_TYPES.NONE));
                }
            }
        });
        chkFairMeter.setChecked(!(Boolean.valueOf(SETTINGSMANAGER.SETTINGS.METER_TYPE.getValue(this).equals(SETTINGSMANAGER.METER_TYPE.NONE))));
*/
        chkFairMeter.setChecked(!(Boolean.valueOf(SETTINGSMANAGER.SETTINGS.METER_TYPE.getValue(this).equals(SETTINGSMANAGER.METER_TYPE.NONE))));
        chkFairMeter.setEnabled(false);

        CheckBox chkFlightModeHack = (CheckBox) this.findViewById(R.id.managerMode_chkFlightHack);
        chkFlightModeHack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(!(ManagerModeMenu.this.isResuming))
                {
                    SETTINGSMANAGER.SETTINGS.FLIGHT_MODE_HACK.putValue(buttonView.getContext(), String.valueOf(isChecked));
                }
            }
        });
        chkFlightModeHack.setChecked(Boolean.valueOf(SETTINGSMANAGER.SETTINGS.FLIGHT_MODE_HACK.getValue(this)));

        CheckBox chkRequireGPS = (CheckBox) this.findViewById(R.id.managerMode_chkRequireGps);
        chkRequireGPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(!(ManagerModeMenu.this.isResuming))
                {
                    SETTINGSMANAGER.SETTINGS.REQUIRE_GPS.putValue(buttonView.getContext(), String.valueOf(isChecked));
                }
            }
        });
        chkRequireGPS.setChecked(Boolean.valueOf(SETTINGSMANAGER.SETTINGS.REQUIRE_GPS.getValue(this)));



        CheckBox chkRequireMobileData = (CheckBox) this.findViewById(R.id.managerMode_chkRequireMobileData);
        chkRequireMobileData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(!(ManagerModeMenu.this.isResuming))
                {
                    SETTINGSMANAGER.SETTINGS.REQUIRE_MOBILE_DATA.putValue(ManagerModeMenu.this, isChecked);
                }
            }
        });
        chkRequireMobileData.setChecked(SETTINGSMANAGER.SETTINGS.REQUIRE_MOBILE_DATA.parseBoolean(ManagerModeMenu.this));


        CheckBox chkUseAlternativeLocationProviders = (CheckBox) this.findViewById(R.id.managerMode_chkAlternativeLocationProviders);
        chkUseAlternativeLocationProviders.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked)
            {
                if(!(ManagerModeMenu.this.isResuming))
                {
                    if(isChecked)
                    {
                        Dialog_MsgBox warn = new Dialog_MsgBox(buttonView.getContext(), "This option may be unstable on some devices. Are you sure you wish to continue", Dialog_MsgBox._SHOWBUTTONS.YESNO);
                        warn.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener() {
                            @Override
                            public void ButtonPressed(Dialog_MsgBox._BUTTON _button)
                            {
                                if(_button.equals(Dialog_MsgBox._BUTTON.YES))
                                {
                                    if(!(SETTINGSMANAGER.SETTINGS.USE_ALTERNATIVE_LOCATION_PROVIDERS.putValue(buttonView.getContext(), String.valueOf(true))))
                                    {
                                        cdToast.showShort(ManagerModeMenu.this, "Could not save setting");
                                        buttonView.setChecked(false);
                                    }

                                }
                                else
                                {
                                    buttonView.setChecked(false);
                                }
                            }
                        });
                        warn.show();
                    }
                    else
                    {
                        SETTINGSMANAGER.SETTINGS.USE_ALTERNATIVE_LOCATION_PROVIDERS.putValue(buttonView.getContext(), String.valueOf(false));
                    }

                }
            }
        });
        Boolean useAltLoc = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_ALTERNATIVE_LOCATION_PROVIDERS.getValue(this));
        chkUseAlternativeLocationProviders.setChecked(useAltLoc);


        CheckBox chkEnforceMinimumMovementDistance = (CheckBox) this.findViewById(R.id.managerMode_chkEnforceMovementAmountLimits);
        chkEnforceMinimumMovementDistance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked)
            {
                if(!(ManagerModeMenu.this.isResuming))
                {
                    if(isChecked)
                    {
                        SETTINGSMANAGER.SETTINGS.ENFORCE_MINIMUM_MOVEMENT_CRITERIA.putValue(buttonView.getContext(), String.valueOf(true));
                    }
                    else
                    {
                        Dialog_MsgBox warn = new Dialog_MsgBox(getApplicationContext(), "Disabling this option may be unstable on some devices. Are you sure you wish to continue", Dialog_MsgBox._SHOWBUTTONS.YESNO);
                        warn.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener() {
                            @Override
                            public void ButtonPressed(Dialog_MsgBox._BUTTON _button)
                            {
                                if(_button.equals(Dialog_MsgBox._BUTTON.YES))
                                {
                                    if(!(SETTINGSMANAGER.SETTINGS.ENFORCE_MINIMUM_MOVEMENT_CRITERIA.putValue(buttonView.getContext(), String.valueOf(false))))
                                    {
                                        cdToast.showShort(ManagerModeMenu.this, "Could not save setting");
                                        buttonView.setChecked(true);
                                    }
                                }
                                else
                                {
                                    buttonView.setChecked(true);
                                }
                            }
                        });
                        warn.show();
                    }

                }
            }
        });
        Boolean enforceMinDist = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.ENFORCE_MINIMUM_MOVEMENT_CRITERIA.getValue(this));
        chkEnforceMinimumMovementDistance.setChecked(enforceMinDist);

        /*
        CheckBox chkConnectionLessData = (CheckBox) this.findViewById(R.id.managerMode_chkConnectionLessData);
        chkConnectionLessData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(!(ManagerModeMenu.this.isResuming))
                {
                    SETTINGSMANAGER.SETTINGS.TEMP_FORCE_DATA_CONNLESS.putValue(ManagerModeMenu.this, isChecked);
                }
            }
        });
*/

        CheckBox chkSignalR = (CheckBox) this.findViewById(R.id.managerMode_chkSignalR);
        chkSignalR.setChecked(SETTINGSMANAGER.SETTINGS.PREFER_SIGNAL_R.parseBoolean(this));
        chkSignalR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SETTINGSMANAGER.SETTINGS.PREFER_SIGNAL_R.putValue(ManagerModeMenu.this, isChecked);
            }
        });


        this.isResuming = false;
    }



    public void btnUpdate_Click(View v)
    {
        Globals.CrossFunctions.UpdateApp(ManagerModeMenu.this);
    }
    /*
    public void btnPlayStore_Click(View v)
    {
        //Dialog_MsgBox d = new Dialog_MsgBox(this, "Not yet implemented");
        //d.show();
        try
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("market://details?id=" + ManagerModeMenu.this.getPackageName()));
            this.startActivity(i);
        }
        catch (ActivityNotFoundException ex)
        {
            cdToast.showShort(this, R.string.play_store_not_installed);
        }

    }*/
    public void btnGoogle_Click(View v)
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://www.google.com"));
        this.startActivity(Intent.createChooser(i, "Visit Google With..."));
    }

    public void btnLog_Click(View v)
    {
        Intent i = new Intent(v.getContext(), ComposeMessage.class);
        i.setAction(ComposeMessage._MESSAGE_TYPE.DEBUG_DATA);
        this.startActivity(i);
    }

    public void btnMusic_Click(View v)
    {
        Intent ij = new Intent(this, SoundChooser.class);
        ij.putExtra(SoundChooser.EXTRA_LAUNCH_REASON, SoundChooser.LAUNCH_REASON_BROWSE_CURRENT_TRACKS);

        this.startActivity(ij);
    }

    public void btnReset_Click(View v)
    {
        Intent i = new Intent(this, ResetApp.class);
        //CLAY
        //i = new Intent(this, JobTotals.class);
        this.startActivity(i);
    }

    public void btnLockWebsites_Click(View v)
    {
        String prompt = "When you click the tick icon, you will be prompted to select an app to launch websites with. Select 'Cab Despatch Android' ";

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        {
            prompt += "and press the 'Always' button";
        }
        else
        {
            prompt += "and tick the box which indicates that the app should be used by default.";
        }

        Dialog_MsgBox d = new Dialog_MsgBox(this, prompt);
        d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener() {
            @Override
            public void ButtonPressed(Dialog_MsgBox._BUTTON _button)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.google.com"));
                ManagerModeMenu.this.startActivity(i);
            }
        });
        d.show();
    }
}
