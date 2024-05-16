package com.planetcoops.android.taximeter;
/**
 * Taximeter Interface Definition
 * @author Planet Coops
 * @version 1.1.2
 */
interface ITaximeterService {
    /**
     * This method indicates whether the Taximeter application is running.
     * The application needs to have been started before most of the API methods can be used
     * (isAppRunning(), isAPIEnabled(), getAPIVersion(), setLicenseKey(String), setPOSPrintLicenseKey(String), and startup(String) are the exceptions).
     * @return true if the Taximeter app is running and false otherwise.
     */
    boolean isAppRunning();

    /**
     * This method indicates whether the meter could be placed into HIRED mode from FOR HIRE mode.
     * The meter is ready when a valid GPS fix or OBD connection has been established.
     * @return true if the meter is ready and false otherwise.
     */
    boolean isMeterReady();

    /**
     * This method returns the active preset's name.
     * @return the active preset's name.
     */
    String getPreset();

    /**
     * This method returns the active tariff identifier.
     * @return the active tariff identifier.
     */
    String getTariff();

    /**
     * This method returns the active preset's currency symbol.
     * @return the active preset's currency symbol.
     */
    String getCurrency();

    /**
     * This method returns the extras (irrespective of whether the displayed fare is totalled or not).
     * @return the extras.
     */
    double getExtras();

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
    double getFare();

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
    int getState();

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
    boolean setState(int state);

    /**
     * This method sets the active tariff identifier.
     * @param tariff The tariff to set.
     * @return true if the tariff has been successfully set and false otherwise.
     */
    boolean setTariff(String tariff);

    /**
     * This method sets the extras.
     * @param extras The extras to set.
     * @return true if the extras have been successfully set and false otherwise.
     */
    boolean setExtras(double extras);

    /**
     * This method toggles the displayed fare/total fare when the meter is in STATE_STOPPED.
     * @return true if the fare was successfully toggled and false otherwise.
     */
    boolean toggleTotalFare();

    /**
     * This method indicates whether the displayed fare is totalled.
     * @return true if the displayed fare is the total fare.
     */
    boolean isTotalFare();

    /**
     * This method indicates whether the Taximeter API is enabled.
     * This method does not require the app to be running.
     * @return true if the API is enabled, false otherwise.
     */
    boolean isAPIEnabled();

    /**
     * This method increments the extras (pass a negative value to decrement the extras).
     * @param extras The extras delta to increment.
     * @return true if the extras have been successfully changed and false otherwise.
     * @since 1.0.1
     */
    boolean incExtras(double extras);

    /**
     * This method asks Taximeter to shutdown.
     * Taximeter will only shutdown if the meter is in STATE_FOR_HIRE.
     * @return true if Taximeter can shutdown, false otherwise.
     * @since 1.0.1
     */
    boolean shutdown();

    /**
     * This method returns the OBD vehicle speed in km/h.
     * This returns the standard OBD-II PID (On-Board Diagnostics Parameter ID) for Vehicle Speed (Mode 01 PID 0D).
     * @return the speed. Returns -1 if data is not available.
     * @since 1.0.1
     */
    int getOBDSpeed();

    /**
     * This method returns the OBD fuel level input as a percentage.
     * This returns the standard OBD-II PID (On-Board Diagnostics Parameter ID) for Fuel Level Input (Mode 01 PID 2F).
     * @return the fuel level as a percent (0% is full, 100% is empty).  Returns -1.0f if data is not available.
     * @since 1.0.1
     */
    float getOBDFuelLevel();

    /**
     * This method returns the displayed fare (this is dependent upon whether the fare is totalled or not see {@link #isTotalFare()}).
     * @return the displayed fare.
     * @since 1.0.2
     */
    double getDisplayedFare();

    /**
     * This method returns the sales tax (irrespective of whether the displayed fare is totalled or not).
     * @return the tax.
     * @since 1.0.2
     */
    double getTax();

    /**
     * This method returns the total chargeable fare (irrespective of whether the displayed fare is totalled or not).
     * The total fare is the equivalent of getFare() + getExtras() + getTax().
     * @return the total fare.
     * @since 1.0.2
     */
    double getTotalFare();

