package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Pleng on 01/05/2015.
 */
public class DissmissableDialog extends Dialog
{

    public DissmissableDialog(Context context)
    {
        super(context);
    }

    @Override
    public void dismiss()
    {
        if ((this != null) && this.isShowing()) {
            super.dismiss();
        }
    }
}
