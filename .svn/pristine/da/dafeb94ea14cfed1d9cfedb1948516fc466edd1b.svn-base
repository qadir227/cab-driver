package com.cabdespatch.driverapp.beta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

public class InteropServices extends BroadcastReceiver
{

    @Override
    public void onReceive(Context _context, Intent _intent)
    {
        int status = _intent.getIntExtra("status", -1);
        String[] data = _intent.getStringArrayExtra("pack");
        String mobileNo = data[0];
        String message = data[1];

        String driverMessageData = "";
        if(status == STATUS_OK)
        {
            driverMessageData = _context.getString(R.string.TEXTBACK_SENT);
        }
        else if (status == STATUS_SENDING_FAILED)
        {
            driverMessageData = _context.getString(R.string.TEXTBACK_FAILED);
        }
        else if (status == STATUS_NOT_DEFAULT_MESSAGE_PROVIDER)
        {
            driverMessageData = _context.getString(R.string.TEXTBACK_INCORRECTLY_CONFIGURED);
        }
        else if (status == STATUS_PERMISSION_ERROR)
        {
            driverMessageData = _context.getString(R.string.TEXTBACK_INCORRECTLY_CONFIGURED);
        }

        STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.MODE.DEFAULT, STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
                STATUSMANAGER.DriverMessage.BOX.INBOX,
                new Date().getTime(),
                driverMessageData,
                false, true);
        STATUSMANAGER.addDriverMessage(_context, m);
    }

    final int STATUS_OK = 0;
    final int STATUS_NOT_DEFAULT_MESSAGE_PROVIDER = -10;
    final int STATUS_PERMISSION_ERROR = -20;
    final int STATUS_SENDING_FAILED = -50;

    public static void sendSms(Context _context, String _phoneNumber, String _message)
    {
        if(Globals.CrossFunctions.checkForPackageExist(_context, "com.cabdespatch.smsconnect"))
        {
            String[] testData = new String[] {_phoneNumber, _message};
            Intent i = new Intent("com.cabdespatch.smsconnect.SEND_SMS");
            i.putExtra("smsd", testData);
            i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

            _context.sendBroadcast(i);
        }
        else
        {
            STATUSMANAGER.DriverMessage m = new STATUSMANAGER.DriverMessage(STATUSMANAGER.DriverMessage.MODE.DEFAULT, STATUSMANAGER.DriverMessage.TYPE.DATA_MESSAGE,
                    STATUSMANAGER.DriverMessage.BOX.INBOX,
                    new Date().getTime(),
                    _context.getString(R.string.TEXTBACK_INCORRECTLY_CONFIGURED),
                    false, true);
            STATUSMANAGER.addDriverMessage(_context, m);
        }

    }
}
