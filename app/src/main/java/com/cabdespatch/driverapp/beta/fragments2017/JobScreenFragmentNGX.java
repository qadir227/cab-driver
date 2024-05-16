package com.cabdespatch.driverapp.beta.fragments2017;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.Layout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.cabdespatch.driverapp.beta.BROADCASTERS;
import com.cabdespatch.driverapp.beta.Globals;
import com.cabdespatch.driverapp.beta.HandyTools;
import com.cabdespatch.driverapp.beta.JobButtonPressResult;
import com.cabdespatch.driverapp.beta.MileageTool;
import com.cabdespatch.driverapp.beta.PauseAndRun;
import com.cabdespatch.driverapp.beta.R;
import com.cabdespatch.driverapp.beta.SETTINGSMANAGER;
import com.cabdespatch.driverapp.beta.SOUNDMANAGER;
import com.cabdespatch.driverapp.beta.STATUSMANAGER;
import com.cabdespatch.driverapp.beta.USERREQUESTS;
import com.cabdespatch.driverapp.beta.ViewFader;
import com.cabdespatch.driverapp.beta.activities.ErrorActivity;
import com.cabdespatch.driverapp.beta.activities2017.PaymentActivity;
import com.cabdespatch.driverapp.beta.cabdespatchJob;
import com.cabdespatch.driverapp.beta.cdToast;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_CLEARBREAK;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_DriverNotes;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_GetPrice;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_MsgBox_New;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_POBSTP;
import com.cabdespatch.driverapp.beta.dialogs.Dialog_PopOut;
import com.cabdespatch.driverapp.beta.fragments.FairMeterFragment;
import com.cabdespatch.driverapp.beta.payment.CreditCardHandler;
import com.cabdespatch.driverapp.beta.pdaLocation;
import com.cabdespatch.driverapp.beta.plot;
import com.cabdespatch.driverapp.beta.services.DataService;
import com.sumup.merchant.reader.api.SumUpState;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;

/**
 * Created by Pleng on 12/02/2017.
 */

public class JobScreenFragmentNGX extends LoggedInFragment implements Dialog_DriverNotes.OnJobNotesEnteredListener
{

    private HaveYouMoved fHaveYouMoved;
    private Dialog_POBSTP dlg_POBSTC;
    private Dialog_CLEARBREAK dlg_ClearBreak;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        shouldShowMeter = false;
        meterIsShowing = false;

