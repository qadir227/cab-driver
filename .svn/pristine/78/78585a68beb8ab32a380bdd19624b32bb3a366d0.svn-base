package com.cabdespatch.libcabapiandroid.Apis.CabApi;

import androidx.annotation.Keep;

import com.cabdespatch.libcabapiandroid.Apis.AnyApiRequest;
import com.google.gson.Gson;

public class GetPaymentIntentApi extends CabApiRequest<GetPaymentIntentApi.GetPaymentIntentResult>
{
    @Keep
    public static class GetPaymentIntentResult extends AnyV2Response
    {
        public int driverCallSign;
        public int amountInLowestDenomination;
        public String clientSecret;
        public String publishableKey;
    }

    @Keep
    public static class GetPaymentIntentRequestArgs extends AnyV2Request
    {
        public String companyId;
        public String driverCallSign;
        public int amountInSmallestDenomination;
    }

    public GetPaymentIntentApi(int tag, GetPaymentIntentRequestArgs args, AnyApiRequest.AnyApiListener listener, boolean isDebug)
    {
        super(tag, args, listener);
    }

    @Override
    protected String getPath()
    {
        return "legacy/stripe/GetDriverPayClientSecret";
    }

    @Override
    protected GetPaymentIntentResult parseData(String data)
    {
        return new Gson().fromJson(data, GetPaymentIntentResult.class);
    }
}
