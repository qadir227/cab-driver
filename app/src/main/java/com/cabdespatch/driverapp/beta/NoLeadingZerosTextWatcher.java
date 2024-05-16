package com.cabdespatch.driverapp.beta;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Pleng on 26/05/2017.
 */

public class NoLeadingZerosTextWatcher implements TextWatcher
{
    EditText oTextBox;


    public NoLeadingZerosTextWatcher(EditText _t)
    {
        oTextBox = _t;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        if(s.toString().equals("0"))
        {
            oTextBox.setText("");
        }
    }
}
