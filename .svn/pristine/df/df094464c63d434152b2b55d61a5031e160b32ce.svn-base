package com.cabdespatch.driverapp.beta.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.R;


public class FontableTextView extends TextView
{

    public FontableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public FontableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public FontableTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontableTextView);
            String fontName = a.getString(R.styleable.FontableTextView_TextFont);
            if (fontName!=null)
            {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

}
