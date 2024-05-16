package com.cabdespatch.driverapp.beta.activities2017;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.cabdespatch.driverapp.beta.DEBUGMANAGER;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER.DriverMessage.BOX;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class ViewMessages extends AnyActivity implements PopupMenu.OnMenuItemClickListener
{
	ViewFlipper flip;
	int currentMessage;
	int messageCount;
	SimpleDateFormat headerformat;

    ImageButton btnMenu;
	
	
	@Override
	public void onCreate(Bundle _savedState)
	{
		super.onCreate(_savedState);
		this.setContentView(R.layout.activity_viewinflipper);
		this.setBackground();
		
		TextView lblTitle = (TextView) this.findViewById(R.id.frmViewInFlipper_lblTitle);
		lblTitle.setText(R.string.messages);
		
		this.flip = (ViewFlipper) this.findViewById(R.id.frmViewInFlipper_flip);
		this.headerformat = new SimpleDateFormat("'received at ' HH:mm'\non' dd/MM/yyyy");
		
		List<com.cabdespatch.driverapp.beta.STATUSMANAGER.DriverMessage> items = STATUSMANAGER.getDriverMessages(this, BOX.INBOX);
        Collections.reverse(items);

        this.currentMessage = 1;
		this.messageCount = items.size();
		
		for (STATUSMANAGER.DriverMessage m:items)
		{
			this.flip.addView(this.getMessageView(m));
		}
		
		//this.flip.setOnTouchListener(Globals.FlipperTouch());

		ImageButton btnBack = (ImageButton) this.findViewById(R.id.frmViewInFlipper_btnBack);
		btnBack.setOnClickListener(new View.OnClickListener()
		{
            @Override
            public void onClick(View view)
            {
                ViewMessages.this.finish();
            }
        });

        this.btnMenu = (ImageButton) this.findViewById(R.id.frmViewInFlipper_btnMenu);
		btnMenu.setOnClickListener(new View.OnClickListener()
		{
            @Override
            public void onClick(View view)
            {
                ViewMessages.this.showMenu();
            }
        });
		
	}

    private void showMenu()
    {
        PopupMenu p = new PopupMenu(this, this.btnMenu);
        p.inflate(R.menu.view_messages);
        p.setOnMenuItemClickListener(this);
        p.show();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		showMenu();
		return true;
	}
	

	
	private View getMessageView(final STATUSMANAGER.DriverMessage _m)
	{
		View v = this.getLayoutInflater().inflate(R.layout.messageitem, null);
		v.setTag(_m.getMessage());

        TextView hcount = (TextView) v.findViewById(R.id.msgitemcount);
		TextView header = (TextView) v.findViewById(R.id.msgitemdetails);
		
		String datetime = this.headerformat.format(_m.getDateTime());
		String count = "(" + String.valueOf(this.currentMessage++) + "/" + String.valueOf(this.messageCount) + ")";

        hcount.setText(count);
		header.setText(datetime);
		
		TextView t = (TextView) v.findViewById(R.id.messageitem_messagetext);
		t.setText(_m.getMessage());

        ScrollView s = (ScrollView) v.findViewById(R.id.scroller);
		
		s.setOnTouchListener(new ActivitySwipeDetector(this));
		
		return v;
	}



    @Override
	public void onPause()
	{
		super.onPause();
	}

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.action_sendnewmessage:
                Intent i = new Intent(this, ComposeMessage.class);
                i.setAction(ComposeMessage._MESSAGE_TYPE.DATA_MESSAGE);
                Globals.StartActivity(this, i);
                break;
            /*case R.id.action_showthismessage:
                View v = this.flip.getChildAt(this.flip.getDisplayedChild());
                String message = v.getTag().toString();

                Intent i2 = new Intent(this, DriverMessage.class);
                i2.putExtra(DriverMessage._FROMMENU, true);
                i2.putExtra(DataService._MESSAGEEXTRA, message);
                Globals.StartActivity(this, i2);
                break;
                */
        }

        return true;
    }



    private class ActivitySwipeDetector implements View.OnTouchListener {

        private Activity activity;
        static final int MIN_DISTANCE = 100;
        private float downX, downY, upX, upY;

        public ActivitySwipeDetector(final Activity activity) {
            this.activity = activity;
        }

        public final void onRightToLeftSwipe()
        {
            if(ViewMessages.this.flip.getChildCount() > 1)
            {
                flip.setInAnimation(activity, R.anim.in_from_right);
                flip.setOutAnimation(activity, R.anim.out_to_left);
                ViewMessages.this.flip.showNext();
            }

            //Log.i("SWIPE", "RightToLeftSwipe!");
        }

        public void onLeftToRightSwipe()
        {
            if(ViewMessages.this.flip.getChildCount() > 1)
            {
                flip.setInAnimation(activity, R.anim.in_from_left);
                flip.setOutAnimation(activity, R.anim.out_to_right);
                ViewMessages.this.flip.showPrevious();
            }
            //Log.i("SWIPE", "LeftToRightSwipe!");
        }

        public void onTopToBottomSwipe(){
            //Log.i( "onTopToBottomSwipe!");
        }

        public void onBottomToTopSwipe(){
            //Log.i( "onBottomToTopSwipe!");
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    downY = event.getY();
                    //   return true;
                }
                case MotionEvent.ACTION_UP: {
                    upX = event.getX();
                    upY = event.getY();

                    float deltaX = downX - upX;
                    float deltaY = downY - upY;

                    // swipe horizontal?
                    if(Math.abs(deltaX) > MIN_DISTANCE){
                        // left or right
                        if(deltaX < 0) { this.onLeftToRightSwipe(); return true; }
                        if(deltaX > 0) { this.onRightToLeftSwipe(); return true; }
                    } else { DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_DEBUG,"SWIPE", "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }

                    // swipe vertical?
                    if(Math.abs(deltaY) > MIN_DISTANCE){
                        // top or down
                        if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
                        if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
                    } else { DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_DEBUG, "SWPIE", "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }

                    //     return true;
                }
            }
            return false;
        }
    }
}
