package com.cabdespatch.driverapp.beta.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;

import java.util.ArrayList;
import java.util.Random;

public class EngineerModePassword extends AnyActivity {
	
	private String unlockCode;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_engineerpassword);
		this.setBackground();
		unlockCode = "";
		
		int i = new Random().nextInt(8999) + 1001; //random number between 0 and (8999+1001)
		i = i - 1; //we now have a random number between 1000 and 9999
		String requestCode=String.valueOf(i);
		
		TextView t = (TextView) findViewById(R.id.engineerPassword_lblRequestID);
		t.setText(requestCode);
		
		ArrayList<Integer> vals = new ArrayList<Integer>();
		
		vals.add(Integer.valueOf(requestCode.substring(0,2)));
		vals.add(Integer.valueOf(requestCode.substring(2,4)));
		
		for (Integer v:vals)
		{
			Integer result = v+10;
			result = result * 2;
			unlockCode += String.valueOf(result);
		}
		
	}
	
	public void btnEnter_Click(View v)
	{
		EditText e = (EditText) this.findViewById(R.id.enginnerPassword_txtPassCode);
		String s = e.getText().toString();
		
		if (s.equals(unlockCode))
		{
            Globals.StartActivity(this, (new Intent(this, EngineerModeMenu.class)));
			this.finish();
		}
		else
		{
			Toast.makeText(this, "Incorrect Code Entered", Toast.LENGTH_SHORT).show();
		}
	}


}
