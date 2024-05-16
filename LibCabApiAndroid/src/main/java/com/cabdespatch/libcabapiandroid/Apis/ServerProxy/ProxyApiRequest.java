package com.cabdespatch.libcabapiandroid.Apis.ServerProxy;

import com.cabdespatch.libcabapiandroid.Apis.AnyApiRequest;

public abstract class ProxyApiRequest<T> extends AnyApiRequest
{

    protected ProxyApiRequest(int tag, Object args, AnyApiListener listener)
    {
        super(tag, args, listener);
    }

    @Override
    protected String GetApiLocation()
    {
        if(isDebug())
        {
            //return "https://cabdespatch-pda-proxy23.conveyor.cloud";
        }

        return "https://proxy23.cabdespatch.com";
    }
}
