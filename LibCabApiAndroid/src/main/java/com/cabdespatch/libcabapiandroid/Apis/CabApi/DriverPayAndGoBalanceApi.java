package com.cabdespatch.libcabapiandroid.Apis.CabApi;

import androidx.annotation.Keep;

import com.google.gson.Gson;

public class DriverPayAndGoBalanceApi extends CabApiRequest<DriverPayAndGoBalanceApi.DriverPayAndGoBalanceResult>
{

    @Keep
    public static class DriverPayAndGoBalanceResult extends AnyV2Response
    {
        public int driverCallSign;
        public int amountInLowestDenomination;
    }

    @Keep
    public static class DriverPayAndGoBalanceRequestArgs extends AnyV2Request
    {
        public String companyId;
        public String driverCallSign;
        public int amountInSmallestDenomination;
    }

    public DriverPayAndGoBalanceApi(int tag, DriverPayAndGoBalanceRequestArgs args, AnyApiListener listener, boolean isDebug)
    {
        super(tag, args, listener);
    }

    @Override
    protected String getPath()
    {
        return "v2/driver/get_driver_pay_and_go_balance";
    }

    @Override
    protected DriverPayAndGoBalanceResult parseData(String data)
    {
        return new Gson().fromJson(data, DriverPayAndGoBalanceResult.class);
    }

}