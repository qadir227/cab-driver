package com.cabdespatch.driverapp.beta.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cabdespatch.driverapp.beta.ViewFader;

/**
 * Created by Pleng on 08/06/2016.
 */
public class PseudoDialog
{
    ViewGroup oLayBase;
    ImageButton btnBack;

    public interface OnDissmissCompleteListener
    {
        void onDissmissComplete();
    }

    protected PseudoDialog(Context _c, ViewGroup _container, int _layoutResource, Integer _backButtonResource)
    {
        this.oLayBase = _container;

        _container.removeAllViewsInLayout();

        LayoutInflater i = LayoutInflater.from(_c);
        i.inflate(_layoutResource, _container, true);

        btnBack = (ImageButton) _container.findViewById(_backButtonResource);
        btnBack.setOnClickListener(btnBack_Clicked());
    }

    protected PseudoDialog(Context _c, ViewGroup _container, int _layoutResource)
    {
        this.oLayBase = _container;

        _container.removeAllViewsInLayout();

        LayoutInflater i = LayoutInflater.from(_c);
        i.inflate(_layoutResource, _container, true);

        btnBack = null;
    }

    protected View findViewById(Integer _id)
    {
        return this.oLayBase.findViewById(_id);
    }

    protected Context getContext()
    {
        return  oLayBase.getContext();
    }

    public void hideBackButton()
    {
        btnBack.setVisibility(View.GONE);
    }

    public void show()
    {
        show(null);
    }

    public void show(Integer _focus)
    {
        this.oLayBase.setVisibility(View.VISIBLE);
        if(_focus == null)
        {
            ViewFader.fadeIn(this.oLayBase.getChildAt(0));
        }
        else
        {
            View v = this.oLayBase.findViewById(_focus);
            ViewFader.fadeInThenFocusControl(this.oLayBase.getChildAt(0), v);
        }
    }

    public void dismiss()
    {
        dismiss(null);
    }

    public void dismiss(final OnDissmissCompleteListener _listener)
    {
        ViewFader.OnFadeCompleteListener listener = new ViewFader.OnFadeCompleteListener()
        {
            @Override
            public void OnFadeComplete()
            {
                PseudoDialog.this.oLayBase.setVisibility(View.GONE);
                if(!(_listener==null))
                {
                    _listener.onDissmissComplete();
                }
            }
        };
        ViewFader.fadeOutToGone(PseudoDialog.this.oLayBase.getChildAt(0), listener);
    }

    public View.OnClickListener btnBack_Clicked()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PseudoDialog.this.dismiss(null);
            }
        };
    }
}
