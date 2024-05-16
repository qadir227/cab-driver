package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.cabdespatch.driverapp.beta.services.DataService;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.regex.Pattern;

public class SOUNDMANAGER
{
	private static MediaPlayer mp;
	protected static TextToSpeech Speaker;
	protected static Queue<String> soundfiles;


	public static void say(Context _c, String _text) { speak(_c, _text);}

	private static void speak(Context _c, String _speak)
	{
		Intent i = new Intent(BROADCASTERS.SPEAK);
		i.putExtra(DataService._MESSAGEDATA, _speak);
		
		_c.sendBroadcast(i);
	}

    private static void speak(Context _c, int _resID)
    {
        String say = _c.getResources().getString(_resID);
        SOUNDMANAGER.speak(_c, say);
    }
	
	public static void playJobOfferSound(Context _c)
	{
        SOUNDMANAGER.stopCurrentSound(_c);

        File f = new File(Globals.getJobOfferSoundsFileLocation(_c));
        File[] files = f.listFiles();

        List<File> suitableFiles = new LinkedList<File>();
        for(File pendingFile:files)
        {
            if(!(pendingFile.isDirectory()))
            {
                if(!(pendingFile.isHidden()))
                {
                    suitableFiles.add(pendingFile);
                }
            }
        }

        if(suitableFiles.size()>0)
        {
			if(suitableFiles.size() > 1)
			{
				File playFile = suitableFiles.get(new Random().nextInt(suitableFiles.size()-1));
				playNow(_c, playFile);
			}
			else
			{
				playNow(_c, suitableFiles.get(0));
			}

        }
        else
        {
            playSoundFromAssets(_c, "job_offer_error.ogg");
        }



	}

	public static void panic(Context _c)
	{
		playSoundFromAssets(_c, "panic.ogg");
	}
	
	public static void playNewMessageSound(Context _c)
	{
		//playNow(new File(Globals.getNewMessageSoundsFileLocation(_c), "newmessage1.wav"));
		
		SOUNDMANAGER.speak(_c, "You have a new message");
	}
	
	public static void playWorkAvailableSound(Context _c, List<String> _shortPlotNames)
	{
		
		String announce = "There is work available in    ";
		
		int count = _shortPlotNames.size();
		int current = 0;
		for(String s:_shortPlotNames)
		{
			current++;
			String say = SETTINGSMANAGER.getPlotsAll(_c).getPlotByShortName(s).getSpeachName();

				if((current==count) && (count >1))
				{
					announce +=" and ";
				}
				
				announce += (say + ", ");
				
		}
		
		SOUNDMANAGER.speak(_c, announce);
		//enqueueSound(new File(Globals.getSoundsFileLocation(_c), "workavail.wav"));
	}
	
	public static void playPlotAnnouncement(Context _c, plot p)
	{
		//enqueueSound(new File(Globals.getSoundsFileLocation(_c), "youarein.wav"));
		//enqueueSound(new File(Globals.getZoneSoundsFileLocation(_c), _shortPlotName + ".wav"));
		
		//Speak(_c, "You Are In " + _shortPlotName);
		//Speak(_c, _shortPlotName);
        if(p.isRank())
        {
            SOUNDMANAGER.speak(_c, R.string.you_are_on_rank);
        }
        else
        {
            SOUNDMANAGER.speak(_c, "You are now in " + p.getSpeachName());
        }

		//a.speak("หัวหิน");
	}
	
		
	public static void stopCurrentSound(Context _c)
	{
		setupMediaPlayer();
		if (mp.isPlaying())
		{
			soundComplete(_c);
		}
	}
	
	public static void stopCurrentSpeaking(Context _c)
	{
		SOUNDMANAGER.speak(_c, "");
	}
	
