package com.cabdespatch.driverapp.beta.activities2017;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pleng on 11/06/2017.
 */

public class PermissionActivity extends AnyActivity
{

    public static final String PERMISSION_REQUEST_ERROR_MESSAGE = "error_message";
    public static final String ERROR_MESSAGE_NO_ERROR = "";

    private static class PermissionRequest
    {
        private int oSortOrder;
        private String oPermission;
        private String oFailMessage;

        public PermissionRequest(int _sortOrder, String _premission, String _failMessage)
        {
            oSortOrder = _sortOrder; oPermission = _premission; oFailMessage = _failMessage;
        }

        public int getSortOrder() { return  oSortOrder; }
        public String getPermission() { return  oPermission; }
        public String getFailMessage() { return oFailMessage; }

        @NonNull
        @Override
        public String toString()
        {
            return oPermission;
        }
    }

    private static final Integer SORT_INDEX_MIN = 100;

    private static List<PermissionRequest> getAllPermissionRequests(Context _context)
    {
        List<PermissionRequest> l = new ArrayList<>();

        int firstIndexOfNonSMSPermissions = SORT_INDEX_MIN;

        /*
        if(STATUSMANAGER.getBoolean(_context, STATUSMANAGER.STATUSES.ALLOW_LOCAL_SMS))
        {
            l.add(new PermissionRequest(SORT_INDEX_MIN
                    ,Manifest.permission.SEND_SMS
                    ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "send SMS messages")));
            l.add(new PermissionRequest(110
                    ,Manifest.permission.RECEIVE_SMS
                    ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "receive SMS messages")));
            l.add(new PermissionRequest(120
                    ,Manifest.permission.READ_SMS
                    ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "read SMS messages")));

            firstIndexOfNonSMSPermissions = 200;
        }*/



        l.add(new PermissionRequest(firstIndexOfNonSMSPermissions
                ,Manifest.permission.READ_PHONE_STATE
                ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "see your device's details")));
        l.add(new PermissionRequest(200
                ,Manifest.permission.READ_PHONE_NUMBERS
                ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "see your device's details")));
        l.add(new PermissionRequest(250
                ,Manifest.permission.READ_SMS
                ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "see your device's details")));
        l.add(new PermissionRequest(300
                ,Manifest.permission.ACCESS_FINE_LOCATION
                ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "access your device's location")));


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            l.add(new PermissionRequest(350
                    ,Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "access your device's location in the background")));
        }


        l.add(new PermissionRequest(400
                ,Manifest.permission.RECORD_AUDIO
                ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "use your device's microphone")));
        /*l.add(new PermissionRequest(500
                ,Manifest.permission.READ_CONTACTS
                ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "manage accounts on the device")));*/
        l.add(new PermissionRequest(500
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "access to your device's storage")));
        l.add(new PermissionRequest(600
                ,Manifest.permission.CALL_PHONE
                ,_context.getString(R.string.permission_failed_quit_message).replace("%1", "make phone calls")));

        return  l;

    }

    private PermissionRequest getPermissionRequestAt(Context _c, int _sortOrder)
    {
        PermissionRequest p = null;
        for(PermissionRequest r:getAllPermissionRequests(_c))
        {
            if(r.getSortOrder() == _sortOrder)
            {
                p = r; break;
            }
        }
        return p;
    }

    private PermissionRequest getNextPermissionRequest(Context _c, int _previousSortOrder)
    {
        PermissionRequest p = null;
        for(PermissionRequest r:getAllPermissionRequests(_c))
        {

            if(r.getSortOrder() > _previousSortOrder)
            {
                p = r;
                break;
            }
        }
        return p;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        TextView t;
        t = new TextView(this);
        t.setBackgroundColor(getResources().getColor(R.color.WhiteSmoke));

        setContentView(t);

        requestNextPermission(this,SORT_INDEX_MIN - 1);

    }


    private void requestNextPermission(Context _c, int _sortOrder)
    {
        PermissionRequest p = getNextPermissionRequest(_c, _sortOrder);
        if(p==null)
        {
            //no more permission requests left... all passed
            Intent resultIntent = new Intent();

            resultIntent.putExtra(PERMISSION_REQUEST_ERROR_MESSAGE, ERROR_MESSAGE_NO_ERROR);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
        else
        {
            if(hasPermission(p.getPermission()))
            {
                requestNextPermission(_c, p.getSortOrder());
            }
            else
            {
                requestPermission(p.getPermission(), p.getSortOrder());
            }
        }
    }

    protected Boolean hasPermission(String _permission)
    {
        return (ContextCompat.checkSelfPermission(this,_permission) == PackageManager.PERMISSION_GRANTED);
    }

    protected void requestPermission(String _permission, int _requestCode)
    {
        ActivityCompat.requestPermissions(this,
                new String[]{_permission},
                _requestCode);

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            requestNextPermission(this, requestCode);
        }
        else
        {
            PermissionRequest p = getPermissionRequestAt(this, requestCode);

            Intent resultIntent = new Intent();

            resultIntent.putExtra(PERMISSION_REQUEST_ERROR_MESSAGE,p.getFailMessage());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    public static Boolean checkPermission(Context _c, String _permission)
    {
        return (ContextCompat.checkSelfPermission(_c,_permission) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean beginPermissionsCheck(Activity _i, Integer _requestCode)
    {
        if(!(hasAllPermissions(_i)))
        {
            Intent i = new Intent(_i, PermissionActivity.class);
            _i.startActivityForResult(i, _requestCode);

            return true; //we've started requesting permissions
        }
        else
        {
            return false; //no need to request
        }

    }

    private static Boolean hasAllPermissions(Context _c)
    {
        Boolean hasPermissions = true;
        for(PermissionRequest p:getAllPermissionRequests(_c))
        {
            if(!(checkPermission(_c, p.getPermission())))
            {
                hasPermissions = false;
                break;
            }
        }
        return hasPermissions;
    }



}
