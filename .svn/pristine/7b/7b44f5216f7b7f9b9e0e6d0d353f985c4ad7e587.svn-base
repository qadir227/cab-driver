package com.cabdespatch.driverapp.beta;



public class uiMessage
{
	public enum UIMESSAGE_TYPE
    {  
    	NULL, DO_NOTHING, LOGON, NAVIGATE, LOGOFF, PLOTUPDATE, NOTLOGGEDIN, RESTART_FOR_DATASERVICE,
        REBOOT_REQUIRED_FOR_LOGIN, TOAST, JOBWAITING, JOB_UPDATE, PRICE_UPDATE, SETONROUTE, SETSTP, SETPOB, SETSTC,
        WORK_WAITING_AT_DESTINATION, SETCLEAR, ANTICHEATRECD, CARSJOBSMESSAGE, DRIVERMESSAGE, SPOKENMESSAGE, FUTUREJOBSMESSAGE,
        IGNORE, NEW_JOB_TOTALS, GETNEWSETTINGS, BREAKSTART, BREAKEND, AKREJECTAFTERACCEPT, UNKNOWN, PONG, 
        RESET_PDA, UPDATE_AVAILABLE, UPDATE_REQUIRED, DATA_WAITING, SEND_SMS;

    }
	
	private UIMESSAGE_TYPE pType;
    private String pData;
    private String pSecondaryData;

    public uiMessage(UIMESSAGE_TYPE type, String data)
    {
        this(type, data, "");
    }

    public uiMessage(UIMESSAGE_TYPE type, String data, String secondaryData)
    {
        pType = type;
        pData = data;
        pSecondaryData = secondaryData;
    }

    public UIMESSAGE_TYPE getMessageType()
    {
        return pType;
    }

    public String getMessageData()
    {
        return pData;
    }

    public String getSecondaryData()
    {
        return pSecondaryData;
    }
}
