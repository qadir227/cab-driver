package com.cabdespatch.driverapp.beta.dialogs;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cabdespatch.driverapp.beta.NoLeadingZerosTextWatcher;
import com.cabdespatch.driverapp.beta.R;

public class Dialog_InitialSetup extends DissmissableDialog
{
	
	
	public interface OnGoPressedListener
	{
        void goPressed(String _driverNo, String _companyID);
    }
	
	private OnGoPressedListener oOnGoPressed;
	private EditText oTxtDriverNumber;
	private EditText oTxtCompanyId;
		
	public Dialog_InitialSetup(Context _context, String _companyID, String _driverNo, OnGoPressedListener _goListener)
	{
		super(_context);
		
		//Context ctw = new ContextThemeWrapper(this.getContext(), R.style.AppDlg);
		//LayoutInflater badinflate = this.getLayoutInflater();
		//LayoutInflater goodInflate = badinflate.cloneInContext(ctw);
		
		
		//View v = goodInflate.inflate(R.layout.dialog_initial_setup, null);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_initial_setup);
		//this.getWindow().setBackgroundDrawableResource(R.drawable.n_appbackground);
		
		
		this.oTxtDriverNumber = (EditText) this.findViewById(R.id.dlgInitialSetup_txtDriverNo);
		if(_companyID.equals(""))
		{
			this.oTxtDriverNumber.setText(_driverNo);
		}
		else
		{
			if(_driverNo.equals(""))
			{
				this.oTxtDriverNumber.setText("199");	
			}
			else
			{
				this.oTxtDriverNumber.setText(_driverNo);
			}
			
		}
		this.oTxtDriverNumber.addTextChangedListener(new NoLeadingZerosTextWatcher(this.oTxtDriverNumber));
		
		
		this.oTxtCompanyId = (EditText) this.findViewById(R.id.dlgInitialSetup_txtCompanyID);
		this.oTxtCompanyId.setText(_companyID);
		
		this.oOnGoPressed = _goListener;
		
		ImageButton iBtnGo = (ImageButton) this.findViewById(R.id.dlgInitialSetup_btnGo);
		iBtnGo.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
                String sDriverNo = Dialog_InitialSetup.this.oTxtDriverNumber.getText().toString();
                String sCompanyId = Dialog_InitialSetup.this.oTxtCompanyId.getText().toString();

                if((sDriverNo.isEmpty()) || (sCompanyId.isEmpty()))
                {
                    //do nothing
                }
                else
                {
                    //SETTINGSMANAGER.reset(v.getContext());
                    Dialog_InitialSetup.this.dismiss();
                    Dialog_InitialSetup.this.oOnGoPressed.goPressed(sDriverNo, sCompanyId);
                }

				
			}
		});
		
		ImageButton iBtnCancel = (ImageButton) this.findViewById(R.id.dlgInitialSetup_btnCancel);
		iBtnCancel.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Dialog_InitialSetup.this.dismiss();
			}
		});
		
		
	}
	
	public void setOnGoPressedListener(OnGoPressedListener _listener)
	{
		this.oOnGoPressed = _listener; 
	}

}
