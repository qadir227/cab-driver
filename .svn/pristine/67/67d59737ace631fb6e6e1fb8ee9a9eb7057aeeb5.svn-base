package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.Layout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HandyTools
{
    public static class StorageHelper
    {

        private boolean externalStorageReadable, externalStorageWritable;

        public StorageHelper()
        {

        }

        public boolean isExternalStorageReadable() {
            checkStorage();
            return externalStorageReadable;
        }

        public boolean isExternalStorageWritable() {
            checkStorage();
            return externalStorageWritable;
        }

        public boolean isExternalStorageReadableAndWritable() {
            checkStorage();
            return externalStorageReadable && externalStorageWritable;
        }

        private void checkStorage() {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                externalStorageReadable = externalStorageWritable = true;
            } else if (state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
                externalStorageReadable = true;
                externalStorageWritable = false;
            } else {
                externalStorageReadable = externalStorageWritable = false;
            }
        }

    }

    public static void runAfterLayout(final View _v, final Runnable _r)
    {
        class postLayoutRunner
        {
            private Runnable oRunnable;
            private View oView;
            private ViewTreeObserver.OnGlobalLayoutListener oListener;

            public postLayoutRunner(View _v, Runnable _r)
            {
                this.oRunnable = _r;
                this.oView = _v;

                this.oListener = getListener();

                _v.getViewTreeObserver().addOnGlobalLayoutListener(this.oListener);
            }

            private ViewTreeObserver.OnGlobalLayoutListener getListener()
            {
                return new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout()
                    {
                        postLayoutRunner.this.oView.post(postLayoutRunner.this.oRunnable);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        {
                            _v.getViewTreeObserver().removeOnGlobalLayoutListener(postLayoutRunner.this.oListener);
                        }
                        else
                        {
                            _v.getViewTreeObserver().removeGlobalOnLayoutListener(postLayoutRunner.this.oListener);
                        }
                    }
                } ;
            }
        }

        new postLayoutRunner(_v, _r);
    }

    public static boolean isTextViewEllipsized(TextView _t)
    {
        Layout layout = _t.getLayout();

            int lines = layout.getLineCount();
            if(lines > 0)
            {
                int ellipsisCount = layout.getEllipsisCount(lines-1);
                return ellipsisCount > 0;
            }
        else
            {
                return  false;
            }

    }
	
	public static class Strings
	{
		public static String fromUri(Uri _uri)
		{
			File f = new File(_uri.getPath());
			StringBuilder  stringBuilder = new StringBuilder();
			
			 BufferedReader reader;
			try
			{
				reader = new BufferedReader( new FileReader (f));
				
			    String         line = null;
			    String         ls = System.getProperty("line.separator");


					while( ( line = reader.readLine() ) != null ) {
					    stringBuilder.append( line );
					    stringBuilder.append( ls );
					}
					
					reader.close();
					
			} 
			catch (FileNotFoundException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			    return stringBuilder.toString();
			
		}

        public static String formatPrice(String _price)
        {
            return formatPrice(Double.valueOf(_price));

        }

        public static String formatPrice(Double _price)
        {
            String price = String.format("%.2f", _price);
            try
            {
                String[] priceEle = price.split(".");
                priceEle[1] = priceEle[1].substring(0, 2);

                price = priceEle[0] + "." + priceEle[1];

            } catch ( Exception ex ) { /* CLAY MAYBE */ }

            return price;
        }

        public static String parseNewLines(String _input)
        {
            return _input.replace("\\n", "\n");
        }

        public static String replaceNewLines(String _input)
        {
            return _input.replace("\n", "\\n");
        }
	}
	
	public static class Conversions
	{
				
		
		public static class Arrays
		{
			public static Queue <String> toQueue(String[] a)
			{
				Queue<String> q = new LinkedList<String>();
				
				for (String s: a)
				{
					q.add(s);
				}
				
				return q;
			}
		
		}
		
		public static class Lists
		{
			public Queue <String> toQueue(ArrayList<String> _l)
			{
					
				Queue<String> q = new LinkedList<String>();
				
				for (String s: _l)
				{
					q.add(s);
				}
				
				return q;
			}
		}
	}

	public static class BitmapTools
    {
        public Bitmap fromDrawable(Drawable drawable)
        {
            Bitmap bitmap = null;

            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if(bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }

            if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        public Bitmap fromFile(File file)
        {
            String filePath = file.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            return bitmap;
        }

        public Bitmap SaveToPng(Bitmap bitmap, int quality, int targetSizeKb, File file) throws FileNotFoundException
        {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            boolean recompress = true;

            while(recompress)
            {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

                int kb = (int) (file.length() / 1024);
                if(kb < targetSizeKb)
                {
                    recompress = false;
                }
                else
                {
                    width = (width/100) * 90;
                    height = (height/100) * 90;

                    Matrix matrix = new Matrix();
                    matrix.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                                         new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
                    Bitmap newbitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    //Bitmap newbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    bitmap.recycle();
                    bitmap = newbitmap;

                    //bitmap = Resize(bitmap, width, height);
                }
            }

            return bitmap;
        }









    }

    public static class ActivityTools
    {
        public static boolean appInstalled(Context _c, String uri)
        {
            PackageManager pm = _c.getPackageManager();
            boolean app_installed;
            try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                app_installed = true;
            }
            catch (PackageManager.NameNotFoundException e) {
                app_installed = false;
            }
            return app_installed;
        }

        public static void startActivityByPackageName(Context _c, String _uri)
        {
            PackageManager pm = _c.getPackageManager();
            Intent launchIntent = pm.getLaunchIntentForPackage(_uri);
            _c.startActivity(launchIntent);
        }
    }

    public static class FileTools
    {
        public byte[] ToByteArray(File file)
        {

            byte[] bytes = new byte[(int) file.length()];

            // funny, if can use Java 7, please uses Files.readAllBytes(path)
            try(FileInputStream fis = new FileInputStream(file)){
                fis.read(bytes);
                return bytes;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }

}