	private static void playNow(Context _c, File file)
	{
		setupMediaPlayer();
		
		String filename = file.getAbsolutePath();
		
		try
		{
			mp.stop();
			mp.reset();
			mp.setOnCompletionListener(soundFinished(_c));
			mp.setDataSource(filename);
			mp.prepare();
			mp.start();
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    private static void playSoundFromAssets(Context _c, String _filename)
    {
        setupMediaPlayer();

        try
        {
            AssetFileDescriptor afd = _c.getAssets().openFd(_filename);

            mp.stop();
            mp.reset();
            mp.setOnCompletionListener(soundFinished(_c));
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	
	private static void enqueueSound(Context _c, File file)
	{
		setupMediaPlayer();
		String filename = file.getAbsolutePath();
		
		if(mp.isPlaying())
		{
			if (soundfiles==null)
			{
				soundfiles = new LinkedList<String>();
			}
			
			soundfiles.add(filename);
		}
		else
		{
			playNow(_c, file);
		}
	}
	
	private static void setupMediaPlayer()
	{
		if(mp==null)
		{
			mp = new MediaPlayer();

		}
		
		
	}

	private static class MediaPlayerCompletionListenerWithContext implements MediaPlayer.OnCompletionListener
    {
        private Context oContext;

        public MediaPlayerCompletionListenerWithContext(Context _c)
        {
            oContext = _c;
        }

        @Override
        public final void onCompletion(MediaPlayer mp)
        {
            SOUNDMANAGER.soundComplete(oContext);
        }
    }

	private static MediaPlayerCompletionListenerWithContext soundFinished(Context _c)
	{
		return new MediaPlayerCompletionListenerWithContext(_c);
	}

	private static String soundCompletionAnnouncement = "";

	protected static void soundComplete(Context _c)
	{
		
		if(!(SOUNDMANAGER.soundfiles == null))
		{
			String nextFile = SOUNDMANAGER.soundfiles.poll();
			if(!(nextFile==null))
			{
				SOUNDMANAGER.playNow(_c, new File(nextFile));
			}
		}
		else
		{
			mp.stop();
			mp.reset();
			mp.setOnCompletionListener(soundFinished(_c));

            if(soundCompletionAnnouncement.equals(""))
            {
                //do nothing
            }
            else
            {
                SOUNDMANAGER.speak(_c, soundCompletionAnnouncement);
                soundCompletionAnnouncement = "";
            }
		}
	}

	public static void announceFlightModeDisabled(Context _c)
	{
		SOUNDMANAGER.speak(_c, _c.getString(R.string.speak_flight_mode_disabled));
	}

	public static void announceLostSignal(Context _c)
	{
        Intent i = new Intent(BROADCASTERS.ANNOUNCE_NO_SIGNAL);
        _c.sendBroadcast(i);

	}

	public static void announceFlightModeEnabled(Context _c)
	{
		SOUNDMANAGER.speak(_c,_c.getString(R.string.speak_flight_mode_enabled));
	}

	public static void announceWorkWaitingAtDestination(Context _c)
	{
		SOUNDMANAGER.speak(_c, R.string.speak_work_waiting_at_destination);
	}

	public static void askIfPob(Context _c)
	{
		SOUNDMANAGER.speak(_c, R.string.speak_are_you_POB);
	}

	public static void askIfMoved(Context _c)
	{
		SOUNDMANAGER.speak(_c, R.string.speak_have_you_moved);
	}

	public static void speakTag(View _v)
	{
		String say = String.valueOf(_v.getTag());
		SOUNDMANAGER.speak(_v.getContext(), say);
	}

	public static void speakThankyou(Context _c)
	{
		SOUNDMANAGER.speak(_c, "Thank you");
	}

	public static void announcePrice(Context _c, String _price)
	{
		String [] price = _price.split(Pattern.quote("."));
		String announce = _c.getString(R.string.your_price_is);
		announce += (" " + price[0] + " " + _c.getString(R.string.pounds));
		if(price.length == 2)
		{
            Double pennies = 0d;
            try
            {
                pennies = Double.valueOf(price[1]);
            }
            catch (Exception ex)
            {
                //CLAY
            }
            if(pennies > 0)
            {
                announce += (" and " + price[1] + " " + _c.getString(R.string.pence));
            }

		}
        SOUNDMANAGER.speak(_c, announce);
	}

    public static void playAutoJobSound(Context _c)
    {
        soundCompletionAnnouncement = _c.getString(R.string.auto_job);
        playSoundFromAssets(_c, "autojob.ogg");
    }
}
