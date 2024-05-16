package com.cabdespatch.driverapp.beta.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.cabdespatch.driverapp.beta.DEBUGMANAGER;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.SOUNDMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;
import com.cabdespatch.driverapp.beta.services.SpeakService;

import java.util.ArrayList;
import java.util.Locale;

public abstract class AnyActivity extends AppCompatActivity
{

    protected boolean isCabApiDebug()
    {
        return SETTINGSMANAGER.SETTINGS.YAPI_USE_DEBUG_HOST.parseBoolean(this);
    }


    public void showCabDespatchDialog(Dialog_MsgBox _d)
    {
        _d.showForReal();
    }


	@SuppressWarnings("deprecation")
	protected final void setBackground()
	{
		if (Build.VERSION.SDK_INT >= 16) {

			this.getWindow().getDecorView().findViewById(android.R.id.content).setBackground(new BitmapDrawable(this.getResources(),Globals.getBackgroundImage(this)));

		}
		else
		{

			this.getWindow().getDecorView().findViewById(android.R.id.content).setBackgroundDrawable(new BitmapDrawable(this.getResources(),Globals.getBackgroundImage(this)));
		}

	}

    public void hideKeyboard()
    {
        InputMethodManager inputMethodManager=(InputMethodManager)  this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        if(this.getCurrentFocus()!=null)
        {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    protected boolean isDeveloperEdition()
    {
        return (getPackageName().contains(".devmode"));
    }

    protected void overrideAnimations()
    {
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
    }

    @Override
    public void onCreate(Bundle _savedState)
    {
        super.onCreate(_savedState);
        Window window = this.getWindow();
        if(SETTINGSMANAGER.isFullScreen(this))
        {

          /*  if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)&&false)
            {
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

            }
            else
            {*/
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }





        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(getResources().getColor(R.color.GreenYellow));
        }*/

        //Window window = this.getWindow();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Globals.registerBugHandler(this);
        overrideAnimations();

        /*
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(_savedState);
        */
        this.onCreated();
    }

    protected void onCreated()
    {

    }

	@Override
	protected void onResume()
	{
		super.onResume();
		STATUSMANAGER.CURRENT_ACTIVITY = this;

        if(!(SpeakService.RUNNING))
        {
            Intent i = new Intent(this, SpeakService.class);
            this.startService(i);
        }

		SOUNDMANAGER.stopCurrentSpeaking(this);

        //overridePendingTransition(0,0);
	}

    @Override
    protected void onPause()
    {
        super.onPause();
        //overridePendingTransition(0,0);
    }


    protected void onDestroying()
    {

    }

	@Override
	public final void onDestroy()
	{
		super.onDestroy();
        this.onDestroying();

        View rootView = null;

        try {
            rootView = ((ViewGroup) this.findViewById(android.R.id.content))
                    .getChildAt(0);
        } catch (Exception e) {
            DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN,"Cannot find root view",
                  this.toString());
        }

        if (rootView != null) {
            DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_DEBUG,"unbindDrawables", this.toString());
            unbindDrawables(rootView);
        }
    }

    //public abstract void onDestroying();


    /**
      * Utility method to unbind drawables when an activity is destroyed.  This
      * ensures the drawables can be garbage collected.
      */
     private void unbindDrawables(View view)
     {
         if (view.getBackground() != null) {
             view.getBackground().setCallback(null);
         }

         if (view instanceof ViewGroup) {
             for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                 unbindDrawables(((ViewGroup) view).getChildAt(i));
             }

             try {
                 // AdapterView objects do not support the removeAllViews method
                 if (!(view instanceof AdapterView)) {
                     ((ViewGroup) view).removeAllViews();
                 }
             } catch (Exception e) {
                 DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN, "Ignore Ex in unbindDr..", e.getStackTrace().toString());
             }
         }
     }


    public void debugLog(String _message)
    {
        if(this.isDebug())
        {
            cdToast.showLong(this, _message);
            DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN,"DEBUG", _message);
        }
    }
    public boolean isDebug()
    {
        return Globals.isDebug(this);
    }

    public ArrayList<View> getViewsByTag(String _tag)
    {
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        return getViewsByTag(viewGroup, _tag);
    }

    public ArrayList<View> getViewsByTag(ViewGroup root, String tag)
    {
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.toString().equalsIgnoreCase(tag)) {
                views.add(child);
            }

        }
        return views;
    }

    public ArrayList<View> getViews(ViewGroup root)
    {
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViews((ViewGroup) child));
            }

            final Object tagObj = child.getTag();
            views.add(child);

        }
        return views;
    }

    public ViewGroup drawOnTop(int _layout)
    {
        WindowManager manager = ((WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));

        WindowManager.LayoutParams localLayoutParams =
                new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                                PixelFormat.TRANSPARENT);

        localLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        localLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ViewGroup flt = (ViewGroup) getLayoutInflater().inflate(_layout, null);
        manager.addView(flt, localLayoutParams);

        return flt;
    }

    public void unDrawFromWindow(ViewGroup _view)
    {
        WindowManager manager = ((WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));

        manager.removeView(_view);
    }
}
