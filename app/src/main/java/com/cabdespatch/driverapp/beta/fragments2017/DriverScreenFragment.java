package com.cabdespatch.driverapp.beta.fragments2017;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.SOUNDMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.ViewFader;
import com.cabdespatch.driverapp.beta.activities.PlotList;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.param;
import com.cabdespatch.driverapp.beta.paramGroup;
import com.cabdespatch.driverapp.beta.pdaLocation;
import com.cabdespatch.driverapp.beta.plotList;
import com.cabdespatch.driverapp.beta.services.DataService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverScreenFragment extends LoggedInFragment
{


    public DriverScreenFragment()
    {
        // Required empty public constructor
    }

    private boolean fMenuVisible;
    private RadioButton radShowCars;
    private boolean menuButtonEnabled()
    {
        return fMenuVisible;
    }

    @Override
    public void onTick()
    {
        if(menuButtonEnabled())
        {
            //do nothing
        }
        else if(getSecondsSinceResume() > 5)
        {
            enableMenuButton();
            STATUSMANAGER.STATUSES.HIDE_MENU_BUTTON.putValue(getContext(), false);
        }
    }

    @Override
    protected View onCreateTickingView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState)
    {
        View v = _inflater.inflate(R.layout.fragment_driverscreen, null);

        this.hasHadInitialPlotUpdate = false;
        this.layRank = (ViewGroup) v.findViewById(R.id.lay_onRank);

        //bindService();

        //BROADCASTERS.JobTotals(this.getContext());

        this.oPlots = SETTINGSMANAGER.getPlotsAll(this.getContext());
        this.oCurrentLocation = STATUSMANAGER.getCurrentLocation(this.getContext());

        this.prgFutureWork = (ProgressBar) v.findViewById(R.id.frmDriver_prgFutureJobs);

        ImageButton btnPlot = (ImageButton) v.findViewById(R.id.frmDriver_btnJobButton);
        if(SETTINGSMANAGER.SETTINGS.STRICT_WITH_GPS.parseBoolean(this.getContext()))
        {
            //never enable the plot button
            btnPlot.setEnabled(false);
        }
        else if(SETTINGSMANAGER.SETTINGS.STRICT_WITHOUT_GPS.parseBoolean(this.getContext()))
        {
            //only enable if we DONT have a valid gps signal
            if(STATUSMANAGER.getBoolean(this.getContext(), STATUSMANAGER.STATUSES.HAS_VALID_GPS))
            {
                btnPlot.setEnabled(false);
            }
            else
            {
                btnPlot.setEnabled(true);
            }
        }
        else
        {
            //always enable
            btnPlot.setEnabled(true);
        }

        /*
        Boolean meterAvailable = SETTINGSMANAGER.SETTINGS.METER_ENABLED.parseBoolean(this.getContext());
        if(meterAvailable || true)
        {
            //startTaxiMeter();
        }*/

        btnPlot.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                v.setVisibility(View.INVISIBLE);
                Intent i = new Intent(DriverScreenFragment.this.getContext(), PlotList.class);
                i.setAction(PlotList.ACTION_SEND_PLOT_UPDATE);
                Globals.StartActivity(DriverScreenFragment.this.getContext(),i);


            }
        });

        btnMenu = (ImageButton) v.findViewById(R.id.frmDriver_btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DriverScreenFragment.this.showMenu();
            }
        });

        this.btnPanic = (ImageButton) v.findViewById(R.id.frmDriver_btnPanic);
        if(SETTINGSMANAGER.SETTINGS.USE_PANIC.parseBoolean(this.getContext()))
        {
            if(SETTINGSMANAGER.SETTINGS.LONG_PRESS_PANIC.parseBoolean(this.getContext()))
            {
                this.btnPanic.setImageResource(R.drawable.btn_panic_armed);
                this.btnPanic.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        Globals.CrossFunctions.Panic(DriverScreenFragment.this.getContext());
                        return true;
                    }
                });
            }
            else
            {
                this.btnPanic.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(DriverScreenFragment.this.panicArmed)
                        {
                            Globals.CrossFunctions.Panic(DriverScreenFragment.this.getContext());
                        }
                        else
                        {
                            DriverScreenFragment.this.panicArmed = true;
                            Globals.CrossFunctions.armPanic(DriverScreenFragment.this.btnPanic, DriverScreenFragment.this.getActivity(), new Globals.UnarmPanicListener()
                            {

                                @Override
                                public void unarmPanic()
                                {
                                    DriverScreenFragment.this.panicArmed = false;
                                }
                            });
                        }
                    }
                });
            }
        }
        else
        {
            btnPanic.setVisibility(View.INVISIBLE);
        }




        this.flip = (ViewFlipper) v.findViewById(R.id.frmDriver_flip);

        this.flipIn = AnimationUtils.loadAnimation(this.getContext(), R.anim.in_from_right);
        this.flipOut = AnimationUtils.loadAnimation(this.getContext(), R.anim.out_to_left);

        this.flipIn.setDuration(500);
        this.flipOut.setDuration(500);

        this.flip.setInAnimation(this.flipIn);
        this.flip.setOutAnimation(this.flipOut);


        this.flipOut.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationEnd(Animation animation)
            {
                //DriverScreenFragment.this.canFlip = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation animation)
            {
                //animation.start();
            }

        });

		/*ImageButton btnCarsWork = (ImageButton) this.findViewById(R.id.frmDriver_btnJobsCarsSwitch);
		btnCarsWork.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(DriverScreenFragment.this.canFlip)
				{
					DriverScreenFragment.this.canFlip = false;
					DriverScreenFragment.this.flip.showNext();
				}

			}
		});*/

        radShowCars = (RadioButton) v.findViewById(R.id.frmDriver_btnShowCars);

        this.oListCars = new paramGroup();
        this.oAdapterCars = new DriverScreenFragment.PlotAndCountAdapter(this.oListCars.getParamList());

        ListView lstCars = (ListView) v.findViewById(R.id.frmDriver_lstCarsAvaliable);
        lstCars.setAdapter(this.oAdapterCars);
        lstCars.setSelector(new ColorDrawable(0x0));

        this.oListFutureJobs = new paramGroup();
        this.oAdapterFutureJobs = new PlotAndCountAdapter(this.oListFutureJobs.getParamList());

        ListView lstFutureJobs = (ListView) v.findViewById(R.id.frmDriver_lstFutureJobs);
        lstFutureJobs.setAdapter(this.oAdapterFutureJobs);
        lstFutureJobs.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                param p = (param) adapter.getItemAtPosition(position);
                String shortPlot = p.getName();
                String longPlot = SETTINGSMANAGER.getPlotsAll(DriverScreenFragment.this.getContext()).getPlotByShortName(shortPlot).getLongName();

                BROADCASTERS.Bid(DriverScreenFragment.this.getContext(),shortPlot,"f");

                cdToast.show(v.getContext(), "Bid sent for: " + longPlot, "Bid Sent", Toast.LENGTH_LONG);

                DriverScreenFragment.this.radShowCars.setChecked(true);

            }

        });

        //lstFutureJobs.setSelector(new ColorDrawable(0x0));



        this.oListWork = new ArrayList<String>();
        this.oAdapterWork = new WorkAdapter(this.oListWork);

        ListView lstWork = (ListView) v.findViewById(R.id.frmDriver_lstWorkAvaliable);
        lstWork.setAdapter(this.oAdapterWork);
        lstWork.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                String shortPlot = (String) adapter.getItemAtPosition(position);
                String longPlot = SETTINGSMANAGER.getPlotsAll(DriverScreenFragment.this.getContext()).getPlotByShortName(shortPlot).getLongName();

                BROADCASTERS.Bid(DriverScreenFragment.this.getContext(),shortPlot,"s");

                cdToast.show(v.getContext(), "Bid sent for: " + longPlot, "Bid Sent", Toast.LENGTH_LONG);

                DriverScreenFragment.this.oListWork = new ArrayList<String>();
                DriverScreenFragment.this.oAdapterWork.setWorkList(DriverScreenFragment.this.oListWork);
                DriverScreenFragment.this.oAdapterWork.notifyDataSetChanged();

            }

        });



        radShowCars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    DriverScreenFragment.this.flip.setDisplayedChild(1);
                }
            }
        });

        RadioButton radShowBids = (RadioButton) v.findViewById(R.id.frmDriver_btnShowBids);
        radShowBids.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    DriverScreenFragment.this.flip.setDisplayedChild(0);
                }
            }

        });


        RadioButton radShowFutureJobs = (RadioButton) v.findViewById(R.id.frmDriver_btnShowFutureJobs);
        if(Boolean.valueOf(SETTINGSMANAGER.SETTINGS.SHOW_FUTURE_JOBS.getValue(this.getContext())))
        {
            radShowFutureJobs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if(isChecked)
                    {
                        DriverScreenFragment.this.prgFutureWork.setVisibility(View.VISIBLE);
                        BROADCASTERS.FutureJobs(DriverScreenFragment.this.getContext());
                        DriverScreenFragment.this.flip.setDisplayedChild(2);
                    }
                }

            });
        }
        else
        {
            radShowFutureJobs.setVisibility(View.GONE);
        }


        radShowBids.setChecked(true);

        /*
        this.oBroadcast = new BroadcastHandler();
        this.registerReceiver(this.getContext().oBroadcast, new IntentFilter(BROADCASTERS.CARS_WORK_UPDATE));
        this.registerReceiver(this.getContext().oBroadcast, new IntentFilter(BROADCASTERS.PLOT_UPDATED));
        this.registerReceiver(this.getContext().oBroadcast, new IntentFilter(BROADCASTERS.FUTURE_JOBS_UPDATE));

        DataService.checkOutstandingDriverMessages(this.getContext());

        /*Integer x = 0;
        for(View v:getViewsByTag("SAFETY"))
        {
            x++;
            cdToast.showShort(this.getContext(), x.toString() + ": " + String.valueOf(v.getId()));
        }*/

        this.onLocationChange(STATUSMANAGER.getCurrentLocation(this.getContext()), false, false);
        STATUSMANAGER.setStatusBarText(this.getContext());

        return v;
    }



    private boolean panicArmed = false;
    private ImageButton btnPanic;
    private Boolean hasHadInitialPlotUpdate;

    private ViewGroup layRank;



    private pdaLocation oCurrentLocation;
    private plotList oPlots;
    //private BroadcastHandler oBroadcast;
    private List<String> oListWork;
    private paramGroup oListCars;
    private paramGroup oListFutureJobs;
    private PlotAndCountAdapter oAdapterCars;
    private PlotAndCountAdapter oAdapterFutureJobs;
    private WorkAdapter oAdapterWork;
    private ViewFlipper flip;
    private Animation flipIn;
    private Animation flipOut;
    private ProgressBar prgFutureWork;
    private ImageButton btnMenu;
    //private boolean canFlip = true;




    @Override
    protected void onStopping()
    {

    }

    @Override
    public void onResuming()
    {
        if(STATUSMANAGER.STATUSES.HIDE_MENU_BUTTON.parseBoolean(getContext()))
        {
            disableMenuButton();
        }
        else
        {
            enableMenuButton();
        }

    }

    private void disableMenuButton()
    {
        this.fMenuVisible = false;
        View v = getActivity().findViewById(R.id.frmDriver_btnMenu);
        v.setVisibility(View.INVISIBLE);

        View v2 = getActivity().findViewById(R.id.frmDriver_prgMenuHider);
        v2.setVisibility(View.VISIBLE);
    }

    private void enableMenuButton()
    {
        this.fMenuVisible = true;
        View v = getActivity().findViewById(R.id.frmDriver_btnMenu);
        v.setVisibility(View.VISIBLE);

        View v2 = getActivity().findViewById(R.id.frmDriver_prgMenuHider);
        v2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBroadcastReceived(Context _context, Intent _intent)
    {
        if (_intent.getAction().equals(BROADCASTERS.CARS_WORK_UPDATE))
        {
            DriverScreenFragment.this.onCarsWorkMessage(_context);
        }
        else if (_intent.getAction().equals(BROADCASTERS.FUTURE_JOBS_UPDATE))
        {
            DriverScreenFragment.this.onFutureJobsMessage(_context);
        }
        else if(_intent.getAction().equals(BROADCASTERS.LOCATION_UPDATED))
        {
            pdaLocation p = STATUSMANAGER.getCurrentLocation(_context);
            Boolean force = _intent.getBooleanExtra(DataService._MESSAGEEXTRA, false);
            DriverScreenFragment.this.onLocationChange(p, true, force);
        }
    }

    protected void onLocationChange(pdaLocation _p, Boolean _doNotify, boolean _force)
    {

        Boolean doStuff = false;
        if(this.oCurrentLocation == null)
        {
            this.oCurrentLocation = _p;
            doStuff = true;
        }
        else if(!(_p.getPlot().equals(this.oCurrentLocation.getPlot())))
        {
            this.oCurrentLocation = _p;
            doStuff = true;
            //Toast.makeText(this.getContext(), "New Location: " + _p.getPlot().getLongName(), Toast.LENGTH_SHORT).show();
        }
        else if(!(this.hasHadInitialPlotUpdate))
        {
            this.hasHadInitialPlotUpdate = true;
            doStuff = true;
        }


        if((doStuff) || (_force))
        {
            if(_doNotify)
            {
                Boolean soundEnabled = SETTINGSMANAGER.SETTINGS.PLOT_ANNOUNCEMENTS_ENABLED.parseBoolean(this.getContext());
                if(soundEnabled)
                {
                    SOUNDMANAGER.playPlotAnnouncement(this.getContext(), _p.getPlot());
                }
                else
                {
                    cdToast.showLong(this.getContext(), "You are now in: " + _p.getPlot().getLongName());
                }
            }

            Boolean onRank = STATUSMANAGER.getBoolean(this.getContext(), STATUSMANAGER.STATUSES.ON_RANK);
            String debug = "CURRENTLY ON RANK: " + (onRank?"YES":"NO") + "\n";
            debug += "New Plot: " + _p.getPlot().getShortName() + " (" + _p.getPlot().getLongName() + ")\n";
            debug += "Plot is " + (_p.getPlot().isRank()?"":"NOT ") + "a rank \n";
            if(_p.getPlot().isRank())
            {
                if(SETTINGSMANAGER.SETTINGS.USE_FLAGDOWN.parseBoolean(this.getContext()) == true)
                {

                    if(!(onRank))
                    {
                        debug += "Putting on rank\n";
                        //cdToast.showShort(this.getContext(), "On Rank");
                        ViewFader.fadeIn(this.layRank);
                        this.layRank.setClickable(true);
                        STATUSMANAGER.putBoolean(this.getContext(), STATUSMANAGER.STATUSES.ON_RANK, true);
                        BROADCASTERS.onRank(this.getContext());
                    }
                    else
                    {
                        debug += "NOT putting on rank as driver is already on rank...\n";
                    }
                }
            }
            else
            {
                if(SETTINGSMANAGER.SETTINGS.USE_FLAGDOWN.parseBoolean(this.getContext()) == true)
                {
                    if(onRank)
                    {
                        debug += "Taking off rank\n";
                        //cdToast.showShort(this.getContext(), "Off Rank");
                        ViewFader.fadeOutToGone(layRank);
                        this.layRank.setClickable(false);
                        STATUSMANAGER.putBoolean(this.getContext(), STATUSMANAGER.STATUSES.ON_RANK, false);
                        BROADCASTERS.offRank(this.getContext());
                    }
                    else
                    {
                        debug += "NOT taking off rank as driver is already off rank...\n";
                    }
                }
            }

            debug += "     -----     \n";

            if(false)
            {
                //debugLog(debug);
                /*
                try
                {
                    File f = new File(getExternalFilesDir(null), "plot.log");
                    if(!(f.exists()))
                    {
                        f.createNewFile();
                    }
                    FileWriter fw = new FileWriter(f, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.append(debug);
                    bw.newLine();
                    bw.close();
                }
                catch (Exception ex)
                {
                    cdToast.showLong(DriverScreenFragment.this, "Could not write to debug");
                }*/

            }

        }

    }


    protected void onFutureJobsMessage(Context _c)
    {
        this.prgFutureWork.setVisibility(View.GONE);
        this.oListFutureJobs = STATUSMANAGER.getFutureJobs(_c);
        this.oAdapterFutureJobs.setItemsList(this.oListFutureJobs.getParamList());
        this.oAdapterFutureJobs.notifyDataSetChanged();
    }

    protected void onCarsWorkMessage(Context _c)
    {
        this.oListCars = STATUSMANAGER.getCarsAvailable(_c);
        this.oAdapterCars.setItemsList(this.oListCars.getParamList());
        this.oAdapterCars.notifyDataSetChanged();



        this.oListWork = STATUSMANAGER.getWorkAvailable(_c);
        this.oAdapterWork.setWorkList(this.oListWork);
        this.oAdapterWork.notifyDataSetChanged();

        if(this.oListWork.size() > 0)
        {
            SOUNDMANAGER.playWorkAvailableSound(_c, this.oListWork);
        }
    }

    private class PlotAndCountAdapter extends BaseAdapter
    {
        private List<param> oItems;

        public PlotAndCountAdapter(List<param> _items)
        {
            this.oItems = _items;
        }

        public void setItemsList(List<param> _items)
        {
            this.oItems = _items;
        }

        @Override
        public int getCount()
        {
            return this.oItems.size();
        }

        @Override
        public Object getItem(int index)
        {
            return this.oItems.get(index);
        }

        @Override
        public long getItemId(int arg0)
        {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int index, View arg1, ViewGroup arg2)
        {
            //CLAY!
            LinearLayout l = (LinearLayout) DriverScreenFragment.this.getActivity().getLayoutInflater().inflate(R.layout.row_cars, null);
            TextView lblCount = (TextView) l.findViewById(R.id.rowCar_count);
            TextView lblPlot = (TextView) l.findViewById(R.id.rowCar_Plot);

            String shortPlotName = this.oItems.get(index).getName();
            String longPlotName = DriverScreenFragment.this.oPlots.getPlotByShortName(shortPlotName).getLongName();
            String count = this.oItems.get(index).getValue();

            lblCount.setText(count);
            lblPlot.setText(longPlotName);

            return l;
        }

    }

    private class WorkAdapter extends BaseAdapter
    {

        private List<String> oWork;

        public WorkAdapter(List<String> _work)
        {
            this.oWork = _work;
        }

        public void setWorkList(List<String> _work)
        {
            this.oWork = _work;
        }

        @Override
        public int getCount()
        {
            return this.oWork.size();
        }

        @Override
        public String getItem(int index)
        {
            return this.oWork.get(index);
        }

        @Override
        public long getItemId(int arg0)
        {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int index, View arg1, ViewGroup arg2)
        {
            //CLAY!
            LinearLayout l = (LinearLayout) DriverScreenFragment.this.getActivity().getLayoutInflater().inflate(R.layout.row_plot, null);
            TextView t = (TextView) l.findViewById(R.id.plotRow_label);

            String shortPlotName = this.oWork.get(index);
            String longPlotName = DriverScreenFragment.this.oPlots.getPlotByShortName(shortPlotName).getLongName();

            t.setText(longPlotName);
            t.setTag(shortPlotName);
            return l;
        }

    }




}
