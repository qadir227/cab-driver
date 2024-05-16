package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.Settable;
import com.cabdespatch.driverapp.beta.cabdespatchJob;

/**
 * Created by Pleng on 05/01/2017.
 */

public class Dialog_DriverNotes extends PseudoDialog implements Globals.UnarmPanicListener
{

    public interface OnJobNotesEnteredListener
    {
        public void onJobNotesEntered();
    }

    public Dialog_DriverNotes(final Activity _a, ViewGroup _container, final OnJobNotesEnteredListener _listener)
    {
        super(_a, _container, R.layout.dialog_drivernotes, R.id.dlgDriverNotes_btnBack);

        ImageButton btnPanic = (ImageButton) _container.findViewById(R.id.dlgPrice_btnPanic);
        Globals.CrossFunctions.armPanic(btnPanic, _a, this);
        btnPanic.setOnClickListener(this.btnPanic_Clicked());

        final ImageButton btnOK = (ImageButton) _container.findViewById(R.id.dlgDriverNotes_btnGo);
       /* btnOK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cdToast.showShort(_c, "Button Click");
            }
        });*/

        final EditText txtNotes = (EditText) _container.findViewById(R.id.dlgDriverNotes_txtNotes);
        String notes = STATUSMANAGER.getCurrentJob(_a).getDriverNotes();


        final Integer minChars = SETTINGSMANAGER.SETTINGS.JOB_DRIVER_NOTES_MINIMUM_SIZE.parseInteger(_a);

        txtNotes.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length() >= minChars)
                {
                    btnOK.setEnabled(true);
                }
                else
                {
                    btnOK.setEnabled(false);
                }
            }
        });

        if(notes.equals(Settable.NOT_SET))
        {
            String realValue = SETTINGSMANAGER.SETTINGS.JOB_DRIVER_NOTES_PRESET.getValue(_a);
            if(!(realValue.equals(" "))) //pda settings can't be blank otherwise they wont be processed. se we treat a single space as blank
            {
                txtNotes.setText(HandyTools.Strings.parseNewLines(realValue));
                txtNotes.setSelection(txtNotes.getText().length());
            }
        }
        else
        {
            txtNotes.setText(HandyTools.Strings.parseNewLines(notes));
            txtNotes.setSelection(txtNotes.getText().length());
        }

        btnOK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cabdespatchJob j = STATUSMANAGER.getCurrentJob(_a);
                j.setDriverNotes(txtNotes.getText().toString());
                STATUSMANAGER.setCurrentJob(_a, j);
                _listener.onJobNotesEntered();
                Dialog_DriverNotes.this.dismiss();
            }
        });
    }

    public View.OnClickListener btnPanic_Clicked()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Globals.CrossFunctions.Panic(v.getContext());
            }
        };
    }

    @Override
    public void unarmPanic()
    {
        //do nothing
    }
}
