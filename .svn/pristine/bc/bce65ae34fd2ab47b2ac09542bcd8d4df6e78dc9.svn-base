package com.cabdespatch.driverapp.beta;

import android.content.Context;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CabDespatchNetworkFire extends CabDespatchNetwork
{

	final String SENDER_ID  = "803144030977";

	private String COMPANY_ID;
	private String DRIVER_CALL_SIGN;


	public CabDespatchNetworkFire(String _companyID, String _driverCallSign)
	{
		COMPANY_ID = _companyID; DRIVER_CALL_SIGN = _driverCallSign;
	}

	@Override
	public Boolean sendMessage(priorityString _s)
	{
		String id = String.valueOf(System.currentTimeMillis()) + DRIVER_CALL_SIGN;
		FirebaseMessaging fm = FirebaseMessaging.getInstance();
		fm.send(new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com")
				.setMessageId(id)
				.addData(String.valueOf(EncryptionHandler.EncryptString("company_id")),String.valueOf(EncryptionHandler.EncryptString(COMPANY_ID)))
				.addData(String.valueOf(EncryptionHandler.EncryptString("call_sign")),String.valueOf(EncryptionHandler.EncryptString(DRIVER_CALL_SIGN)))
				.addData(String.valueOf(EncryptionHandler.EncryptString("payload")),String.valueOf(EncryptionHandler.EncryptString(_s.getString())))
				//.setTtl(5000)
				.setMessageId(id)
				.build());
		return true;
	}


	@Override
	public void Stop()
	{

	}

	@Override
	public int getTimeOutSeconds()
	{
		return 120;
	}

	@Override
	public void requestDriverCallSignChange(Context _c)
	{

	}

	@Override
	public long getTimeOfLastAdditionOrAcknowledgement()
	{
		return System.currentTimeMillis();
	}
}
