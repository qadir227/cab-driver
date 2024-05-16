package com.cabdespatch.libcabapiandroid.Apis.Activation;

import androidx.annotation.Keep;

import com.cabdespatch.libcabapiandroid.Apis.ServerProxy.ProxyApiRequest;
import com.google.gson.Gson;

public class Checkin extends ProxyApiRequest<Checkin.PdaCheckinResponse>
{


    @Keep
    public static class PdaCheckinRequest
    {
        public String deviceIdentifier;
        public String companyId;
        public String deviceKey;
        public double appVersion;
    }

    @Keep
    public class PdaCheckinResponse
    {
        public static final int CHECKIN_STATUS_PASS = 0;
        public static final int CHECKIN_STATUS_FAIL_DEVICE_NOT_ACTIVE = -100;
        public static final int CHECKIN_STATUS_FAIL_INVALID_DEVICE_KEY = -200;
        public static final int CHECKIN_STATUS_FAIL_UNSPECIFIED_ERROR = -999;

        public int checkinStatus;
        public String token;
    }

    private Checkin(int tag, PdaCheckinRequest request, AnyApiListener listener)
    {
        super(tag, request, listener);
    }

    public static Checkin Obtain(int tag, String companyId, String deviceIdentifier, String deviceKey, double appVersion,  AnyApiListener listener)
    {
        PdaCheckinRequest request = new PdaCheckinRequest();
        request.companyId = companyId;
        request.deviceIdentifier = deviceIdentifier;
        request.deviceKey = deviceKey;
        request.appVersion = appVersion;

        return new Checkin(tag, request, listener);
    }

    @Override
    protected String getPath()
    {
        return "activation/pda/check_in";
    }

    @Override
    protected PdaCheckinResponse parseData(String data)
    {
        Gson gson = new Gson();
        PdaCheckinResponse jsonObject = gson.fromJson(data, PdaCheckinResponse.class); // Deserialize as a generic Object
        return jsonObject; // Perform a cast (unchecked)
    }

}
