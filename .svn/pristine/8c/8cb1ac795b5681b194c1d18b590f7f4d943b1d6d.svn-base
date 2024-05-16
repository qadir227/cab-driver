package com.cabdespatch.driverapp.beta.activities2017;

import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

/**
 * Created by Pleng on 26/02/2017.
 */

public abstract class MenuActivity extends DataActivity implements Globals.CrossFunctions.Menus.OnMenuItemSelectedListener
{

    private ViewGroup aStoredMenuBackground;
    private Boolean aMenuShowing;

    protected boolean menuShowing()
    {
        if(aMenuShowing==null)
        {
            aMenuShowing = false;
        }
        return aMenuShowing;
    }

    @Override
    public void onItemSelected(String _tag)
    {
        hideMenu(this.aStoredMenuBackground);
    }

    protected abstract void showMenu();
    protected void onBackPressedWithNoMenuShowing(){};


    @Override
    public final void onBackPressed()
    {
        if(this.menuShowing())
        {
            this.hideMenu(this.aStoredMenuBackground);
        }
        else
        {
            this.onBackPressedWithNoMenuShowing();
        }
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu _menu)
    {
        showMenu();
        return true;
    }


    protected void showMenu(final ViewGroup _background, Boolean _loggedIn)
    {
        this.aMenuShowing = true;
        this.aStoredMenuBackground = _background;

        _background.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MenuActivity.this.hideMenu(_background);
            }
        });

        _background.removeAllViews();
        final ViewGroup menuContent = (ViewGroup) this.getLayoutInflater().inflate(R.layout.menu, _background);

        TextView lblSubHeader = (TextView) menuContent.findViewById(R.id.lblMenuSubHeader);
        String appVersion = "";
        try
        {
            appVersion = " v" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            //don't care (never going to happen anyway!)
        }
        lblSubHeader.setText(appVersion);


        ImageButton btnMenuClose = (ImageButton) menuContent.findViewById(R.id.frmMenu_btnBack);
        btnMenuClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MenuActivity.this.hideMenu(_background);
            }
        });

        Globals.CrossFunctions.Menus.setupMenuItems(this, (LinearLayout) this.findViewById(R.id.frmMenu_layoutMenuItems), _loggedIn, this);

        YoYo.with(Techniques.BounceInLeft)
                .duration(500)
                .withListener(new Animator.AnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        _background.setVisibility(View.VISIBLE);
                        menuContent.setVisibility(View.VISIBLE);
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
                .playOn(menuContent);
    }

    public void hideMenu(final ViewGroup _background)
    {
        final ViewGroup menuContent = (ViewGroup) _background.getChildAt(0);
        aMenuShowing = false;

        YoYo.with(Techniques.SlideOutLeft)
                .duration(500)
                .withListener(new Animator.AnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        _background.setVisibility(View.GONE);
                        menuContent.setVisibility(View.GONE);
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
                .playOn(menuContent);
    }
}
