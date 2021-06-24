package com.bcaf.bus.utils;

import android.content.Context;
import android.os.Build;

public class MyUtils {

    private Context context;

    public MyUtils(Context ctx) {
        this.context = ctx;
    }

    //Checking Emulator
    public boolean isEmulator(){
        boolean result = Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.HARDWARE.equals("vbox86")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MODEL.toLowerCase().contains("droid4x")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_google_phone_x86")
                || Build.PRODUCT.contains("vbox86p")
                || android.os.Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.MANUFACTURER.contains("CMDC")
                || Build.MANUFACTURER.contains("BlueStacks")
                || Build.BOARD.toLowerCase().contains("nox")
                || Build.BOOTLOADER.toLowerCase().contains("nox")
                || Build.HARDWARE.toLowerCase().contains("nox")
                || Build.PRODUCT.toLowerCase().contains("nox")
                || Build.SERIAL.toLowerCase().contains("nox");

        if (result) return true;
        result |= Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic");
        if (result) return true;
        result |= "google_sdk".equals(Build.PRODUCT);
        return result;

    }
}
