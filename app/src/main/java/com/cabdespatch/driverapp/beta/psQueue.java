package com.cabdespatch.driverapp.beta;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
//unique queue with priority - no duplicates allowed
public class psQueue 
{
	volatile Queue<priorityString> data;

	public psQueue()
	{
		data = new LinkedList<priorityString>();
	}
	
	public void add (priorityString _item)
	{
		synchronized(this)
		{

			boolean matchFound = false;

            if(data.size() > 0)
            {
                for (priorityString s:data)
                {
                    if (s.equals(_item))
                    {
                        matchFound = true;
                        break;
                    }
                }
            }
			
			if (!(matchFound))
			{
				data.add(_item);
			}
			
		}
	}
	

	@SuppressWarnings("unchecked")
	public priorityString poll()
	{
		synchronized(this)
		{				
			try
			{
				Collections.sort((List<priorityString>) data);
			} catch (Exception ex) {}
		}
		return data.poll();
	}
	/*
	@SuppressWarnings("unchecked")
	public priorityString remove()
	{
		synchronized(this)
		{
			try
			{
				Collections.sort((List<priorityString>) data);
			} catch (Exception ex) {}
			return data.remove();
		}
	}
	*/
	public int size()
	{
		if(data==null) {return 0; }
		else {return data.size();}
	}


	public Queue<priorityString> flush()
	{
		synchronized(this)
		{
			Queue<priorityString> ps = data;
			data = new LinkedList<priorityString>();
			return ps;
		}		
	}
	
	public Queue<priorityString> getAndRemoveItemsAtPriorityLevel(int _priority)
	{
		Queue<priorityString> itemsWithPriority = new LinkedList<priorityString>();
		Queue<priorityString> itemsWithOutPriority = new LinkedList<priorityString>();
		
		synchronized(data)
		{
			while (data.size() > 0)
			{
				if(data==null)
				{
					//do nothing
				}
				else
				{
					priorityString ps = data.poll();

					if(ps==null)
					{
						//I don't know why this is happenign as we are synchronised
						//but it is happening...

						//do nothing
					}
					else
					{
						if (ps.getPriority() == _priority)
						{
							itemsWithPriority.add(ps);
						}
						else
						{
							itemsWithOutPriority.add(ps);
						}
					}
				}

			}

		}

		data = itemsWithOutPriority;
		return itemsWithPriority;
		
	}
	
}
