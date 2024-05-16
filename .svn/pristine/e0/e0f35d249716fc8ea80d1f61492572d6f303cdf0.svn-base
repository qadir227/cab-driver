package com.cabdespatch.driverapp.beta.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.cabdespatch.driverapp.beta.R;

public class Dialog_PopOut extends Dialog
{
    public Dialog_PopOut(Context _c, View _v)
    {
        super(_c);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);

        _v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        View content = this.getLayoutInflater().inflate(R.layout.dialog_popout, null);

        ImageButton btnBack = (ImageButton) content.findViewById(R.id.dlg_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Dialog_PopOut.this.dismiss();
            }
        });

        ScrollView s = (ScrollView) content.findViewById(R.id.dlgPopOut_Content);
        s.addView(_v);

        this.setContentView(content);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }
}
