package com.cabdespatch.driverapp.beta.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.USERREQUESTS;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.cabdespatch.driverapp.beta.services.DataService;
import com.cabdespatch.driverapp.beta.views.CaptureSignatureView;

import java.util.List;

public class POD extends Activity
{

    @Override
    protected void onCreate(Bundle _savedInstance)
    {
        super.onCreate(_savedInstance);
        setContentView(R.layout.activity_pod);

        TextView lblAccountNo = (TextView) this.findViewById(R.id.layPOD_lblAccountNo);
        TextView lblJobID = (TextView) this.findViewById(R.id.layPOD_lblJobID);

        final cabdespatchJob j = STATUSMANAGER.getCurrentJob(this);
        lblAccountNo.setText("Account No: " + j.getAccount());
        lblJobID.setText("Job Id: " + j.getID());

        final CaptureSignatureView sig = (CaptureSignatureView) this.findViewById(R.id.layPOD_sig);

        ImageButton btnClear = (ImageButton) this.findViewById(R.id.layPOD_btnClear);
        btnClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sig.Clear();
            }
        });

        ImageButton btnOK = (ImageButton) this.findViewById(R.id.layPOD_btnOk);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //cdToast.showLong(POD.this, String.valueOf(sig.getSignatureData().getPaths().size()));

                String sigData = "";
                for(List<Point> path:sig.getSignatureData().getPaths())
                {
                    //need to arrange this as a list of from-to
                    Boolean firstAlready = false;
                    int oldX = 0;
                    int oldY = 0;

                    for(Point p:path)
                    {
                        if(firstAlready)
                        {
                            sigData += (String.valueOf(oldX) + ";" + String.valueOf(oldY));
                            sigData += ":";
                            sigData += (String.valueOf(p.x) + ";" + String.valueOf(p.y));

                            sigData +="#";
                        }
                        else
                        {
                            firstAlready = true;
                        }

                        oldX = p.x;
                        oldY = p.y;
                    }
                }

                //CLAY
                if(sigData.length() > 1)
                {
                    sigData = sigData.substring(0, sigData.length() - 1);
                }


                if(sigData.length() > 0)
                {
                    BROADCASTERS.POD(POD.this, sigData);
                }


                Boolean askprice = false;
                if (j.isCash())
                {
                    askprice = false;
                }
                else
                {
                    askprice = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.REQUEST_PRICE_FROM_DRIVER_ACCOUNT.getValue(POD.this));
                }


                if((askprice))
                {
                    Globals.CrossFunctions.OnPriceUpdatedListener listener = new Globals.CrossFunctions.OnPriceUpdatedListener()
                    {
                        @Override
                        public void onPriceUpdated()
                        {
                            POD.this.finish();
                            //the driver to go through a loop...
                        }
                    };

                    Globals.CrossFunctions.startSimplePriceRequest(POD.this,
                            (ViewGroup) POD.this.findViewById(R.id.dlg_container),
                            POD.this.getWindow(),
                            POD.this.getCurrentFocus(),
                            listener);

                }
                else
                {
                    Intent i = new Intent(BROADCASTERS.USER_REQUEST);
                    i.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
                    i.putExtra(DataService._MESSAGEDATA, USERREQUESTS.SET_CLEAR);

                    POD.this.sendBroadcast(i);

                    POD.this.finish();
                }


            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
