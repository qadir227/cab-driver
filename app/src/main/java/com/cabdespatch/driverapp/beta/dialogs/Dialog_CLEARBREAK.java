package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.cabdespatch.driverapp.beta.R;

public class Dialog_CLEARBREAK extends Dialog
{

	public enum RESULT
	{
		CLEAR,BREAK,CANCEL;
	}

	public interface OnResultListener
	{
		void OnResult(Dialog_CLEARBREAK.RESULT _result);
	}

	private Dialog_CLEARBREAK.OnResultListener oListener;

	public Dialog_CLEARBREAK(Context _context)
	{
		super(_context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setCancelable(false);
		//this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		this.setContentView(R.layout.dialog_clearbreak);


		View btnBack =  this.findViewById(R.id.dlg_btnBack);
		btnBack.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Dialog_CLEARBREAK.this.oListener.OnResult(Dialog_CLEARBREAK.RESULT.CANCEL);
				Dialog_CLEARBREAK.this.dismiss();
			}
		});

		View btnClear =  this.findViewById(R.id.dlg_CLEAR);
		btnClear.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Dialog_CLEARBREAK.this.oListener.OnResult(Dialog_CLEARBREAK.RESULT.CLEAR);
				Dialog_CLEARBREAK.this.dismiss();
			}
		});

		View btnBreak =  this.findViewById(R.id.dlg_BREAK);
		btnBreak.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Dialog_CLEARBREAK.this.oListener.OnResult(Dialog_CLEARBREAK.RESULT.BREAK);
				Dialog_CLEARBREAK.this.dismiss();
			}
		});


		this.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
								 KeyEvent event)
			{
				if (keyCode == KeyEvent.KEYCODE_BACK)
				{
					Dialog_CLEARBREAK.this.oListener.OnResult(Dialog_CLEARBREAK.RESULT.CANCEL);
					Dialog_CLEARBREAK.this.dismiss();
					return true;
				}
				return false;
			}
		});

	}

	public void setOnResultListener(Dialog_CLEARBREAK.OnResultListener _listener)
	{
		this.oListener = _listener;
	}

}
