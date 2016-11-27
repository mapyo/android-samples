package com.mapyo.limitnumberimagepicker;

import android.util.Log;

import java.util.regex.Pattern;

public class L {
    public static void d(String msg) {
        if (!BuildConfig.DEBUG) return;
        Log.d(getTag(), msg);
    }

    private static String getTag() {
        final StackTraceElement trace = Thread.currentThread().getStackTrace()[4];
        final String cla = trace.getClassName();
        Pattern pattern = Pattern.compile("[\\.]+");
        final String[] splitedStr = pattern.split(cla);
        final String simpleClass = splitedStr[splitedStr.length - 1];
        final String mthd = trace.getMethodName();
        final int line = trace.getLineNumber();
        return simpleClass + "#" + mthd + ":" + line;
    }
}

