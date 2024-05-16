package com.cabdespatch.libcabapiandroid.Apis.ServerProxy;

import androidx.annotation.Keep;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SendAndReceive extends ProxyApiRequest<SendAndReceive.MessageFromPdaToHostResponse>
{
    @Keep
    public static class ProxyMessageContainer
    {
        public int id;
        public String content;
    }

    @Keep
    static class MessageFromPdaToHostRequest
    {
        public String companyId;
        public int driverCallSign;
        public String deviceId;
        public String accessToken;
        public List<String> messages = new ArrayList<>();
        public int maxIdAlreadyProcessed;
    }



    @Keep
    public class MessageFromPdaToHostResponse
    {
        public static final int AUTH_STATUS_FAIL_GENERAL = -999;
        public static final int AUTH_STATUS_OK = 10;

        public int authStatus;
        public int errorCode;
        public String errorMessage;
        public List<ProxyMessageContainer> messagesFromServer = new ArrayList<>();
    }

    private SendAndReceive(MessageFromPdaToHostRequest request, AnyApiListener listener)
    {
        super(-1, request, listener);


    }

    public static SendAndReceive Obtain(String companyId, int driverCallSign, String deviceId, String accessToken, List<String> messages,  AnyApiListener listener, int currentMaxMessage)
    {
        MessageFromPdaToHostRequest request = new MessageFromPdaToHostRequest();
        request.companyId = companyId;
        request.driverCallSign = driverCallSign;
        request.deviceId = deviceId;
        request.accessToken = accessToken;
        request.messages = messages;
        request.maxIdAlreadyProcessed = currentMaxMessage;

        return new SendAndReceive(request, listener);
    }

    @Override
    protected String getPath()
    {
        return "pda_to_host/message_from_pda_to_host";
    }

    @Override
    protected MessageFromPdaToHostResponse parseData(String data)
    {
        Gson gson = new Gson();
        return gson.fromJson(data, MessageFromPdaToHostResponse.class);
    }
}