    /**
     * Set license key.
     * This method does not require the app to be running.
     * @since 1.0.3
     */
    void setLicenseKey(String key);

    /**
     * Set the license key for POS Print.
     * This method does not require the app to be running.
     * @since 1.0.3
     */
    void setPOSPrintLicenseKey(String key);

    /**
     * This method returns the API version.
     * This method does not require the app to be running.
     * @return the API version.
     * @since 1.0.4
     */
    String getAPIVersion();

    /**
     * This method starts Taximeter and then brings the application with the specified package name
     * to the front of the activity stack.
     * This method does not require Taximeter to be running.
     * @return true is Taximeter was successfully started, false otherwise.
     * @since 1.0.4
     */
    boolean startup(String packageName);

    /**
     * This method returns the meter's serial number.
     * @return the meter's serial number.
     * @since 1.0.4
     */
    String getSerialNumber();

    /**
     * This method indicates whether the settings are locked.
     * @return true if the settings are locked and false otherwise.
     * @since 1.0.4
     */
    boolean areSettingsLocked();

    /**
     * This method locks the settings.
     * Notes: If the preset and/or settings are locked, only the Android user that 
     * locked them can perform this operation.
     * @return true if the settings have been successfully locked and false otherwise.
     * @since 1.0.4
     */
    boolean lockSettings();

    /**
     * This method unlocks the settings.
     * Notes: If the preset and/or settings are locked, only the Android user that 
     * locked them can perform this operation.
     * @return true if the settings have been successfully unlocked and false otherwise.
     * @since 1.0.4
     */
    boolean unlockSettings();

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
    boolean setPreset(String name);

    /**
     * Get the version of the active preset.
     * @return the version of the active preset.
     * @since 1.0.4
     */
    String getPresetVersion();

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
    boolean downloadPresets(String url);

    /**
     * Send Taximeter to the back of the activity stack.
     * @since 1.0.4
     */
    void sendToBack();

    /**
     * Bring Taximeter to the front of the activity stack.
     * @since 1.0.4
     */
    void bringToFront();

    /**
     * This method indicates whether the OBD interface is enabled.
     * @return true if OBD is enabled, false otherwise.
     * @since 1.0.5
     */
    boolean isOBDEnabled();

    /**
     * This method attempts to enable the OBD interface.
     * Notes: Equivalent to checking Menu > Settings > OBD settings > Enable OBD.
     * @since 1.0.5
     */
    void enableOBD();

    /**
     * This method indicates whether the preset is locked.
     * @return true if the preset is locked and false otherwise.
     * @since 1.0.6
     */
    boolean isPresetLocked();

    /**
     * This method locks the current preset.
     * Note: Inoperative if the preset and/or settings have been locked by another
     * Android user.
     * @return true if the preset has been successfully locked and false otherwise.
     * @since 1.0.6
     */
    boolean lockPreset();

    /**
     * This method unlocks the preset.
     * Notes: If the preset is locked, only the Android user that locked
     * the preset can perform this operation.
     * @return true if the preset has been successfully unlocked and false otherwise.
     * @since 1.0.6
     */
    boolean unlockPreset();

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
    boolean setPresetXML(String xml);

    /**
     * This method returns the preset XML definition for the active preset.
     * @return the active preset's XML definition wrapped in a top level
     * "taximeters" element.
     * @since 1.0.6
     */
    String getPresetXML();

    /**
     * Gets the last error.
     * (Added to help resolve XML parse errors when setPresetXML(String) returns false).
     * @return the last error.
     * @since 1.0.6
     */
    String getLastError();

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
    boolean resetPreset();

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
    boolean restorePreferences();

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
    double getDiscount();

    /**
     * This method returns the active preset's ISO 4217 currency code.
     * @return the active preset's ISO 4217 currency code.
     * @since 1.0.8
     */
    String getCurrencyCode();

    /**
     * This method returns the active tariff description.
     * @return the active tariff description.
     * @since 1.0.9
     */
    String getTariffDescription();

    /**
     * This method returns the tip.
     * @return the tip.
     * @since 1.0.9
     */
    double getTip();

    /**
     * This method returns the preset download URL.
     * @return the preset download URL.
     * @since 1.1.1
     */
    String getDownloadURL();
}