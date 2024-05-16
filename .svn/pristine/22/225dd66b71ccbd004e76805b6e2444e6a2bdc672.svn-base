package com.cabdespatch.libcabapiandroid.Apis.CabApi;

import androidx.annotation.Keep;

import com.cabdespatch.libcabapiandroid.Apis.CabApi.CabApiRequest;

public class DriverDocumentsApi extends CabApiRequest<String>
{
    @Keep
    public static class DriverDocumentRequestArgs
    {
        public String imageData; //base64
        public String pdaCode;
        public String companyID;
        public String driverCallSign;
    }

    public DriverDocumentsApi(int tag, DriverDocumentRequestArgs args, AnyApiListener listener, boolean isDebug)
    {
        super(tag, args, listener);
    }

    @Override
    protected String getPath()
    {
        return "document/uploadDocument";
    }

    @Override
    protected String parseData(String data)
    {
        return data;
    }


}
