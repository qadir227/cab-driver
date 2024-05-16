package com.cabdespatch.driverapp.beta.activities2017;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cabdespatch.driverapp.beta.BuildConfig;
import com.cabdespatch.driverapp.beta.PauseAndRun;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.cabdespatch.driverapp.beta.fragments.StatusBar;
import com.cabdespatch.driverapp.beta.fragments2017.BreakFragment;
import com.cabdespatch.driverapp.beta.fragments2017.DriverScreenFragment;
import com.cabdespatch.driverapp.beta.fragments2017.DriverScreenFragmentNGX;
import com.cabdespatch.driverapp.beta.fragments2017.JobOfferFragment;
import com.cabdespatch.driverapp.beta.fragments2017.JobScreenFragment;
import com.cabdespatch.driverapp.beta.fragments2017.JobScreenFragmentNGX;
import com.cabdespatch.driverapp.beta.fragments2017.LoggedInFragment;
import com.cabdespatch.driverapp.beta.fragments2017.WaitingTimeFragment;
import com.cabdespatch.driverapp.beta.payment.CreditCardHandler;

import java.util.HashMap;

public class LoggedInHost extends MenuActivity implements View.OnClickListener
{

    public static final String DEBUG_TAG = "LoggedInHost.java";
    private CreditCardHandler aCreditCardHandler;
    @Override
    protected String getDebugTag()
    {
        return DEBUG_TAG;
    }

    private FRAGMENT_OPTION aCurrentFragment;
    private LoggedInFragment aCurrentFragmentObject;
    private ViewGroup aMenuBackground;
    //private View aMenuContent;
    private StatusBar oStatusBar;

    private Button aBtnResume;
    private ProgressBar aProgressPaused;
    private View aResumeContainer;

    private static Boolean sIsPaused;
    private Boolean aFirstTickSincePause;
    public static Boolean isPaused()
    {
        if(sIsPaused==null)
        {
            sIsPaused = true;
        }

        return sIsPaused;
    }

    public void setCreditCardHandler(CreditCardHandler _cardHandler)
    {
        this.aCreditCardHandler = _cardHandler;
    }

    public CreditCardHandler getCreditCardHandler()
    {
        return this.aCreditCardHandler;
    }

    @Override
    public void onClick(View v)
    {
        switch ((v.getId()))
        {
            case R.id.btnResumeApp:
                btnResume_Click();
                break;
        }
    }

    private void btnResume_Click()
    {
        this.aBtnResume.setVisibility(View.INVISIBLE);

        LoggedInHost.this.doStatusCheck(true);
        LoggedInHost.this.forceActivateTicker();

        HashMap<String, String> errorDetails = new HashMap<>();
        errorDetails = addGenericErrorDetails(errorDetails);
        ErrorActivity.genericReportableError("Button Resume Click", errorDetails);

    }


    public enum FRAGMENT_OPTION
    {
        NULL,DRIVER_SCREEN,BREAK_SCREEN,JOB_OFFER,JOB_SCREEN,WAITING_TIME;
    }

    public void showMenu()
    {
        this.showMenu(aMenuBackground, true);
    }

    private boolean doStatusCheck(Boolean _forceSwitch)
    {
        String appStatus = STATUSMANAGER.getAppState(this);
        FRAGMENT_OPTION newFragment = this.aCurrentFragment;

        if(appStatus == STATUSMANAGER.APP_STATE.PAUSED)
        {
            //do nothing... we are waiting for the server to respond
            //to useraction
        }
        else if(appStatus.equals( STATUSMANAGER.APP_STATE.LAUNCHER))
        {
            //Intent i = new Intent(this, Launcher.class);
            //this.startActivity(i);
            this.finish();
            return false;
        }
        else if(appStatus.equals( STATUSMANAGER.APP_STATE.LOGGED_OFF))
        {
            Intent i = new Intent(this, LoginActivity.class);
            this.startActivity(i);
            this.finish();
            return false;
        }
        else if(appStatus.equals( STATUSMANAGER.APP_STATE.LOGGED_ON))
        {
            newFragment = FRAGMENT_OPTION.DRIVER_SCREEN;
        }
        else if(appStatus.equals( STATUSMANAGER.APP_STATE.PAUSED))
        {
            //hmm we're waiting for a response to our job offer accept/reject
            //and then we've paused the app and resumed...
            //
            //let's go driver screen for now; I'm sure we'll get put to the
            //job screen once we receive the ak
            newFragment = FRAGMENT_OPTION.DRIVER_SCREEN;
        }
        else if(appStatus.equals( STATUSMANAGER.APP_STATE.ON_BREAK))
        {
            newFragment = FRAGMENT_OPTION.BREAK_SCREEN;
        }
        else if(appStatus.equals(STATUSMANAGER.APP_STATE.WAITING_TIME))
        {
            newFragment = FRAGMENT_OPTION.WAITING_TIME;
        }
        else if(appStatus.equals(STATUSMANAGER.APP_STATE.ON_JOB))
        {
            newFragment = assessOnJobFragment();
        }

        if(newFragment==this.aCurrentFragment)
        {
            if(_forceSwitch)
            {
                this.switchFragment(newFragment);
            }
        }
        else
        {

            this.switchFragment(newFragment);

        }

        return true;
    }

    private FRAGMENT_OPTION assessOnJobFragment()
    {
        FRAGMENT_OPTION retVal;

        cabdespatchJob j = STATUSMANAGER.getCurrentJob(this);

        switch (j.getJobStatus())
        {
            case UNDER_OFFER:
                if(j.isAutoAccept())
                {
                    //wait until we've sent and received ACK for
                    //job
                    retVal = FRAGMENT_OPTION.DRIVER_SCREEN;
                }
                else
                {
                    retVal = FRAGMENT_OPTION.JOB_OFFER;
                }
                break;
            default:
                retVal = FRAGMENT_OPTION.JOB_SCREEN;
                break;
        }

        return retVal;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logged_in_host);