        fHaveYouMoved = new HaveYouMoved(true);



    }

    @Override
    protected View onCreateTickingView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState)
    {
        View v = _inflater.inflate(R.layout.fragment_job_screen, null);

        STATUSMANAGER.releaseLock(this.getContext());
        //Toast.makeText(this, "Dropping lock", Toast.LENGTH_SHORT).show();

        this.flip = (ViewFlipper) v.findViewById(R.id.frmJob_ViewFlipper);

        this.oNudger = new PobNudger(this.getContext());
        this.clearPending = false;


        ImageButton btnMenu = (ImageButton) v.findViewById(R.id.frmJob_btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                JobScreenFragmentNGX.this.showMenu();
            }
        });

        this.btnPlay = (ImageButton) v.findViewById(R.id.frmJob_btnPlay);
        this.btnPlay.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                setupViewFlipper(STATUSMANAGER.getCurrentJob(v.getContext()));
                //JobScreenFragmentNGX.this.startFlipping(false);
            }
        });

        this.sliderViews = new LinkedList<SliderView>();

        cabdespatchJob j = STATUSMANAGER.getCurrentJob(this.getContext());
        Boolean onRank = STATUSMANAGER.getBoolean(this.getContext(), STATUSMANAGER.STATUSES.ON_RANK);
        if(j.isFlagDown())
        {
            this.flip.addView(this.getJobDetailView(VIEW_TYPE.FLAGDOWN, ""));
            if(SETTINGSMANAGER.SETTINGS.METER_TYPE.notEquals(this.getContext(), SETTINGSMANAGER.METER_TYPE.NONE))
            {
                psuedoShowMeter();
            }
        }
        else
        {
            setupViewFlipper(j);
            if(onRank)
            {
                setupOnRankLock(v);
            }
            else if(false)
            {
                setupOnRankLock(v);
            }
        }

        this.oFragMeter = new FairMeterFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragFairMeter, (Fragment) this.oFragMeter);
        fragmentTransaction.commit();

        //this.oFragMeter.getView().setVisibility(View.GONE);

        setupUIButtons(v);
        this.updateJobButtonAndSliders(j);
        //DataService.checkOutstandingDriverMessages(this.getContext());

        SumUpState.init(this.getContext());
        if(onRank)
        {
            this.flip.setVisibility(View.INVISIBLE);
        }
        else
        {
            ViewFader.fadeIn(this.flip);
        }



        return v;
    }


    @Override
    public void onJobNotesEntered()
    {
        btnJobStatus.setVisibility(View.INVISIBLE);

        class DoNewClick extends PauseAndRun
        {
            @Override
            protected void onPostExecute(Void _void)
            {
                JobScreenFragmentNGX.this.btnJobStatus.performClick();
            }
        }

        //job notes have been entered and saved to job
        //reperform the click to see what the next
        //required step is...

        //we need to give the UI time to deal with
        //the job notes dialog being dismissed in
        //case we need to show a new dialog (job price)
        //after...
        new DoNewClick().Start(ViewFader.DURATION + 300);
    }


    public static class VIEW_TYPE
    {
        public static final String TIME (Context _c) { return _c.getString(R.string.setting_tag_time); }
        public static final String PICKUP (Context _c) { return _c.getString(R.string.setting_tag_pickup); }
        public static final String DESTINATION (Context _c) { return _c.getString(R.string.setting_tag_destination); }
        public static final String VIA = "VIA";
        public static final String PRICE (Context _c) { return _c.getString(R.string.setting_tag_price); }
        public static final String NAME (Context _c) { return _c.getString(R.string.setting_tag_name); }
        public static final String COMMENTS (Context _c) { return _c.getString(R.string.setting_tag_comments); }
        public static final String VEHICLE_TYPE (Context _c) { return _c.getString(R.string.setting_tag_vehicle_type); }
        public static final String FLAGDOWN = "FLGD";
    }

    private int oItteratingSlide = -1;
    private int oStartSlide = 0;

    /*private class StatusReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context _c, Intent _intent)
        {
            cabdespatchJob j = STATUSMANAGER.getCurrentJob(_c);

            //cabdespatchJob will have been reset if clear is pending
            //and we're about to die anyway, so let's not bother processing
            //if we're expecting that the next status will be clear
            if(!(JobScreenFragmentNGX.this.clearPending))
            {
                JobScreenFragmentNGX.this.updateJobButtonAndSliders(j);
            }




        }

    }*/

    ViewFlipper flip;
    ImageButton btnPlay;

    ViewGroup btnJobStatus;
    ImageView imgJobStatus;
    TextView lblJobStatus;

    ViewGroup btnPanic;
    ViewGroup btnPanic_ActualView;
    TextView lblPanic;
    ImageButton btnPay;
    ImageButton btnMeter;
    //StatusReceiver recStatus;
    Dialog_MsgBox_New dlgRankLock;

    public static final String _NOJOBBUTTON = "_NOJOBBUTTON";

    private LinkedList<SliderView> sliderViews;
    private Boolean panicArmed = false;
    private PobNudger oNudger;
    private Boolean clearPending;
    private FairMeterFragment oFragMeter;

    private class PobNudger
    {
        private boolean oEnabled;
        private boolean oPlotEntered;
        private boolean oPlotExited;

        public PobNudger(Context _c)
        {
            this.oEnabled = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.PROMPT_POB_NUDGE.getValue(_c));
            this.oPlotEntered = false;
            this.oPlotExited = false;
        }

        public boolean shouldNudge(cabdespatchJob _j, String _currentPlot)
        {
            if(this.oEnabled)
            {
                cabdespatchJob.JOB_STATUS status = _j.getJobStatus();
                if((status.equals(cabdespatchJob.JOB_STATUS.ON_ROUTE)) || (status.equals(cabdespatchJob.JOB_STATUS.STP)))
                {
                    if(this.oPlotEntered)
                    {
                        //we have already entered the pickup plot... is the new plot different to the pickup plot
                        if(_currentPlot.equals(_j.getFromPlot()))
                        {
                            return false; //we're somehow still in the pickup plot???
                        }
                        else
                        {
                            this.oPlotExited = true; //do we even need this variable??
                            return true;
                        }
                    }
                    else
                    {
                        if(_currentPlot.equals(_j.getFromPlot())) { this.oPlotEntered = true; }
                        return false;
                    }
                }
                else
                {
                    return false; //inapproriate job status to be nudging
                }
            }
            else
            {
                return false;
            }
        }

    }

    private class HaveYouMoved extends MileageTool
    {
        private Double oStartLat = -999.99;
        private Double oStartLon = -999.99;
        private final Integer oMinsToWait = 3;
        private final Double oThreshold = 0.2; //miles
        private long startTime;
        private boolean expired;

        public HaveYouMoved(Boolean _dead)
        {
            pdaLocation p = STATUSMANAGER.getCurrentLocation(getContext());

            if(this.oStartLat == -999.99)
            {
                this.oStartLat = p.getLat();
                this.oStartLon = p.getLon();
            }

            expired = _dead;
            startTime   = System.currentTimeMillis();
        }

        public void tick()
        {
            if(expired)
            {
                //do nothing
            }
            else
            {
                long millis = System.currentTimeMillis() - this.startTime;
                int seconds = (int) Math.floor(millis / 1000);

                if(seconds >= (60 * oMinsToWait))
                {
                    doCheck();
                    expired = true;
                }
            }
        }

        public void doCheck()
        {
            pdaLocation p = STATUSMANAGER.getCurrentLocation(JobScreenFragmentNGX.this.getContext());
            Double newLat = p.getLat();
            Double newLon = p.getLon();

            Double mileageMoved = HaveYouMoved.this.getCrudeMileage(HaveYouMoved.this.oStartLat, HaveYouMoved.this.oStartLon, newLat, newLon);

            if(mileageMoved < HaveYouMoved.this.oThreshold)
            {
                if(STATUSMANAGER.getCurrentJob(JobScreenFragmentNGX.this.getContext()).getJobStatus().equals(cabdespatchJob.JOB_STATUS.ON_ROUTE))
                {
                    SOUNDMANAGER.askIfMoved(JobScreenFragmentNGX.this.getContext());
                }
            }

        }


    }


    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            try
            {
                cabdespatchJob j = STATUSMANAGER.getCurrentJob(JobScreenFragmentNGX.this.getContext());
                //String flightNo = j.getFlightNo();

                if(url.toUpperCase().startsWith(j.getFlightQueryUrl().toUpperCase()))
                {
                    view.loadUrl(url);
                }
                else
                {
                    try
                    {
                        Globals.WebTools.launchWebIntent(JobScreenFragmentNGX.this.getContext(), url);
                    }
                    catch (NullPointerException ex)
                    {
                        //CLAY DEBUG
                    }

                }

            }
            catch (Exception ex)
            {

            }

            return true;

        }
    }


    private void rejectJobFromRankLock()
    {
        BROADCASTERS.RejectJob(this.getContext());
        Globals.CrossFunctions.Back(this.getContext());
    }

    private void setupOnRankLock(View _v)
    {
        //this.flip.setVisibility(View.INVISIBLE);
        Dialog_MsgBox_New.OnMessageBoxButtonListener l = new Dialog_MsgBox_New.OnMessageBoxButtonListener()
        {
            @Override
            public void MessageBoxOKPressed()
            {
                dlgRankLock.cancelCountdown();
                JobScreenFragmentNGX.this.rejectJobFromRankLock();
            }

            @Override
            public void CountdownFinished()
            {
                JobScreenFragmentNGX.this.rejectJobFromRankLock();
            }
        };

        String message = getString(R.string.leaveRankPrompt);
        if(!(dlgRankLock==null))
        {
            //there is an existing dialog somehow... cancel it's countdown timer
            dlgRankLock.cancelCountdown();
            this.flip.setVisibility(View.VISIBLE);
        }
        dlgRankLock = new Dialog_MsgBox_New(this.getActivity(), R.drawable.big_icon, (ViewGroup) _v.findViewById(R.id.dlg_container), "", message, true, "Reject", 90, l);
        dlgRankLock.hideBackButton();

        //Debug.waitForDebugger();


        dlgRankLock.show();
        dlgRankLock.stretchToFill();

    }

    public View.OnClickListener btnJob_Click()
    {
        return new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                v.setVisibility(View.INVISIBLE);
                JobButtonPressResult res = Globals.CrossFunctions.jobButtonPressed(v);

                Integer action = res.getAction();

                switch (action)
                {
                    case JobButtonPressResult.ACTION_NONE:
                        showJobButtonAfter5Seconds();
                        break;
                    case JobButtonPressResult.ACTION_REQUEST_DRIVER_NOTES:
                        Dialog_DriverNotes dlg = new Dialog_DriverNotes(JobScreenFragmentNGX.this.getActivity(), (ViewGroup) JobScreenFragmentNGX.this.getView().findViewById(R.id.dlg_container), JobScreenFragmentNGX.this);
                        dlg.show(R.id.dlgDriverNotes_txtNotes);
                        break;
                    case JobButtonPressResult.ACTION_POD:
                        //do nothing - still handeld by data service at the moment...
                        break;
                    case JobButtonPressResult.ACTION_REQUEST_PRICE:

                        Globals.CrossFunctions.OnPriceUpdatedListener listener = new Globals.CrossFunctions.OnPriceUpdatedListener()
                        {
                            @Override
                            public void onPriceUpdated()
                            {
                                JobScreenFragmentNGX.this.updateJobButtonAndSliders(STATUSMANAGER.getCurrentJob(JobScreenFragmentNGX.this.getContext()));
                                JobScreenFragmentNGX.this.btnJobStatus.setVisibility(View.INVISIBLE); //we're sending a clear so we don't want
                                //the driver to go through a loop...
                            }
                        };

                        Globals.CrossFunctions.startSimplePriceRequest(JobScreenFragmentNGX.this.getActivity(),
                                (ViewGroup) JobScreenFragmentNGX.this.getView().findViewById(R.id.dlg_container),
                                JobScreenFragmentNGX.this.getActivity().getWindow(),
                                JobScreenFragmentNGX.this.getActivity().getCurrentFocus(),
                                listener);
                        break;
                    default:
                        ErrorActivity.genericReportableError("Unhandled JobButtonPressResult: " + String.valueOf(res.getAction()));
                }
            }
        };
    }

    private void showJobButtonAfter5Seconds()
    {
        new PauseAndRun()
        {
            @Override
            protected void onPostExecute(Void _void)
            {
                if(!(this == null))
                {
                    if(JobScreenFragmentNGX.this.btnJobStatus.getVisibility() == View.VISIBLE)
                    {
                        //do nothing
                    }
                    else
                    {
                        ViewFader.fadeIn(JobScreenFragmentNGX.this.btnJobStatus);
                    }
                }
            }
        }.Start(5000);
    }

    private Float button_text_size = (float) 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setupUIButtons(View _v)
    {
        if (this.btnPanic == null)
        {
            this.btnPanic = _v.findViewById(R.id.frmJob_btnPanic);

            //we now have a dummy button, for getting correct text sizing, and a real button
            //to get the label, first we need to get the inner button...

            //for the puropsed of setting visibility and click handlers, we still
            //want to use the parent view
            this.btnPanic_ActualView = (ViewGroup) this.btnPanic.getChildAt(1);

            this.lblPanic = (TextView) btnPanic_ActualView.getChildAt(1);

            this.btnJobStatus = _v.findViewById(R.id.frmJob_btnJobButton);
            this.imgJobStatus = (ImageView) this.btnJobStatus.getChildAt(0);
            this.lblJobStatus = (TextView) this.btnJobStatus.getChildAt(1);

            this.lblPanic.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout()
            {
                if (button_text_size < 1)
                {
                    /* CLAY IMPORTANT
                    removed due to compile error
                    but must be figured out before enabling NGX branch

                    TextView t = getActivity().findViewById(R.id.lblDummyButton);
                    button_text_size = t.getTextSize();

                    JobScreenFragmentNGX.this.lblJobStatus.setTextSize(TypedValue.COMPLEX_UNIT_PX, button_text_size);
                    JobScreenFragmentNGX.this.lblPanic.setTextSize(TypedValue.COMPLEX_UNIT_PX, button_text_size);
                    */

                }
            }
        });

        }



        this.btnJobStatus.setClickable(true);
        this.btnJobStatus.setOnClickListener(btnJob_Click());

        String extra = JobScreenFragmentNGX.this.getActivity().getIntent().getStringExtra(_NOJOBBUTTON);

        Boolean hideJobButton = true;
        if(extra==null)
        {
            hideJobButton = false;
        }
        else
        {
            if(extra.equals(""))
            {
                hideJobButton = false;
            }
            else
            {
                if(Boolean.valueOf(extra) == false)
                {
                    hideJobButton = false;
                }
            }
        }


        if(hideJobButton)
        {
            this.btnJobStatus.setVisibility(View.INVISIBLE);
            showJobButtonAfter5Seconds();
        }

        //todo
        if(SETTINGSMANAGER.SETTINGS.USE_PANIC.parseBoolean(this.getContext()))
        {

            if(SETTINGSMANAGER.SETTINGS.LONG_PRESS_PANIC.parseBoolean(JobScreenFragmentNGX.this.getContext()))
            {
                this.btnPanic.setBackgroundColor(getResources().getColor(R.color.button_background_panic_armed));
                this.btnPanic.setLongClickable(true);
                this.btnPanic.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        Globals.CrossFunctions.Panic(JobScreenFragmentNGX.this.getContext());
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
                        if(JobScreenFragmentNGX.this.panicArmed)
                        {
                            Globals.CrossFunctions.Panic(JobScreenFragmentNGX.this.getContext());
                        }
                        else
                        {
                            JobScreenFragmentNGX.this.armPanic();
                        }
                    }
                });
            }
        }
        else
        {
            this.btnPanic.setVisibility(View.INVISIBLE);
        }

        this.btnPay = (ImageButton) _v.findViewById(R.id.frmJob_btnPay);
        btnPay.setOnClickListener(btnPay_Click());

        this.btnMeter = (ImageButton) _v.findViewById(R.id.frmJob_btnMeter);
        btnMeter.setOnClickListener(btnMeter_Click());
    }

    private String getPriceString(cabdespatchJob j)
    {
        String price = "";
        if(j.getPriceDisplay().equals(cabdespatchJob.EMPTY))
        {
            String jobPrice = j.getPrice();
            if(Double.valueOf(jobPrice) > 0)
            {
                price = "£" + j.getPrice() + "\n";
            }

            String accountNo = j.getAccount();
            if(j.isCash())
            {
                accountNo = "(Cash)"; //socket server currently doesn't send an account number for cash jobs...!
            }
            else
            {
                accountNo = ("Account " + accountNo);
            }
            price += accountNo;

            Double bookingFee = j.getBookingFee();
            if(bookingFee > 0)
            {
                NumberFormat n = new DecimalFormat("###0.00");
                String bookingFeeString = ", including a booking fee of £" + n.format(bookingFee);

                if(Double.parseDouble(j.getAmountPrepaid()) > 0)
                {
                    bookingFeeString += " (which is taken from your payment to cover card fees).";
                }
                else
                {
                    bookingFeeString += " (of which a £0.25 commission has been added to your circuit fee).";
                }

                price += bookingFeeString;
            }

            if(!(j.getAmountPrepaid().equals("0.00")))
            {
                String amntString = ("\n\n(of which: £" + j.getAmountPrepaid() + " has already been paid - ");
                amntString += ("\nBalance: £" + j.getPriceBalance() + ")");

                price += amntString;
            }
        }
        else
        {
            price = j.getPriceDisplay();
        }

        return price;
    }

    private void setupViewFlipper(cabdespatchJob j)
    {
        this.flip.removeAllViews();

        String time = j.getTime();
        if(time.toUpperCase().contains("ASAP"))
        {
            time = "ASAP";
        }
        else
        {
            String[] actualTimeEles = time.split(" ");

            if (actualTimeEles.length >= 2 )
            {
                String actualTime = time.split(" ")[1];
                time = actualTime.substring(0, 5);
            }
            else
            {
                ErrorActivity.handleError(JobScreenFragmentNGX.this.getContext(), new ErrorActivity.ERRORS.INVALID_JOB_TIME_STRING(time, ""));
            }
        }


        this.flip.addView(this.getJobDetailView(VIEW_TYPE.TIME(JobScreenFragmentNGX.this.getContext()), time));
        String vType = j.getVehicleType();
        if(!(vType.isEmpty()))
        {
            this.flip.addView(this.getJobDetailView(VIEW_TYPE.VEHICLE_TYPE(JobScreenFragmentNGX.this.getContext()), vType));
        }

        this.flip.addView(this.getJobDetailView(VIEW_TYPE.PICKUP(JobScreenFragmentNGX.this.getContext()),j.getFromAddress(),0,(j.getJobStatus()== cabdespatchJob.JOB_STATUS.ON_ROUTE?true:false)));


        int viaCount = 0;
        for(cabdespatchJob.JobDetailLocation via:j.getVias())
        {
            //debugLog(via.toString());
            this.flip.addView(this.getJobDetailView(VIEW_TYPE.VIA, via.getAddressLine(), ++viaCount,(j.getJobStatus()== cabdespatchJob.JOB_STATUS.POB?true:false) ,via.getLat(), via.getLon()));
        }

        this.flip.addView(this.getJobDetailView(VIEW_TYPE.DESTINATION(JobScreenFragmentNGX.this.getContext()),j.getToAddress(), 0, (j.getJobStatus()== cabdespatchJob.JOB_STATUS.POB?true:false)));



        if(j.priceHasBeenUpdated())
        {
            String title = getString(R.string.price_slide_title_updated);
            this.flip.addView(this.getJobDetailView(VIEW_TYPE.PRICE(JobScreenFragmentNGX.this.getContext()),getPriceString(j), title));
        }
        else
        {
            if(j.isAccount())
            {
                this.flip.addView(this.getJobDetailView(VIEW_TYPE.PRICE(JobScreenFragmentNGX.this.getContext()),getPriceString(j)));
            }
            else
            {
                this.flip.addView(this.getJobDetailView(VIEW_TYPE.PRICE(JobScreenFragmentNGX.this.getContext()),getPriceString(j)));
            }
        }


        String name = j.getName();
        if(!(name.isEmpty()))
        {
            this.flip.addView(this.getJobDetailView(VIEW_TYPE.NAME(JobScreenFragmentNGX.this.getContext()), name));
        }

        String comments = j.getComments();
        if(!(comments.isEmpty()))
        {
            this.flip.addView(this.getJobDetailView(VIEW_TYPE.COMMENTS(JobScreenFragmentNGX.this.getContext()), comments));
        }

        String flightNo = j.getFlightNo();
        if(!((flightNo.equals("")||(flightNo.equals(cabdespatchJob.EMPTY)))))
        {
            WebView w = new WebView(JobScreenFragmentNGX.this.getContext());
            w.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            w.setWebViewClient(new MyWebViewClient());
            w.loadUrl(j.getFlightQueryUrl());

            this.flip.addView(w);
        }

        this.flip.setFlipInterval(4000);
        this.flip.setOnTouchListener(this.FlipperTouch());
        this.flip.setDisplayedChild(this.oStartSlide);
        this.startFlipping(false);
    }

    private View.OnClickListener btnMeter_Click()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Context c = v.getContext();
                if(HandyTools.ActivityTools.appInstalled(c, SETTINGSMANAGER.getTaxiMeterPackageName()))
                {
                    try
                    {
                        HandyTools.ActivityTools.startActivityByPackageName(c, SETTINGSMANAGER.getTaxiMeterPackageName());
                    }
                    catch (Exception ex)
                    {
                        cdToast.showShort(c, R.string.warning_could_not_start_meter_app);
                    }
                }
                else
                {
                    cdToast.showShort(c, R.string.warning_taxi_meter_not_installed);
                    // SETTINGSMANAGER.SETTINGS.METER_ENABLED.putValue(c, false);
                }
            }
        };
    }


    private View.OnClickListener btnPay_Click()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean showAmendPrice = SETTINGSMANAGER.CreditCardPayment.priceAmendAvailable(JobScreenFragmentNGX.this.getContext());

                if(showAmendPrice)
                {
                    Dialog_GetPrice dlg = new Dialog_GetPrice(JobScreenFragmentNGX.this.getActivity(), (ViewGroup) JobScreenFragmentNGX.this.getView().findViewById(R.id.dlg_container), true);
                    dlg.setOnFareSetListener(new Dialog_GetPrice.OnFareSetListener()
                    {
                        @Override
                        public void onFareSet(Double _fare)
                        {
                            JobScreenFragmentNGX.this.getHostActivity().hideKeyboard();
                            startTotalFareAdvice(_fare);
                        }
                    });
                    dlg.show(R.id.dlgPrice_txtFare);

                }
                else
                {
                    startTotalFareAdvice(Double.valueOf(STATUSMANAGER.getCurrentJob(JobScreenFragmentNGX.this.getContext()).getPrice()));
                }

            }
        };
    }


    private void startTotalFareAdvice(Double _baseFare)
    {
        //preFeeFare = _baseFare;

        Double surcharge = cabdespatchJob.calculateSurcharge(JobScreenFragmentNGX.this.getContext(), _baseFare);

        final Double totalFare = _baseFare + surcharge;

        String title = "£" + HandyTools.Strings.formatPrice(String.valueOf(totalFare));

        String message;
        if(surcharge>0)
        {
            message = getString(R.string.confirm_surcharge_prompt);
            message = message.replace("%s", HandyTools.Strings.formatPrice(String.valueOf(surcharge)));
        }
        else
        {
            message = getString(R.string.confirm_no_surcharge_prompt);
        }

        Dialog_MsgBox_New.OnMessageBoxButtonListener l = new Dialog_MsgBox_New.OnMessageBoxButtonListener()
        {
            @Override
            public void MessageBoxOKPressed()
            {
                cabdespatchJob j = STATUSMANAGER.getCurrentJob(JobScreenFragmentNGX.this.getContext());
                j.updatePrice(totalFare);
                STATUSMANAGER.setCurrentJob(JobScreenFragmentNGX.this.getContext(), j);
                JobScreenFragmentNGX.this.getHostActivity().hideKeyboard();
                startCreditCardPayment();
            }

            @Override
            public void CountdownFinished()
            {
                //no countdown
            }
        };
        new Dialog_MsgBox_New(JobScreenFragmentNGX.this.getActivity(), R.drawable.n_ico_card, (ViewGroup) JobScreenFragmentNGX.this.getActivity().findViewById(R.id.dlg_container), title, message, l).show();
    }

