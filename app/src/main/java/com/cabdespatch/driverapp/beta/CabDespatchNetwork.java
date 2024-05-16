package com.cabdespatch.driverapp.beta;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.LinkedList;
import java.util.Queue;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Pleng on 04/06/2017.
 */

public abstract class CabDespatchNetwork extends Thread
{

    public static enum CONNECTION_STATUS
    {
        CONNECTED,CONNECTING,DISCONNECTED
    }

    protected static CONNECTION_STATUS sConnectionStatus = CONNECTION_STATUS.DISCONNECTED;
    private sQueue messagesReceived = new sQueue();

    public Queue<String> errorMessages = new LinkedList<>();

    public static CONNECTION_STATUS getConnectionStatus()
    {
        return sConnectionStatus;
    }

    public static final class _SPECIALMESSAGES
    {
        public static final String PURGE_CHECK_MESSGAE = "?";
        public static final String NETWORK_READY = "667-NETWORK_READY";
        public static final String NETWORK_NOT_READY = "666 - NETWORK_NOT_READY";
        public static final String NETWORK_RECONNECTING = "665 - NETWORK_RECONNECTING";
        public static final String KILL_DATA_SERVICE = "555 - KILL_DATA_SERVICE";
    }

    public static long NO_DATA_RECEIVED = -1l;

    private long lastDataReceived = NO_DATA_RECEIVED;
    //private void setLastDataReceived(long _value) { lastDataReceived = _value; }




    public abstract Boolean sendMessage(priorityString _s)
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidParameterSpecException, BadPaddingException, InvalidKeyException;
    public sQueue getMessages()
    {
        sQueue s =new sQueue();
        while(messagesReceived.size() > 0)
        {
            String st = messagesReceived.poll();
            //doLog("<<<", st);
            s.add(st);
        }
        return s;
    }

    public Queue<String> getErrorMessages()
    {
        Queue<String> errors = new LinkedList<>();
        while (errorMessages.size()>0)
        {
            String error = errorMessages.poll();
            errors.add(error);
        }

        return  errors;
    }
    public long getTimeOfLastAdditionOrAcknowledgement() { return this.lastDataReceived; }
    public abstract void Stop();
    public abstract int getTimeOutSeconds();
    public abstract void requestDriverCallSignChange(Context _c);

    public void addPushMessage(String _message)
    {
        addReceivedMessage(_message);
    }

    protected void addSpecialMessage(String _message)
    {
        messagesReceived.add(_message);
    }

    protected void addErrorMessage(String message)
    {
        errorMessages.add(message);
    }

    protected void addReceivedMessage(String _message)
    {
        dataReceived();
        messagesReceived.add(_message);
    }

    protected void dataReceived()
    {
        lastDataReceived = System.currentTimeMillis();
        sConnectionStatus = CONNECTION_STATUS.CONNECTED;
    }
}
