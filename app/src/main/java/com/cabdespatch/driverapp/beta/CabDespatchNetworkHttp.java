package com.cabdespatch.driverapp.beta;

import android.content.Context;

import com.cabdespatch.libcabapiandroid.Apis.AnyApiRequest;
import com.cabdespatch.libcabapiandroid.Apis.ServerProxy.SendAndReceive;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CabDespatchNetworkHttp extends CabDespatchNetwork implements AnyApiRequest.AnyApiListener
{

    private static boolean isBusy = false;

    private static Queue<priorityString> OutboundMessages =  new ConcurrentLinkedQueue<priorityString>();


    private String companyId;
    private int driverCallSign;
    private String deviceId;
    private String accessToken;

    public CabDespatchNetworkHttp(String companyId, int driverCallSign, String deviceId, String accessToken)
    {
        this.companyId = companyId;
        this.driverCallSign = driverCallSign;
        this.deviceId = deviceId;
        this.accessToken = accessToken;

        isBusy = false;
    }

    @Override
    public Boolean sendMessage(priorityString _s) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidParameterSpecException, BadPaddingException, InvalidKeyException
    {
        OutboundMessages.add(_s);

        if(!(isBusy))
        {
            isBusy = true;

            List<String> messages = new ArrayList<>();
            while(OutboundMessages.size() > 0)
            {
                messages.add(OutboundMessages.poll().getString());
            }

            SendAndReceive(messages);
        }

        return true;
    }

    private static int MAX_MESSAGE_ID_ALREADY_PROCESSED = -1;
    private void SendAndReceive(List<String> messages)
    {
        SendAndReceive s = SendAndReceive.Obtain(companyId, driverCallSign, deviceId, accessToken, messages, this, MAX_MESSAGE_ID_ALREADY_PROCESSED);

        s.Go();
    }

    @Override
    public void Stop()
    {

    }

    @Override
    public int getTimeOutSeconds()
    {
        return 999;
    }

    @Override
    public void requestDriverCallSignChange(Context _c)
    {

    }

    @Override
    public void OnApiRequestProgress(int tag, double progress)
    {

    }

    @Override
    public void OnApiRequestComplete(int tag, AnyApiRequest.AnyApiResult response)
    {

        try
        {
            if(response.Failed())
            {
                addErrorMessage(response.getErrorResponse());
                isBusy = false;
                return;
            }

            SendAndReceive.MessageFromPdaToHostResponse  result = (SendAndReceive.MessageFromPdaToHostResponse) response.getResult();

            for(SendAndReceive.ProxyMessageContainer message : result.messagesFromServer)
            {
                MAX_MESSAGE_ID_ALREADY_PROCESSED = Math.max(MAX_MESSAGE_ID_ALREADY_PROCESSED, message.id);
                addReceivedMessage(message.content);
            }

            for(SendAndReceive.ProxyMessageContainer message : result.messagesFromServer)
            {
                if(message.id == -999)
                {
                    MAX_MESSAGE_ID_ALREADY_PROCESSED = 0;
                }
            }

            //we may not have received any messages from this
            //round, but we do have a successful response

            //set the MAX_MESSAGE_ID_ALREADY_PROCESSED to be 0
            //if no messages were received, to prevent the
            //Proxy from purging all messages on the next round
            MAX_MESSAGE_ID_ALREADY_PROCESSED = Math.max(MAX_MESSAGE_ID_ALREADY_PROCESSED, 0);
        }
        catch (Exception ex)
        {
            //do nothing
        }

        isBusy = false;
    }

    @Override
    public long getTimeOfLastAdditionOrAcknowledgement()
    {
        return System.currentTimeMillis();
    }
}