package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.cdToast;

public class Dialog_MsgBox extends Dialog
{
	public enum _BUTTON
	{OK,YES,NO;}
	public enum _SHOWBUTTONS
	{OK,YESNO;}
	
	public interface OnButtonPressListener
	{
		public void ButtonPressed(_BUTTON _button);
	}
	
	private OnButtonPressListener oButtonPressListener;
	private String oMessage;
	
	Dialog_MsgBox(Context _context, int _tag, String _title, String _subtitle, String _message, String _footnote,  _SHOWBUTTONS _showbuttons)
	{
		super(STATUSMANAGER.CURRENT_ACTIVITY,false,null);
		oMessage = _message;

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);

		this.setContentView(R.layout.dialog_msgbox);
		//this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		TextView lblTitle = (TextView) this.findViewById(R.id.dlg_msgbox_lblTitle);
		if(_title.equals(""))
		{
			lblTitle.setVisibility(View.GONE);
		}
		else
		{
			lblTitle.setText(_title);
		}

		TextView lblSubtitle = (TextView) this.findViewById(R.id.dlg_msgbox_lblSubTitle);
		if(_subtitle.equals(""))
		{
			lblSubtitle.setVisibility(View.GONE);
		}
		else
		{
			lblSubtitle.setText(_subtitle);
		}

		TextView lblMessage = (TextView) this.findViewById(R.id.dlg_msgbox_lblMessage);
		lblMessage.setText(_message);

		TextView lblFootnote = (TextView) this.findViewById(R.id.dlg_msgbox_lblFootnote);
		if(_footnote.equals(""))
		{
			lblFootnote.setVisibility(View.GONE);
		}
		else
		{
			lblFootnote.setText(_footnote);
		}

		ImageButton btnOK = (ImageButton) this.findViewById(R.id.dlg_msgbox_btnOk);

		switch(_showbuttons)
		{
			case OK:
				LinearLayout layoutYesNo = (LinearLayout) this.findViewById(R.id.dlg_msgbox_layoutYesNo);
				layoutYesNo.setVisibility(View.GONE);


				btnOK.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						if(!(Dialog_MsgBox.this.oButtonPressListener == null))
						{
							Dialog_MsgBox.this.oButtonPressListener.ButtonPressed(_BUTTON.OK);
						}
						Dialog_MsgBox.this.dismiss();
					}
				});

				break;
			case YESNO:

				btnOK.setVisibility(View.GONE);


				ImageButton btnYes = (ImageButton) this.findViewById(R.id.dlg_msgbox_btnYes);
				btnYes.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						if(!(Dialog_MsgBox.this.oButtonPressListener==null))
						{
							Dialog_MsgBox.this.oButtonPressListener.ButtonPressed(_BUTTON.YES);
						}
						Dialog_MsgBox.this.dismiss();
					}
				});


				ImageButton btnNo = (ImageButton) this.findViewById(R.id.dlg_msgbox_btnNo);
				btnNo.setOnClickListener(new View.OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						if(!(Dialog_MsgBox.this.oButtonPressListener==null))
						{
							Dialog_MsgBox.this.oButtonPressListener.ButtonPressed(_BUTTON.NO);
						}
						Dialog_MsgBox.this.dismiss();
					}
				});
				break;

		}
	}
	
	public Dialog_MsgBox(Context _context, int _tag, String _title, String _subtitle, String _message,  _SHOWBUTTONS _showbuttons)
	{
		this(_context, _tag, _title, _subtitle, _message, "", _showbuttons);
	}
	
	public Dialog_MsgBox(Context _context, int _tag, String _title, String _message,  _SHOWBUTTONS _showbuttons)
	{
		this(_context, _tag, _title, "", _message, "",  _showbuttons);
	}
	
	public Dialog_MsgBox(Context _context, int _tag, String _message, _SHOWBUTTONS _showbuttons)
	{
		this(_context, _tag, "", "", _message, "",  _showbuttons);
	}
	
	public Dialog_MsgBox(Context _context, String _title, String _subtitle, String _message,  _SHOWBUTTONS _showbuttons)
	{
		this(_context, -1, _title, _subtitle, _message, "", _showbuttons);
	}
	
	public Dialog_MsgBox(Context _context,  String _title, String _message,  _SHOWBUTTONS _showbuttons)
	{
		this(_context, -1, _title, "", _message, "", _showbuttons);
	}
	
	public Dialog_MsgBox(Context _context, String _message,  _SHOWBUTTONS _showbuttons)
	{
		this(_context, -1, "", "", _message, "",  _showbuttons);
	}
	
	public Dialog_MsgBox(Context _context, String _message)
	{
		this(_context, _message, _SHOWBUTTONS.OK);
	}

	public Dialog_MsgBox(Context _context, int _messageRes)
	{
		this(_context, _context.getResources().getString(_messageRes), _SHOWBUTTONS.OK);
	}
	
	public void setOnButtonPressListener(OnButtonPressListener _listener)
	{
		this.oButtonPressListener = _listener;
	}

	@Override
	public void show()
	{
		try
		{
			super.show();
		}
		catch (Exception ex)
		{
			try
			{
				cdToast.showLong(this.getContext().getApplicationContext(), oMessage);
			}
			catch (Exception ex2)
			{
				//Give UP!
			}
		}

	}

	public void showForReal()
	{
		super.show();
	}
}
