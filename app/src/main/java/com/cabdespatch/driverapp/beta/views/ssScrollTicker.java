package com.cabdespatch.driverapp.beta.views;

import android.view.Display;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;

/* Thread safe scroll view with auto scroll and wrap around
 * * 
 */

public class ssScrollTicker
{
	int speed;
	Display display;
	boolean scrolling;
	boolean hasMoved;
	int pausedX;
	ScrollView s;
	
	public ssScrollTicker(ScrollView _s, int _speed, Display _display, boolean _scrollingAtStart)
	{
		//pass in display with activity.getWindowManager().getDefaultDisplay();
		s = _s;
		speed = _speed;
		display = _display;
		scrolling = _scrollingAtStart;
		pausedX = 0;
		
				
		Thread Scroller = new scroller(_s);
		Scroller.start();
			
	}
	
	public void SetOnTouchListener(OnTouchListener o)
	{
		s.setOnTouchListener(o);
	}
	
	
	public void addPadding()
	{
		try
		{
			View c = (View) s.getChildAt(s.getChildCount()-1);
		
		
			c.setPadding(c.getPaddingLeft(), display.getHeight(), c.getPaddingRight(), display.getHeight());
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void removePadding()
	{
	try
		{
			View c = (View) s.getChildAt(s.getChildCount()-1);
		
		
			c.setPadding(c.getPaddingLeft(), c.getPaddingLeft(), c.getPaddingRight(), c.getPaddingRight());
		}
		catch (Exception ex)
		{
			
		}
	}
	
	public void scrollBy(final int x, final int y)
	{

		try
		{
			s.getHandler().post(new Runnable(){
				
				@Override
				public void run()
				{
					s.scrollBy(x, y);
					
				}});
		} catch (java.lang.NullPointerException ex ) {} //possible the element no longer exists
		
	}
	
	public void registerScrollPos()
	{
		pausedX = s.getScrollY();
	}

	public boolean hasMoved()
	{
		if (pausedX == s.getScrollY())
		{
			//pausedX = s.getScrollY();
			return false;
		}
		else
		{
			//pausedX = s.getScrollY();
			return true;
		}
	}
	
	public void scrollTo(final int x, final int y)
	{
		try
		{
		s.getHandler().post(new Runnable(){
			
			@Override
			public void run()
			{
				s.scrollTo(x, y);
				
			}});
		} catch (java.lang.NullPointerException ex ) {} //possible the element no longer exists
	}
	
	public void startScroll()
	{
		scrolling = true;
	}
	
	public void stopScroll()
	{
		scrolling = false;
	}
	
	public boolean IsScrolling()
	{
		return scrolling;
	}
	
	public void setSpeed(int _speed)
	{
		speed = _speed;
	}
	
	public void addView(View v)
	{
		s.addView(v);
	}
		
	class scroller extends Thread
	{
		ScrollView v;
		
		scroller(ScrollView _v)
		{
			v = _v;
		}

		@Override
		public void run()
		{
		while (true)
		{
				if (scrolling)
				{
					try
					{
						v.getHandler().post(new Runnable(){
			
							@Override
							public void run()
							{
								v.smoothScrollBy(0, 3);
								
							}});
						
						View c = (View) v.getChildAt(v.getChildCount()-1);
						
						int diff = (c.getBottom() - (v.getHeight() + v.getScrollY()));
						
						if (diff <= 0)
						{
							v.getHandler().post(new Runnable(){
			
								@Override
								public void run()
								{
									v.scrollTo(0, 0);
									
								}});
							
						}
					}
					catch (Exception ex)
					{
						
					}
				
				}
				
				try
				{
					Thread.sleep(speed);
				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
			
		}
		
	}

}
