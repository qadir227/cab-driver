package com.cabdespatch.driverapp.beta.activities2017;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.activities.AnyActivity;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.libcabapiandroid.Apis.CabApi.CabApiRequest;
import com.cabdespatch.libcabapiandroid.Apis.CabApi.DriverDocumentsApi;

import java.io.File;
import java.io.IOException;

public class DocumentScanner extends AnyActivity implements CabApiRequest.AnyApiListener
{

    static final int REQUEST_DRIVER_DOCUMENT_SCAN = 1;

    static final String TEMP_FILE_NAME = "TEMPIMAGEFILE";

    public void Start(AnyActivity activity)
    {
        Intent i = new Intent(activity, DocumentScanner.class);
        activity.startActivity(i);
    }

    @Override
    public void onCreate(Bundle _savedState)
    {
        super.onCreate(_savedState);

        Intent i = new Intent(this, DriverDocumentActivity.class);
        startActivity(i);
        finish();

        /*
        setContentView(R.layout.activity_document_scan);
        try
        {
            dispatchTakePictureIntent();
        }
        catch (ActivityNotFoundException e)
        {
            cdToast.showLong(this, "No camera found");
        }*/
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) == null)
        {
            cdToast.showLong(this, "No camera app found");
        }
        else
        {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = getImageFile(true);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                String packagename = getPackageName();
                Uri photoURI = FileProvider.getUriForFile(this,packagename + ".provider", photoFile);
                //Uri photoURI = Uri.parse(photoFile.getAbsolutePath());
                //Uri photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_DRIVER_DOCUMENT_SCAN);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DRIVER_DOCUMENT_SCAN || resultCode == RESULT_OK)
        {
            //Bundle extras = data.getExtras();
            try
            {
                File f = getImageFile(false);
                ImageView i = findViewById(R.id.img);
                i.setScaleType(ImageView.ScaleType.FIT_CENTER);

                HandyTools.BitmapTools tools = new HandyTools.BitmapTools();
                Bitmap b = tools.fromFile(f);

                f = new File(getFilesDir(), "tempdriverscan.png");
                b = tools.SaveToPng(b, 90, 1024, f);
                i.setImageBitmap(b);

                View v = findViewById(R.id.progress);
                v.setVisibility(View.VISIBLE);

                byte[] filedata = new HandyTools.FileTools().ToByteArray(f);

                DriverDocumentsApi.DriverDocumentRequestArgs args = new DriverDocumentsApi.DriverDocumentRequestArgs();
                args.companyID = SETTINGSMANAGER.SETTINGS.COMPANY_ID.getValue(this);
                args.driverCallSign = SETTINGSMANAGER.SETTINGS.DRIVER_CALL_SIGN.getValue(this);
                args.pdaCode = Globals.CrossFunctions.getDeviceIdentifier_wasIMEI(this);
                args.imageData = Base64.encodeToString(filedata, Base64.DEFAULT);

                f.delete();

                DriverDocumentsApi api = new DriverDocumentsApi(0, args, this, isCabApiDebug());
                api.Go();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }



    private File getImageFile(Boolean deleteExisting) throws IOException
    {
        // Create an image file name
        String imageFileName = TEMP_FILE_NAME;
        File storageDir = getFilesDir();

        File f = new File(storageDir, imageFileName + ".jpg");

        if(deleteExisting)
        {
            if(f.exists()){f.delete();}
        }

        f.createNewFile();
        f = new File(storageDir, imageFileName + ".jpg");

        return f;

    }

    @Override
    public void OnApiRequestProgress(int tag, double progress)
    {
        Log.e("Progress", String.valueOf(progress));
    }

    @Override
    public void OnApiRequestComplete(int tag, CabApiRequest.AnyApiResult response)
    {

    }


}
