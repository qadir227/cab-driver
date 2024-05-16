package com.cabdespatch.driverapp.beta;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class cabdespatchJob
{
	public static String EMPTY = "_CABSESPATCHJOBEMPTYSTRING";



    public enum JOB_STATUS
    {
		@SerializedName("not_on_job")
    	NOT_ON_JOB(-1),
		@SerializedName("accepting")
		ACCEPTING(21),
		@SerializedName("rejecting")
		REJECTING(22),
		@SerializedName("under_offer")
		UNDER_OFFER(50),
		@SerializedName("on_route")
		ON_ROUTE(100),
		@SerializedName("stp")
		STP(200),
		@SerializedName("pob")
		POB(300),
		@SerializedName("stc")
		STC(400),
		@SerializedName("error")
		ERROR(9999);
    	int oValue;
    	
    	JOB_STATUS(int _value)
    	{
    		this.oValue = _value;
    	}
    	
    	public int getValue() { return this.oValue; }
    	
    	public static JOB_STATUS fromInt(int _int)
    	{
    		JOB_STATUS status = JOB_STATUS.ERROR;
    		
    		switch(_int)
    		{
    			case -1:
    				status = NOT_ON_JOB;
    				break;
    			case 50:
    				status = JOB_STATUS.UNDER_OFFER;
    				break;
    			case 100:
    				status = JOB_STATUS.ON_ROUTE;
    				break;
    			case 200:
    				status = JOB_STATUS.STP;
    				break;
    			case 300:
    				status = JOB_STATUS.POB;
    				break;
    			case 400:
    				status = JOB_STATUS.STC;
    				break;
    		}
    		
    		return status;
    	}
        
    }
    
    public static final char SPLITTER = (char) 167;
    public static final char SPLITTER_INTERNAL = (char) 181;

    public static class JobDetailLocation
    {
        public static final String DETAIL_UNSET = "_UNSET!";

        private String oAddressLine;
        private String oLat;
        private String oLon;

        public JobDetailLocation(String _addressLine, String _lat, String _lon)
        {
            this.oAddressLine = _addressLine;
            this.oLat = _lat;
            this.oLon = _lon;
        }

        public String getAddressLine() {return this.oAddressLine; }
        public String getLat() { return this.oLat; }
        public String getLon() { return this.oLon; }

        @Override
        public String toString()
        {
            String s = this.oAddressLine + "\n";
            s += (this.oLat + "\n");
            s += this.oLon;

            return s;
        }
    }

    private Long                     dateTimeRecd;
    private ArrayList<JobDetailLocation> vias;
    private String                       fromPlot;
    private String                       fromLat;
    private String                       fromLon;
    private String                       toPlot;
    private String                       toLat;
    private String                       toLon;
    private String                       fromAddress;
    private String                       toAddress;
    private String                       price;
    private String                       priceDisplay;
    private String                       account;
    private String                       comments;
    private String                       name;
    private String                       time;
    private String                       id;
    private String                       distance;
    private String                       mode;
    private String                       vehicleType;
    private Boolean                      pickupPlotHit;
    private Boolean                      antiCheat;
    private Boolean                      isDiverted;
    private plot                         divertedPlot;
    private int                          waitingSeconds;
    private boolean                      isFlagJob;
    //public String viaDetails;
    private boolean                      showZoneOnJobOffer;
    private Double                       amountPrepaid;
    private String telephoneNumber;
    private Double meterPrice;
    private boolean autoAccept;
    private Double bookingFee;
    private Boolean price_has_been_updated;


    private JOB_STATUS status;
    private String     flightNo;

    private String driverNotes;


    public cabdespatchJob(Double _price, Boolean _isFlagDown)
    {
        fromPlot = "";
        fromLat = "";
        fromLon = "";
        toPlot = "";
        toLat = "";
        toLon = "";
        fromAddress = "";
        toAddress = "";
        setPrice(_price);
        account = "";
        comments = "";
        name = "";
        time = "";
        id = "";
        distance = "";
        mode = "";
        vehicleType = "";
        telephoneNumber = "";
        waitingSeconds = 0;
        isDiverted = false;
        divertedPlot = plot.errorPlot();
        isFlagJob = _isFlagDown;
        vias = new ArrayList<JobDetailLocation>();
        this.dateTimeRecd = DateTime.now().getMillis();
        this.amountPrepaid = 0d;
        this.flightNo = "";
        this.meterPrice = 0.0;
        this.driverNotes = Settable.NOT_SET;
        this.price_has_been_updated = false;

        pickupPlotHit = false;
        antiCheat = false;

        if (_isFlagDown)
        {
            status = JOB_STATUS.POB;
            pendingStatus = JOB_STATUS.POB;
        }
        else
        {
            status = JOB_STATUS.NOT_ON_JOB;
            pendingStatus = JOB_STATUS.ON_ROUTE;
        }

        autoAccept = false;
        bookingFee = 0.0;

        this.priceDisplay = EMPTY;
        this.notesRequired = false;

    }

    public void plotDivert(plot _plot)
    {
        isDiverted = true;
        divertedPlot = _plot;

    }

    public Boolean canPOB(Context _c, Boolean _useAntiCheat)
    {
        if(_useAntiCheat)
        {

            if ((this.hasAntiCheat() || (this.hasPickupPlotBeenHit())))
            {
                return true;
            }
            else
            {
                pdaLocation p = STATUSMANAGER.getCurrentLocation(_c);
                return (p.getPlot().getShortName().toUpperCase().equals(this.getFromPlot()));
            }
        }
        else
        {
            return true;
        }

    }

    public void clearPlotDivert()
    {
        //CLAY replotting in NAT??
        isDiverted = false;
        divertedPlot = plot.errorPlot();
    }

    public boolean isPlotDiverted()
    {
        return isDiverted;
    }

    public plot getDivertedPlot()
    {
        return divertedPlot;
    }

    public void addWaitingTime(int _seconds)
    {
        waitingSeconds += _seconds;
    }

    private Boolean hasAntiCheat()
    {
        return antiCheat;
    }

    public boolean overRrideNoNoShow()
    {
        return hasAntiCheat();
    }

    public void setAntiCheatOn()
    {
        antiCheat = true;
    }

    public Boolean hasPickupPlotBeenHit()
    {
        return pickupPlotHit;
    }

    public void setPickupPlotAsHit()
    {
        pickupPlotHit = true;
    }

    public void setFromPlot(String _plot)
    {
        fromPlot = _plot;
    }

    public void setFromLat(String _lat)
    {
        fromLat = _lat;
    }
    
    public void setFromLon(String _lon)
    {
    	fromLon = _lon;
    }

    public void setToPlot(String _plot)
    {
        toPlot = _plot;
    }
    
    public void setToLat(String _lat)
    {
    	toLat = _lat;
    }
    
    public void setToLon(String _lon)
    {
    	toLon = _lon;
    }

    public void setFromAddress(String _address)
    {
        fromAddress = _address;
    }

    public void setToAddress(String _address)
    {
        toAddress = _address;
    }

    public void setPriceDisplay(String _price)
    {
        this.priceDisplay = _price;
    }

    public String getPriceDisplay() { return  this.priceDisplay; }

    private void setPrice(String _price)
    {

    	if ((_price==null) || (_price.equals(""))) {_price="0";} 
    	this.price = HandyTools.Strings.formatPrice(_price);

    }

    public void updatePrice(String _price)
    {
        setPrice(_price); price_has_been_updated = true;
    }
    public void updatePrice(Double _price)
    {
        setPrice(_price); price_has_been_updated = true;
    }

    public void revertPriceTo(Double _price)
    {
        setPrice(_price); price_has_been_updated = false;
    }

    public Boolean priceHasBeenUpdated() { return  price_has_been_updated; }

    public void setMeterPrice(Double _price)
    {
        this.meterPrice = _price;
    }

    public void setBookingFee(Double _bookingFee) { bookingFee = _bookingFee; }

    private void setPrice(Double _price)
    {
        setPrice(String.valueOf(_price));
    }


    public void setTelephoneNumber(String _telephoneNumber) { telephoneNumber = _telephoneNumber; }

    public void setAccount(String _account)
    {
        account = _account;
    }

    public void setComments(String _comments)
    {
        comments = _comments;
    }

    public void setVehcileType(String _vehicleType)
    {
        vehicleType = _vehicleType;
    }

    public void setName(String _name)
    {
        name = _name;
    }

    public void setTime(String _time)
    {
        time = _time;
    }

    public void setID(String _id)
    {
        id = _id;
    }

    public void setDistance(String _distance)
    {
        distance = _distance;
    }
    
    public void setIsFlagDown(Boolean _isFlagDown)
    {
    	this.isFlagJob = _isFlagDown;
    }

    public void setMode(String _mode)
    {
        mode = _mode;
    }

    public void setJobStatus(JOB_STATUS _status)
    {
        status = _status;
    }

    public void setPendingStatus(JOB_STATUS _pendingStatus)
    {
        pendingStatus = _pendingStatus;
    }

    public void makeAutoAccept() { this.autoAccept = true; }

    public void setStatusFromPendng()
    {
        this.status = this.pendingStatus;
        //return this.status;
    }

    public JOB_STATUS getPendingStatus()
    {
        return this.pendingStatus;
    }


    public void addVia(JobDetailLocation _via)
    {
    	vias.add(_via);
    }

    public void clearVias()
    {
        vias.clear();
    }

    /* returns short plot name */
    public String getFromPlot()
    {
        return fromPlot;
    }
    
    public String getFromLat()
    {
    	return fromLat;
    }
    
    public String getFromLon()
    {
    	return fromLon;
    }

    public String getToPlot()
    {
        return toPlot;
    }
    
    public String getToLat()
    {
    	return toLat;
    }
    
    public String getToLon()
    {
    	return toLon;
    }

    public String getFromAddress()
    {
        return fromAddress;
    }

    public String getToAddress()
    {
        return toAddress;
    }

    public Double getBookingFee() { return bookingFee; }

    public String getPrice()
    {
        return price;
    }

    public Double getMeterPrice()
    {
        return meterPrice;
    }

    public String getTelephoneNumber() { return telephoneNumber; }

    public String getAccount()
    {
        return account;
    }

    public boolean isCash()
    {
        return
                this.getAccount().equals(cabdespatchJob.EMPTY)
                || this.getAccount().equals("0")
                || this.getAccount().equals("");
    }

    public boolean isAccount()
    {
        return (!(this.isCash()));
    }


    public boolean isAutoAccept() { return autoAccept; }

    public String getComments()
    {
        return comments;
    }

    public String getName()
    {
        return name;
    }

    public String getTime()
    {
        return time;
    }

    public String getID()
    {
        return id;
    }

    public String getDistance()
    {
        return distance;
    }

    public String getMode()
    {
        return mode;
    }

    public String getVehicleType()
    {
        return vehicleType;
    }

    public JOB_STATUS getJobStatus()
    {
        return status;
    }
    
    public int getWaitingTime()
    {
    	return waitingSeconds;
    }
    
    public Boolean isFlagDown()
    {
    	return isFlagJob;
    }
    
    public ArrayList<JobDetailLocation> getVias() { return this.vias; }

    public Boolean getShowZoneOnJobOffer()
    {
        return this.showZoneOnJobOffer;
    }

    public void setShowZoneOnJobOffer(Boolean _show)
    {
        this.showZoneOnJobOffer = _show;
    }

    public void setAmountPrePaid(Double _amount)
    {
        this.amountPrepaid = _amount;
    }

    public String getAmountPrepaid() { return HandyTools.Strings.formatPrice(String.valueOf(this.amountPrepaid)); }

    public String getPriceBalance()
    {
        Double jobPrice = Double.valueOf(this.price);
        return HandyTools.Strings.formatPrice(String.valueOf(jobPrice - this.amountPrepaid));
    }

    public static Double calculateSurcharge(Context _c, Double _price)
    {
        /*
        Double actualSurcharge = 0.0;
        try
        {
            Double surchargePercent = SETTINGSMANAGER.CreditCardPayment.getSurchargePercentage(_c);
            Double surchargeFlat = SETTINGSMANAGER.CreditCardPayment.getSurchargeFixed(_c);
            actualSurcharge = surchargeFlat;

            if(surchargePercent > 0)
            {
                actualSurcharge = (_price / 100) * surchargePercent;

                if(actualSurcharge < surchargeFlat)
                {
                    actualSurcharge = surchargeFlat;
                }
            }


        }
        catch (Exception _ex)
        {

        }

        return actualSurcharge;*/
        return 0.0;

    }

    @Override
    public String toString()
    {
    	String jString = this.getDateTimeReceived().toString().replace("T", " ");
    	jString = jString.substring(0, jString.length() - 5);
    	jString += "\n\nFrom :";
    	jString += this.fromAddress + "\n\nTo:";
    	jString += this.toAddress + "\n\nPrice:";
    	jString += this.price;
    	
    	return jString;
    }
    
    public DateTime getDateTimeReceived()
    {
    	return new DateTime(this.dateTimeRecd);
    }

    public void setFlightNo(String _flightNo)
    {
        this.flightNo = _flightNo;
    }
    public String getFlightNo() { return this.flightNo; }

    private Boolean notesRequired;
    public void setNotesRequired(Boolean _required)
    {
        this.notesRequired = _required;
    }
    public Boolean getNotesRequired()
    {
        //return null;
        return notesRequired;
    }

    public String getFlightQueryUrl()
    {
        return "https://www.google.co.uk/search?q=" + this.flightNo;
    }

    public JobDetailLocation getViaByAddressLine(String _addressLine)
    {
        JobDetailLocation match = null;
        for(JobDetailLocation v:vias)
        {
            if(v.getAddressLine().equals(_addressLine))
            {
                match = v;
                break;
            }
        }

        return  match;
    }

    private String packVias()
    {
        String v = "";
        for (JobDetailLocation l:vias)
        {
            v += l.getAddressLine() + "?";
            v += l.getLat() + "?";
            v += l.getLon() + "?";

            v += SPLITTER_INTERNAL;
        }

        if(v.length() > 0)
        {
            v = v.substring(0, v.length() - 1);
        }

        return v;
    }

    private static ArrayList<JobDetailLocation> unpackVias(String _viaData)
    {
        ArrayList<JobDetailLocation> locs = new ArrayList<JobDetailLocation>();

        if(_viaData.length() > 0)
        {
            String[] viastrings = _viaData.split(String.valueOf(SPLITTER_INTERNAL));
            for(String s:viastrings)
            {
                String[] details = s.split(Pattern.quote("?"));
                locs.add(new JobDetailLocation(details[0], details[1], details[2]));
            }
        }

        return locs;

    }

    public String getDriverNotes() { return this.driverNotes; }
    public void setDriverNotes(String _notes)
    {
        this.driverNotes = _notes.replace(String.valueOf(SPLITTER), "").replace(String.valueOf(SPLITTER_INTERNAL), "");
    }

    private JOB_STATUS pendingStatus;

    public String pack()
    {

        String json = new Gson().toJson(this);
        return json;
        /*
    	String p = terminate(String.valueOf(this.status.getValue()));
    	p += terminate(this.id);
    	p += terminate(this.fromAddress);
    	p += terminate(this.fromPlot);
    	p += terminate(this.fromLat);
    	p += terminate(this.fromLon);
    	p += terminate(this.toAddress);
    	p += terminate(this.toPlot);
    	p += terminate(this.toLat);
    	p += terminate(this.toLon);
    	p += terminate(packVias());
    	p += terminate(this.price);
    	p += terminate(this.name);
    	p += terminate(this.comments);
    	p += terminate(String.valueOf(this.isFlagDown()));
    	p += terminate(this.vehicleType);
    	p += terminate(this.distance);
    	p += terminate(String.valueOf(this.hasAntiCheat()));
    	p += terminate(String.valueOf(this.waitingSeconds));
    	p += terminate(this.account);
    	p += terminate(this.time);
    	p += terminate(this.dateTimeRecd.toString());
        p += terminate(String.valueOf(this.showZoneOnJobOffer));
        p += terminate(String.valueOf(this.amountPrepaid));
        p += terminate(this.flightNo);
        p += terminate(this.telephoneNumber);
        p += terminate(String.valueOf(this.meterPrice));
        p += terminate(this.driverNotes);
        p += terminate(String.valueOf(this.pendingStatus.getValue()));
        p += terminate(String.valueOf(this.autoAccept));
        p += terminate(String.valueOf(this.notesRequired));
        p += terminate(String.valueOf(this.bookingFee));
        p += terminate(String.valueOf(this.pickupPlotHit));
        p += terminate(String.valueOf(this.price_has_been_updated));
    	
    	return p;*/
    }
    
    private String terminate(String _string)
    {
    	if (_string.isEmpty())
    	{
    		_string = cabdespatchJob.EMPTY;
    	}
    	return _string + String.valueOf(cabdespatchJob.SPLITTER);
    }


    
    public static cabdespatchJob unpack(String _data)
    {
        if(_data==null)
        {
            return new cabdespatchJob(0d, false);
        }
        else if(_data.equals("null"))
        {
            return new cabdespatchJob(0d, false);
        }
        else if(StringUtils.countMatches(_data, String.valueOf(cabdespatchJob.SPLITTER)) >= 12)
        {
            return legacyUnpack(_data);
        }
        else
        {
            return new Gson().fromJson(_data, cabdespatchJob.class);
        }
    }

    private static cabdespatchJob legacyUnpack(String _data)
    {
        cabdespatchJob j = new cabdespatchJob(0d, false);
        String[] data = _data.split(String.valueOf(cabdespatchJob.SPLITTER));
        if(data.length >= 12)
        {
            j.status = JOB_STATUS.fromInt(Integer.valueOf(data[0]));
            j.id = notnull(data[1]);
            j.fromAddress = notnull(data[2]);
            j.fromPlot = notnull(data[3]);
            j.fromLat = notnull(data[4]);
            j.fromLon = notnull(data[5]);
            j.toAddress = notnull(data[6]);
            j.toPlot = notnull(data[7]);
            j.toLat = notnull(data[8]);
            j.toLon = notnull(data[9]);
            j.vias =  unpackVias(notnull(data[10]));
            j.price = notnull(data[11]);
            j.name = notnull(data[12]);
            j.comments = notnull(data[13]);
            j.isFlagJob = Boolean.valueOf((data[14]));
            j.vehicleType = notnull(data[15]);
            j.distance = notnull(data[16]);
            j.antiCheat = Boolean.valueOf(data[17]);
            j.waitingSeconds = Integer.valueOf(data[18]);
            j.account = data[19];
            j.time = data[20];
            try
            {
                //we used to use JodaTime.toString(), but this caused issues with GSON so we now just store the tick count
                j.dateTimeRecd = Long.valueOf(data[21]);
            }
            catch (Exception ex)
            {
                try
                {
                    //try and cast in the old way
                    j.dateTimeRecd = new DateTime(data[21]).getMillis();
                }
                catch (Exception ex2)
                {
                    //give up.... just set the time to right now
                    j.dateTimeRecd = DateTime.now().getMillis();
                }

            }

            j.showZoneOnJobOffer = Boolean.valueOf(data[22]);
            j.amountPrepaid = Double.valueOf(data[23]);
            j.flightNo = data[24];
            j.telephoneNumber = data[25];
            j.meterPrice = Double.valueOf(data[26]);
            j.driverNotes = data[27];
            j.pendingStatus = JOB_STATUS.fromInt(Integer.valueOf(data[28]));
            j.autoAccept = Boolean.valueOf(data[29]);
            j.notesRequired = Boolean.valueOf(data[30]);
            j.bookingFee = Double.valueOf(data[31]);
            j.pickupPlotHit = Boolean.valueOf(data[32]);
            j.price_has_been_updated = Boolean.valueOf(data[33]);
        }
        return j;
    }

    private static String notnull(String _string)
    {
    	if(_string==null)
    	{
    		return "";
    	}
    	else if(_string.equals(cabdespatchJob.EMPTY))
    	{
    		return "";
    	}
    	else
    	{
    		return _string;
    	}
    }
}
