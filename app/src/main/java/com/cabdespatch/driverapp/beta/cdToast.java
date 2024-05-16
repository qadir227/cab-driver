package com.cabdespatch.driverapp.beta;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class cdToast
{

	private static int sGravity = Gravity.TOP;
	private static int vMargin = 0;

	public static void setTempBottomGravity()
	{
		sGravity = Gravity.BOTTOM;
		vMargin = 10;
	}

	private static void resetGravity()
	{
		sGravity = Gravity.TOP;
		vMargin = 0;
	}

	public static void showShort(Context _c, int _stringResource)
	{
		showShort(_c, _c.getString(_stringResource));
	}

	public static void showShort(Context _c, String _message)
	{
		showShort(_c, _message, "");
	}

	public static void showShort(Context _c, int _message, int _title)
	{
		showShort(_c, _c.getString(_message), _c.getString(_title));
	}

	public static void showShort(Context _c, String _message, String _title)
	{
		show(_c, _message, _title, Toast.LENGTH_SHORT);
	}


	public static void showLong(Context _c, int _resourceID)
	{
		showLong(_c, _c.getString(_resourceID));
	}

	public static void showLong(Context _c, String _message)
	{
		showLong(_c, _message, "");
	}

	public static void showLong(Context _c, int _message, int _title)
	{
		showLong(_c, _c.getString(_message), _c.getString(_title));
	}

	public static void showLong(Context _c, String _message, String _title)
	{
		show(_c, _message, _title, Toast.LENGTH_LONG);
	}

	//CLAY remove this and replace all references with showShort or showLong
	public static void show(Context _c, String _message, int _length)
	{
		show(_c, _message, "", _length);
	}

	public static void show(Context _c, String _message, String _title, final int _length)
	{

		final Context context = (_c.getApplicationContext());

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final View layout = inflater.inflate(R.layout.toast, null);

		//ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
		//image.setImageResource(R.drawable.big_icon);


		View headerView = layout.findViewById(R.id.toast_title_layout);
		View smallToastIcon = layout.findViewById(R.id.toast_image_small);

		if(_title.equals(""))
		{
			headerView.setVisibility(View.GONE);
		}
		else
		{
			smallToastIcon.setVisibility(View.GONE);

			TextView title = (TextView) layout.findViewById(R.id.toast_title);
			title.setText(_title);
		}

		TextView text = (TextView) layout.findViewById(R.id.toast_text);
		text.setText(_message);

		TextView footer = (TextView) layout.findViewById(R.id.toast_footer);
		footer.setVisibility(View.GONE);



		Handler h = new Handler(context.getMainLooper());

		h.post(new Runnable()
		{

			@Override
			public void run()
			{
				Toast toast = new Toast(context);
				//toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.setGravity(sGravity, 0, vMargin);
				toast.setMargin(0, 0);
				toast.setDuration(_length);
				toast.setView(layout);
				toast.show();

				resetGravity();
			}

		});


	}
}
