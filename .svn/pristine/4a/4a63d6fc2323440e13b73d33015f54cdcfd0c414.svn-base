package com.cabdespatch.driverapp.beta.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

import com.cabdespatch.driverapp.beta.BROADCASTERS;

import java.util.Locale;

public class SpeakService extends Service implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener
{
    protected TextToSpeech speaker;
    protected MessageHandler oMessageHandler;
    private boolean canSpeak = false;
    public static boolean RUNNING = false;


    private class MessageHandler extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context _context, Intent _intent)
        {
            String action = _intent.getAction();

            if(action.equals(BROADCASTERS.SPEAK))
            {
                String say = _intent.getStringExtra(DataService._MESSAGEDATA);
                SpeakService.this.speak(say);
            }
            else if(action.equals(BROADCASTERS.ANNOUNCE_NO_SIGNAL))
            {
                /*
                class FifteenSecondCheck extends PauseAndRun
                {
                    @Override
                    protected void onPostExecute(Void _void)
                    {
                        Boolean nowHasNetworkConnection =STATUSMANAGER.getBoolean(SpeakService.this, STATUSMANAGER.STATUSES.HAS_NETWORK_CONNECTION);
                        if(!(nowHasNetworkConnection))
                        {
                            String say = SpeakService.this.getString(R.string.speak_lost_singal);
                            SpeakService.this.speak(say);
                        }
                    }
                }
                new FifteenSecondCheck().Start(15000);*/
            }

        }

    }


    private void speak(final String _speak)
    {
        class SpeakSpeak extends Thread
        {
            @Override
            public void run()
            {

                //wait for up to ten seconds, otherwise discard the request
                for (int x = 0; x <= 10; x++)
                {
                    try
                    {
                        if (SpeakService.this.canSpeak)
                        {
                            // SpeakService.this.canSpeak = false;
                            SpeakService.this.speaker.speak(_speak, TextToSpeech.QUEUE_FLUSH, null);
                            break;
                        }
                        else
                        {
                            Thread.sleep(1000);
                        }
                    }
                    catch (Exception ex) {}
                }
            }
        }

        new SpeakSpeak().start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);

        /*class stayalive extends Thread
        {
            @Override
            public void run()
            {
                while (SpeakService.RUNNING)
                {
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        }*/

        SpeakService.RUNNING = true;

        this.canSpeak = false;
        this.speaker = new TextToSpeech(this, this);

        this.oMessageHandler = new MessageHandler();
        this.registerReceiver(this.oMessageHandler, new IntentFilter(BROADCASTERS.SPEAK));
        this.registerReceiver(this.oMessageHandler, new IntentFilter(BROADCASTERS.ANNOUNCE_NO_SIGNAL));

        return Service.START_STICKY;


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if (this.speaker != null)
        {
            this.speaker.stop();
            this.speaker.shutdown();
        }

        try
        {
            this.unregisterReceiver(this.oMessageHandler);
        }
        catch (Exception ex)
        {
            //never mind
        }

        SpeakService.RUNNING = false;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onInit(int status)
    {
        if (status == TextToSpeech.SUCCESS) {
            int result = this.speaker.setLanguage(Locale.UK);
            if ((result != TextToSpeech.LANG_MISSING_DATA) && (result != TextToSpeech.LANG_NOT_SUPPORTED))
            {
                this.canSpeak=true;
            }
        }
    }

    @Override
    public void onUtteranceCompleted(String utteranceId)
    {

    }
}
