package com.cabdespatch.driverapp.beta.activities2017;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cabdespatch.driverapp.beta.R;

import java.util.ArrayList;

/**
 * Created by User on 18/01/2018.
 */

public class DataMonitorActivity extends DataActivity
{


    private class SuperAwesomeAdapter extends BaseAdapter
    {

        private ArrayList<String> oItems;

        public SuperAwesomeAdapter()
        {
            oItems = new ArrayList<>();
        }

        public void addItem(String _item)
        {
            oItems.add(0, _item);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return oItems.size();
        }

        @Override
        public String getItem(int i)
        {
            return oItems.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            TextView t = new TextView(DataMonitorActivity.this);
            t.setText(getItem(i));
            return t;
        }
    }

    private SuperAwesomeAdapter aAdapter;


    @Override
    public void onCreate(Bundle _savledInstance)
    {
        super.onCreate(_savledInstance);
        setContentView(R.layout.activity_datamonitor);

        aAdapter = new SuperAwesomeAdapter();
        ListView v = (ListView) findViewById(R.id.list);
        v.setAdapter(aAdapter);
    }

    @Override
    protected String getDebugTag()
    {
        return "DataMonitorActivity";
    }

    @Override
    protected void showDriverMessage()
    {

    }

    @Override
    protected void onNetworkStateChange(Boolean _connected)
    {

    }

    @Override
    protected void onBroadcastReceived(Context _context, Intent _intent) {

    }

    @Override
    public void onLogEvent(String _data)
    {
        aAdapter.addItem(_data);
    }
}
