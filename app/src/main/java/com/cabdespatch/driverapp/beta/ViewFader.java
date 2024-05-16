package com.cabdespatch.driverapp.beta;

/**
 * Created by Pleng on 01/06/2016.
 */

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

public class ViewFader
{
    private View oViewFrom;
    private View oViewTo;
    private int oFadeOutVisibility;
    private View oFocusView;

    public static final int DURATION = 700;

    public interface OnFadeCompleteListener
    {
        void OnFadeComplete();
    }
    private OnFadeCompleteListener oListener;

    private ViewFader(View _fromView, View _toView, int _fadeOutVisibility)
    {
        this(_fromView, _toView, _fadeOutVisibility, null);
    }

    private ViewFader(View _fromView, View _toView, int _fadeOutVisibility, OnFadeCompleteListener _listener)
    {
        this.oViewFrom = _fromView;
        this.oViewTo = _toView;
        this.oFocusView = null;
        this.oFadeOutVisibility = _fadeOutVisibility;
        this.oListener = _listener;
    }

    private ViewFader(View _fromView, View _focusView)
    {
        this(_fromView, _focusView, null);
    }

    private ViewFader(View _toView, View _focusView, OnFadeCompleteListener _listener)
    {
        this.oViewFrom = null;
        this.oViewTo = _toView;
        this.oFocusView = _focusView;
        this.oFadeOutVisibility = View.GONE; //doesn't matter...
        this.oListener = _listener;
    }

    public void Start()
    {
        if(this.oViewFrom==null)
        {
            if(this.oViewTo==null)
            {
                //CLAY raise error??
            }
            else
            {
                fadeInNew();
            }

        }
        else
        {
               fadeOutOld();
        }
    }



    private void fadeOutOld()
    {

        try
        {
            YoYo.with(Techniques.FadeOut)
                    .duration(DURATION)
                    .withListener(new Animator.AnimatorListener()
                    {
                        @Override
                        public void onAnimationStart(Animator animation)
                        {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            oViewFrom.setVisibility(ViewFader.this.oFadeOutVisibility);
                            if(oViewTo==null)
                            {
                                if(!(oListener==null))
                                {
                                    oListener.OnFadeComplete();
                                }
                            }
                            else
                            {
                                fadeInNew();
                            }

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
                    .playOn(this.oViewFrom);
        }
        catch (Exception ex)
        {
            //CLAY - fix this properly!
        }


    }

    private void fadeInNew()
    {
        YoYo.with(Techniques.FadeIn)
                .duration(DURATION)
                .withListener(new Animator.AnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        oViewTo.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        if(!(oFocusView==null))
                        {
                            oFocusView.requestFocus();
                        }

                        if(!(oListener==null))
                        {
                            oListener.OnFadeComplete();
                        }
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
                .playOn(this.oViewTo);
    }

    public static void fadeIn(View _view)
    {
        new ViewFader(null, _view, 0).Start();
    }

    public static void fadeOutToInvisible(View _view)
    {
        new ViewFader(_view, null, View.INVISIBLE).Start();
    }

    public static void quickFadetoInvisible(View _v)
    {

    }

    public static void fadeOutToGone(View _view)
    {
        new ViewFader(_view, null, View.GONE).Start();
    }

    public static void fadeOutToGone(View _view, OnFadeCompleteListener _listener)
    {
        new ViewFader(_view, null, View.GONE, _listener).Start();
    }

    public static void fadeBetween(View _out, View _in)
    {
        new ViewFader(_out, _in, View.INVISIBLE).Start();
    }

    public static void fadeInThenFocusControl(View _in, View _focus)
    {
        new ViewFader(_in, _focus).Start();
    }
}
