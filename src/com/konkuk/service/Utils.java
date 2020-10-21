package com.konkuk.service;

import com.konkuk.asset.Settings;

public class Utils {
    public static void debug(String msg) {
        if(Settings.DEBUG) System.out.println("Debug: " + msg);
    }

    public static void exit(String msg) {
        debug("강제 종료");
        debug(msg);
        System.exit(0);
    }
}
