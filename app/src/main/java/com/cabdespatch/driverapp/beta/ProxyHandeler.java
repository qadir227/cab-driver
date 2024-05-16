package com.cabdespatch.driverapp.beta;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ProxyHandeler
{
    String proxyIp;
    int proxyPortNo;
    Socket proxySocket;
    BufferedWriter bw;

    public ProxyHandeler(String ip, int port){
        proxyIp = ip;
        proxyPortNo = port;
    }

    private void SendToProxy(String data){
        try
        {
            proxySocket = new Socket(proxyIp, proxyPortNo);
            bw = new BufferedWriter(new OutputStreamWriter(proxySocket.getOutputStream()));

            bw.write(data);
            bw.flush();
            bw.close();
            proxySocket.close();
        }
        catch (IOException ex)
        {
        }
    }
}
