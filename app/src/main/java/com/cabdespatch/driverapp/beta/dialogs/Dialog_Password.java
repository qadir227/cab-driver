package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Dialog;
import android.content.Context;
import com.google.android.material.textfield.TextInputEditText;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;

public class Dialog_Password extends Dialog
{
	
	
	public interface OnGoPressedListener
	{
        void goPressed(String _companyID, String _password);
    }
	
	private OnGoPressedListener oOnGoPressed;
	private TextInputEditText oTxtPassword;;
	private EditText oTxtCompanyId;

	public Dialog_Password(Context _context, String _companyId, String _prompt)
	{
		super(_context, false, null);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_password);
		//this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		TextView l;
	
		
		l = (TextView) this.findViewById(R.id.dlgPassword_lblTitle);
		l.setText(_prompt);
		
		this.oTxtPassword = (TextInputEditText) this.findViewById(R.id.dlgPassword_txtPassword);
		this.oTxtPassword.setText(STATUSMANAGER.getString(_context, STATUSMANAGER.STATUSES.STORED_PASSWORD));
		
		this.oTxtCompanyId = (EditText) this.findViewById(R.id.dlgPassword_txtCompanyId);
		this.oTxtCompanyId.setText(_companyId);
		this.oTxtCompanyId.setEnabled(false);
		
		
		ImageButton iBtnGo = (ImageButton) this.findViewById(R.id.dlgPassword_btnGo);
		iBtnGo.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				Dialog_Password.this.oOnGoPressed.goPressed(Dialog_Password.this.oTxtCompanyId.getText().toString(), Dialog_Password.this.oTxtPassword.getText().toString());
				Dialog_Password.this.dismiss();
			}
		});
		
		ImageButton iBtnCancel = (ImageButton) this.findViewById(R.id.dlgPassword_btnCancel);
		iBtnCancel.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Dialog_Password.this.dismiss();
			}
		});
		
	}
	
	public void setOnGoPressedListener(OnGoPressedListener _listener)
	{
		this.oOnGoPressed = _listener;
	}

}
