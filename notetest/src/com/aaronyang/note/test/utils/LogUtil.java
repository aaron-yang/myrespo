package com.aaronyang.note.test.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.util.Log;

import com.aaronyang.note.test.ConfigrationFile;

public final class LogUtil {
    public static final String TAG = "[AutoTest]";
    private static final Logger mLogger = new Logger();

    private static class Logger {
        private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        private static final String LOGS_FOLDER = "logs";
        private static final String LOG_FILE = "Log_file.log";
        private static final Object syncObject = new Object();

        private String fileName;

        Logger() {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                try {
                    File path = Environment.getExternalStorageDirectory();
                    File logPath = new File(path +"/"+ConfigrationFile.LOG_FILE_FOLER+ "/" + LOGS_FOLDER);
                    logPath.mkdirs();

                    fileName = logPath.getAbsolutePath() + "/" + LOG_FILE;
                    File file = new File(fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    Log.e(TAG, " exception in Logger initialization ", e);
                }
            }
        }

        void log(String msg) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                synchronized (syncObject) {
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName), true));
                        bw.write(formatter.format(new Date(System.currentTimeMillis())));
                        bw.write(" ");
                        bw.write(msg);
                        bw.newLine();
                        bw.flush();
                        bw.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }

    public static void i(String itag, String imsg) {
        Log.i(itag, imsg);
        logMsg(LogLevel.INFO.toString() + " " + itag + " " + imsg);
    }

    public static void i(String itag, String imsg, Throwable ith) {
        Log.i(itag, imsg, ith);
        logMsg(LogLevel.INFO.toString() + " " + itag + " " + imsg + " " + android.util.Log.getStackTraceString(ith));
    }

    public static void v(String itag, String imsg) {
        Log.v(itag, imsg);
        logMsg(LogLevel.VERBOSE.toString() + " " + itag + " " + imsg);
    }

    public static void v(String itag, String imsg, Throwable ith) {
        Log.v(itag, imsg, ith);
        logMsg(LogLevel.VERBOSE.toString() + " " + itag + " " + imsg + " " + android.util.Log.getStackTraceString(ith));
    }

    public static void d(String itag, String imsg) {
        Log.d(itag, imsg);
        logMsg(LogLevel.DEBUG.toString() + " " + itag + " " + imsg);
    }

    public static void d(String itag, String imsg, Throwable ith) {
        Log.d(itag, imsg, ith);
        logMsg(LogLevel.DEBUG.toString() + " " + itag + " " + imsg + " " + android.util.Log.getStackTraceString(ith));
    }

    public static void w(String itag, String imsg) {
        Log.w(itag, imsg);
        logMsg(LogLevel.WARNING.toString() + " " + itag + " " + imsg);
    }

    public static void w(String itag, Throwable ith) {
        Log.w(itag, ith);
        logMsg(LogLevel.WARNING.toString() + " " + itag + " " + android.util.Log.getStackTraceString(ith));
    }

    public static void w(String itag, String imsg, Throwable ith) {
        Log.w(itag, imsg, ith);
        logMsg(LogLevel.WARNING.toString() + " " + itag + " " + imsg + " " + android.util.Log.getStackTraceString(ith));
    }

    public static void e(String itag, String imsg) {
        Log.e(itag, imsg);
        logMsg(LogLevel.ERROR.toString() + " " + itag + " " + imsg);
    }

    public static void e(String itag, String imsg, Throwable ith) {
        Log.e(itag, imsg, ith);
        logMsg(LogLevel.ERROR.toString() + " " + itag + " " + imsg + " " + android.util.Log.getStackTraceString(ith));
    }

    private static void logMsg(String msg) {
        mLogger.log(msg);
    }

    private static class LogLevel {
        static final LogLevel INFO = new LogLevel("INFO");
        static final LogLevel VERBOSE = new LogLevel("VERBOSE");
        static final LogLevel DEBUG = new LogLevel("DEBUG");
        static final LogLevel WARNING = new LogLevel("WARNING");
        static final LogLevel ERROR = new LogLevel("ERROR");

        private String name;

        protected LogLevel(String n) {
            name = n;
        }

        @Override
        public String toString() {
            return String.format("%7s ", name);
        }
    }
}
