package com.cabdespatch.driverapp.beta;

import java.util.LinkedList;
import java.util.Queue;
//unique queue - no duplicates allowed
public class sQueue 
{
	Queue<String> data;
	
	
	public sQueue()
	{
		data = new LinkedList<String>();
	}
	
	public void add (String _item)
	{
		
		synchronized(this)
		{	
			boolean matchFound = false;
			for (String s:data)
			{
				if (s.equals(_item))
				{
					matchFound = true;
				}
			}
			
			if (!(matchFound))
			{
				data.add(_item);
			}
		}
	}
	
	public String poll()
	{
		synchronized(this)
		{
			return data.poll();
		}
	}
	
	public String remove()
	{
		synchronized(this)
		{
			return data.remove();
		}
	}
	
	public int size()
	{
		synchronized(this)
		{
			return data.size();
		}
	}
	
}
