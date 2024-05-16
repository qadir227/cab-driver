package com.cabdespatch.driverapp.beta;

import android.content.Context;

public class CabDespatchNetworkSignalR extends CabDespatchNetwork
{

    public  CabDespatchNetworkSignalR(Context _c, String _host)
    {

    }
    @Override
    public Boolean sendMessage(priorityString _s) {
        return null;
    }

    @Override
    public void Stop() {

    }

    @Override
    public int getTimeOutSeconds() {
        return 0;
    }

    @Override
    public void requestDriverCallSignChange(Context _c) {

    }
}
/*
import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.cabdespatch.driverapp.beta.activities2017.DataActivity;
import com.cabdespatch.driverapp.beta.services.DataService;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.ConnectionState;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.StateChangedCallback;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.LongPollingTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

/**
 * Created by Pleng on 04/06/2017.




public class CabDespatchNetworkSignalR extends CabDespatchNetwork
{

    private HubProxy oHubProxy;
    //private sQueue oMessages;
    private Context oContext;
    private Boolean oHasAuth;
    private Boolean oStopping;
    private String oHost;

    private HubConnection oHubConnection;


    public  CabDespatchNetworkSignalR(Context _c, String _host)
    {
        oContext = _c;
        oHost = _host;
        //oHost = "http://192.168.4.20/SignalRProxy/";
        oHasAuth = false;
        oStopping = false;
        sConnectionStatus = CONNECTION_STATUS.DISCONNECTED;

    }

    @Override
    public void run()
    {
        startSignalR(0, true);
    }



    private void startSignalR(final int _connectionTryCount, Boolean _firstRun)
    {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        //String serverUrl = "https://signalrtest.cabdespatch.com/signalr";
        //serverUrl = "http://192.168.4.20/SignalRProxy/";
        oHubConnection = new HubConnection(oHost);
        //mHubConnection.disconnect();


        oHubConnection.stateChanged(
                new StateChangedCallback()
        {
            @Override
            public void stateChanged(final ConnectionState _oldState, final ConnectionState _newState)
            {
                if(!(CabDespatchNetworkSignalR.this.oStopping))
                {
                    if(_newState==ConnectionState.Connected)
                    {
                        //wait until we've actually received a message from the server
                        //otherwise we'll never go red as long as we're connected to the proxy
                        //sConnectionStatus = CONNECTION_STATUS.CONNECTED;
                    }
                    else if(_newState==ConnectionState.Reconnecting)
                    {
                        addSpecialMessage(_SPECIALMESSAGES.NETWORK_RECONNECTING);
                        sConnectionStatus = CONNECTION_STATUS.CONNECTING;
                    }
                    else if(_newState==ConnectionState.Connecting)
                    {
                        addSpecialMessage(_SPECIALMESSAGES.NETWORK_RECONNECTING);
                        sConnectionStatus = CONNECTION_STATUS.CONNECTING;
                    }
                    else if(_newState==ConnectionState.Disconnected)
                    {
                        addSpecialMessage(_SPECIALMESSAGES.NETWORK_NOT_READY);
                        sConnectionStatus = CONNECTION_STATUS.DISCONNECTED;

                        /*
                        if((_connectionTryCount + 1) < 16)
                        {
                            class reconnect extends PauseAndRun
                            {

                                @Override
                                protected void onPostExecute(Void _void)
                                {
                                    CabDespatchNetworkSignalR.this.startSignalR(_connectionTryCount + 1, false);
                                }
                            }

                            new reconnect().Start(6000);
                        }

                    }
                }

            }
        });


        //mHubConnection.setCredentials(credentials);
        String SERVER_HUB_CHAT = "DriverHub";
        oHubProxy = oHubConnection.createHubProxy(SERVER_HUB_CHAT);

        oHubProxy.on("ReceiveMessageFromServer",
                new SubscriptionHandler1<String>()
                {
                    @Override
                    public void run(final String msg)
                    {
                        sConnectionStatus = CONNECTION_STATUS.CONNECTED;
                        dataReceived();

                            addMessage(msg);
                            DataActivity.addDebugDataMessage(new DataActivity.DebugDataMessage(DataActivity.DebugDataMessage.DIRECTION_IN, msg));

                    }
                }
                , String.class);

        oHubProxy.on("CallSignUpdated",
                new SubscriptionHandler1<String>()
                {
                    @Override
                    public void run(final String msg)
                    {
                        sConnectionStatus = CONNECTION_STATUS.CONNECTED;
                        //CLAY we need to now send the login...
                    }
                }
                , String.class);

        oHubProxy.on("AuthState",
                new SubscriptionHandler1<String>()
                {
                    @Override
                    public void run(final String msg)
                    {

                        if(msg.equals("Y"))
                        {
                            dataReceived();
                            DEBUGMANAGER.Log("AUTH", "Y");
                            DataActivity.addDebugDataMessage(new DataActivity.DebugDataMessage(DataActivity.DebugDataMessage.DIRECTION_IN, "AUTH REC"));
                            //this would send a "Server located message.... we don't really want that
                            //as there's not necissarily a connection to SS
                            //oMessages.add(_SPECIALMESSAGES.NETWORK_READY);
                        }
                        else if(msg.equals("X"))
                        {
                            //CLAY --- alert user and quit
                        }
                        else
                        {
                            doAuth();
                            DEBUGMANAGER.Log("AUTH", msg);
                            DataActivity.addDebugDataMessage(new DataActivity.DebugDataMessage(DataActivity.DebugDataMessage.DIRECTION_IN, "NO AUTH"));
                            addSpecialMessage(_SPECIALMESSAGES.NETWORK_NOT_READY);
                        }

                    }
                }
                , String.class);

        oHubProxy.on("DisconnectPDA", new SubscriptionHandler1<String>()
        {

            @Override
            public void run(String s)
            {
                DataActivity.addDebugDataMessage(new DataActivity.DebugDataMessage(DataActivity.DebugDataMessage.DIRECTION_IN, "DISCONNECT PDA"));
                CabDespatchNetworkSignalR.this.Stop();
            }
        }, String.class);
        //ClientTransport clientTransport = new ServerSentEventsTransport(oHubConnection.getLogger());
        ClientTransport clientTransport = new LongPollingTransport(oHubConnection.getLogger());
        SignalRFuture<Void> signalRFuture = oHubConnection.start(clientTransport);
        String afdfsd="ffsfdfsd";

        try
        {
            signalRFuture.get();
            doAuth();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();

            addSpecialMessage(_SPECIALMESSAGES.NETWORK_NOT_READY);
            if(_firstRun)
            {
                dataReceived(); //so data service will stop itself after specified timeout (probably?)
            }

            return;
        }


    }




    @Override
    public Boolean sendMessage(priorityString _s)
    {

        /*
        String SERVER_METHOD_SEND = "receiveMessageFromDriver";
        oHubProxy.invoke(SERVER_METHOD_SEND, _s.getString() + "\n");

        String SERVER_METHOD_SEND = "receiveMessageFromDriver";
        String message = _s.getString();
        message += ("\n");
        oHubProxy.invoke(SERVER_METHOD_SEND, message + "\n");
        DataActivity.addDebugDataMessage(new DataActivity.DebugDataMessage(DataActivity.DebugDataMessage.DIRECTION_OUT, _s.getString()));

        return true;

    }



    @Override
    public void Stop()
    {
        oStopping = true;
        oHubConnection.stop();
        sConnectionStatus = CONNECTION_STATUS.DISCONNECTED;
    }

    @Override
    public int getTimeOutSeconds()
    {
        return 60;
    }

    @Override
    public void requestDriverCallSignChange(final Context _c)
    {
        String SERVER_METHOD_NEW_CALL_SIGN = "updateDriverCallSign";
        oHubProxy.invoke(SERVER_METHOD_NEW_CALL_SIGN, STATUSMANAGER.getActingDriverNumber(_c));


    }

    @SuppressLint("MissingPermission")
    public void doAuth()
    {
        TelephonyManager t = (TelephonyManager) oContext.getSystemService(Context.TELEPHONY_SERVICE);

        String SERVER_METHOD_SEND = "doAuth";

        String DriverCallSign = STATUSMANAGER.getActingDriverNumber(oContext);

        String CompanyID = SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(oContext).toUpperCase();
        oHubProxy.invoke(SERVER_METHOD_SEND, DriverCallSign, t.getDeviceId(), CompanyID);

    }
}
*/