        oStatusBar = (StatusBar) getSupportFragmentManager().findFragmentById(R.id.status_bar_logged_in);

        this.aResumeContainer = findViewById(R.id.container);
        this.aBtnResume = (Button) findViewById(R.id.btnResumeApp);
        this.aProgressPaused = (ProgressBar) findViewById(R.id.progress);
        this.aBtnResume.setOnClickListener(this);

        aFirstTickSincePause = true;

        this.aMenuBackground = (ViewGroup) this.findViewById(R.id.menuBackground);


        //ok so we will give the app 1.5 seconds which should allow plenty of
        //time for the ticker execute. If the ticker *does* execute successfully,
        //all the child views of the container will be hidden anyway so nothing
        //will actually be displayed

        //if it failes to execute successfully then the container's child views
        //will still be set to visible... thus showing the container will present
        //them for the user to interact with

        class containerShower extends PauseAndRun
        {
            @Override
            protected void onPostExecute(Void _void)
            {
                if(!(LoggedInHost.this.aResumeContainer==null))
                {
                    LoggedInHost.this.aResumeContainer.setVisibility(View.VISIBLE);
                }
            }
        }
        new containerShower().Start(1500);


        class buttonEnabler extends PauseAndRun
        {

            @Override
            protected void onPostExecute(Void _void)
            {
                if(!(LoggedInHost.this.aBtnResume==null))
                {
                    LoggedInHost.this.aBtnResume.setEnabled(true);
                    LoggedInHost.this.aProgressPaused.setVisibility(View.INVISIBLE);
                }
            }
        }
        new buttonEnabler().Start(5000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        this.aCurrentFragmentObject.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onTick(long millis)
    {
        super.onTick(millis);
        if (doStatusCheck(aFirstTickSincePause))
        {
            if(!(this.aCurrentFragment==null))
            {
                this.aCurrentFragmentObject.onPreTick();
            }
        }
        if(!(oStatusBar==null))
        {
            oStatusBar.onTick();
        }

        aFirstTickSincePause = false;
    }

    @Override
    public void onLogEvent(String _data)
    {
        //do nothing
    }

    @Override
    protected void showDriverMessage()
    {
        if(!(aCurrentFragment==FRAGMENT_OPTION.JOB_OFFER))
        {
            Intent i = new Intent(this, DriverMessage.class);
            startActivity(i);
        }
    }

    @Override
    protected void onNetworkStateChange(Boolean _connected)
    {

    }

    @Override
    protected void onBroadcastReceived(Context _context, Intent _intent)
    {
        this.aCurrentFragmentObject.onPreBroadcastReceived(_context, _intent);
        if(!(this.oStatusBar==null))
        {
            this.oStatusBar.onPreBroadcastReceived(_context, _intent);
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        aFirstTickSincePause = true;
        sIsPaused = false;

        /*
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragMain);
        if(!(f==null))
        {
            View v = f.getView();
            if(!(v==null))
            {
                v.setVisibility(View.INVISIBLE);
            }
        }
       //turnOnScreen();
        //this.hideMenu();*/
    }

    @Override
    protected void onResumeFragments()
    {
        super.onResumeFragments();
        this.doStatusCheck(true);

    }


    @Override
    public void onPause()
    {
        super.onPause();
        sIsPaused = true;
    }



    private void switchFragment(FRAGMENT_OPTION _fragment)
    {


        this.aBtnResume.setVisibility(View.GONE);
        this.aProgressPaused.setVisibility(View.GONE);


        LoggedInFragment frag = null;

        switch (_fragment)
        {
            case NULL:
                break;
            case DRIVER_SCREEN:
                if (BuildConfig.NEW_BRANCH)
                {
                    frag = new DriverScreenFragmentNGX();
                }
                else
                {
                    frag = new DriverScreenFragment();
                }
                break;
            case BREAK_SCREEN:
                frag = new BreakFragment();
                break;
            case JOB_OFFER:
                frag = new JobOfferFragment();
                break;
            case JOB_SCREEN:
                if (BuildConfig.NEW_BRANCH)
                {
                    frag = new JobScreenFragmentNGX();
                }
                else
                {
                    frag = new JobScreenFragment();
                }
                break;
            case WAITING_TIME:
                frag = new WaitingTimeFragment();
                break;
        }

        if(frag == null)
        {
            HashMap<String, String> errorDetails = new HashMap<>();
            errorDetails.put("DATA FragmentOption", _fragment.toString());
            errorDetails = addGenericErrorDetails(errorDetails);

            ErrorActivity.genericReportableError("Null fragment - finishing activity", errorDetails);
            this.finish();
        }
        else
        {
            aCurrentFragment = _fragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragMain, (Fragment) frag);
            fragmentTransaction.commitAllowingStateLoss();

            this.aCurrentFragmentObject = frag;
            this.aCurrentFragmentObject.setRetainInstance(true);

            //hideKeyboard();
            //frag.getView().setVisibility(View.VISIBLE);
            //this.aCurrentFragmentObject.getView().setTag(FRAG_TAG++);

        }

    }

    private HashMap<String, String> addGenericErrorDetails(HashMap <String,String> _input)
    {
        _input.put("DATA Current Fragment", (aCurrentFragment==null?"NULL":aCurrentFragment.toString()));
        _input.put("DATA ActivityPaused", String.valueOf(isPaused()));
        _input.put("DATA Lock Down Mode", SETTINGSMANAGER.getLockDownMode(this));

        return _input;
    }

    private static int FRAG_TAG = 1;

}
