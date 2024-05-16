/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.planetcoops.android.taximeter;
/**
 * Taximeter Interface Definition
 * @author Planet Coops
 * @version 1.1.2
 */
public interface ITaximeterService extends android.os.IInterface
{
  /** Default implementation for ITaximeterService. */
  public static class Default implements com.planetcoops.android.taximeter.ITaximeterService
  {
    /**
         * This method indicates whether the Taximeter application is running.
         * The application needs to have been started before most of the API methods can be used
         * (isAppRunning(), isAPIEnabled(), getAPIVersion(), setLicenseKey(String), setPOSPrintLicenseKey(String), and startup(String) are the exceptions).
         * @return true if the Taximeter app is running and false otherwise.
         */
    @Override public boolean isAppRunning() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method indicates whether the meter could be placed into HIRED mode from FOR HIRE mode.
         * The meter is ready when a valid GPS fix or OBD connection has been established.
         * @return true if the meter is ready and false otherwise.
         */
    @Override public boolean isMeterReady() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method returns the active preset's name.
         * @return the active preset's name.
         */
    @Override public java.lang.String getPreset() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * This method returns the active tariff identifier.
         * @return the active tariff identifier.
         */
    @Override public java.lang.String getTariff() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * This method returns the active preset's currency symbol.
         * @return the active preset's currency symbol.
         */
    @Override public java.lang.String getCurrency() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * This method returns the extras (irrespective of whether the displayed fare is totalled or not).
         * @return the extras.
         */
    @Override public double getExtras() throws android.os.RemoteException
    {
      return 0.0d;
    }
    /**
         * This method returns the chargeable fare (irrespective of whether the displayed fare is totalled or not).
         * If the tariff has a minimum fare this will always be equal to or greater than the minimum fare.
         * Any extras and tax have to be added to this value to obtain the total fare, i.e. the total fare (see {@link #getTotalFare()}) is
         * <pre>
         * {@code
         * getFare() + getExtras() + getTax()
         * }</pre>
         * @return the fare.
         */
    @Override public double getFare() throws android.os.RemoteException
    {
      return 0.0d;
    }
    /**
         * This method returns the current operating state (mode) as an integer. The states are:
         * <pre>
         * {@code
         * public static final int STATE_FOR_HIRE = 0;
         * public static final int STATE_HIRED    = 1;
         * public static final int STATE_STOPPED  = 2;
         * }
         * </pre>
         * @return the current state.
         */
    @Override public int getState() throws android.os.RemoteException
    {
      return 0;
    }
    /**
         * This method sets the current operating state (mode). The states are:
         * <pre>
         * {@code
         * public static final int STATE_FOR_HIRE = 0;
         * public static final int STATE_HIRED    = 1;
         * public static final int STATE_STOPPED  = 2;
         * }</pre>
         * @param state The state to set.
         * @return true if the state has been successfully set and false otherwise.
         */
    @Override public boolean setState(int state) throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method sets the active tariff identifier.
         * @param tariff The tariff to set.
         * @return true if the tariff has been successfully set and false otherwise.
         */
    @Override public boolean setTariff(java.lang.String tariff) throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method sets the extras.
         * @param extras The extras to set.
         * @return true if the extras have been successfully set and false otherwise.
         */
    @Override public boolean setExtras(double extras) throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method toggles the displayed fare/total fare when the meter is in STATE_STOPPED.
         * @return true if the fare was successfully toggled and false otherwise.
         */
    @Override public boolean toggleTotalFare() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method indicates whether the displayed fare is totalled.
         * @return true if the displayed fare is the total fare.
         */
    @Override public boolean isTotalFare() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method indicates whether the Taximeter API is enabled.
         * This method does not require the app to be running.
         * @return true if the API is enabled, false otherwise.
         */
    @Override public boolean isAPIEnabled() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method increments the extras (pass a negative value to decrement the extras).
         * @param extras The extras delta to increment.
         * @return true if the extras have been successfully changed and false otherwise.
         * @since 1.0.1
         */
    @Override public boolean incExtras(double extras) throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method asks Taximeter to shutdown.
         * Taximeter will only shutdown if the meter is in STATE_FOR_HIRE.
         * @return true if Taximeter can shutdown, false otherwise.
         * @since 1.0.1
         */
    @Override public boolean shutdown() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method returns the OBD vehicle speed in km/h.
         * This returns the standard OBD-II PID (On-Board Diagnostics Parameter ID) for Vehicle Speed (Mode 01 PID 0D).
         * @return the speed. Returns -1 if data is not available.
         * @since 1.0.1
         */
    @Override public int getOBDSpeed() throws android.os.RemoteException
    {
      return 0;
    }
    /**
         * This method returns the OBD fuel level input as a percentage.
         * This returns the standard OBD-II PID (On-Board Diagnostics Parameter ID) for Fuel Level Input (Mode 01 PID 2F).
         * @return the fuel level as a percent (0% is full, 100% is empty).  Returns -1.0f if data is not available.
         * @since 1.0.1
         */
    @Override public float getOBDFuelLevel() throws android.os.RemoteException
    {
      return 0.0f;
    }
    /**
         * This method returns the displayed fare (this is dependent upon whether the fare is totalled or not see {@link #isTotalFare()}).
         * @return the displayed fare.
         * @since 1.0.2
         */
    @Override public double getDisplayedFare() throws android.os.RemoteException
    {
      return 0.0d;
    }
    /**
         * This method returns the sales tax (irrespective of whether the displayed fare is totalled or not).
         * @return the tax.
         * @since 1.0.2
         */
    @Override public double getTax() throws android.os.RemoteException
    {
      return 0.0d;
    }
    /**
         * This method returns the total chargeable fare (irrespective of whether the displayed fare is totalled or not).
         * The total fare is the equivalent of getFare() + getExtras() + getTax().
         * @return the total fare.
         * @since 1.0.2
         */
    @Override public double getTotalFare() throws android.os.RemoteException
    {
      return 0.0d;
    }
    /**
         * Set license key.
         * This method does not require the app to be running.
         * @since 1.0.3
         */
    @Override public void setLicenseKey(java.lang.String key) throws android.os.RemoteException
    {
    }
    /**
         * Set the license key for POS Print.
         * This method does not require the app to be running.
         * @since 1.0.3
         */
    @Override public void setPOSPrintLicenseKey(java.lang.String key) throws android.os.RemoteException
    {
    }
    /**
         * This method returns the API version.
         * This method does not require the app to be running.
         * @return the API version.
         * @since 1.0.4
         */
    @Override public java.lang.String getAPIVersion() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * This method starts Taximeter and then brings the application with the specified package name
         * to the front of the activity stack.
         * This method does not require Taximeter to be running.
         * @return true is Taximeter was successfully started, false otherwise.
         * @since 1.0.4
         */
    @Override public boolean startup(java.lang.String packageName) throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method returns the meter's serial number.
         * @return the meter's serial number.
         * @since 1.0.4
         */
    @Override public java.lang.String getSerialNumber() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * This method indicates whether the settings are locked.
         * @return true if the settings are locked and false otherwise.
         * @since 1.0.4
         */
    @Override public boolean areSettingsLocked() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method locks the settings.
         * Notes: If the preset and/or settings are locked, only the Android user that 
         * locked them can perform this operation.
         * @return true if the settings have been successfully locked and false otherwise.
         * @since 1.0.4
         */
    @Override public boolean lockSettings() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method unlocks the settings.
         * Notes: If the preset and/or settings are locked, only the Android user that 
         * locked them can perform this operation.
         * @return true if the settings have been successfully unlocked and false otherwise.
         * @since 1.0.4
         */
    @Override public boolean unlockSettings() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method finds and sets the preset from the currently loaded set of presets
         * based on its name (this will not affect an active hire).
         * Notes: If the preset and/or settings are locked, only the Android user that 
         * locked them can perform this operation.  If the preset is locked the named 
         * preset will replace the locked preset definition and the preset will
         * remain locked.
         * @param name The name of the preset to set.
         * @return true if the preset has been successfully set and false otherwise.
         * @since 1.0.4
         */
    @Override public boolean setPreset(java.lang.String name) throws android.os.RemoteException
    {
      return false;
    }
    /**
         * Get the version of the active preset.
         * @return the version of the active preset.
         * @since 1.0.4
         */
    @Override public java.lang.String getPresetVersion() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * Download presets from the specified URL.
         * Notes: If the settings are locked, only the Android user that 
         * locked them can perform this operation.
         * The "Download URL" setting will be updated to match the specified URL
         * if communication is successful.
         * This method will block until the operation completes.
         * @return true if presets were downloaded, false otherwise.
         * @since 1.0.4
         */
    @Override public boolean downloadPresets(java.lang.String url) throws android.os.RemoteException
    {
      return false;
    }
    /**
         * Send Taximeter to the back of the activity stack.
         * @since 1.0.4
         */
    @Override public void sendToBack() throws android.os.RemoteException
    {
    }
    /**
         * Bring Taximeter to the front of the activity stack.
         * @since 1.0.4
         */
    @Override public void bringToFront() throws android.os.RemoteException
    {
    }
    /**
         * This method indicates whether the OBD interface is enabled.
         * @return true if OBD is enabled, false otherwise.
         * @since 1.0.5
         */
    @Override public boolean isOBDEnabled() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method attempts to enable the OBD interface.
         * Notes: Equivalent to checking Menu > Settings > OBD settings > Enable OBD.
         * @since 1.0.5
         */
    @Override public void enableOBD() throws android.os.RemoteException
    {
    }
    /**
         * This method indicates whether the preset is locked.
         * @return true if the preset is locked and false otherwise.
         * @since 1.0.6
         */
    @Override public boolean isPresetLocked() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method locks the current preset.
         * Note: Inoperative if the preset and/or settings have been locked by another
         * Android user.
         * @return true if the preset has been successfully locked and false otherwise.
         * @since 1.0.6
         */
    @Override public boolean lockPreset() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method unlocks the preset.
         * Notes: If the preset is locked, only the Android user that locked
         * the preset can perform this operation.
         * @return true if the preset has been successfully unlocked and false otherwise.
         * @since 1.0.6
         */
    @Override public boolean unlockPreset() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method sets the preset based on the passed preset XML definition
         * (this will not affect an active hire).
         * A top level "taximeters" element is required, i.e.
         * "&lt;taximeters version=\"ABC123\"&gt;&lt;preset&gt;...&lt;/preset&gt;&lt;/taximeters&gt;"
         * Notes: If the preset and/or settings are locked, only the Android user that
         * locked them can perform this operation.  If the preset is locked this
         * definition will replace the locked preset definition and the preset will
         * remain locked.  If the preset is not locked the definition is volatile (it will
         * not be remembered across application restarts).
         * @param xml The XML preset definition to set.
         * @return true if the preset has been successfully set and false otherwise.
         * @since 1.0.6
         */
    @Override public boolean setPresetXML(java.lang.String xml) throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method returns the preset XML definition for the active preset.
         * @return the active preset's XML definition wrapped in a top level
         * "taximeters" element.
         * @since 1.0.6
         */
    @Override public java.lang.String getPresetXML() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * Gets the last error.
         * (Added to help resolve XML parse errors when setPresetXML(String) returns false).
         * @return the last error.
         * @since 1.0.6
         */
    @Override public java.lang.String getLastError() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * This method resets the preset to the selected preset from the user settings
         * (this will not affect an active hire).
         * Notes: If the preset and/or settings are locked, only the Android user that
         * locked them can perform this operation.  If the preset is locked the selected
         * preset will replace the locked preset definition and the preset will
         * remain locked.
         * @return true if the preset has been reset and false otherwise.
         * @since 1.0.6
         */
    @Override public boolean resetPreset() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method will overwrite Taximeter's settings with those from a previously
         * saved backup file.  Any passwords or settings associated with a locked preset
         * will not be overwritten.  The settings file is portable between devices and is
         * located at the following path, Environment.getExternalStorageDirectory() +
         * "/Taximeter/taximeter.backup".
         * Notes: If the preset and/or settings are locked, only the Android user that
         * locked them can perform this operation.
         * @return true if the settings were restored and false otherwise.
         * @since 1.0.6
         */
    @Override public boolean restorePreferences() throws android.os.RemoteException
    {
      return false;
    }
    /**
         * This method returns the discount as a monetary amount.  The user can only apply a 
         * discount when the fare is totalled. Toggling the fare clears any discount.
         * The pre-discount total can be calculated by adding the discount to the 
         * total chargeable fare, i.e.
         * <pre>
         * {@code
         * pre-discount total = getTotalFare() + getDiscount()
         * pre-discount total = chargeable_fare + discount (from the broadcast intent)
         * }</pre>
         * @return the discount.
         * @since 1.0.7
         */
    @Override public double getDiscount() throws android.os.RemoteException
    {
      return 0.0d;
    }
    /**
         * This method returns the active preset's ISO 4217 currency code.
         * @return the active preset's ISO 4217 currency code.
         * @since 1.0.8
         */
    @Override public java.lang.String getCurrencyCode() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * This method returns the active tariff description.
         * @return the active tariff description.
         * @since 1.0.9
         */
    @Override public java.lang.String getTariffDescription() throws android.os.RemoteException
    {
      return null;
    }
    /**
         * This method returns the tip.
         * @return the tip.
         * @since 1.0.9
         */
    @Override public double getTip() throws android.os.RemoteException
    {
      return 0.0d;
    }
    /**
         * This method returns the preset download URL.
         * @return the preset download URL.
         * @since 1.1.1
         */
    @Override public java.lang.String getDownloadURL() throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.planetcoops.android.taximeter.ITaximeterService
  {
    private static final java.lang.String DESCRIPTOR = "com.planetcoops.android.taximeter.ITaximeterService";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.planetcoops.android.taximeter.ITaximeterService interface,
     * generating a proxy if needed.
     */
    public static com.planetcoops.android.taximeter.ITaximeterService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.planetcoops.android.taximeter.ITaximeterService))) {
        return ((com.planetcoops.android.taximeter.ITaximeterService)iin);
      }
      return new com.planetcoops.android.taximeter.ITaximeterService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_isAppRunning:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isAppRunning();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_isMeterReady:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isMeterReady();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getPreset:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getPreset();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getTariff:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getTariff();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getCurrency:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getCurrency();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getExtras:
        {
          data.enforceInterface(descriptor);
          double _result = this.getExtras();
          reply.writeNoException();
          reply.writeDouble(_result);
          return true;
        }
        case TRANSACTION_getFare:
        {
          data.enforceInterface(descriptor);
          double _result = this.getFare();
          reply.writeNoException();
          reply.writeDouble(_result);
          return true;
        }
        case TRANSACTION_getState:
        {
          data.enforceInterface(descriptor);
          int _result = this.getState();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_setState:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          boolean _result = this.setState(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_setTariff:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.setTariff(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_setExtras:
        {
          data.enforceInterface(descriptor);
          double _arg0;
          _arg0 = data.readDouble();
          boolean _result = this.setExtras(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_toggleTotalFare:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.toggleTotalFare();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_isTotalFare:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isTotalFare();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_isAPIEnabled:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isAPIEnabled();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_incExtras:
        {
          data.enforceInterface(descriptor);
          double _arg0;
          _arg0 = data.readDouble();
          boolean _result = this.incExtras(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_shutdown:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.shutdown();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getOBDSpeed:
        {
          data.enforceInterface(descriptor);
          int _result = this.getOBDSpeed();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_getOBDFuelLevel:
        {
          data.enforceInterface(descriptor);
          float _result = this.getOBDFuelLevel();
          reply.writeNoException();
          reply.writeFloat(_result);
          return true;
        }
        case TRANSACTION_getDisplayedFare:
        {
          data.enforceInterface(descriptor);
          double _result = this.getDisplayedFare();
          reply.writeNoException();
          reply.writeDouble(_result);
          return true;
        }
        case TRANSACTION_getTax:
        {
          data.enforceInterface(descriptor);
          double _result = this.getTax();
          reply.writeNoException();
          reply.writeDouble(_result);
          return true;
        }
        case TRANSACTION_getTotalFare:
        {
          data.enforceInterface(descriptor);
          double _result = this.getTotalFare();
          reply.writeNoException();
          reply.writeDouble(_result);
          return true;
        }
        case TRANSACTION_setLicenseKey:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.setLicenseKey(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_setPOSPrintLicenseKey:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.setPOSPrintLicenseKey(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_getAPIVersion:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getAPIVersion();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_startup:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.startup(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getSerialNumber:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getSerialNumber();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_areSettingsLocked:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.areSettingsLocked();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_lockSettings:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.lockSettings();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_unlockSettings:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.unlockSettings();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_setPreset:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.setPreset(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getPresetVersion:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getPresetVersion();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_downloadPresets:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.downloadPresets(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_sendToBack:
        {
          data.enforceInterface(descriptor);
          this.sendToBack();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_bringToFront:
        {
          data.enforceInterface(descriptor);
          this.bringToFront();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_isOBDEnabled:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isOBDEnabled();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_enableOBD:
        {
          data.enforceInterface(descriptor);
          this.enableOBD();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_isPresetLocked:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.isPresetLocked();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_lockPreset:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.lockPreset();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_unlockPreset:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.unlockPreset();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_setPresetXML:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          boolean _result = this.setPresetXML(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getPresetXML:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getPresetXML();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getLastError:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getLastError();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_resetPreset:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.resetPreset();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_restorePreferences:
        {
          data.enforceInterface(descriptor);
          boolean _result = this.restorePreferences();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          return true;
        }
        case TRANSACTION_getDiscount:
        {
          data.enforceInterface(descriptor);
          double _result = this.getDiscount();
          reply.writeNoException();
          reply.writeDouble(_result);
          return true;
        }
        case TRANSACTION_getCurrencyCode:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getCurrencyCode();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getTariffDescription:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getTariffDescription();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_getTip:
        {
          data.enforceInterface(descriptor);
          double _result = this.getTip();
          reply.writeNoException();
          reply.writeDouble(_result);
          return true;
        }
        case TRANSACTION_getDownloadURL:
        {
          data.enforceInterface(descriptor);
          java.lang.String _result = this.getDownloadURL();
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.planetcoops.android.taximeter.ITaximeterService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
           * This method indicates whether the Taximeter application is running.
           * The application needs to have been started before most of the API methods can be used
           * (isAppRunning(), isAPIEnabled(), getAPIVersion(), setLicenseKey(String), setPOSPrintLicenseKey(String), and startup(String) are the exceptions).
           * @return true if the Taximeter app is running and false otherwise.
           */
      @Override public boolean isAppRunning() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isAppRunning, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isAppRunning();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method indicates whether the meter could be placed into HIRED mode from FOR HIRE mode.
           * The meter is ready when a valid GPS fix or OBD connection has been established.
           * @return true if the meter is ready and false otherwise.
           */
      @Override public boolean isMeterReady() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isMeterReady, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isMeterReady();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the active preset's name.
           * @return the active preset's name.
           */
      @Override public java.lang.String getPreset() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPreset, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPreset();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the active tariff identifier.
           * @return the active tariff identifier.
           */
      @Override public java.lang.String getTariff() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getTariff, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getTariff();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the active preset's currency symbol.
           * @return the active preset's currency symbol.
           */
      @Override public java.lang.String getCurrency() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getCurrency, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getCurrency();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the extras (irrespective of whether the displayed fare is totalled or not).
           * @return the extras.
           */
      @Override public double getExtras() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        double _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getExtras, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getExtras();
          }
          _reply.readException();
          _result = _reply.readDouble();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the chargeable fare (irrespective of whether the displayed fare is totalled or not).
           * If the tariff has a minimum fare this will always be equal to or greater than the minimum fare.
           * Any extras and tax have to be added to this value to obtain the total fare, i.e. the total fare (see {@link #getTotalFare()}) is
           * <pre>
           * {@code
           * getFare() + getExtras() + getTax()
           * }</pre>
           * @return the fare.
           */
      @Override public double getFare() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        double _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getFare, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getFare();
          }
          _reply.readException();
          _result = _reply.readDouble();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the current operating state (mode) as an integer. The states are:
           * <pre>
           * {@code
           * public static final int STATE_FOR_HIRE = 0;
           * public static final int STATE_HIRED    = 1;
           * public static final int STATE_STOPPED  = 2;
           * }
           * </pre>
           * @return the current state.
           */
      @Override public int getState() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getState, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getState();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method sets the current operating state (mode). The states are:
           * <pre>
           * {@code
           * public static final int STATE_FOR_HIRE = 0;
           * public static final int STATE_HIRED    = 1;
           * public static final int STATE_STOPPED  = 2;
           * }</pre>
           * @param state The state to set.
           * @return true if the state has been successfully set and false otherwise.
           */
      @Override public boolean setState(int state) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(state);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setState, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setState(state);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method sets the active tariff identifier.
           * @param tariff The tariff to set.
           * @return true if the tariff has been successfully set and false otherwise.
           */
      @Override public boolean setTariff(java.lang.String tariff) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(tariff);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setTariff, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setTariff(tariff);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method sets the extras.
           * @param extras The extras to set.
           * @return true if the extras have been successfully set and false otherwise.
           */
      @Override public boolean setExtras(double extras) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeDouble(extras);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setExtras, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setExtras(extras);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method toggles the displayed fare/total fare when the meter is in STATE_STOPPED.
           * @return true if the fare was successfully toggled and false otherwise.
           */
      @Override public boolean toggleTotalFare() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_toggleTotalFare, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().toggleTotalFare();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method indicates whether the displayed fare is totalled.
           * @return true if the displayed fare is the total fare.
           */
      @Override public boolean isTotalFare() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isTotalFare, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isTotalFare();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method indicates whether the Taximeter API is enabled.
           * This method does not require the app to be running.
           * @return true if the API is enabled, false otherwise.
           */
      @Override public boolean isAPIEnabled() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isAPIEnabled, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isAPIEnabled();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method increments the extras (pass a negative value to decrement the extras).
           * @param extras The extras delta to increment.
           * @return true if the extras have been successfully changed and false otherwise.
           * @since 1.0.1
           */
      @Override public boolean incExtras(double extras) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeDouble(extras);
          boolean _status = mRemote.transact(Stub.TRANSACTION_incExtras, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().incExtras(extras);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method asks Taximeter to shutdown.
           * Taximeter will only shutdown if the meter is in STATE_FOR_HIRE.
           * @return true if Taximeter can shutdown, false otherwise.
           * @since 1.0.1
           */
      @Override public boolean shutdown() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_shutdown, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().shutdown();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the OBD vehicle speed in km/h.
           * This returns the standard OBD-II PID (On-Board Diagnostics Parameter ID) for Vehicle Speed (Mode 01 PID 0D).
           * @return the speed. Returns -1 if data is not available.
           * @since 1.0.1
           */
      @Override public int getOBDSpeed() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getOBDSpeed, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getOBDSpeed();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the OBD fuel level input as a percentage.
           * This returns the standard OBD-II PID (On-Board Diagnostics Parameter ID) for Fuel Level Input (Mode 01 PID 2F).
           * @return the fuel level as a percent (0% is full, 100% is empty).  Returns -1.0f if data is not available.
           * @since 1.0.1
           */
      @Override public float getOBDFuelLevel() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        float _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getOBDFuelLevel, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getOBDFuelLevel();
          }
          _reply.readException();
          _result = _reply.readFloat();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the displayed fare (this is dependent upon whether the fare is totalled or not see {@link #isTotalFare()}).
           * @return the displayed fare.
           * @since 1.0.2
           */
      @Override public double getDisplayedFare() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        double _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getDisplayedFare, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getDisplayedFare();
          }
          _reply.readException();
          _result = _reply.readDouble();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the sales tax (irrespective of whether the displayed fare is totalled or not).
           * @return the tax.
           * @since 1.0.2
           */
      @Override public double getTax() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        double _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getTax, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getTax();
          }
          _reply.readException();
          _result = _reply.readDouble();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the total chargeable fare (irrespective of whether the displayed fare is totalled or not).
           * The total fare is the equivalent of getFare() + getExtras() + getTax().
           * @return the total fare.
           * @since 1.0.2
           */
      @Override public double getTotalFare() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        double _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getTotalFare, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getTotalFare();
          }
          _reply.readException();
          _result = _reply.readDouble();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * Set license key.
           * This method does not require the app to be running.
           * @since 1.0.3
           */
      @Override public void setLicenseKey(java.lang.String key) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(key);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setLicenseKey, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setLicenseKey(key);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
           * Set the license key for POS Print.
           * This method does not require the app to be running.
           * @since 1.0.3
           */
      @Override public void setPOSPrintLicenseKey(java.lang.String key) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(key);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setPOSPrintLicenseKey, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setPOSPrintLicenseKey(key);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
           * This method returns the API version.
           * This method does not require the app to be running.
           * @return the API version.
           * @since 1.0.4
           */
      @Override public java.lang.String getAPIVersion() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getAPIVersion, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getAPIVersion();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method starts Taximeter and then brings the application with the specified package name
           * to the front of the activity stack.
           * This method does not require Taximeter to be running.
           * @return true is Taximeter was successfully started, false otherwise.
           * @since 1.0.4
           */
      @Override public boolean startup(java.lang.String packageName) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(packageName);
          boolean _status = mRemote.transact(Stub.TRANSACTION_startup, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().startup(packageName);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the meter's serial number.
           * @return the meter's serial number.
           * @since 1.0.4
           */
      @Override public java.lang.String getSerialNumber() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getSerialNumber, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getSerialNumber();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method indicates whether the settings are locked.
           * @return true if the settings are locked and false otherwise.
           * @since 1.0.4
           */
      @Override public boolean areSettingsLocked() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_areSettingsLocked, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().areSettingsLocked();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method locks the settings.
           * Notes: If the preset and/or settings are locked, only the Android user that 
           * locked them can perform this operation.
           * @return true if the settings have been successfully locked and false otherwise.
           * @since 1.0.4
           */
      @Override public boolean lockSettings() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_lockSettings, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().lockSettings();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method unlocks the settings.
           * Notes: If the preset and/or settings are locked, only the Android user that 
           * locked them can perform this operation.
           * @return true if the settings have been successfully unlocked and false otherwise.
           * @since 1.0.4
           */
      @Override public boolean unlockSettings() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_unlockSettings, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().unlockSettings();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method finds and sets the preset from the currently loaded set of presets
           * based on its name (this will not affect an active hire).
           * Notes: If the preset and/or settings are locked, only the Android user that 
           * locked them can perform this operation.  If the preset is locked the named 
           * preset will replace the locked preset definition and the preset will
           * remain locked.
           * @param name The name of the preset to set.
           * @return true if the preset has been successfully set and false otherwise.
           * @since 1.0.4
           */
      @Override public boolean setPreset(java.lang.String name) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(name);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setPreset, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setPreset(name);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * Get the version of the active preset.
           * @return the version of the active preset.
           * @since 1.0.4
           */
      @Override public java.lang.String getPresetVersion() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPresetVersion, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPresetVersion();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * Download presets from the specified URL.
           * Notes: If the settings are locked, only the Android user that 
           * locked them can perform this operation.
           * The "Download URL" setting will be updated to match the specified URL
           * if communication is successful.
           * This method will block until the operation completes.
           * @return true if presets were downloaded, false otherwise.
           * @since 1.0.4
           */
      @Override public boolean downloadPresets(java.lang.String url) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(url);
          boolean _status = mRemote.transact(Stub.TRANSACTION_downloadPresets, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().downloadPresets(url);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * Send Taximeter to the back of the activity stack.
           * @since 1.0.4
           */
      @Override public void sendToBack() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendToBack, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().sendToBack();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
           * Bring Taximeter to the front of the activity stack.
           * @since 1.0.4
           */
      @Override public void bringToFront() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_bringToFront, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().bringToFront();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
           * This method indicates whether the OBD interface is enabled.
           * @return true if OBD is enabled, false otherwise.
           * @since 1.0.5
           */
      @Override public boolean isOBDEnabled() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isOBDEnabled, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isOBDEnabled();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method attempts to enable the OBD interface.
           * Notes: Equivalent to checking Menu > Settings > OBD settings > Enable OBD.
           * @since 1.0.5
           */
      @Override public void enableOBD() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_enableOBD, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().enableOBD();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /**
           * This method indicates whether the preset is locked.
           * @return true if the preset is locked and false otherwise.
           * @since 1.0.6
           */
      @Override public boolean isPresetLocked() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isPresetLocked, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().isPresetLocked();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method locks the current preset.
           * Note: Inoperative if the preset and/or settings have been locked by another
           * Android user.
           * @return true if the preset has been successfully locked and false otherwise.
           * @since 1.0.6
           */
      @Override public boolean lockPreset() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_lockPreset, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().lockPreset();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method unlocks the preset.
           * Notes: If the preset is locked, only the Android user that locked
           * the preset can perform this operation.
           * @return true if the preset has been successfully unlocked and false otherwise.
           * @since 1.0.6
           */
      @Override public boolean unlockPreset() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_unlockPreset, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().unlockPreset();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method sets the preset based on the passed preset XML definition
           * (this will not affect an active hire).
           * A top level "taximeters" element is required, i.e.
           * "&lt;taximeters version=\"ABC123\"&gt;&lt;preset&gt;...&lt;/preset&gt;&lt;/taximeters&gt;"
           * Notes: If the preset and/or settings are locked, only the Android user that
           * locked them can perform this operation.  If the preset is locked this
           * definition will replace the locked preset definition and the preset will
           * remain locked.  If the preset is not locked the definition is volatile (it will
           * not be remembered across application restarts).
           * @param xml The XML preset definition to set.
           * @return true if the preset has been successfully set and false otherwise.
           * @since 1.0.6
           */
      @Override public boolean setPresetXML(java.lang.String xml) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(xml);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setPresetXML, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().setPresetXML(xml);
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the preset XML definition for the active preset.
           * @return the active preset's XML definition wrapped in a top level
           * "taximeters" element.
           * @since 1.0.6
           */
      @Override public java.lang.String getPresetXML() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getPresetXML, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getPresetXML();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * Gets the last error.
           * (Added to help resolve XML parse errors when setPresetXML(String) returns false).
           * @return the last error.
           * @since 1.0.6
           */
      @Override public java.lang.String getLastError() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getLastError, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getLastError();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method resets the preset to the selected preset from the user settings
           * (this will not affect an active hire).
           * Notes: If the preset and/or settings are locked, only the Android user that
           * locked them can perform this operation.  If the preset is locked the selected
           * preset will replace the locked preset definition and the preset will
           * remain locked.
           * @return true if the preset has been reset and false otherwise.
           * @since 1.0.6
           */
      @Override public boolean resetPreset() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_resetPreset, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().resetPreset();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method will overwrite Taximeter's settings with those from a previously
           * saved backup file.  Any passwords or settings associated with a locked preset
           * will not be overwritten.  The settings file is portable between devices and is
           * located at the following path, Environment.getExternalStorageDirectory() +
           * "/Taximeter/taximeter.backup".
           * Notes: If the preset and/or settings are locked, only the Android user that
           * locked them can perform this operation.
           * @return true if the settings were restored and false otherwise.
           * @since 1.0.6
           */
      @Override public boolean restorePreferences() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_restorePreferences, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().restorePreferences();
          }
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the discount as a monetary amount.  The user can only apply a 
           * discount when the fare is totalled. Toggling the fare clears any discount.
           * The pre-discount total can be calculated by adding the discount to the 
           * total chargeable fare, i.e.
           * <pre>
           * {@code
           * pre-discount total = getTotalFare() + getDiscount()
           * pre-discount total = chargeable_fare + discount (from the broadcast intent)
           * }</pre>
           * @return the discount.
           * @since 1.0.7
           */
      @Override public double getDiscount() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        double _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getDiscount, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getDiscount();
          }
          _reply.readException();
          _result = _reply.readDouble();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the active preset's ISO 4217 currency code.
           * @return the active preset's ISO 4217 currency code.
           * @since 1.0.8
           */
      @Override public java.lang.String getCurrencyCode() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getCurrencyCode, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getCurrencyCode();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the active tariff description.
           * @return the active tariff description.
           * @since 1.0.9
           */
      @Override public java.lang.String getTariffDescription() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getTariffDescription, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getTariffDescription();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the tip.
           * @return the tip.
           * @since 1.0.9
           */
      @Override public double getTip() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        double _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getTip, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getTip();
          }
          _reply.readException();
          _result = _reply.readDouble();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * This method returns the preset download URL.
           * @return the preset download URL.
           * @since 1.1.1
           */
      @Override public java.lang.String getDownloadURL() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getDownloadURL, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getDownloadURL();
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static com.planetcoops.android.taximeter.ITaximeterService sDefaultImpl;
    }
    static final int TRANSACTION_isAppRunning = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_isMeterReady = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_getPreset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_getTariff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_getCurrency = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_getExtras = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_getFare = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_getState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_setState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    static final int TRANSACTION_setTariff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
    static final int TRANSACTION_setExtras = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
    static final int TRANSACTION_toggleTotalFare = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
    static final int TRANSACTION_isTotalFare = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
    static final int TRANSACTION_isAPIEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
    static final int TRANSACTION_incExtras = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
    static final int TRANSACTION_shutdown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
    static final int TRANSACTION_getOBDSpeed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
    static final int TRANSACTION_getOBDFuelLevel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
    static final int TRANSACTION_getDisplayedFare = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
    static final int TRANSACTION_getTax = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
    static final int TRANSACTION_getTotalFare = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
    static final int TRANSACTION_setLicenseKey = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
    static final int TRANSACTION_setPOSPrintLicenseKey = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
    static final int TRANSACTION_getAPIVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
    static final int TRANSACTION_startup = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
    static final int TRANSACTION_getSerialNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
    static final int TRANSACTION_areSettingsLocked = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
    static final int TRANSACTION_lockSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
    static final int TRANSACTION_unlockSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
    static final int TRANSACTION_setPreset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
    static final int TRANSACTION_getPresetVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
    static final int TRANSACTION_downloadPresets = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
    static final int TRANSACTION_sendToBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
    static final int TRANSACTION_bringToFront = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
    static final int TRANSACTION_isOBDEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
    static final int TRANSACTION_enableOBD = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
    static final int TRANSACTION_isPresetLocked = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
    static final int TRANSACTION_lockPreset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
    static final int TRANSACTION_unlockPreset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
    static final int TRANSACTION_setPresetXML = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
    static final int TRANSACTION_getPresetXML = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
    static final int TRANSACTION_getLastError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
    static final int TRANSACTION_resetPreset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
    static final int TRANSACTION_restorePreferences = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
    static final int TRANSACTION_getDiscount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
    static final int TRANSACTION_getCurrencyCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
    static final int TRANSACTION_getTariffDescription = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
    static final int TRANSACTION_getTip = (android.os.IBinder.FIRST_CALL_TRANSACTION + 47);
    static final int TRANSACTION_getDownloadURL = (android.os.IBinder.FIRST_CALL_TRANSACTION + 48);
    public static boolean setDefaultImpl(com.planetcoops.android.taximeter.ITaximeterService impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Stub.Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.planetcoops.android.taximeter.ITaximeterService getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
       * This method indicates whether the Taximeter application is running.
       * The application needs to have been started before most of the API methods can be used
       * (isAppRunning(), isAPIEnabled(), getAPIVersion(), setLicenseKey(String), setPOSPrintLicenseKey(String), and startup(String) are the exceptions).
       * @return true if the Taximeter app is running and false otherwise.
       */
  public boolean isAppRunning() throws android.os.RemoteException;
  /**
       * This method indicates whether the meter could be placed into HIRED mode from FOR HIRE mode.
       * The meter is ready when a valid GPS fix or OBD connection has been established.
       * @return true if the meter is ready and false otherwise.
       */
  public boolean isMeterReady() throws android.os.RemoteException;
  /**
       * This method returns the active preset's name.
       * @return the active preset's name.
       */
  public java.lang.String getPreset() throws android.os.RemoteException;
  /**
       * This method returns the active tariff identifier.
       * @return the active tariff identifier.
       */
  public java.lang.String getTariff() throws android.os.RemoteException;
  /**
       * This method returns the active preset's currency symbol.
       * @return the active preset's currency symbol.
       */
  public java.lang.String getCurrency() throws android.os.RemoteException;
  /**
       * This method returns the extras (irrespective of whether the displayed fare is totalled or not).
       * @return the extras.
       */
  public double getExtras() throws android.os.RemoteException;
  /**
       * This method returns the chargeable fare (irrespective of whether the displayed fare is totalled or not).
       * If the tariff has a minimum fare this will always be equal to or greater than the minimum fare.
       * Any extras and tax have to be added to this value to obtain the total fare, i.e. the total fare (see {@link #getTotalFare()}) is
       * <pre>
       * {@code
       * getFare() + getExtras() + getTax()
       * }</pre>
       * @return the fare.
       */
  public double getFare() throws android.os.RemoteException;
  /**
       * This method returns the current operating state (mode) as an integer. The states are:
       * <pre>
       * {@code
       * public static final int STATE_FOR_HIRE = 0;
       * public static final int STATE_HIRED    = 1;
       * public static final int STATE_STOPPED  = 2;
       * }
       * </pre>
       * @return the current state.
       */
  public int getState() throws android.os.RemoteException;
  /**
       * This method sets the current operating state (mode). The states are:
       * <pre>
       * {@code
       * public static final int STATE_FOR_HIRE = 0;
       * public static final int STATE_HIRED    = 1;
       * public static final int STATE_STOPPED  = 2;
       * }</pre>
       * @param state The state to set.
       * @return true if the state has been successfully set and false otherwise.
       */
  public boolean setState(int state) throws android.os.RemoteException;
  /**
       * This method sets the active tariff identifier.
       * @param tariff The tariff to set.
       * @return true if the tariff has been successfully set and false otherwise.
       */
  public boolean setTariff(java.lang.String tariff) throws android.os.RemoteException;
  /**
       * This method sets the extras.
       * @param extras The extras to set.
       * @return true if the extras have been successfully set and false otherwise.
       */
  public boolean setExtras(double extras) throws android.os.RemoteException;
  /**
       * This method toggles the displayed fare/total fare when the meter is in STATE_STOPPED.
       * @return true if the fare was successfully toggled and false otherwise.
       */
  public boolean toggleTotalFare() throws android.os.RemoteException;
  /**
       * This method indicates whether the displayed fare is totalled.
       * @return true if the displayed fare is the total fare.
       */
  public boolean isTotalFare() throws android.os.RemoteException;
  /**
       * This method indicates whether the Taximeter API is enabled.
       * This method does not require the app to be running.
       * @return true if the API is enabled, false otherwise.
       */
  public boolean isAPIEnabled() throws android.os.RemoteException;
  /**
       * This method increments the extras (pass a negative value to decrement the extras).
       * @param extras The extras delta to increment.
       * @return true if the extras have been successfully changed and false otherwise.
       * @since 1.0.1
       */
  public boolean incExtras(double extras) throws android.os.RemoteException;
  /**
       * This method asks Taximeter to shutdown.
       * Taximeter will only shutdown if the meter is in STATE_FOR_HIRE.
       * @return true if Taximeter can shutdown, false otherwise.
       * @since 1.0.1
       */
  public boolean shutdown() throws android.os.RemoteException;
  /**
       * This method returns the OBD vehicle speed in km/h.
       * This returns the standard OBD-II PID (On-Board Diagnostics Parameter ID) for Vehicle Speed (Mode 01 PID 0D).
       * @return the speed. Returns -1 if data is not available.
       * @since 1.0.1
       */
  public int getOBDSpeed() throws android.os.RemoteException;
  /**
       * This method returns the OBD fuel level input as a percentage.
       * This returns the standard OBD-II PID (On-Board Diagnostics Parameter ID) for Fuel Level Input (Mode 01 PID 2F).
       * @return the fuel level as a percent (0% is full, 100% is empty).  Returns -1.0f if data is not available.
       * @since 1.0.1
       */
  public float getOBDFuelLevel() throws android.os.RemoteException;
  /**
       * This method returns the displayed fare (this is dependent upon whether the fare is totalled or not see {@link #isTotalFare()}).
       * @return the displayed fare.
       * @since 1.0.2
       */
  public double getDisplayedFare() throws android.os.RemoteException;
  /**
       * This method returns the sales tax (irrespective of whether the displayed fare is totalled or not).
       * @return the tax.
       * @since 1.0.2
       */
  public double getTax() throws android.os.RemoteException;
  /**
       * This method returns the total chargeable fare (irrespective of whether the displayed fare is totalled or not).
       * The total fare is the equivalent of getFare() + getExtras() + getTax().
       * @return the total fare.
       * @since 1.0.2
       */
  public double getTotalFare() throws android.os.RemoteException;
  /**
       * Set license key.
       * This method does not require the app to be running.
       * @since 1.0.3
       */
  public void setLicenseKey(java.lang.String key) throws android.os.RemoteException;
  /**
       * Set the license key for POS Print.
       * This method does not require the app to be running.
       * @since 1.0.3
       */
  public void setPOSPrintLicenseKey(java.lang.String key) throws android.os.RemoteException;
  /**
       * This method returns the API version.
       * This method does not require the app to be running.
       * @return the API version.
       * @since 1.0.4
       */
  public java.lang.String getAPIVersion() throws android.os.RemoteException;
  /**
       * This method starts Taximeter and then brings the application with the specified package name
       * to the front of the activity stack.
       * This method does not require Taximeter to be running.
       * @return true is Taximeter was successfully started, false otherwise.
       * @since 1.0.4
       */
  public boolean startup(java.lang.String packageName) throws android.os.RemoteException;
  /**
       * This method returns the meter's serial number.
       * @return the meter's serial number.
       * @since 1.0.4
       */
  public java.lang.String getSerialNumber() throws android.os.RemoteException;
  /**
       * This method indicates whether the settings are locked.
       * @return true if the settings are locked and false otherwise.
       * @since 1.0.4
       */
  public boolean areSettingsLocked() throws android.os.RemoteException;
  /**
       * This method locks the settings.
       * Notes: If the preset and/or settings are locked, only the Android user that 
       * locked them can perform this operation.
       * @return true if the settings have been successfully locked and false otherwise.
       * @since 1.0.4
       */
  public boolean lockSettings() throws android.os.RemoteException;
  /**
       * This method unlocks the settings.
       * Notes: If the preset and/or settings are locked, only the Android user that 
       * locked them can perform this operation.
       * @return true if the settings have been successfully unlocked and false otherwise.
       * @since 1.0.4
       */
  public boolean unlockSettings() throws android.os.RemoteException;
  /**
       * This method finds and sets the preset from the currently loaded set of presets
       * based on its name (this will not affect an active hire).
       * Notes: If the preset and/or settings are locked, only the Android user that 
       * locked them can perform this operation.  If the preset is locked the named 
       * preset will replace the locked preset definition and the preset will
       * remain locked.
       * @param name The name of the preset to set.
       * @return true if the preset has been successfully set and false otherwise.
       * @since 1.0.4
       */
  public boolean setPreset(java.lang.String name) throws android.os.RemoteException;
  /**
       * Get the version of the active preset.
       * @return the version of the active preset.
       * @since 1.0.4
       */
  public java.lang.String getPresetVersion() throws android.os.RemoteException;
  /**
       * Download presets from the specified URL.
       * Notes: If the settings are locked, only the Android user that 
       * locked them can perform this operation.
       * The "Download URL" setting will be updated to match the specified URL
       * if communication is successful.
       * This method will block until the operation completes.
       * @return true if presets were downloaded, false otherwise.
       * @since 1.0.4
       */
  public boolean downloadPresets(java.lang.String url) throws android.os.RemoteException;
  /**
       * Send Taximeter to the back of the activity stack.
       * @since 1.0.4
       */
  public void sendToBack() throws android.os.RemoteException;
  /**
       * Bring Taximeter to the front of the activity stack.
       * @since 1.0.4
       */
  public void bringToFront() throws android.os.RemoteException;
  /**
       * This method indicates whether the OBD interface is enabled.
       * @return true if OBD is enabled, false otherwise.
       * @since 1.0.5
       */
  public boolean isOBDEnabled() throws android.os.RemoteException;
  /**
       * This method attempts to enable the OBD interface.
       * Notes: Equivalent to checking Menu > Settings > OBD settings > Enable OBD.
       * @since 1.0.5
       */
  public void enableOBD() throws android.os.RemoteException;
  /**
       * This method indicates whether the preset is locked.
       * @return true if the preset is locked and false otherwise.
       * @since 1.0.6
       */
  public boolean isPresetLocked() throws android.os.RemoteException;
  /**
       * This method locks the current preset.
       * Note: Inoperative if the preset and/or settings have been locked by another
       * Android user.
       * @return true if the preset has been successfully locked and false otherwise.
       * @since 1.0.6
       */
  public boolean lockPreset() throws android.os.RemoteException;
  /**
       * This method unlocks the preset.
       * Notes: If the preset is locked, only the Android user that locked
       * the preset can perform this operation.
       * @return true if the preset has been successfully unlocked and false otherwise.
       * @since 1.0.6
       */
  public boolean unlockPreset() throws android.os.RemoteException;
  /**
       * This method sets the preset based on the passed preset XML definition
       * (this will not affect an active hire).
       * A top level "taximeters" element is required, i.e.
       * "&lt;taximeters version=\"ABC123\"&gt;&lt;preset&gt;...&lt;/preset&gt;&lt;/taximeters&gt;"
       * Notes: If the preset and/or settings are locked, only the Android user that
       * locked them can perform this operation.  If the preset is locked this
       * definition will replace the locked preset definition and the preset will
       * remain locked.  If the preset is not locked the definition is volatile (it will
       * not be remembered across application restarts).
       * @param xml The XML preset definition to set.
       * @return true if the preset has been successfully set and false otherwise.
       * @since 1.0.6
       */
  public boolean setPresetXML(java.lang.String xml) throws android.os.RemoteException;
  /**
       * This method returns the preset XML definition for the active preset.
       * @return the active preset's XML definition wrapped in a top level
       * "taximeters" element.
       * @since 1.0.6
       */
  public java.lang.String getPresetXML() throws android.os.RemoteException;
  /**
       * Gets the last error.
       * (Added to help resolve XML parse errors when setPresetXML(String) returns false).
       * @return the last error.
       * @since 1.0.6
       */
  public java.lang.String getLastError() throws android.os.RemoteException;
  /**
       * This method resets the preset to the selected preset from the user settings
       * (this will not affect an active hire).
       * Notes: If the preset and/or settings are locked, only the Android user that
       * locked them can perform this operation.  If the preset is locked the selected
       * preset will replace the locked preset definition and the preset will
       * remain locked.
       * @return true if the preset has been reset and false otherwise.
       * @since 1.0.6
       */
  public boolean resetPreset() throws android.os.RemoteException;
  /**
       * This method will overwrite Taximeter's settings with those from a previously
       * saved backup file.  Any passwords or settings associated with a locked preset
       * will not be overwritten.  The settings file is portable between devices and is
       * located at the following path, Environment.getExternalStorageDirectory() +
       * "/Taximeter/taximeter.backup".
       * Notes: If the preset and/or settings are locked, only the Android user that
       * locked them can perform this operation.
       * @return true if the settings were restored and false otherwise.
       * @since 1.0.6
       */
  public boolean restorePreferences() throws android.os.RemoteException;
  /**
       * This method returns the discount as a monetary amount.  The user can only apply a 
       * discount when the fare is totalled. Toggling the fare clears any discount.
       * The pre-discount total can be calculated by adding the discount to the 
       * total chargeable fare, i.e.
       * <pre>
       * {@code
       * pre-discount total = getTotalFare() + getDiscount()
       * pre-discount total = chargeable_fare + discount (from the broadcast intent)
       * }</pre>
       * @return the discount.
       * @since 1.0.7
       */
  public double getDiscount() throws android.os.RemoteException;
  /**
       * This method returns the active preset's ISO 4217 currency code.
       * @return the active preset's ISO 4217 currency code.
       * @since 1.0.8
       */
  public java.lang.String getCurrencyCode() throws android.os.RemoteException;
  /**
       * This method returns the active tariff description.
       * @return the active tariff description.
       * @since 1.0.9
       */
  public java.lang.String getTariffDescription() throws android.os.RemoteException;
  /**
       * This method returns the tip.
       * @return the tip.
       * @since 1.0.9
       */
  public double getTip() throws android.os.RemoteException;
  /**
       * This method returns the preset download URL.
       * @return the preset download URL.
       * @since 1.1.1
       */
  public java.lang.String getDownloadURL() throws android.os.RemoteException;
}