/*
    private Double preFeeFare;
    @Override
    public void onCreditCardPaymentResult(int _result, Double _amount)
    {
        String logData = "there was an anexpected response type from the payment class";

        switch(_result)
        {
            case CreditCardHandler.RESULT_CANX:
                logData = "Driver's request for credit card transaction was cancelled";
                cabdespatchJob jc = STATUSMANAGER.getCurrentJob(this.getContext());
                jc.revertPriceTo(preFeeFare);
                STATUSMANAGER.setCurrentJob(this.getContext(), jc);
                updatePriceSlide(true);
                break;
            case CreditCardHandler.RESULT_FAIL:
                logData = "Driver's request for credit card transaction failed";
                cabdespatchJob jf = STATUSMANAGER.getCurrentJob(this.getContext());
                jf.revertPriceTo(preFeeFare);
                STATUSMANAGER.setCurrentJob(this.getContext(), jf);
                updatePriceSlide(true);
                break;
            case CreditCardHandler.RESULT_PASS:
                logData = "Driver received card payment of £" + String.valueOf(_amount);
                break;
        }

        BROADCASTERS.HistoryStringMessage(this.getContext(), logData);

    }
*/

    private void startCreditCardPayment()
    {
        /*
        if (Build.VERSION.SDK_INT >= 16)
        {
            getHostActivity().setCreditCardHandler(new SumUpHandler(this));
            getHostActivity().getCreditCardHandler().startPayment(this.getActivity());
        }
        else
        {
            cdToast.showLong(getContext(), R.string.credit_card_payments_unavialble_on_this_device);
        }*/


        if (Build.VERSION.SDK_INT >= 16)
        {
            PaymentActivity.Launch(getContext());
        }
        else
        {
            cdToast.showLong(getContext(), R.string.credit_card_payments_unavialble_on_this_device);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CreditCardHandler.REQUEST_SUMUP_LOGIN)
        {
            if (getHostActivity().getCreditCardHandler().isReady())
            {

                getHostActivity().getCreditCardHandler().startPayment(getHostActivity());
                cdToast.setTempBottomGravity();
                if (isDebug()){cdToast.showLong(getContext(), "Starting payment...");}
            }
            else
            {
                cdToast.setTempBottomGravity();
                cdToast.showLong(getContext(), "Could not start payment", "Login Error");
            }
        }
        else if (requestCode == CreditCardHandler.REQUEST_SUMUP_CARD_PAYMENT)
        {
            getHostActivity().getCreditCardHandler().processActivityResult(data, resultCode, "FJSN");
        }
    }
        /* Check which request we're responding to
        if (requestCode == CreditCardHandler.REQUEST_SUMUP_CARD_PAYMENT)
        {
            new SumUpHandler(this).processActivityResult(data, resultCode);
        }
        else if(requestCode == CreditCardHandler.REQUEST_PAYLEVEN_CARD_PAYMENT)
        {
            new PaylevenHandler(this).processActivityResult(data, resultCode);
        }

    }*/

    private boolean shouldShowMeter;
    private boolean meterIsShowing;

    private void psuedoShowMeter()
    {
        shouldShowMeter = true;
    }

    private void reallyShowMeter()
    {
        meterIsShowing = true;
        this.oFragMeter.show(this.getContext());
    }

    /*
    private void showMeter(Context _c)
    {
        shouldShowMeter = true;
    }*/

    private void updatePriceSlide(Boolean _fromCreditCardPayment)
    {
        cabdespatchJob j = STATUSMANAGER.getCurrentJob(this.getContext());

        int indexOfPriceSlide = -1;
        int indexOfDuplicate = -1;
        for(SliderView v:this.sliderViews)
        {
            if(v.getViewType().equals(VIEW_TYPE.PRICE(JobScreenFragmentNGX.this.getContext())));
            {
                TextView lblTitle = (TextView) v.getView().findViewById(R.id.jobDetailHeading);
                lblTitle.setText(R.string.price_slide_title_updated);

                TextView lblDetail = (TextView) v.getView().findViewById(R.id.jobDetailText);
                lblDetail.setText(getPriceString(j));

                if(indexOfPriceSlide>=0)
                {
                    indexOfDuplicate = sliderViews.indexOf(v);
                }
                else
                {
                    indexOfPriceSlide = sliderViews.indexOf(v);
                }

            }
        }


        if(!(_fromCreditCardPayment))
        {
            if(indexOfPriceSlide>=0)
            {
                if(indexOfDuplicate>=1)
                {
                    this.flip.removeViewAt(indexOfDuplicate);
                }
                this.flip.setDisplayedChild(indexOfPriceSlide);
                stopFlipping();
            }
        }

    }

    private void configureJobButton  (cabdespatchJob.JOB_STATUS _s)
    {
        int bground = R.color.Black;
        int icon = R.drawable.alert;
        int text = R.string.job_button_stp;
        switch (_s)
        {
            case ACCEPTING:
            case ON_ROUTE:
                bground = R.drawable.btn_job_stp;
                icon = R.drawable.ico_btn_stp;
                text = R.string.job_button_stp;
                break;
            case STP:
                bground = R.drawable.btn_job_pob;
                icon = R.drawable.ico_btn_pob;
                text = R.string.job_button_pob;
                break;
            case POB:
                bground = R.drawable.btn_job_stc;
                icon = R.drawable.ico_btn_stc;
                text = R.string.job_button_stc;
                break;
            case STC:
                bground = R.drawable.btn_job_clear;
                icon = R.drawable.ico_btn_clear;
                text = R.string.job_button_clear;
                break;
        }

        btnJobStatus.setBackgroundResource(bground);
        //btnJobStatus.setPressed(true);
        //btnJobStatus.setAddStatesFromChildren(true);
        //btnJobStatus.setClickable(true);
        imgJobStatus.setImageResource(icon);
        lblJobStatus.setText(text);
    }

    private void updateJobButtonAndSliders(cabdespatchJob _j)
    {
        try
        {
            this.btnMeter.setVisibility(View.GONE);

            for(SliderView v:this.sliderViews)
            {
                v.hideHanvigateButton();
            }

            this.btnJobStatus.setOnLongClickListener(null);

            configureJobButton(_j.getJobStatus());

            switch(_j.getJobStatus())
            {
                case ACCEPTING:
                case ON_ROUTE:
                    this.oFragMeter.hide();

                    for(SliderView v:this.sliderViews)
                    {
                        if(v.getViewType()== VIEW_TYPE.PICKUP(this.getContext()))
                        {
                            v.showNavigateButton();
                        }
                    }
                   this.btnJobStatus.setOnLongClickListener(new View.OnLongClickListener()
                    {

                        @Override
                        public boolean onLongClick(final View v)
                        {
                            v.setVisibility(View.INVISIBLE);

                            JobScreenFragmentNGX.this.dlg_POBSTC = new Dialog_POBSTP(JobScreenFragmentNGX.this.getContext(), btnPanic);
                            JobScreenFragmentNGX.this.dlg_POBSTC.setOnResultListener(new Dialog_POBSTP.OnResultListener()
                            {

                                @Override
                                public void OnResult(Dialog_POBSTP.RESULT _result)
                                {
                                    switch (_result)
                                    {
                                        case TEXTBACK:
                                            //textback would be the default option....
                                            Globals.CrossFunctions.jobButtonPressed(v);
                                            break;
                                        case POB:
                                            Intent Message = new Intent(BROADCASTERS.USER_REQUEST);
                                            Message.putExtra(DataService._MESSAGETYPE, DataService._MESSAGETYPES.USER_REQUEST);
                                            Message.putExtra(DataService._MESSAGEDATA, USERREQUESTS.SET_POB);

                                            JobScreenFragmentNGX.this.getContext().sendBroadcast(Message);
                                            break;
                                        case CANCEL:
                                            v.setVisibility(View.VISIBLE);
                                            break;
                                    }

                                    JobScreenFragmentNGX.this.currentDialog = null;

                                }
                            });

                            JobScreenFragmentNGX.this.currentDialog = JobScreenFragmentNGX.this.dlg_POBSTC;
                            JobScreenFragmentNGX.this.dlg_POBSTC.show();


                            return true;
                        }
                    });

                    break;
                case POB:
                    for(SliderView v:this.sliderViews)
                    {
                        if(v.getViewType()== VIEW_TYPE.DESTINATION(JobScreenFragmentNGX.this.getContext()))
                        {
                            v.showNavigateButton();
                        }
                        if(v.getViewType()== VIEW_TYPE.VIA)
                        {
                            //hacky...
                            //we know there's at least one VIA as there wouldn't be any VIA slides otherwise
                            //SS will either sent Lat/Lons for ALL VIAS, or none at all
                            //
                            //so we'll check if via at position 0 has a lat/lon and if so we'll show
                            //navigation on all via slides
                            if(!(_j.getVias().get(0).getLat()
                                    .equals(cabdespatchJob.JobDetailLocation.DETAIL_UNSET)))
                            {
                                v.showNavigateButton();
                            }
                        }

                        psuedoShowMeter();

                    }

                    if(!(SETTINGSMANAGER.SETTINGS.LONG_PRESS_PANIC.parseBoolean(JobScreenFragmentNGX.this.getContext())))
                    {
                        this.armPanic();
                    }
                    if(SETTINGSMANAGER.CreditCardPayment.isEnabled(JobScreenFragmentNGX.this.getContext()))
                    {

                        if(STATUSMANAGER.getCurrentJob(JobScreenFragmentNGX.this.getContext()).isCash())
                        {
                            ViewFader.fadeIn(this.btnPay);
                        }
                    }

                    break;
                case STC:
                    if(!(SETTINGSMANAGER.SETTINGS.LONG_PRESS_PANIC.parseBoolean(JobScreenFragmentNGX.this.getContext())))
                    {
                        this.armPanic();
                    }

                    if (Build.VERSION.SDK_INT >= 16)
                    {
                        if(SETTINGSMANAGER.CreditCardPayment.isEnabled(JobScreenFragmentNGX.this.getContext()))
                        {

                            if(STATUSMANAGER.getCurrentJob(JobScreenFragmentNGX.this.getContext()).isCash())
                            {
                                ViewFader.fadeIn(this.btnPay);
                            }
                        }
                    }
                    else
                    {
                        //new SumUP SDK requires SDK 16 or higher...
                        this.btnPay.setVisibility(View.GONE);
                    }

                    this.clearPending = true;



                    this.btnJobStatus.setOnLongClickListener(new View.OnLongClickListener()
                    {
                        @Override
                        public boolean onLongClick(final View v)
                        {
                            v.setVisibility(View.INVISIBLE);
                            JobScreenFragmentNGX.this.dlg_ClearBreak = new Dialog_CLEARBREAK(JobScreenFragmentNGX.this.getContext());
                            JobScreenFragmentNGX.this.dlg_ClearBreak.setOnResultListener(new Dialog_CLEARBREAK.OnResultListener()
                            {

                                @Override
                                public void OnResult(Dialog_CLEARBREAK.RESULT _result)
                                {
                                    switch (_result)
                                    {
                                        case CLEAR:
                                            Globals.CrossFunctions.jobButtonPressed(v);
                                            break;
                                        case BREAK:
                                            STATUSMANAGER.STATUSES.REQUEST_BREAK_ON_CLEAR.putValue(v.getContext(), true);
                                            Globals.CrossFunctions.jobButtonPressed(v);
                                            break;
                                        case CANCEL:
                                            v.setVisibility(View.VISIBLE);
                                            break;
                                    }

                                    JobScreenFragmentNGX.this.currentDialog = null;
                                }
                            });

                            JobScreenFragmentNGX.this.currentDialog = JobScreenFragmentNGX.this.dlg_ClearBreak;
                            JobScreenFragmentNGX.this.dlg_ClearBreak.show();
                            return true;
                        }
                    });

                    break;
                case STP:
                    break;
                case UNDER_OFFER:
                case ERROR:
                default:
                    //CLAY should we fix this??
                    //maybe not... it will probably be caught by status checker anyway
            }

            if(!(_j.getJobStatus().equals(cabdespatchJob.JOB_STATUS.STC)))
            {
                this.btnPay.setVisibility(View.GONE);
            }

            this.btnJobStatus.setEnabled(true);
            if(_j.getJobStatus().equals(cabdespatchJob.JOB_STATUS.STP))
            {
                Boolean useAntiCheat = Boolean.valueOf(SETTINGSMANAGER.get(this.getContext(), SETTINGSMANAGER.SETTINGS.USE_ANTICHEAT));
                this.btnJobStatus.setEnabled(_j.canPOB(getContext(), useAntiCheat));

            /*
            if(useAntiCheat)
            {
                if(!(_j.getFromPlot().equals(STATUSMANAGER.getCurrentLocation(this.getContext()).getPlot().getShortName())))
                {
                    //we're using anticheat and we're NOT in the pickup area.
                    if(!(_j.hasAntiCheat()))
                    {
                        //we don't have an anticheat - so disable the button

                    }
                }
            }*/

            }

            this.btnJobStatus.setVisibility(View.VISIBLE);
        }
        catch (NullPointerException ex)
        {
            //this is a terrible generic catch but hopefully it won't cause any
            //unpredictable behavior issues...
        }


    }

    @Override
    public void onBroadcastReceived(Context _context, Intent _intent)
    {
        cabdespatchJob j = STATUSMANAGER.getCurrentJob(_context);
        if(_intent.getAction().equals(BROADCASTERS.PLOT_UPDATED))
        {
            plot currentPlot = STATUSMANAGER.getCurrentLocation(_context).getPlot();
            if(JobScreenFragmentNGX.this.oNudger.shouldNudge(j, currentPlot.getShortName()))
            {
                SOUNDMANAGER.askIfPob(_context);
            }

            if(STATUSMANAGER.getBoolean(_context, STATUSMANAGER.STATUSES.ON_RANK))
            {
                if(currentPlot.isRank())
                {
                    //do nothing...
                }
                else
                {
                    //try catch... so lazy
                    try
                    {
                        dlgRankLock.cancelCountdown();
                        dlgRankLock.dismiss();
                        BROADCASTERS.offRank(_context);
                        STATUSMANAGER.putBoolean(_context, STATUSMANAGER.STATUSES.ON_RANK, false);
                        JobScreenFragmentNGX.this.flip.setVisibility(View.VISIBLE);

                    }
                    catch(Exception ex)
                    {
                        //do nothing
                    }
                }
            }
        }
        else if(_intent.getAction().equals(BROADCASTERS.PRICE_UPDATED))
        {
            updatePriceSlide(false);
        }
        else if(_intent.getAction().equals(BROADCASTERS.JOB_AMEND))
        {
            setupViewFlipper(STATUSMANAGER.getCurrentJob(this.getContext()));
        }
        else if(_intent.getAction().equals(BROADCASTERS.LOCATION_UPDATED))
        {
            updateJobButtonAndSliders(STATUSMANAGER.getCurrentJob(_context));
        }
        else if(_intent.getAction().equals(BROADCASTERS.JOB_STATUS_UPDATE))
        {
            updateJobButtonAndSliders(STATUSMANAGER.getCurrentJob(_context));
            if(j.getJobStatus().equals(cabdespatchJob.JOB_STATUS.ON_ROUTE))
            {
                Boolean doIt = Boolean.valueOf(SETTINGSMANAGER.SETTINGS.PROMPT_HAVE_YOU_MOVED.getValue(_context));
                if(doIt)
                {
                    this.fHaveYouMoved = new HaveYouMoved(false);
                }
            }
        }
    }


    private View getJobDetailView(String _view_type, String _detail)
    {
        return getJobDetailView(_view_type, _detail, 0, false,
                cabdespatchJob.JobDetailLocation.DETAIL_UNSET,
                cabdespatchJob.JobDetailLocation.DETAIL_UNSET);
    }

    private View getJobDetailView(String _view_type, String _detail, String _titleOverride)
    {
        return getJobDetailView(_view_type, _detail, 0, false,
                cabdespatchJob.JobDetailLocation.DETAIL_UNSET,
                cabdespatchJob.JobDetailLocation.DETAIL_UNSET, _titleOverride);
    }

