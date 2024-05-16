package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;

import com.cabdespatch.driverapp.beta.BuildConfig;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.cabdespatchJob;

public class Dialog_POBSTP extends Dialog implements ViewTreeObserver.OnGlobalLayoutListener {

	private View btnSTP;
	private View btnPOB;

	public enum RESULT
	{
		TEXTBACK,POB,CANCEL;
	}

	public interface OnResultListener
	{
		void OnResult(RESULT _result);
	}

	private OnResultListener oListener;


	public Dialog_POBSTP(Context _context, View _button)
	{
		super(_context);


		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setCancelable(false);

		this.setContentView(R.layout.dialog_pobstc);

		ImageButton btnBack = (ImageButton) this.findViewById(R.id.dlg_btnBack);
		btnBack.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Dialog_POBSTP.this.oListener.OnResult(RESULT.CANCEL);
				Dialog_POBSTP.this.dismiss();
			}
		});

		btnSTP = this.findViewById(R.id.dlg_STP);
		btnSTP.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Dialog_POBSTP.this.oListener.OnResult(RESULT.TEXTBACK);
				Dialog_POBSTP.this.dismiss();
			}
		});

		btnPOB = this.findViewById(R.id.dlg_POB);
		btnPOB.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Dialog_POBSTP.this.oListener.OnResult(RESULT.POB);
				Dialog_POBSTP.this.dismiss();
			}
		});


		if(BuildConfig.NEW_BRANCH)
		{
			ViewGroup.LayoutParams l = btnPOB.getLayoutParams();
			l.width =  _button.getMeasuredWidth();
			l.height = _button.getMeasuredHeight();
			btnPOB.setLayoutParams(l);

			ViewGroup.LayoutParams m = btnSTP.getLayoutParams();
			m.width =  _button.getMeasuredWidth();
			m.height = _button.getMeasuredHeight();
			btnSTP.setLayoutParams(m);

			btnPOB.getViewTreeObserver().addOnGlobalLayoutListener(this);
		}

		Boolean useAntiCheat = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.USE_ANTICHEAT.getValue(_context));
		if (useAntiCheat)
		{
			//it's enabled? are we in the correct plot?

			cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
			btnPOB.setEnabled(j.canPOB(_context	, useAntiCheat));

		}



		this.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
								 KeyEvent event)
			{
				if (keyCode == KeyEvent.KEYCODE_BACK)
				{
					Dialog_POBSTP.this.oListener.OnResult(RESULT.CANCEL);
					Dialog_POBSTP.this.dismiss();
					return true;
				}
				return false;
			}
		});

	}


	@Override
	public void onGlobalLayout()
	{
		AppCompatTextView t1 = (AppCompatTextView) ((ViewGroup) btnPOB).getChildAt(1);
		AppCompatTextView t2 = (AppCompatTextView) ((ViewGroup) btnSTP).getChildAt(1);
		Globals.CrossFunctions.makeUniformButtonTextSize(t1, t2);
	}

	public void setOnResultListener(OnResultListener _listener)
	{
		this.oListener = _listener;
	}

}