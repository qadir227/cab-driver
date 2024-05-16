package com.cabdespatch.driverapp.beta.activities2017;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.SOUNDMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;

import java.util.HashMap;
import java.util.List;

public class DriverMessage extends TickingActivity implements OnGlobalLayoutListener
{

	public static String _FROMMENU = "FROM_MENU";
	//public static String CONFIRMATION_REQUIRED = "CONF_REQUIRED";
	private boolean doScroll;
    private STATUSMANAGER.DriverMessage oMessage;
    private boolean timerCancelled;
	//private String oMessage = "";
	//Boolean confRequired;
	//private String oMessageID;
    private static final Integer COUNTDOWN_TIMEOUT = 30;

    @Override
    protected String getDebugTag()
    {
        return "DriverMessage.java";
    }


	private TextView         oLblMessage;
	private ScrollView       oScroller;
	private TextView         lblCountDown;
	private ImageButton      oBtnBack;

    //private static final Integer COUNTDOWN_TIMEOUT = 30;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_messages, menu);
		return true;
	}

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if(DriverMessage.this.getIntent().getBooleanExtra(DriverMessage._FROMMENU, false))
        {
            //DriverMessage.this.finish();
        }
        else
        {

            DriverMessage.this.oMessage = STATUSMANAGER.setDriverMessageToDisplayed(DriverMessage.this, DriverMessage.this.oMessage);
            STATUSMANAGER.releaseLock(this); //uhhh headache due???
            Globals.CrossFunctions.Back(this);
        }

        DriverMessage.this.finish();
    }

    @Override
	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		timerCancelled = false;

        this.oMessage = STATUSMANAGER.getFirstUnconfirmedDriverMessage(this);
        if(this.oMessage==null) //no messages that require confirmation...
        {
            List<STATUSMANAGER.DriverMessage> unreadMessages = STATUSMANAGER.getUnreadDriverMessages(this);
            if(unreadMessages.size() < 1) //no messages that are unread
            {
                //how did this happen?
                //finish();
            }
            else
            {
                this.oMessage = unreadMessages.get(0);
            }
        }

        if(!(this.oMessage == null))
        {
            if(DriverMessage.this.getIntent().getBooleanExtra(DriverMessage._FROMMENU, false))
            {
                //do nothing
            }
            else
            {
                STATUSMANAGER.aquireLock();
            }

            this.setContentView(R.layout.activity_driver_message);
            this.setBackground();
            this.oLblMessage = (TextView) this.findViewById(R.id.frmDriverMessage_lblMessage);
            this.oLblMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, SETTINGSMANAGER.TextTools.getTextSizeDIP(this, SETTINGSMANAGER.TextTools.SIZE.DRIVER_MESSAGE));

            this.oScroller = (ScrollView) this.findViewById(R.id.frmDriverMessage_scroller);


            View.OnClickListener listen;
            if(this.oMessage.isConfirmationRequired())
            {
                this.oBtnBack = (ImageButton) this.findViewById(R.id.frmDriverMessage_btnConfirm);
                listen = new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        DriverMessage.this.oMessage = STATUSMANAGER.setDriverMessageToDisplayed(DriverMessage.this, DriverMessage.this.oMessage);
                        DriverMessage.this.oMessage = STATUSMANAGER.setDriverMessageToConfirmed(DriverMessage.this, DriverMessage.this.oMessage);
                        BROADCASTERS.ConfirmMessageRead(DriverMessage.this, DriverMessage.this.oMessage);
                        STATUSMANAGER.releaseLock(v.getContext()); //uhhh headache due???
                        Globals.CrossFunctions.Back(v);
                        DriverMessage.this.finish();
                    }
                };

            }
            else
            {
                this.oBtnBack = (ImageButton) this.findViewById(R.id.frmDriverMessage_btnBack);
                listen = new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        DriverMessage.this.onBackPressed();
                    }
                };

            }
            this.oBtnBack.setOnClickListener(listen);
            this.oBtnBack.setVisibility(View.VISIBLE);

            this.doScroll = true;
            this.lblCountDown = (TextView) this.findViewById(R.id.frmDriverMessage_lblCountDown);

            this.lblCountDown.setText(COUNTDOWN_TIMEOUT.toString());

            if(this.oMessage.isConfirmationRequired())
            {
                //this.lblCountDown.setText("...");
                this.lblCountDown.setVisibility(View.INVISIBLE);
            }



            this.oLblMessage.setText("");

            this.oLblMessage.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    DriverMessage.this.doScroll = (!(DriverMessage.this.doScroll));
                    DriverMessage.this.timerCancelled = true;
                    DriverMessage.this.lblCountDown.setVisibility(View.INVISIBLE);

                    if (DriverMessage.this.doScroll)
                    {
                        DriverMessage.this.doScroll();
                    }
                }
            });

            this.oLblMessage.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}

	private Integer oMessageTimeout;
	private Integer aSecondsEllapsed;
    private final Integer SECONDS_ELLAPSED_MESSAGE_SHOWN_THRESHOLD = 20;

    @Override
    protected void onTick(long millis)
    {
        aSecondsEllapsed = COUNTDOWN_TIMEOUT - getSeconds(millis);
        if(!(this.timerCancelled))
        {
            updateCountdown();
        }
    }

    private void updateCountdown()
    {
    	try
		{
			if(aSecondsEllapsed <= 0)
			{
				DriverMessage.this.lblCountDown.setText(String.valueOf(0));

				if(DriverMessage.this.oMessage.isConfirmationRequired())
				{
					//do nothing
				}
				else
				{
					if(DriverMessage.this.getIntent().getBooleanExtra(DriverMessage._FROMMENU, false))
					{
						DriverMessage.this.finish();
					}
					else
					{
						DriverMessage.this.oBtnBack.performClick();
					}
				}
			}
			else
			{
				if(aSecondsEllapsed < SECONDS_ELLAPSED_MESSAGE_SHOWN_THRESHOLD)
				{
					//the message has been shown for at least 10 seconds. mark it as displayed
					STATUSMANAGER.setDriverMessageToDisplayed(DriverMessage.this, DriverMessage.this.oMessage);
				}

				if(!(DriverMessage.this==null))
				{
					if(!(DriverMessage.this.lblCountDown==null))
					{
						DriverMessage.this.lblCountDown.setText(String.valueOf(aSecondsEllapsed));
					}
				}
			}
		}
		catch (NullPointerException ex)
		{
			//never mind?
		}

    }

    @Override
	public void onPause()
	{
		super.onPause();
		this.oScroller = null;

		this.finish();
	}


	protected void newMessage(String _message, Boolean _addHandler)
	{
        try
        {
            //ImageButton btnBack = (ImageButton) this.findViewById(R.id.frmDriverMessage_btnBack);
            //btnBack.setVisibility(View.VISIBLE);
            this.oLblMessage.setText(this.oLblMessage.getText() + "\n\n\n" + _message);
            //this.lblCountDown.setText("30");
            if(this.oMessage.isConfirmationRequired())
            {
                //this.lblCountDown.setText("...");
                this.lblCountDown.setVisibility(View.INVISIBLE);
            }
            else
            {
                this.lblCountDown.setVisibility(View.VISIBLE);
            }

            DriverMessage.this.doScroll = true;

            if(_addHandler)
            {
                try
                {
                    this.oScroller.getHandler().post(new Runnable(){

                        @Override
                        public void run()
                        {
                            DriverMessage.this.doScroll();

                        }});

                }catch(Exception ex){}
            }


            if (!(this.getIntent().getBooleanExtra(DriverMessage._FROMMENU, false)))
            {
                if( this.oMessage.getMode() == STATUSMANAGER.DriverMessage.MODE.DISPLAY_AND_SPEAK)
                {
                    SOUNDMANAGER.say(this, _message);
                }
                else
                {
                    if(_message.toUpperCase().contains("PRICE AMEND:"))
                    {
                        if(SETTINGSMANAGER.SETTINGS.SPEAK_PRICES.parseBoolean(this))
                        {
                            SOUNDMANAGER.announcePrice(this, STATUSMANAGER.getCurrentJob(this).getPrice());
                        }
                        else
                        {
                            SOUNDMANAGER.playNewMessageSound(this);
                        }

                    }
                    else
                    {
                        SOUNDMANAGER.playNewMessageSound(this);
                    }
                }

            }
        }
        catch (Exception ex)
        {
            ErrorActivity.genericReportableError(ex, "Error in DriverMessage.newMessage()");
        }

	}

	private void announceOrRead(STATUSMANAGER.DriverMessage _m)
    {
        if(_m.getMode().equals(STATUSMANAGER.DriverMessage.MODE.DISPLAY_AND_SPEAK))
        {
            SOUNDMANAGER.say(this, _m.getMessage());
        }
        else
        {
            SOUNDMANAGER.playNewMessageSound(this);
        }
    }
	
	private void doScroll()
	{
		if(!(this.oScroller==null))
		{
			if(doScroll)
			{
				View c = (View) this.oScroller.findViewById(R.id.frmDriverMessage_lblMessage);
				int diff = (c.getBottom() - (this.oScroller.getHeight() + this.oScroller.getScrollY()));
				
				if(diff <=0)
				{
					this.oScroller.scrollTo(0, 0);
				}
				
				this.oScroller.smoothScrollBy(0, 1);
				
				Handler h = this.oScroller.getHandler();
				
				if(!(h==null))
				{
					h.postDelayed(new Runnable(){

						@Override
						public void run()
						{
							DriverMessage.this.doScroll();
							
						}}, 10);

				}
			}
		}		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onGlobalLayout()
	{
		try
        {

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            {
                this.oLblMessage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
            else
            {
                this.oLblMessage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }


            if(this.oMessage.isConfirmationRequired())
            {
                //do nothing
                this.oLblMessage.setPadding(0, 10, 0, 10);
            }
            else
            {
                //pad the message
                int txtHeight = this.oLblMessage.getHeight();
                this.oLblMessage.setPadding(0, (int) Math.ceil(txtHeight) / 2, 0, (int) Math.ceil(txtHeight) + 10);
            }





            //cdToast.showShort(this, "posting doScroll");
            if(doScroll)
            {
                this.oScroller.getHandler().postDelayed(new Runnable(){

                    @Override
                    public void run()
                    {
                        DriverMessage.this.newMessage(DriverMessage.this.oMessage.getMessage(), (!(DriverMessage.this.oMessage.isConfirmationRequired())));

                    }}, 100);
            }

        }
	    catch (Exception ex)
        {
            ErrorActivity.genericReportableError(ex, new HashMap<String, String>());
        }
		
	}

}
