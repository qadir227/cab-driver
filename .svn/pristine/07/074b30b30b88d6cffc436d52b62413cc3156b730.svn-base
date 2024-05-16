package com.cabdespatch.driverapp.beta.activities2017;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cabdespatch.driverapp.beta.AndroidBug5497Workaround;
import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.UnfairMeterLocal;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._BUTTON;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox._SHOWBUTTONS;

import java.io.File;
import java.util.Date;

public class ComposeMessage extends AnyActivity
{
	public static class _MESSAGE_TYPE
	{
		public static String NO_SHOW = "NO_SHOW";
		public static String DATA_MESSAGE = "DATA_MESSAGE";
		public static String DEBUG_DATA = "DEBUG";
	}
	
	public static String NEW_MESSAGE = "(new...)";
	
	ListView lstPresets;
	EditText txtMessage;
	CheckBox chkSave; 
	ViewFlipper flip;
	ImageButton btnBack;

    @Override
	public void onCreate(Bundle savedState)
	{
		super.onCreate(savedState);
		this.setContentView(R.layout.activity_composemessage);
		this.setBackground();
		AndroidBug5497Workaround.assistActivity(this);

        this.flip = (ViewFlipper) this.findViewById(R.id.frmCompose_flip);
        this.txtMessage = (EditText) this.findViewById(R.id.frmCompose_txtCustomMessage);
        this.lstPresets = (ListView) this.findViewById(R.id.frmCompase_lstPresets);

        this.setupListAdapter();

        /*
        if(SETTINGSMANAGER.SETTINGS.SMALL_UI.parseBoolean(this))
        {
            final ViewGroup layTitle = (ViewGroup) this.findViewById(R.id.compose_message_title);

            this.txtMessage.setOnFocusChangeListener(new View.OnFocusChangeListener()
            {
                @Override
                public void onFocusChange(View v, boolean hasFocus)
                {
                    layTitle.setVisibility(hasFocus?View.GONE:View.VISIBLE);
                }
            });
        }*/


        this.btnBack = (ImageButton) this.findViewById(R.id.frmCompose_btnBack);
        View.OnClickListener l = new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ComposeMessage.this.onBackPressed();
            }
        };

        this.btnBack.setOnClickListener(l);

        this.chkSave = (CheckBox) this.findViewById(R.id.frmCompose_chkSave);
        ImageButton btnSendCustom = (ImageButton) this.findViewById(R.id.frmCompose_btnSendMessage);
        btnSendCustom.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
            	Boolean finishAfter = true;

                String item = ComposeMessage.this.txtMessage.getText().toString();
                String action = ComposeMessage.this.getIntent().getAction();
                //String messageData = "";

                if(action.equals(_MESSAGE_TYPE.DATA_MESSAGE))
                {
                    if(item.equals("METER")){
						String extra = UnfairMeterLocal.getSummary();

						STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.MODE.DEFAULT, STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
																						STATUSMANAGER.DriverMessage.BOX.INBOX,
																						new Date().getTime(),
																						extra,
																						false, true);
						STATUSMANAGER.addDriverMessage(ComposeMessage.this, m);
					}
					else if(item.equals("METERL"))
					{
						File path = Environment.getExternalStoragePublicDirectory(
								Environment.DIRECTORY_DOCUMENTS);

						path.mkdirs();

						File logfile = new File (path, "cabdespatch_meter.log");
						if (logfile.exists())
						{
							logfile.delete();
						}

						Globals.FileTools.writeStringToFile(UnfairMeterLocal.getLog(), logfile.getAbsolutePath());


						cdToast.show(ComposeMessage.this, "Generating email...", Toast.LENGTH_LONG);

                    	Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("text/plain");
						intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@cabdespatch.com"});
						intent.putExtra(Intent.EXTRA_SUBJECT, "Meter Log");
						intent.putExtra(Intent.EXTRA_TEXT, "Please see file attached");

						Uri uri = Uri.fromFile(logfile);
						intent.putExtra(Intent.EXTRA_STREAM, uri);

						startActivity(Intent.createChooser(intent, "Send Email"));

						finishAfter = false;
					}
					else{
						BROADCASTERS.DriverMessage(ComposeMessage.this, item);
						if(ComposeMessage.this.chkSave.isChecked())
						{
							SETTINGSMANAGER.addDriverMessagePreset(ComposeMessage.this, item);
						}	
					}
					
					
                }
                else if(action.endsWith(_MESSAGE_TYPE.NO_SHOW))
                {
                    BROADCASTERS.NoShow(ComposeMessage.this, item);
                    if(ComposeMessage.this.chkSave.isChecked())
                    {
                        SETTINGSMANAGER.addNoShowMessagePreset(ComposeMessage.this, item);
                    }
                }
                else
                {
                    throw new UnsupportedOperationException("Invalid message type on send");
                }


                if(finishAfter)
				{
					cdToast.show(ComposeMessage.this, "Message Sent", Toast.LENGTH_LONG);
					ComposeMessage.this.finish();
				}


            }
        });
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		this.finish();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();

	}
	
	protected void setupListAdapter()
	{
		String launchreason = this.getIntent().getAction();
		this.lstPresets.setAdapter(new PresetAdapter(this, launchreason));

		if(launchreason.equals(_MESSAGE_TYPE.DEBUG_DATA))
		{
			this.lstPresets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> _aview, View _view,
											   int _position, long _id) {
					String data = ((PresetAdapter) _aview.getAdapter()).getItem(_position);
					final String item = (data == null ? ComposeMessage.NEW_MESSAGE : data);

					Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
					intent2.setType("text/plain");
					intent2.putExtra(Intent.EXTRA_TEXT, item);
					startActivity(Intent.createChooser(intent2, "Share via"));

					return true;
				}
			});
		}
		else
		{
			this.lstPresets.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> aview, View v, int position, long arg3) {
					String data = ((PresetAdapter) aview.getAdapter()).getItem(position);
					String item = (data == null ? ComposeMessage.NEW_MESSAGE : data);
					ComposeMessage.this.txtMessage.setText("");

					if (!(item.equals(ComposeMessage.NEW_MESSAGE))) {
						//use append to ensure that the cursor goes to the end of the edittext...
						ComposeMessage.this.txtMessage.append(data);
					}
					ComposeMessage.this.flip.setInAnimation(ComposeMessage.this, R.anim.in_from_right);
					ComposeMessage.this.flip.setOutAnimation(ComposeMessage.this, R.anim.out_to_left);

					ComposeMessage.this.flip.getInAnimation().setDuration(250);
					ComposeMessage.this.flip.getOutAnimation().setDuration(250);
					ComposeMessage.this.flip.showNext();

					ComposeMessage.this.txtMessage.setEnabled(true);
					ComposeMessage.this.txtMessage.requestFocus();


					InputMethodManager inputManager = (InputMethodManager)
							getSystemService(Context.INPUT_METHOD_SERVICE);

					inputManager.showSoftInput(ComposeMessage.this.txtMessage, InputMethodManager.SHOW_IMPLICIT);
				}

			});
			this.lstPresets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
			{

				@Override
				public boolean onItemLongClick(AdapterView<?> _aview, View _view,
											   int _position, long _id)
				{
					String data = ((PresetAdapter) _aview.getAdapter()).getItem(_position);
					final String item = (data==null?ComposeMessage.NEW_MESSAGE:data);

					if(!(item.equals(ComposeMessage.NEW_MESSAGE)))
					{
						final String prompt = "Delete message: \n\n " + item +"?";

						Dialog_MsgBox d = new Dialog_MsgBox(getApplicationContext(), prompt, _SHOWBUTTONS.YESNO);
						d.setOnButtonPressListener(new Dialog_MsgBox.OnButtonPressListener()
						{

							@Override
							public void ButtonPressed(_BUTTON _button)
							{

								if(_button.equals(_BUTTON.YES))
								{
									String action = ComposeMessage.this.getIntent().getAction();
									if(action.equals(_MESSAGE_TYPE.DATA_MESSAGE))
									{
										SETTINGSMANAGER.removeDriverMessage(ComposeMessage.this, item);
										ComposeMessage.this.setupListAdapter();
									}
									else if(action.endsWith(_MESSAGE_TYPE.NO_SHOW))
									{
										SETTINGSMANAGER.removeNoShowMessage(ComposeMessage.this, item);
										ComposeMessage.this.setupListAdapter();
									}
									else
									{
										throw new UnsupportedOperationException("Invalid message type on delete");
									}
								}

							}
						});
						d.show();
					}

					return true;
				}
			});
		}


	}

    @Override
    public void onBackPressed()
    {
        if(ComposeMessage.this.flip.getDisplayedChild()==1)
        {
            ComposeMessage.this.flip.setInAnimation(ComposeMessage.this, R.anim.in_from_left);
            ComposeMessage.this.flip.setOutAnimation(ComposeMessage.this,R.anim.out_to_right);

            ComposeMessage.this.flip.getInAnimation().setDuration(250);
            ComposeMessage.this.flip.getOutAnimation().setDuration(250);
            ComposeMessage.this.flip.showPrevious();
            ComposeMessage.this.txtMessage.setEnabled(false);
            ComposeMessage.this.lstPresets.requestFocus();

            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        else
        {
            finish();
        }
    }
	
	private class PresetAdapter extends BaseAdapter
	{

		String oAdapterType;
		String[] oItems;
		
		public PresetAdapter(Context _c, String _messageType)
		{
			this.oAdapterType = _messageType;
			
			if(_messageType.equals(_MESSAGE_TYPE.DATA_MESSAGE))
			{
				this.oItems = SETTINGSMANAGER.getDriverMessagePresets(_c); 
			}
			else if(_messageType.equals(_MESSAGE_TYPE.NO_SHOW))
			{
				this.oItems = SETTINGSMANAGER.getNoShowReasonPresets(_c);
			}
			else if(_messageType.equals(_MESSAGE_TYPE.DEBUG_DATA))
			{
				this.oItems = SETTINGSMANAGER.getDebugList(_c);
			}
			else
			{
				throw new UnsupportedOperationException();
			}
		}
		
		public String getAdapterType()
		{
			return this.oAdapterType;
		}
		
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return this.oItems.length;
		}

		@Override
		public String getItem(int position)
		{
			return this.oItems[position];
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			//CLAY!
			LinearLayout l = (LinearLayout) ComposeMessage.this.getLayoutInflater().inflate(R.layout.row_plot, null);			
			TextView t = (TextView) l.findViewById(R.id.plotRow_label);
						
			t.setText(this.oItems[position]);
			
			return l;
		}

	}
}
