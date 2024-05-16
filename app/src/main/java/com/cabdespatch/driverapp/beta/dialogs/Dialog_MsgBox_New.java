package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Pleng on 08/06/2016.
 */
public class Dialog_MsgBox_New extends PseudoDialog
{

    Boolean cancelCountdownPending;
    LinearLayout layActualContent;

    public interface OnMessageBoxButtonListener
    {
        void MessageBoxOKPressed();
        void CountdownFinished();
    }
    private OnMessageBoxButtonListener oListener;

    public Dialog_MsgBox_New(Activity _a, int _imageResourceID, ViewGroup _container, String _title, String _message, OnMessageBoxButtonListener _listener)
    {
        this(_a, _imageResourceID, _container, _title, _message, false, "", 0, _listener);
    }

    public Dialog_MsgBox_New(Activity _a, int _imageResourceID, ViewGroup _container, String _title, String _message, String _buttonPrompt, OnMessageBoxButtonListener _listener)
    {
        this(_a, _imageResourceID, _container, _title, _message, false, _buttonPrompt, 0, _listener);
    }

    public Dialog_MsgBox_New(Activity _a, int _imageResourceID, ViewGroup _container, String _title, String _message, Boolean _negativeButton, String _buttonPrompt, int _countDownSeconds, final OnMessageBoxButtonListener _listener)
    {
        super(_a, _container, R.layout.dialog_messagebox_new, R.id.dlgMsgboxNew_btnBack);

        this.layActualContent = (LinearLayout) this.oLayBase.findViewById(R.id.actualcontent);

        oListener = _listener;
        cancelCountdownPending = false;

        TextView lblTitle = (TextView) _container.findViewById(R.id.dlgMsgboxNew_lblTitle);
        if(_title.isEmpty())
        {
            lblTitle.setVisibility(View.GONE);
        }
        else
        {
            ImageView i = (ImageView) _container.findViewById(R.id.messagebox_new_image);
            i.setImageResource(_imageResourceID);
            lblTitle.setText(_title);
        }


        TextView lblMessage = (TextView) _container.findViewById(R.id.dlgMsgboxNew_lblMessage);
        lblMessage.setText(_message);

        ImageButton btnOk = (ImageButton) _container.findViewById(R.id.dlgMsgboxNew_btnOk);

        if(_negativeButton)
        {
            btnOk.setImageResource(R.drawable.n_icono);
        }

        TextView lblButtonPrompt = (TextView) _container.findViewById(R.id.dlgMsgBoxNew_btnPrompt);
        if(_buttonPrompt.equals(""))
        {
            lblButtonPrompt.setVisibility(View.GONE);
        }
        else
        {
            lblButtonPrompt.setText(_buttonPrompt);
        }

        btnOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Dialog_MsgBox_New.this.dismiss(new OnDissmissCompleteListener()
                {
                    @Override
                    public void onDissmissComplete()
                    {
                        Dialog_MsgBox_New.this.oListener.MessageBoxOKPressed();
                    }
                });
            }
        });

        final ProgressBar prg = (ProgressBar) _container.findViewById(R.id.dlgMsgBoxNew_prgTimeout);

        if(_countDownSeconds==0)
        {
            prg.setVisibility(View.GONE);
        }
        else
        {
            prg.setMax(_countDownSeconds);
            prg.setProgress(_countDownSeconds);

            final Timer t = new Timer();

            class task extends TimerTask
            {

                int oTimeOutValue;

                public  task(int _timeOutValue)
                {
                    oTimeOutValue = _timeOutValue;
                }

                @Override
                public void run()
                {
                    if(cancelCountdownPending)
                    {
                        Dialog_MsgBox_New.this.dismiss();
                    }
                    else
                    {
                        if (oTimeOutValue <= 0)
                        {
                            _listener.CountdownFinished();
                            Dialog_MsgBox_New.this.dismiss();
                        }
                        else
                        {
                            prg.setProgress(oTimeOutValue);
                            t.schedule(new task(oTimeOutValue - 1), 1000);
                        }
                    }
                }
            }

            t.schedule(new task(_countDownSeconds), 0);
        }
    }

    public void cancelCountdown()
    {
        cancelCountdownPending = true;
    }

    public void stretchToFill()
    {
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) this.layActualContent.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;

        this.layActualContent.requestLayout();
    }






}
