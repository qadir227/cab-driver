package com.cabdespatch.driverapp.beta.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;

/**
 * Created by Pleng on 01/06/2015.
 */
public class Dialog_DataWarning extends DissmissableDialog
{
    public interface OnDataWarningConfirmCompleteListener
    {
        public void OnConfirmComplete();
    }

    private OnDataWarningConfirmCompleteListener oListener;


    public Dialog_DataWarning(Context context, OnDataWarningConfirmCompleteListener _listener)
    {
        super(context);
        this.oListener = _listener;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_datawarning);

        final ScrollView scroll = (ScrollView) this.findViewById(R.id.scroll);
        final CheckBox chkUnderstood = (CheckBox) this.findViewById(R.id.dlg_datawarning_chkUnderstood);
        final ImageButton btnOK = (ImageButton) this.findViewById(R.id.dlg_datawarning_btnOk);
        btnOK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(chkUnderstood.isChecked())
                {
                    SETTINGSMANAGER.SETTINGS.DATA_WARNING_ACCEPT_VERSION.putValue(v.getContext(), SETTINGSMANAGER.CURRENT_DATA_WARNING_VERSION);
                    Dialog_DataWarning.this.oListener.OnConfirmComplete();
                    Dialog_DataWarning.this.dismiss();
                }
                else
                {
                    Dialog_MsgBox dlg = new Dialog_MsgBox(v.getContext(), R.string.please_confirm);
                    dlg.setOnDismissListener(new OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface)
                        {
                            scroll.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                    dlg.show();
                }

            }
        });

    }
}
