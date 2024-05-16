package com.cabdespatch.driverapp.beta.activities;


import android.os.Bundle;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._BUTTON;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._SHOWBUTTONS;

public class ResetApp extends AnyActivity
{
	@Override
	public void onCreate(Bundle _savedState)
	{
		super.onCreate(_savedState);

		String msgTitle = this.getString(R.string.reset_heading);
		String msgPrompt = this.getString(R.string.reset_prompt);
		
		Dialog_MsgBox d = new Dialog_MsgBox(this, msgTitle, msgPrompt, _SHOWBUTTONS.YESNO);
		
		d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
		{
			
			@Override
			public void ButtonPressed(_BUTTON _button)
			{
				if(_button==Dialog_MsgBox._BUTTON.YES)
				{
					BROADCASTERS.Quit(ResetApp.this);
					SETTINGSMANAGER.reset(ResetApp.this, false);
					
					String msgAllDone = ResetApp.this.getString(R.string.reset_confirm);
					
					Dialog_MsgBox e = new Dialog_MsgBox(getApplicationContext(), msgAllDone, _SHOWBUTTONS.OK);
					e.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
					{
						
						@Override
						public void ButtonPressed(_BUTTON _button)
						{
							ResetApp.this.finish();							
						}
					});
					
					e.show();
					
				}
				else
				{
					ResetApp.this.finish();
				}
			}
		});
		
		d.show();
		
	}


}