//	private View getJobDetailView(String _view_type, String _detail, int _viaCount, Boolean _showNavigation, String _toLat, String _toLon)
//	{
//         return getJobDetailView(_view_type, _detail, _viaCount, _showNavigation, _toLat, _toLon);
//	}

    private View getJobDetailView(final String _viewType, final String _detail, int _viaCount, boolean _showNavigate)
    {
        return getJobDetailView(_viewType, _detail, _viaCount, _showNavigate,
                cabdespatchJob.JobDetailLocation.DETAIL_UNSET,
                cabdespatchJob.JobDetailLocation.DETAIL_UNSET);
    }

    private void shrinkJobDetailButton(ImageButton v)
    {
        ViewGroup.LayoutParams l = v.getLayoutParams();
        l.width = (int) getResources().getDimension(R.dimen.job_detail_button_width_small);
        l.height = (int) getResources().getDimension(R.dimen.job_detail_button_height_small);
        v.setLayoutParams(l);
    }

    private View getJobDetailView(final String _viewType, final String _detail, int _viaCount, boolean _showNavigate, String _toLat, String _toLon)
    {
        return getJobDetailView(_viewType, _detail, _viaCount, _showNavigate, _toLat, _toLon, null);
    }

    @SuppressWarnings("deprecation")
    private View getJobDetailView(final String _viewType, final String _detail, int _viaCount, boolean _showNavigate, String _toLat, String _toLon, String _titleOverride)
    {

        final Boolean smallui = SETTINGSMANAGER.SETTINGS.SMALL_UI.parseBoolean(this.getContext());

        // toLat and toLon are ONLY used
        // for vias. Otherwise, navigation
        // is handled by the old system
        //
        // (ie. deducing what navigation
        // params to use based on job status)

        this.oItteratingSlide++;

        String requestedStartSlide = SETTINGSMANAGER.SETTINGS.JOB_SCREEN_FIRST_SLIDE.getValue(this.getContext());
        if (requestedStartSlide.equals(_viewType))
        {
            this.oStartSlide = this.oItteratingSlide;
        }

        final ViewGroup detail = (ViewGroup) this.getActivity().getLayoutInflater().inflate(R.layout.job_detail, null);
        final String detailText = _detail;

        final TextView lblDetail = (TextView) detail.findViewById(R.id.jobDetailText);
        if (smallui) { lblDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.job_detail_detail_small)); }
        lblDetail.setText(detailText);

        final ImageButton btnPopOut = (ImageButton) detail.findViewById(R.id.jobDetail_btnPopOut);
        if (smallui)  { shrinkJobDetailButton(btnPopOut); }

        HandyTools.runAfterLayout(lblDetail, new Runnable()
        {
            @Override
            public void run()
            {
                float mHeight = lblDetail.getMeasuredHeight();
                float lHeight = lblDetail.getLineHeight();

                /*
                String out = String.valueOf(mHeight) + " * " + String.valueOf(lHeight);
                if(_viewType.equals(VIEW_TYPE.PICKUP(this)))
                {
                    Toast.makeText(this, out, Toast.LENGTH_LONG).show();
                }
                */

                int maxLines = (int) Math.floor(lblDetail.getMeasuredHeight()
                        / lblDetail.getLineHeight());

                Boolean isEllipsised = false;

                if (lblDetail.getLineCount() > maxLines)
                {
                    isEllipsised = true;
                } else
                {
                    if (lblDetail.getLineCount() == maxLines)
                    {
                        //now we need to establish if the last line will fit on the screen
                        Layout l = lblDetail.getLayout();
                        if(l==null)
                        {
                            //do nothing
                        }
                        else if (l.getEllipsisCount(maxLines - 1) > 0)
                        {
                            isEllipsised = true;
                        }
                    }
                }

                lblDetail.setMaxLines(maxLines);
                if (isEllipsised)
                {
                    btnPopOut.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            TextView t = new TextView(JobScreenFragmentNGX.this.getActivity());
                            t.setText(detailText);
                            t.setTextSize(JobScreenFragmentNGX.this.getResources().getDimensionPixelSize(R.dimen.job_detail_popout));

                            Dialog_PopOut d = new Dialog_PopOut(JobScreenFragmentNGX.this.getActivity(), t);
                            d.show();
                        }
                    });
                    btnPopOut.setVisibility(View.VISIBLE);
                }
            }
        });

        //t.setTextSize(TypedValue.COMPLEX_UNIT_SP, SETTINGSMANAGER.TextTools.getTextSizeDIP(this, SETTINGSMANAGER.TextTools.SIZE.JOB_SCREEN_DETAIL));

        TextView title = (TextView) detail.findViewById(R.id.jobDetailHeading);
        if (smallui)
        {
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.job_detail_heading_small));
        }


        ImageButton icon = (ImageButton) detail.findViewById(R.id.jobDetailIcon);
        icon.setAlpha(0.4f);

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            icon.setAlpha(0.4f);
        } else
        {
            icon.setAlpha(100); //old apis
        }*/

        ImageButton btnSpeak = (ImageButton) detail.findViewById(R.id.jobDetail_btnSpeak);
        ImageButton btnNav = (ImageButton) detail.findViewById(R.id.jobDetail_btnNavigate);

        if(smallui)
        {
            shrinkJobDetailButton(btnSpeak);
            shrinkJobDetailButton(btnNav);
        }


        if (_viewType.equals(VIEW_TYPE.FLAGDOWN))
        {
            detail.setBackgroundColor(this.getResources().getColor(R.color.TransparentOrange));

            title.setVisibility(View.GONE);
            lblDetail.setText("FLAGDOWN");
            lblDetail.setGravity(Gravity.CENTER);
            icon.setImageResource(R.drawable.n_icoflag);

            float vPad = this.getResources().getDimension(R.dimen.activity_vertical_margin);
            float hPad = this.getResources().getDimension(R.dimen.activity_horizontal_margin);

            detail.setPadding((int) vPad, (int) hPad, (int) vPad, (int) hPad);
        }
        else if (_viewType.equals(VIEW_TYPE.TIME(JobScreenFragmentNGX.this.getContext())))
        {
            title.setText("Time");
        }
        else if (_viewType.equals(VIEW_TYPE.DESTINATION(JobScreenFragmentNGX.this.getContext())))
        {
            btnSpeak.setTag(detailText);
            btnSpeak.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    SOUNDMANAGER.speakTag(v);
                }
            });
            btnSpeak.setVisibility(View.VISIBLE);
            title.setText("Destination");
        }
        else if (_viewType.equals(VIEW_TYPE.PICKUP(JobScreenFragmentNGX.this.getContext())))
        {
            btnSpeak.setTag(detailText);
            btnSpeak.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    SOUNDMANAGER.speakTag(v);
                }
            });
            btnSpeak.setVisibility(View.VISIBLE);
            title.setText("Pickup");
        }
        else if (_viewType.equals(VIEW_TYPE.PRICE(JobScreenFragmentNGX.this.getContext())))
        {
            title.setText("Price");
            icon.setImageResource(R.drawable.coin);
        }
        else if (_viewType.equals(VIEW_TYPE.VIA))
        {
            title.setText("Via " + String.valueOf(_viaCount));
            btnSpeak.setTag(detailText);
            btnSpeak.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    SOUNDMANAGER.speakTag(v);
                }
            });
            btnSpeak.setVisibility(View.VISIBLE);
        }
        else if (_viewType.equals(VIEW_TYPE.COMMENTS(JobScreenFragmentNGX.this.getContext())))
        {
            title.setText("Notes");
        } else if (_viewType.equals(VIEW_TYPE.NAME(JobScreenFragmentNGX.this.getContext())))
        {
            btnSpeak.setTag(detailText);
            btnSpeak.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    SOUNDMANAGER.speakTag(v);
                }
            });
            btnSpeak.setVisibility(View.VISIBLE);
            title.setText("Name");
        }
        else if (_viewType.equals(VIEW_TYPE.VEHICLE_TYPE(JobScreenFragmentNGX.this.getContext())))
        {
            title.setText(R.string.setting_tag_vehicle_type);
        }
        else
        {
            ErrorActivity.genericReportableError("Unhandled job slide type");
        }

        if(_titleOverride == null)
        {
            //do nothing
        }
        /* seems to look fine as-is
        else if (_titleOverride.equals(""))
        {
            title.setVisibility(View.GONE);
        }*/
        else
        {
            title.setText(_titleOverride);
        }

        this.sliderViews.add(new SliderView(detail, _viewType, _showNavigate, _toLat, _toLon));


        return detail;
    }


    private View.OnTouchListener FlipperTouch()
    {
        return new View.OnTouchListener()
        {

            float lastX = -1;


            @Override
            public boolean onTouch(View v, MotionEvent touchevent)
            {
                ViewFlipper vf = (ViewFlipper)  v;

                switch (touchevent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        lastX = touchevent.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float currentX = touchevent.getX();

                        if(vf.isFlipping())
                        {
                            JobScreenFragmentNGX.this.stopFlipping();
                        }

                        if (lastX < currentX)
                        {
                            vf.setInAnimation(JobScreenFragmentNGX.this.getContext(), R.anim.in_from_left);
                            vf.setOutAnimation(JobScreenFragmentNGX.this.getContext(), R.anim.out_to_right);
                            vf.showPrevious();
                            this.lastX = -1;
                            return true;
                        }

                        if (lastX > currentX)
                        {
                            vf.setInAnimation(JobScreenFragmentNGX.this.getContext(), R.anim.in_from_right);
                            vf.setOutAnimation(JobScreenFragmentNGX.this.getContext(), R.anim.out_to_left);
                            vf.showNext();
                            this.lastX = -1;
                            return true;
                        }
                        break;
                }
                return true;
            }
        };
    }

    private void startFlipping(boolean _showNext)
    {
        this.btnPlay.setVisibility(View.INVISIBLE);
        //this.flip.setInAnimation(null);
        //this.flip.setOutAnimation(null);
        //this.flip.startFlipping();
        this.flip.setInAnimation(JobScreenFragmentNGX.this.getContext(), R.anim.in_from_right);
        this.flip.setOutAnimation(JobScreenFragmentNGX.this.getContext(), R.anim.out_to_left);
        if(_showNext)
        {
            this.flip.showNext();
        }
        this.flip.startFlipping();
    }

    private void stopFlipping()
    {
        this.flip.stopFlipping();
        this.btnPlay.setVisibility(View.VISIBLE);
    }



    @Override
    public void onTick()
    {
        if(shouldShowMeter)
        {
            if(!(meterIsShowing))
            {
                reallyShowMeter();
            }
        }

        this.oFragMeter.onTick();
        this.fHaveYouMoved.tick();

        //updateJobButtonAndSliders(STATUSMANAGER.getCurrentJob(this.getContext()));
    }

    private Dialog currentDialog;

    @Override
    protected void onStopping()
    {
        if(!(currentDialog==null))
        {
            try
            {
                currentDialog.dismiss();
            }
            catch (Exception ex)
            {
                //do nothing
            }
            finally
            {
                currentDialog = null;
            }
        }
    }

    @Override
    public void onResuming()
    {

    }


    private class SliderView
    {
        private String oViewType;
        private ImageButton oBtnNavigate;
        private View oView;

        public SliderView(View _v, String _viewType, boolean _showButton,
                          final String _toLat, final String _toLon)
        {
            this.oViewType = _viewType;
            this.oView = _v;

            this.oBtnNavigate = (ImageButton) _v.findViewById(R.id.jobDetail_btnNavigate);
            this.oBtnNavigate.setAlpha(0.6f);

            this.oBtnNavigate.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(_toLat.equals(cabdespatchJob.JobDetailLocation.DETAIL_UNSET))
                    {
                        //old system - navigate based on job status
                        Globals.CrossFunctions.startNavigate(v.getContext());
                    }
                    else
                    {
                        //new system - navigate based on supplied info
                        Globals.CrossFunctions.startNavigate(v.getContext(), _toLat, _toLon);
                    }

                }
            });

            if(_showButton)
            {
                if(SETTINGSMANAGER.navigationAvailable(JobScreenFragmentNGX.this.getContext()))
                {
                    this.showNavigateButton();
                }
                else
                {
                    this.hideHanvigateButton();
                }
            }
            else
            {
                this.hideHanvigateButton();
            }
        }


        public String getViewType()
        {
            return this.oViewType;
        }

        public void showNavigateButton()
        {
            if(SETTINGSMANAGER.navigationAvailable(JobScreenFragmentNGX.this.getContext()))
            {
                this.oBtnNavigate.setVisibility(View.VISIBLE);
            }
        }

        public void hideHanvigateButton()
        {
            this.oBtnNavigate.setVisibility(View.GONE);
        }

        public View getView()
        {
            return this.oView;
        }

    }

    public void armPanic()
    {
        this.panicArmed = true;
        Globals.CrossFunctions.armPanic(this.btnPanic_ActualView, this.getActivity(), new Globals.UnarmPanicListener()
        {

            @Override
            public void unarmPanic()
            {
                JobScreenFragmentNGX.this.panicArmed = false;
            }
        });
    }
}

