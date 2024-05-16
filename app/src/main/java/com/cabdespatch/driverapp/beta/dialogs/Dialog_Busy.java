package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.cabdespatch.driverapp.beta.R;

public class Dialog_Busy extends Dialog
{

	public Dialog_Busy(Context _context)
	{
		super(_context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));						
		this.setContentView(R.layout.dialog_busy);
		
		//TextView lblBusy = (TextView) this.findViewById(R.id.dlgBusy_lblBusy);

	}
	

}
