package com.cabdespatch.driverapp.beta.fragments.settings;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;

public abstract class SettingsFragment extends Fragment
{
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = onCreateSettingsFragmentView(inflater);
		
		TextView t = (TextView) v.findViewById(R.id.settingstitle);
		
		if(t==null)
		{
			ErrorActivity.handleError(this.getActivity(), new ErrorActivity.ERRORS.MISFORMED_SETTING_LAYOUT(this.toString()));
		}
		
		
		
		t.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.valueOf(SETTINGSMANAGER.TextTools.SIZE.SETTING_HEADER.getValue(this.getContext())));
		//t.setTextColor(this.getResources().getColor(R.color.Blue));
		return v;
	}
	
	/* Inflate your view here... the view MUST contain a n element with
	 * the id of "settingstitle"
	 */
	
	public abstract View onCreateSettingsFragmentView(LayoutInflater inflater);
	
	
}
