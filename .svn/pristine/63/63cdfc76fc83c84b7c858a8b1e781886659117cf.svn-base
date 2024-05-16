package com.cabdespatch.libcabapiandroid.Apis.Activation;

import androidx.annotation.Keep;

import com.cabdespatch.libcabapiandroid.Apis.AnyApiRequest;
import com.cabdespatch.libcabapiandroid.Apis.ServerProxy.ProxyApiRequest;
import com.google.gson.Gson;

public class Activation extends ProxyApiRequest<Activation.PdaActivationResponse>
{
    @Keep
    public static class PdaActivationRequest
    {
        public String deviceIdentifier;
        public String deviceDescription;
        public String companyId;
        public String companyKey;
        public double appVersion;
    }

    @Keep
    public class PdaActivationResponse
    {
        public static final int STATUS_ACTIVE = 0;
        public static final int STATUS_INVALID_CREDENTIALS = -1;
        public static final int STATUS_COULD_NOT_ACTIVATE = -10;
        public static final int STATUS_UNSPECIFIED_ERROR = -999;

        public int activationStatus;
        public String deviceKey;
    }

    private Activation(int tag, PdaActivationRequest request, AnyApiRequest.AnyApiListener listener)
    {
        super(tag, request, listener);
    }

    public static Activation Obtain(int tag, String companyId, String deviceIdentifier, String deviceDescription, String webPass, double appVersion,  AnyApiRequest.AnyApiListener listener)
    {
        PdaActivationRequest request = new PdaActivationRequest();
        request.companyId = companyId;
        request.deviceIdentifier = deviceIdentifier;
        request.deviceDescription = deviceDescription;
        request.companyKey = webPass;
        request.appVersion = appVersion;

        return new Activation(tag, request, listener);
    }

    @Override
    protected String getPath()
    {
        return "activation/pda/activate";
    }

    @Override
    protected PdaActivationResponse parseData(String data)
    {
        Gson gson = new Gson();
        PdaActivationResponse jsonObject = gson.fromJson(data, PdaActivationResponse.class); // Deserialize as a generic Object
        return jsonObject; // Perform a cast (unchecked)
    }
}
