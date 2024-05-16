package com.cabdespatch.driverapp.beta.services;

import android.content.Context;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.cdToast;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService
{
    @Override
    public void onNewToken(String s)
    {
        super.onNewToken(s);
        updateToken(this, s);
    }

    private boolean isValidDriverCallSign(String _d)
    {
        return _d.equals(SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.getValue(this));
        //return true;
    }

    private class MESSAGE_HEADERS
    {
        public static final String MESSAGE_TYPE = "*";
        public static final String MESSAGE_DATA_STRING = "m";
        public static final String DRIVER_CALL_SIGN = "d";
    }

    private class MESSAGE_TYPES
    {
        public static final String DATA_WAITING = ".";
        public static final String SOCKET_SERVER_PAYLOAD = "s";
        public static final String TOAST = "t";
    }

    public static void updateToken(Context _context, String _token)
    {
        STATUSMANAGER.setFirebaseInstanceId(_context, _token);
        BROADCASTERS.resetFirebaseID(_context);
    }

    //actual message handling
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);

        Map<String, String> payload = remoteMessage.getData();

        if(payload.containsKey("message_type"))
        {
            //do nothing - looks like a FCM ack
        }
        else
        {
            String driverCallSign = payload.get(MESSAGE_HEADERS.DRIVER_CALL_SIGN);

            if(isValidDriverCallSign(driverCallSign))
            {
                String messageType = payload.get(MESSAGE_HEADERS.MESSAGE_TYPE);
                if(messageType.equals(MESSAGE_TYPES.DATA_WAITING))
                {
                    handleDataWaiting();
                }
                else if(messageType.equals(MESSAGE_TYPES.TOAST))
                {
                    handleToast(payload);
                }
                else if(messageType.equals(MESSAGE_TYPES.SOCKET_SERVER_PAYLOAD))
                {
                    handleSocketServerPayload(payload);
                }
            }
            else
            {
                BROADCASTERS.resetFirebaseID(this);
            }
        }

    }


    private void handleSocketServerPayload(Map<String, String> _payload)
    {
        String payload = _payload.get(MESSAGE_HEADERS.MESSAGE_DATA_STRING);
        DataService.addPushMessage(payload);
    }

    private void handleDataWaiting()
    {
        DataService.adviseDataWaiting();
    }

    private void handleToast(Map<String, String> _payload)
    {
        String message = _payload.get(MESSAGE_HEADERS.MESSAGE_DATA_STRING);
        cdToast.showShort(this, message);
    }



    public static TextView textview;

    //two way test
    /*
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        if(!(textview==null))
        {
            String output = "";

            Map<String,String> remoteData = remoteMessage.getData();
            for(String s:remoteData.keySet())
            {
               output  += s + ": ";
               output  += remoteData.get(s) + "\n";
            }


            final String finalOutput = output;
            Handler h = new Handler(getMainLooper());
            h.post(new Runnable()
            {
                @Override
                public void run()
                {
                    textview.append(finalOutput + "\n");
                }
            });

        }
    }*/

}