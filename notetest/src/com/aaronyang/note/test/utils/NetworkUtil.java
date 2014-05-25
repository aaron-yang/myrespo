package com.aaronyang.note.test.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Class is used for working with network (switch on/off Airplane mode, mobile Network, Wifi)
 */
public class NetworkUtil {

    private static final String DISABLE_DATA_CONNECTION_METHOD = "disableDataConnectivity";
    private static final String ENABLE_DATA_CONNECTION_METHOD = "enableDataConnectivity";
    private static final String I_TELEPHONY_METHOD = "getITelephony";
    private static final String TAG = "NetworkSwitcher";

    /**
     * Switch off AirplaneMode, mobile network and wifi
     * 
     * @param context
     * @throws Exception
     */
    public static void setAllPossibleNetworksOn(Context context) throws Exception {
        if (isAirplaneModeOn(context)) {
            boolean success = setAirplaneModeOff(context);
            if (!success) {
                throw new IllegalStateException("AirplaneMode was NOT turned ON");
            }
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        byte numberOfErrors = 0;
        try {
            setNetworkOn(context);
        } catch (Exception e) {
            numberOfErrors++;
        }
        try {
            setWifiOn(context);
        } catch (Exception e) {
            numberOfErrors++;
        }
        if (numberOfErrors == 2) {
            throw new Exception("Wifi and Network are not turned on");
        }
    }

    /**
     * Switch off Wifi network
     * 
     * @param context
     *            - Context
     * @return true if Wifi was switched off successfully
     * @throws SettingNotFoundException
     *             if Wifi settings were not found
     */
    public static boolean setWifiOff(Context context) throws SettingNotFoundException {
        WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMng == null) {
            return false;
        }
        if (!wifiMng.isWifiEnabled()) {
            return true;
        }
        Settings.System.putInt(context.getContentResolver(), Settings.Secure.WIFI_ON, 0);
        try {
            wifiMng.setWifiEnabled(false);
            Settings.System.putInt(context.getContentResolver(), Settings.Secure.WIFI_ON, 0);
            return !wifiMng.isWifiEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Switch On Wifi network
     * 
     * @param context
     *            - Context
     * @return true if Wifi successfully switched on
     * @throws SettingNotFoundException
     *             if Wifi settings were not found
     */
    public static boolean setWifiOn(Context context) throws SettingNotFoundException {
        WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMng == null) {
            return false;
        }
        if (wifiMng.isWifiEnabled()) {
            return true;
        }
        Settings.System.putInt(context.getContentResolver(), Settings.Secure.WIFI_ON, 1);
        try {
            wifiMng.reassociate();
            wifiMng.reconnect();
            wifiMng.setWifiEnabled(true);
            Settings.System.putInt(context.getContentResolver(), Settings.Secure.WIFI_ON, 1);
            Thread.sleep(5000);
            return wifiMng.isWifiEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify is Wifi network on now
     * 
     * @param context
     *            - Context
     * @return true if Wifi is switched on
     * @throws SettingNotFoundException
     */
    public static boolean isWifiOn(Context context) throws SettingNotFoundException {
        int value = Settings.System.getInt(context.getContentResolver(), Settings.Secure.WIFI_ON);
        return value == 0 ? false : true;
    }

    /**
     * @param context
     *            - Context
     * @return true if Network switched off successfully
     * @throws SettingNotFoundException
     */
    public static boolean setNetworkOff(Context context) throws SettingNotFoundException {
        boolean isEnabled = true;
        if (!isNetworkOn(context)) {
            return true;
        }
        isEnabled = changeNetworkState(context, DISABLE_DATA_CONNECTION_METHOD);
        return !isEnabled;
    }

    /**
     * @param context
     *            - Context
     * @return true if Network switched on successfully
     * @throws SettingNotFoundException
     */
    public static boolean setNetworkOn(Context context) throws SettingNotFoundException {
        boolean isEnabled = false;
        if (isNetworkOn(context)) {
            return true;
        }
        isEnabled = changeNetworkState(context, ENABLE_DATA_CONNECTION_METHOD);
        return isEnabled;
    }

    /**
     * @param context
     *            - Context
     * @return true if it is mobile network connection
     */
    public static boolean isNetworkOn(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED ? true : false;
    }

    /**
     * 
     * @param context
     * @return true if AirplaneMode is switched successfully
     * @throws SettingNotFoundException
     */
    public static boolean setAirplaneModeOn(Context context) throws SettingNotFoundException {
        if (isAirplaneModeOn(context)) {
            return true;
        }
        boolean success = Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 1);
        if (!success) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", true);
        context.sendOrderedBroadcast(intent, null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        return success;
    }

    /**
     * @param context
     * @return true if Airplane mode is on
     * @throws SettingNotFoundException
     */
    public static boolean isAirplaneModeOn(Context context) throws SettingNotFoundException {
        int value = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON);
        return value == 0 ? false : true;
    }

    /**
     * 
     * @param context
     * @return true if airplane mode is switched successfully
     * @throws SettingNotFoundException
     */
    public static boolean setAirplaneModeOff(Context context) throws SettingNotFoundException {
        if (!isAirplaneModeOn(context)) {
            return true;
        }
        boolean success = Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
        if (!success) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", false);
        context.sendOrderedBroadcast(intent, null);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        return success;
    }

    private static boolean changeNetworkState(Context context, String method) throws SettingNotFoundException {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method dataConnSwitchmethod = null;
            Class<?> telephonyManagerClass;
            Object ITelephonyStub;
            Class<?> ITelephonyClass;

            telephonyManagerClass = Class.forName(telephonyManager.getClass().getName());
            Method getITelephonyMethod = telephonyManagerClass.getDeclaredMethod(I_TELEPHONY_METHOD);
            getITelephonyMethod.setAccessible(true);
            ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
            ITelephonyClass = Class.forName(ITelephonyStub.getClass().getName());
            Log.i(TAG, "changeNetworkState(): " + "ITelephonyStub = " + ITelephonyStub.toString());
            dataConnSwitchmethod = ITelephonyClass.getDeclaredMethod(method);
            Log.i(TAG, "changeNetworkState(): " + "ITelephonyClass = " + ITelephonyClass.getName());
            Log.i(TAG, "changeNetworkState(): " + "dataConnSwitchmethod = " + dataConnSwitchmethod.getName());
            dataConnSwitchmethod.setAccessible(true);
            dataConnSwitchmethod.invoke(ITelephonyStub);
            Thread.sleep(5000);
        } catch (Exception e) {
            Log.i(TAG, "changeNetworkState(): Exception: " + e);
            if (method == DISABLE_DATA_CONNECTION_METHOD) {
                setAirplaneModeOn(context);
            }
        }
        return telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED;
    }

    /**
     * Switch on all types of connection
     * 
     * @param context
     */
    public static void setAirplaneModeOffAndNetworkOn(Context context) {
        try {
            if (isAirplaneModeOn(context)) {
                setAirplaneModeOff(context);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.e("[TestRC]", "setAirplaneModeOffAndNetworkOn() : " + e);
                }
            }
        } catch (Exception e) {
            // do nothing
        }
        try {
            setNetworkOn(context);
        } catch (Exception e) {
            // do nothing
        }
        try {
            setWifiOn(context);
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * Switched Off all types of connection
     * 
     * @param context
     * @throws NetworkSwitchException
     * @throws SettingNotFoundException
     */
    public static void setAllPossibleNetworksOff(Context context) throws SettingNotFoundException {
        setWifiOff(context);
        try {
            waitForWifiOff(context);
        } catch (Exception e1) {
            Log.e(TAG, e1.getMessage());
        
        setNetworkOff(context);
        try {
            Thread.sleep(6000);
        } catch (Exception e) {
            // ignore
        }
        }
    }

    private static void waitForWifiOff(Context context) throws Exception {
        boolean isWifiOff = false;
        long startTime = System.currentTimeMillis();
        do {
            Thread.sleep(500);
            isWifiOff = !isWifiOn(context);
        } while (!isWifiOff && (System.currentTimeMillis() - startTime < 10000));
        if (!isWifiOff) {
            throw new Exception("Wifi is not turned off");
        }
    }

    public static void waitForWifiOrNetworkOn(Context context) throws Exception {
        boolean isWifiOrNetworkOn = false;
        long startTime = System.currentTimeMillis();
        do {
            Thread.sleep(500);
            isWifiOrNetworkOn = isWifiOn(context) || isNetworkOn(context);
        } while (!isWifiOrNetworkOn
                && (System.currentTimeMillis() - startTime < 10000));
        if (!isWifiOrNetworkOn) {
            throw new Exception("Wifi and Network is not turned on");
        }
    }

}
