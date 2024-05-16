package com.cabdespatch.driverapp.beta.stylecontrols;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class cdTextView extends TextView
{
//class not working - do not use
	
	
	public cdTextView(TextView _t)
	{
		super(_t.getContext());;
	}
	
	public cdTextView(Context _context, AttributeSet _attrs, int _defStyle) 
	{
		super(_context, _attrs, _defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public cdTextView(Context _context, AttributeSet _attrs) 
	{
		super(_context, _attrs);
		// TODO Auto-generated constructor stub
	}
	
	
//	private void setTheme(TextView _control, theme _theme, _CONTROLTYPE _controltype)
//	{
//		switch(_controltype)
//		{
//		case TITLE:
//			_control.setTypeface(_theme.fonts.titleFont);
//			_control.setTextColor(_theme.colours.titleText);
//			_control.setTextSize(_theme.fonts.getPointSize(theme._POINTSIZES.TITLE));
//			break;
//		case ICON:
//			_control.setTypeface(_theme.fonts.iconFont);
//			_control.setTextColor(_theme.colours.iconText);
//			_control.setTextSize(_theme.fonts.getPointSize(theme._POINTSIZES.ICON));
//			break;
//		case MESSAGE:
//			_control.setTypeface(_theme.fonts.messageFont);
//			_control.setTextColor(_theme.colours.messageText);
//			_control.setTextSize(_theme.fonts.getPointSize(theme._POINTSIZES.MESSAGE));
//			break;
//		case SUBTITLE:
//			_control.setTypeface(_theme.fonts.subTitleFont);
//			_control.setTextColor(_theme.colours.subTitleText);
//			_control.setTextSize(_theme.fonts.getPointSize(theme._POINTSIZES.SUBTITLE));
//			break;
//		case LABEL:
//			_control.setTypeface(_theme.fonts.labelFont);
//			_control.setTextColor(_theme.colours.labelText);
//			_control.setTextSize(_theme.fonts.getPointSize(theme._POINTSIZES.LABEL));
//			break;
//		case LARGE_LABEL:
//			_control.setTypeface(_theme.fonts.labelFont);
//			_control.setTextColor(_theme.colours.labelText);
//			_control.setTextSize(_theme.fonts.getPointSize(theme._POINTSIZES.LARGE_LABEL));
//			break;
//		}
//
//	}

}
