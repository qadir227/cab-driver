package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Queue;

public class CabDespatchNetworkOldSty extends CabDespatchNetwork
{
    public static final class _CONNECTION_TYPE
    {
        public static final String CLASSIC = "01w";
        public static final String ANDROID_DEFAULT = "01a";
        public static final String ANDROID_CONNECTIONLESS = "01b";
        public static final String ANDROID_HTTP = "01h";
    }

    private boolean doStop;


    private psQueue messagesOut = new psQueue();


    private int threadTimeout;
    private Socket coSock;
    PrintWriter w;
    BufferedReader r;

    private String ipAddress;
    private int portNo;
    private Boolean networkReady = false;
    private Boolean isDebug;
    private String conType;


    public CabDespatchNetworkOldSty(Context _c, boolean _isDebug)
    {
        //Debug.waitForDebugger();
        sConnectionStatus = CONNECTION_STATUS.DISCONNECTED;
        this.networkReady = false;

        this.ipAddress = SETTINGSMANAGER.SETTINGS.IP_ADDRESS.getValue(_c);
        this.portNo = Integer.valueOf(SETTINGSMANAGER.SETTINGS.PORT_NO.getValue(_c));


        //this.awaitingResponse = 0;
        this.threadTimeout = 20;
        this.isDebug = _isDebug;

        this.doStop = false;

        this.conType = SETTINGSMANAGER.SETTINGS.DATA_MODE.getValue(_c);

        DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN,"cMOD", this.conType);


