package com.cabdespatch.libcabapiandroid.Apis.CabApi;

import androidx.annotation.Keep;

import com.cabdespatch.libcabapiandroid.Apis.AnyApiRequest;

public abstract class CabApiRequest<T> extends AnyApiRequest
{
    @Keep
    public static class AuthRequest
    {
        public String companyId;
        public final String apiKey = "android_app_api";
        public final String userIdentifier = "";
        public final String accessToken = "android_app_api";
    }

    @Keep
    public static abstract class AnyV2Request
    {
        public AuthRequest auth = new AuthRequest();
    }

    @Keep
    public static class AuthResponse
    {
        public String status;
    }
    @Keep
    public static class ApiError
    {
        public int code;
        public String message;
    }

    @Keep
    public static abstract class AnyV2Response
    {
        public AuthResponse auth = new AuthResponse();
        public ApiError error = new ApiError();
    }

    protected CabApiRequest(int tag, Object args, AnyApiListener listener)
    {
        super(tag, args, listener);
    }

    @Override
    protected String GetApiLocation()
    {
        if(isDebug())
        {
            //return "https://cdapi2.conveyor.cloud/api/";
        }

        return "https://yapi.cabdespatch.com/api/";
    }
}