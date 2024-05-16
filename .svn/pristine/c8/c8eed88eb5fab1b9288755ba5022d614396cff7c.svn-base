package com.cabdespatch.libcabapiandroid.Apis;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cabdespatch.libcabapiandroid.Apis.Activation.Checkin;
import com.cabdespatch.libcabapiandroid.BuildConfig;
import com.cabdespatch.libcabapiandroid.okhttptools.CountingRequestBody;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class AnyApiRequest <U> implements CountingRequestBody.Listener
{
    public static class AnyApiResult<T>
    {
        public class ResponseCodes
        {
            public static final int ERROR = -1;
            public static final int OK = 0;
        }
        private int ResponseCode;
        private T Result;
        private String errorResponse = null;

        public Boolean WasSuccss() { return ResponseCode == ResponseCodes.OK;}
        public Boolean Failed() {return !WasSuccss();}

        public int getResponseCode() {return ResponseCode;}
        public T getResult() { return  Result;}
        public String getErrorResponse() { return errorResponse; }
        private AnyApiResult(T result) { ResponseCode = ResponseCodes.OK; Result = result;}
        private AnyApiResult(String errorResponse) { this.ResponseCode = ResponseCodes.ERROR; this.errorResponse = errorResponse; }

        public static <T>AnyApiResult<T> AsSuccess(T value)
        {
            //return null;
            return new AnyApiResult<T>(value);
        }

        public static <T>AnyApiResult<T> AsFail(String error)
        {
            //return null;
            return new AnyApiResult<T>(error);
        }

    }

    public interface AnyApiListener
    {
        void OnApiRequestProgress(int tag, double progress);
        void OnApiRequestComplete(int tag, AnyApiResult response);
    }



    //private final String ApiLocationDebug =
    protected abstract String getPath();
    protected Object Args;
    public int Tag;
    private AnyApiListener Listener;


    protected AnyApiRequest(int tag, Object args, AnyApiListener listener)
    {
        Args = args;
        Listener = listener;
        Tag = tag;

    }

    public boolean isDebug()
    {
        return BuildConfig.DEBUG;
    }

    protected abstract String GetApiLocation();

    @Override
    public void onRequestProgress(long bytesWritten, double progress)
    {
        if(!(Listener==null))
        {
            Listener.OnApiRequestProgress(Tag, progress);
        }
    }

    private String GetFullUrl()
    {
        String apiLocation = GetApiLocation();
        if(apiLocation.endsWith("/"))
        {
            apiLocation = apiLocation.substring(0, apiLocation.length() - 1);
        }

        String apiPath = getPath();
        if(!(apiPath.startsWith("/")))
        {
            apiPath = "/" + apiPath;
        }

        return apiLocation + apiPath;
    }

    public void Go()
    {

        Log.e("REQ", "REQUEST START");

        String url = GetFullUrl();

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String bodyString = new Gson().toJson(Args);

        RequestBody body = RequestBody.create(bodyString, mediaType);
        CountingRequestBody cbody = new CountingRequestBody(body, this);

        Request request  = new Request.Builder()
                .url(url)
                .post(cbody)
                .build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e)
            {
                Log.e("REQ", "REQUEST FAIL");

                e.printStackTrace();
                if(!(Listener==null))
                {
                    String error = "CALL: " + call.request() +"\r\n\r\n";
                    error += "CALL String: " + new Gson().toJson(Args) +"\r\n\r\n";
                    error += "EX: " + e.toString() +"\r\n\r\n";

                    AnyApiResult res = AnyApiResult.AsFail(error);
                    Listener.OnApiRequestComplete(Tag, res);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {
                Log.e("REQ", "REQUEST RESPONSE");

                if (response.isSuccessful())
                {
                    Log.e("REQ", "REQUEST RESPONSE SUCCESS");

                    ResponseBody responseBody = response.body();
                    String responseBodyString = responseBody.string();

                    AnyApiResult res = null;

                    try
                    {
                        U result = parseData(responseBodyString);
                        res = AnyApiResult.AsSuccess(result);
                        Log.e("TAG_", result.toString());

                    }
                    catch (Exception ex)
                    {
                        res = AnyApiResult.AsFail(responseBodyString);
                    }

                    if(!(Listener==null))
                    {

                        Listener.OnApiRequestComplete(Tag, res);
                    }
                }
                else
                {
                    Log.e("REQ", "REQUEST RESPONSE FAIL");
                    if(!(Listener==null))
                    {
                        StringWriter cunt = new StringWriter();

                        String error = "CALL: " + call.request().toString() +"\r\n\r\n";
                        error += "CALL String: " + new Gson().toJson(Args) +"\r\n\r\n";
                        error += "STATUS: " + response.code() +"\r\n\r\n";
                        error += "RESPONSE: " + response.body().string() +"\r\n\r\n";

                        AnyApiResult res = AnyApiResult.AsFail(error);
                        Listener.OnApiRequestComplete(Tag, res);
                    }
                }
            }
        });
    }

    protected abstract U parseData(String data);



}
