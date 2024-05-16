package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.cabdespatch.driverapp.beta.R;

public class Dialog_NavLocation extends Dialog
{
	
	public enum RESULT
	{
		OK, CANCELLED;
	}
	
	public interface OnResultListener
	{
		public void OnResult(RESULT _result);
	}
	
	private OnResultListener oListener;

	public Dialog_NavLocation(Context context) 
	{
		super(context);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setCancelable(false);
		
		//setting manipulation is handled by the fragment 
		//which is contained in the layout
		this.setContentView(R.layout.dialog_navlocation);
		
		ImageButton btnBack = (ImageButton) this.findViewById(R.id.dlg_btnBack);
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				Dialog_NavLocation.this.oListener.OnResult(RESULT.CANCELLED);
				Dialog_NavLocation.this.dismiss();
			}
		});
		
		ImageButton btnOk = (ImageButton) this.findViewById(R.id.dlg_btnOk);		
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				Dialog_NavLocation.this.oListener.OnResult(RESULT.OK);
				Dialog_NavLocation.this.dismiss();	
			}
		});
		
		
	}
	
	public void setOnResultListener (OnResultListener _listener)
	{
		this.oListener = _listener;
	}

}