        //CLAY
        //this.conType = _CONNECTION_TYPE.ANDROID_CONNECTIONLESS;
        //messagesReceived.add(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_READY); //network ready

    }

    private void debugWrite(String _tag, String _message)
    {
        if (isDebug)
        {
            DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN,_tag, _message);
        }
    }

    private void setupSocket(Boolean _setTimeOut) throws UnknownHostException, IOException
    {
        debugWrite("CabDespatchNetworkOldSty","Set up socket");
        coSock = new Socket(ipAddress,portNo); //connects when created?
        w = new PrintWriter(coSock.getOutputStream());
        r = new BufferedReader(new InputStreamReader(coSock.getInputStream()));

        if(_setTimeOut)
        {
            coSock.setSoTimeout(7000);
        }
        addSpecialMessage(_SPECIALMESSAGES.NETWORK_READY); //network ready
        dataReceived();

        networkReady = true;
        sConnectionStatus = CONNECTION_STATUS.CONNECTED;

    }




    public void run()
    {

        if (this.conType.equals(_CONNECTION_TYPE.CLASSIC))
        {
            classicNetworkLoop();
        }
        else if(this.conType.equals(_CONNECTION_TYPE.ANDROID_DEFAULT))
        {
            androidDefaultNetowrkLoop();
        }
        else
        {
            new sendConnectionlessMessage().start();
        }
    }

    private void classicNetworkLoop()
    {
        class sendLoop extends Thread
        {
            public void run()
            {
                boolean allOK = (!(CabDespatchNetworkOldSty.this.doStop));
                while (allOK)
                {
                    if (messagesOut.size() > 0)
                    {
                        debugWrite("CabDespatchNetworkOldSty","Messages Size:" + Integer.toString(messagesOut.size()));

                        synchronized (messagesOut)
                        {
                            //reorder so that messages with a priority of 100 are sent first
                            Queue <priorityString> urgentMessages = messagesOut.getAndRemoveItemsAtPriorityLevel(100);
                            Queue <priorityString> otherMessages = messagesOut.flush();

                            messagesOut = new psQueue();

                            while (urgentMessages.size() > 0)
                            {
                                messagesOut.add(urgentMessages.remove());
                            }
                            while (otherMessages.size() > 0)
                            {
                                messagesOut.add(otherMessages.remove());
                            }
                        }

                        //get the next waiting message

                        if (messagesOut.size() > 0)
                        {
                            priorityString _o = messagesOut.poll();

                            if (!(_o==null))
                            {
                                String _d = (_o.getString());
                                _d += "\r\n";
                                debugWrite("CabDespatchNetworkOldSty","Sending: " + _d);

                                try
                                {
                                    w.write(_d);
                                    w.flush();

                                }
                                catch (Exception ex)
                                {
                                    debugWrite("NETWORK", "NETWORK EXCEPTION");
                                    handleUnsentMessage(_o);
                                }
                            }

                            if(!(networkReady)) { allOK = false; }
                        }

                    }

                    try {Thread.sleep(1000);} catch (Exception ex) {}
                }
            }
        }

        class receiveLoop extends Thread
        {
            public void run()
            {
                boolean allOK = (!(CabDespatchNetworkOldSty.this.doStop));

                while (allOK)
                {
                    String response;
                    try
                    {
                        if (!(r==null)) //why should r be null???
                        {
                            response = r.readLine();

                            if(!(response==null))
                            {
                                if (!(response.equals("")))
                                {
                                    if(!(response.equals("}")))
                                    {
                                        debugWrite("CabDespatchNetworkOldSty","Data Recd: " + response);
                                        addReceivedMessage(response);
                                        sConnectionStatus = CONNECTION_STATUS.CONNECTED;
                                    }

                                }
                            }
                        }
                        else
                        {
                            debugWrite ("NETWORK", "r is null");
                        }
                    }
                    catch (IOException e)
                    {
                        networkReady = false;
                        sConnectionStatus = CONNECTION_STATUS.DISCONNECTED;
                    }

                    if(!(networkReady)) { allOK = false; } //thread will die
                }
            }
        }

        while (CabDespatchNetworkOldSty.this.doStop == false)
        {
            if (!(networkReady))
            {
                //if the network is not ready, then set it up!
                try
                {
                    setupSocket(false);
                    new sendLoop().start(); new receiveLoop().start();
                }
                catch (Exception e)
                {
                    debugWrite("CabDespatchNetworkOldSty","Could not set up socket");
                    addSpecialMessage(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_NOT_READY); //network not ready

                    try
                    {
                        Thread.sleep(15000);
                    }
                    catch (Exception ex) { debugWrite ("CabDespatchNetworkOldSty", "catch while sleeping");}// don't care }

                }

            }
        }



    }


    private void androidDefaultNetowrkLoop()
    {
        while (CabDespatchNetworkOldSty.this.doStop == false)
        {
            Boolean connectionless = (this.conType.equals(_CONNECTION_TYPE.ANDROID_CONNECTIONLESS));

            if ((!(networkReady)) && (!(connectionless)))
            {

                //if the network is not ready, then set it up!
                try
                {
                    setupSocket(true);
                }
                catch (Exception e)
                {
                    debugWrite("CabDespatchNetworkOldSty", "Could not set up socket");
                    addSpecialMessage(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_NOT_READY); //network not ready

                    try
                    {
                        Thread.sleep(15000);
                    }
                    catch (Exception ex)
                    {
                        debugWrite("CabDespatchNetworkOldSty", "catch while sleeping");
                    }// don't care }

                }

            }

            if (messagesOut.size() > 0)
            {
                debugWrite("CabDespatchNetworkOldSty", "Messages Size:" + Integer.toString(messagesOut.size()));

                Socket actualSock = null;
                BufferedReader actualReader = null;
                PrintWriter actualWriter = null;

                if (connectionless)
                {
                    try
                    {
                        actualSock = new Socket(ipAddress, portNo); //connects when created?
                        actualWriter = new PrintWriter(actualSock.getOutputStream());
                        actualReader = new BufferedReader(new InputStreamReader(actualSock.getInputStream()));

                        this.networkReady = true;
                        sConnectionStatus = CONNECTION_STATUS.CONNECTED;
                        addSpecialMessage(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_READY); //network ready

                    }
                    catch (IOException e)
                    {
                        actualSock = null;
                        actualWriter = null;
                        actualReader = null;

                        this.networkReady = false;
                        sConnectionStatus = CONNECTION_STATUS.DISCONNECTED;
                        addSpecialMessage(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_NOT_READY); //network ready
                        //e.printStackTrace();
                    }


                }
                else
                {
                    actualSock = coSock;
                    actualReader = r;
                    actualWriter = w;
                }



                if (actualSock == null)
                {
                    //CLAY send network not ready message
                }
                else
                {
                    doSendMessagesAndroidStyle(actualWriter, actualReader);

                    try
                    {
                        //CLAY
                        if (connectionless)
                        {
                            actualSock.close();
                        }

                        Thread.sleep(250);
                        //debugWrite("CabDespatchNetworkOldSty","Snoooze...");
                    }
                    catch (InterruptedException e)
                    {
                        // dont care...
                        e.printStackTrace();
                        debugWrite("CabDespatchNetworkOldSty", "Network Sleep Interrupted");

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private void doSendMessagesAndroidStyle(PrintWriter _w, BufferedReader _r)
    {

        synchronized (messagesOut)
        {
            //reorder so that messages with a priority of 100 are sent first
            Queue <priorityString> urgentMessages = messagesOut.getAndRemoveItemsAtPriorityLevel(100);
            Queue <priorityString> otherMessages = messagesOut.flush();

            messagesOut = new psQueue();

            while (urgentMessages.size() > 0)
            {
                messagesOut.add(urgentMessages.remove());
            }
            while (otherMessages.size() > 0)
            {
                messagesOut.add(otherMessages.remove());
            }
        }

        //get the next waiting message
        priorityString _o = messagesOut.poll();

        String _d = ("*" + _o.getString());

        DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN,"Message", _d);
        //it cant encrypt the return string so add it after
        //_d += "\r\n";
        debugWrite("CabDespatchNetworkOldSty","Sending: " + _d);

        try
        {
            //New encrypt data to socket server
            try
            {
                _d = EncryptionHandler.EncryptPDA(_d);
            }
            catch (Exception ex)
            {
            }
            _d += "\r\n";
            //intersept here to proxy
            ProxyHandeler proxy = new ProxyHandeler(ipAddress, 3376);


            _w.write(_d);
            _w.flush();

            String TempString;
            long t = SystemClock.uptimeMillis();
            boolean hasData = true;
            while (hasData)
            {
                if (t + (1000 * threadTimeout) <= SystemClock.uptimeMillis())
                {
                    hasData = false; // timeout gracefully
                    // but we've probably lost data :(
                    debugWrite("CabDespatchNetworkOldSty","Response terminus not found");
                    //no response so assume the sever didn't actually
                    //get the message. and send again...
                    handleUnsentMessage(_o);
                }
                else
                {
                    try
                    {
                        String response = _r.readLine();
                        TempString = response;
                        //new decrypt message
                        try
                        {
                            if (response.contains("\r\n"))
                            {

                                response = response.replace("\r\n", "");
                                response = EncryptionHandler.DecryptPDA(response);
                                response += "\r\n";

                            }
                            else
                            {
                                TempString = response;
                                response = EncryptionHandler.DecryptPDA(response);
                            }
                        }
                        catch (Exception ex)
                        {}

                        if (TempString != response)
                        {

                        }
                        // r.DiscardBufferedData();
                        if (response.equals(null))
                        {
                            hasData = false;
                            //do nothing
                        }
                        else if (response.equals("}"))
                        {
                            hasData = false;
                            debugWrite("CabDespatchNetworkOldSty","\"}\" - no more data");
                            //we've received aknowledgement - the connection is still live
                            dataReceived();

                        }
                        else
                        {
                            if (!(response.equals("")))
                            {

                                debugWrite("CabDespatchNetworkOldSty","Data Recd: " + response);
                                addReceivedMessage(response);
                                sConnectionStatus = CONNECTION_STATUS.CONNECTED;
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        debugWrite("NETWORK", "NETWORK EXCEPTION");
                        handleUnsentMessage(_o);
                    }
                }


            }

        }
        catch (Exception ex)
        {
            handleUnsentMessage(_o);
        }

    }



    private void handleUnsentMessage(priorityString _o)
    {
        //could not write to socket
        debugWrite("MESSAGE", "MESSAGE PRIORITY: " + _o.getPriority());
        addSpecialMessage(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_NOT_READY); //network not ready
        try{Thread.sleep(5000);}catch(Exception ex){}
        networkReady = false;
        sConnectionStatus = CONNECTION_STATUS.DISCONNECTED;

        if (_o.getPriority() > 0)
        {
            //its an 'important message
            //send again at earliest opportunity
            messagesOut.add(_o);
            debugWrite("MESSAGE", "MESSAGE PRIORITY: " + _o.getPriority() + "ADDED TO QUEUE");
        }


    }

    @Override
    public Boolean sendMessage(priorityString _s)
    {
        if(this.conType==_CONNECTION_TYPE.ANDROID_CONNECTIONLESS)
        {
            //new sendConnectionlessMessage(_s).start();
            messagesOut.add(_s);
            return true;
        }
        else if ((networkReady)||(_s.getPriority()>0))
        {
            messagesOut.add(_s);
            return true;
        }
        else
        {
            return false;
        }
    }


    private static final char TERMINATOR = "ง".toCharArray()[0];
    private static final char MESSAGE_END = (char)253;
    private class sendConnectionlessMessage extends Thread
    {

        @Override
        public void run()
        {

            doLog("HELO", "Thread.start() called...");
            CabDespatchNetworkOldSty.this.doStop = false;
            while(!(CabDespatchNetworkOldSty.this.doStop))
            {
                if(CabDespatchNetworkOldSty.this.messagesOut.size() > 0)
                {
                    priorityString pString = CabDespatchNetworkOldSty.this.messagesOut.poll();

                    doLog("SEND", pString.getString());
                    Socket actualSock = null;
                    BufferedReader actualReader = null;
                    PrintWriter actualWriter = null;


                    try
                    {
                        actualSock = new Socket(ipAddress, 5102); //connects when created?
                        actualWriter = new PrintWriter(actualSock.getOutputStream());
                        actualReader = new BufferedReader(new InputStreamReader(actualSock.getInputStream()));

                        CabDespatchNetworkOldSty.this.networkReady = true;
                        sConnectionStatus = CONNECTION_STATUS.CONNECTED;
                        //messagesReceived.add(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_READY); //network ready

                        actualWriter.write(pString.getString());
                        actualWriter.write(TERMINATOR);
                        actualWriter.flush();
                        //actualWriter.close();


                        StringBuilder input = new StringBuilder();
                        char in = " ".toCharArray()[0];

                        while(!(in==TERMINATOR))
                        {
                            in = (char) actualReader.read();
                            if(!(in==TERMINATOR))
                            {
                                input.append(in);
                            }
                        }

                        String data = input.toString();
                        doLog("RECV", data);

                        String[] messages = data.split(String.valueOf(MESSAGE_END));
                        for (String m:messages)
                        {
                            m = m.replace("\r\n", "");
                            doLog("M-IN", m);
                            if(m.equals(""))
                            {
                                //do nothing
                            }
                            else
                            {
                                addReceivedMessage(m);
                            }
                        }

                        actualSock.close();
                        addSpecialMessage(_SPECIALMESSAGES.NETWORK_READY);
                        sConnectionStatus = CONNECTION_STATUS.CONNECTED;
                        //return true;
                    }
                    catch (IOException e)
                    {
                        if(actualSock == null)
                        {
                            //do nothing
                        }
                        else
                        {
                            try
                            {
                                actualSock.close();
                            }
                            catch (IOException e1)
                            {
                                //do nothing
                                e1.printStackTrace();
                            }
                            actualSock = null;
                        }
                        actualSock  = null;
                        actualWriter = null;
                        actualReader = null;
                        doLog("!REC", "EXCEPTION");

                        //never say network is not ready, else data service will want to restart
                        //cabDespatchNetwork and that means there'll be lots and lots of these threads
                        //running which will choke the system

                        //we're going to need to think of a different way of
                        //alerting the user to 'no signal'

                        //CabDespatchNetworkOldSty.this.networkReady = false;
                        //messagesReceived.add(CabDespatchNetworkOldSty._SPECIALMESSAGES.NETWORK_NOT_READY); //network not ready

                        try
                        {
                            Thread.sleep(5000);
                        }
                        catch (InterruptedException i)
                        {
                            i.printStackTrace();
                        }
                        //e.printStackTrace();
                        // return false;
                    }

                }

                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            doLog("BBYE", "Thread finished");

        }



    }


    private void doLog(String _tag, String _message)
    {
        DEBUGMANAGER.Log(DEBUGMANAGER.LOG_LEVEL_WARN,_tag, _message);
    }

    public void Stop()
    {
        this.doStop = true;
    }

    @Override
    public int getTimeOutSeconds()
    {
        return 120;
    }

    @Override
    public void requestDriverCallSignChange(Context _c)
    {
        //only required for signalR...
    }



}
