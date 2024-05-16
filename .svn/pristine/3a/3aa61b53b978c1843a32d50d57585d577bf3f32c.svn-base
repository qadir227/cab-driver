package com.cabdespatch.driverapp.beta;

import android.util.Base64;

import androidx.recyclerview.widget.RecyclerView;

import org.joda.time.DateTime;
import org.joda.time.convert.Converter;
import org.joda.time.convert.ConverterManager;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionHandler {

    private static String passKey = "68uAxTwOYV04G5GhwxoYjwomeMfCwmck";
    private static String iv = "cp2ejV9X+vCs5Q==";

    private static String GeneratePassword(boolean currentPass, String driverNumber, String companyID)
    {
        String output = new String();
        String dateTime = new String();

        if (currentPass == true)
        {
            Date currentDate = Calendar.getInstance().getTime();
            dateTime = currentDate.toString();
        }
        else
        {
            Date currentDate = Calendar.getInstance().getTime();
            dateTime = currentDate.toString();
        }
        output = driverNumber + dateTime + companyID;
        output = EncryptPDA(output);
        output = output.replace("==", "");
        output = output.substring(0, 32);

        return output;
    }


    private static SecretKey generateSecureKey()
    {
        SecretKeySpec secureKey = new SecretKeySpec(passKey.getBytes(), "AES");
        return secureKey;
    }

    private static IvParameterSpec generateIV()
    {
        IvParameterSpec IV = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        return IV;
    }


    public static String EncryptString(String text)
    {
        try
        {
            if (text.contains("\r\n"))
            {
                text.replace("\r\n", "");
            }
            Cipher sym = Cipher.getInstance("AES/CBC/PKCS7Padding");
            sym.init(Cipher.ENCRYPT_MODE, generateSecureKey(), generateIV());
            byte[] encryptedData = sym.doFinal(text.getBytes());
            return Base64.encodeToString(encryptedData, Base64.DEFAULT);
        }
        catch (Exception e)
        {
            return text;
        }
    }

    public static String decryptString(String cipherText)
    {
        try
        {
            byte[] cipherByte = cipherText.getBytes();
            cipherByte = Base64.decode(cipherByte, 0);
            Cipher sym = Cipher.getInstance("AES/CBC/PKCS7Padding");
            sym.init(Cipher.DECRYPT_MODE, generateSecureKey(), generateIV());
            String decryptedData = new String(sym.doFinal(cipherByte));
            return decryptedData;
        }
        catch (Exception e)
        {
            return cipherText;
        }
    }

    public static String DecryptPDA(String cipherText)
    {
        try
        {
            byte[] cipherByte = cipherText.getBytes();
            cipherByte = Base64.decode(cipherByte, 0);

            Cipher sym = Cipher.getInstance("AES/CBC/PKCS7Padding");
            sym.init(Cipher.DECRYPT_MODE, generateSecureKey(), generateIV());

            byte[] decodedBytes = sym.doFinal(cipherByte);
            //decodedBytes = Base64.decode(decodedBytes, Base64.DEFAULT);
            String text = new String(decodedBytes, "UTF-8");
            return text;
        }
        catch (Exception e)
        {
            return cipherText;
        }
    }

    public static String EncryptPDA(String text)
    {
        try
        {
            if (text.contains("\r\n"))
            {
                text.replace("\r\n", "");
            }
            Cipher sym = Cipher.getInstance("AES/CBC/PKCS7Padding");
            sym.init(Cipher.ENCRYPT_MODE, generateSecureKey(), generateIV());
            byte[] encryptedData = sym.doFinal(text.getBytes());
            encryptedData = Base64.encode(encryptedData, Base64.DEFAULT);
            String encryptedText = new String(encryptedData, "UTF-8");
            return encryptedText;
        }
        catch (Exception e)
        {
            return text;
        }
    }
}
