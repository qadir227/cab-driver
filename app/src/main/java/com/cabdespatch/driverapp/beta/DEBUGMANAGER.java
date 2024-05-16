package com.cabdespatch.driverapp.beta;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pleng on 13/01/2016.
 */
public class DEBUGMANAGER
{


    public static File getLogFile(Context _c)
    {
        return new File(_c.getExternalFilesDir(null), "debug.log");
    }

    public static void Log(Context _c, String __message)
    {
        Log(_c, "", __message);
    }

    public static void Log(Context _c, String _tag, String __message)
    {
        Log(getLogFile(_c), _tag, __message);
    }

    public static class LogLevel
    {
        private int oLevel;
        public LogLevel(int _level) {oLevel = _level;}
        public Integer get() { return oLevel; }
    }

    public static final LogLevel LOG_LEVEL_ERROR = new LogLevel(100);
    public static final LogLevel LOG_LEVEL_WARN = new LogLevel(200);
    public static final LogLevel LOG_LEVEL_DEBUG = new LogLevel(300);

    public static void Log(LogLevel _level, String _tag, String __message)
    {
        if(BuildConfig.DEBUG)
        {
            if(_level.get().equals(LOG_LEVEL_ERROR.get()))
            {
                Log.e(_tag, __message);
            }
            else if(_level.get().equals(LOG_LEVEL_WARN.get()))
            {
                Log.w(_tag, __message);
            }
            else if(_level.get().equals(LOG_LEVEL_DEBUG.get()))
            {
                Log.d(_tag, __message);
            }
            else
            {
                Log.e(_tag, __message);
                Log.e("ERRLV", "Unknown LogLevel " + _level.get().toString());
            }

        }
    }



    public static void Log(File _file, String __message)
    {
        Log(_file, "", __message);
    }
    public static void Log(File _file, String _tag, String __message)
    {
        //Debug.waitForDebugger();

        if(BuildConfig.DEBUG)
        {
            Log.e(_tag, __message);
            //cdToast.sh
            if(_tag.equals("")) { _tag = "(no tag)"; }

            if (false)
            {
                try
                {
                    if(!(_file.exists()))
                    {
                        _file.createNewFile();
                    }


                    FileWriter fw = new FileWriter(_file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter p = new PrintWriter(bw);


                    SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm.ss dd-MM-yyyy ");//dd/MM/yyyy
                    Date now = new Date();
                    String output = sdfDate.format(now);
                    output += "\n";
                    output += ("\t" + _tag + "\n");
                    output += ("\t" + __message);

                    p.println(output);

                    p.close();
                    bw.close();
                    fw.close();


                }
                catch (IOException e)
                {
                    Log.e("EXCEPTION", e.toString());
                }
            }
        }

    }

}
